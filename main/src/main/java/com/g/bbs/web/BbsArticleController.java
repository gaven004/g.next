package com.g.bbs.web;

/**
 * Controller for domain model class BbsArticle.
 *
 * @author Hibernate Tools
 * @see com.g.bbs.web.BbsArticle
 */

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.g.bbs.model.BbsArticle;
import com.g.bbs.model.BbsArticleStatus;
import com.g.bbs.persistence.BbsArticleRepository;
import com.g.bbs.service.BbsArticleService;
import com.g.commons.model.ApiResponse;
import com.g.commons.web.GenericController;
import com.g.commons.web.ResponseUtil;
import com.g.config.SystemConfig;
import com.g.sys.att.model.Module;
import com.g.sys.att.service.FileUploader;
import com.g.sys.sec.model.SecurityUser;
import com.g.sys.sec.web.WebSecurityHelper;

@RestController
@RequestMapping("bbs/article")
public class BbsArticleController
        extends GenericController<BbsArticleService, BbsArticleRepository, BbsArticle, Long> {
    static long maxFileSize = 1024 * 1024; // 1M
    static String basePath = SystemConfig.getProperty("upload.basePath");

    static String browseUrl = "/bbs/article/images";

    /*
     * 图像文件类型，参考 https://developer.mozilla.org/zh-CN/docs/Web/Media/Formats/Image_types
     */
    static Set<String> supportImageTypes = Set.of("apng", "avif", "bmp", "gif", "ico", "cur", "jpg", "jpeg", "jfif", "pjpeg", "pjp", "png", "svg", "tif", "tiff", "webp");

    static class UploadedError {
        private String message;

        public UploadedError(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    static class CKEditorUploadedResponse {
        private int uploaded;
        private String fileName;
        private String url;
        private UploadedError error;

        public int getUploaded() {
            return uploaded;
        }

        public void setUploaded(int uploaded) {
            this.uploaded = uploaded;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public UploadedError getError() {
            return error;
        }

        public void setError(UploadedError error) {
            this.error = error;
        }
    }

    private final FileUploader uploader;

    public BbsArticleController() {
        uploader = new FileUploader(maxFileSize, basePath, browseUrl, supportImageTypes);
    }

    @PostMapping(value = "/upload")
    public CKEditorUploadedResponse upload(@RequestParam(value = "upload", required = false) MultipartFile file) throws IOException {
        CKEditorUploadedResponse response = new CKEditorUploadedResponse();
        try {
            String filename = FileUploader.getFilename(uploader.uploadAndGetPath(file, Module.BBS_IMG, null, true));
            String browseUrl = BbsArticleController.browseUrl + "/" + URLEncoder.encode(filename, "UTF-8");
            response.setUploaded(1);
            response.setFileName(URLEncoder.encode(filename, "UTF-8"));
            response.setUrl(browseUrl);
            return response;
        } catch (Exception e) {
            response.setUploaded(0);
            response.setError(new UploadedError(StringEscapeUtils.escapeJava(e.getMessage())));
            return response;
        }
    }

    @GetMapping("images/{filename}")
    public void getImage(HttpServletResponse response, @PathVariable String filename) throws IOException {
        Path source = uploader.getPath(Module.BBS_IMG).resolve(URLDecoder.decode(filename, "UTF-8"));
        if (!Files.exists(source)) {
            throw new RuntimeException(String.format("找不到文件[%s]", source));
        }
        ResponseUtil.write(response, source);
    }

    @Override
    @PostMapping
    public ApiResponse<BbsArticle> save(@Valid BbsArticle entity) {
        SecurityUser currentUser = WebSecurityHelper.getAuthUser();
        entity.setOperator(currentUser.getId());
        entity.setAuthor(currentUser.getNickname());
        return ApiResponse.success(service.save(entity));
    }

    @PatchMapping("/{id}/publish")
    public ApiResponse<BbsArticle> publish(@Valid BbsArticle entity) {
        SecurityUser currentUser = WebSecurityHelper.getAuthUser();
        entity.setOperator(currentUser.getId());
        entity.setAuthor(currentUser.getNickname());
        entity.setStatus(BbsArticleStatus.PUBLISH.toString());
        return ApiResponse.success(service.update(entity));
    }

    @PostMapping("/{id}/publish")
    public ApiResponse<BbsArticle> publish(@PathVariable Long id) {
        BbsArticle entity = service.get(id);
        entity.setStatus(BbsArticleStatus.PUBLISH.toString());
        return ApiResponse.success(service.update(entity));
    }

    @PostMapping("/{id}/withdraw")
    public ApiResponse<BbsArticle> withdraw(@PathVariable Long id) {
        BbsArticle entity = service.get(id);
        entity.setStatus(BbsArticleStatus.WITHDRAWAL.toString());
        return ApiResponse.success(service.update(entity));
    }

    @Override
    @PutMapping("/{id}")
    public ApiResponse<BbsArticle> update(@Valid BbsArticle entity) {
        return ApiResponse.success(service.update(entity));
    }

    @Override
    @PatchMapping("/{id}")
    public ApiResponse<BbsArticle> patch(@Valid BbsArticle entity) {
        return ApiResponse.success(service.update(entity));
    }

}
