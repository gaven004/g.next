package com.g.commons.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.g.commons.utils.LocalDateTimeUtil;

/**
 * @author zhongsh
 * @version 2017/1/19
 */
public class ResponseUtil {
    /**
     * Common MIME types
     * https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types
     * https://www.iana.org/assignments/media-types/media-types.xhtml
     */
    static final Map<String, String> contentTypeMap = Map.of(
            "pdf", "application/pdf",
            "xls", "application/vnd.ms-excel",
            "xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "doc", "application/msword",
            "docx", "application/application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "zip", "application/zip");

    /**
     * 根据不同文件类型，从HttpServletResponse获取输出流，从而将文件以流的方式，输出到客户端
     *
     * @param request
     * @param response
     * @param basename 文件名，不带后缀扩展名
     * @param extension 文件的扩展名
     * @return
     * @throws IOException
     */
    public static OutputStream getOutputStream(HttpServletRequest request, HttpServletResponse response,
                                               String basename, String extension) throws IOException {
        final String contentType = contentTypeMap.get(extension);
        if (null == contentType) {
            throw new RuntimeException("Unsupported file type");
        }

        basename = getFileName(request, basename);
        response.setHeader("content-disposition", "attachment;filename=" + basename + "." + extension);
        response.setContentType(contentType);
        return response.getOutputStream();
    }

    /**
     * HttpServletResponse转成Excel文件输出流
     *
     * @param response HttpServletResponse
     * @param basename excel文件名
     * @return OutputStream
     * @throws IOException
     */
    public static OutputStream getExcelOutputStream(HttpServletRequest request, HttpServletResponse response,
                                                    String basename) throws IOException {
        return getOutputStream(request, response, basename, "xlsx");
    }

    /**
     * HttpServletResponse转成Zip文件输出流
     *
     * @param response HttpServletResponse
     * @param basename zip文件名
     * @return OutputStream
     * @throws IOException
     */
    public static OutputStream getZipOutputStream(HttpServletRequest request, HttpServletResponse response,
                                                  String basename) throws IOException {
        return getOutputStream(request, response, basename, "zip");
    }

    private static String getFileName(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        /*
         * 处理中文文件名，需要根据浏览器做适配，否则会乱码
         *
         * 不同浏览器的 User-Agent
         * FIREFOX：MOZILLA/5.0 (WINDOWS NT 6.1; WIN64; X64; RV:53.0) GECKO/20100101 FIREFOX/53.0
         * Edge：Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393
         * Chrome：Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36
         * IE：Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko
         */
        if (request.getHeader("User-Agent").toUpperCase().indexOf("FIREFOX") > 0) {    // FIREFOX浏览器
            fileName = new String(fileName.getBytes(), "ISO-8859-1");
        } else {
            // Edge、IE需要使用URL Encoder，Chrome在WINDOW下可以使用自动适配，LINUX环境下需要URL Encoder
            fileName = URLEncoder.encode(fileName, "UTF-8");
        }
        fileName = fileName + "." + LocalDateTimeUtil.format(LocalDateTime.now(), "yyyyMMddHHmmss");
        return fileName;
    }
}
