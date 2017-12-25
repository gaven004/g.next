package com.g.commons.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.g.commons.web.UploadFileUtil;


@Controller
@RequestMapping(value = "/upload")
public class UploadController {
    @Autowired
    @Qualifier("articleImgUploader")
    UploadFileUtil articleImgUploader;

    @RequestMapping(value = "/mc/article", method = RequestMethod.POST)
    @ResponseBody
    public String ckEditorUpload(@RequestParam(value = "upload", required = false) MultipartFile file,
                                 Integer CKEditorFuncNum) {
        try {
            String browseUrl = articleImgUploader.upload(file);
            return buildCKResponse(CKEditorFuncNum, browseUrl, true);
        } catch (Exception e) {
            return buildCKResponse(CKEditorFuncNum, e.getMessage(), false);
        }
    }

    private String buildCKResponse(Integer CKEditorFuncNum, String msg, boolean success) {
        StringBuffer buff = new StringBuffer(1024);

        buff.append("<script type='text/javascript'>");

        if (success) {
            try {
                buff.append("var imgUrl = decodeURIComponent('").append(URLEncoder.encode(msg, "UTF-8")).append("');");
            } catch (UnsupportedEncodingException e) {
            }
            buff.append("window.parent.CKEDITOR.tools.callFunction(");
            buff.append("'").append(CKEditorFuncNum).append("', ");
            buff.append("imgUrl, ''");
            buff.append(");");
        } else {
            buff.append("window.parent.CKEDITOR.tools.callFunction(");
            buff.append("'").append(CKEditorFuncNum).append("', ");
            buff.append("'', '").append(StringEscapeUtils.escapeJava(msg)).append("'");
            buff.append(");");
        }

        buff.append("</script>");
        return buff.toString();
    }
}
