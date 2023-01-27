package com.g.commons.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.g.config.SystemConfig;
import com.g.sys.att.model.Module;
import com.g.sys.att.service.FileUploader;

@Controller
public class UploadController {
    static long maxFileSize = 10 * 1024 * 1024; // 10M
    static String basePath = SystemConfig.getProperty("upload.basePath");

    static String browseUrl = "/images";

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

    public UploadController() {
        uploader = new FileUploader(maxFileSize, basePath, browseUrl, supportImageTypes);
    }

    @PostMapping(value = "/upload/{module}")
    @ResponseBody
    public CKEditorUploadedResponse upload(@RequestParam(value = "upload", required = false) MultipartFile file,
                                           @PathVariable Module module) throws IOException {
        CKEditorUploadedResponse response = new CKEditorUploadedResponse();
        try {
            String filename = FileUploader.getFilename(uploader.uploadAndGetPath(file, module, null, true));
            String browseUrl = UploadController.browseUrl + "/" + module + "/" + URLEncoder.encode(filename, "UTF-8");
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

    @GetMapping("images/{module}/{filename}")
    public void getImage(HttpServletResponse response,
                         @PathVariable Module module,
                         @PathVariable String filename) throws IOException {
        Path source = uploader.getPath(module).resolve(URLDecoder.decode(filename, "UTF-8"));
        if (!Files.exists(source)) {
            throw new RuntimeException(String.format("找不到文件[%s]", source));
        }
        ResponseUtil.write(response, source);
    }
}
