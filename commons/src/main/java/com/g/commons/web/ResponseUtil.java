package com.g.commons.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;

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
    static final Map<String, String> contentTypeMap = Map.ofEntries(
            Map.entry("apng", "image/apng"),
            Map.entry("avif", "image/avif"),
            Map.entry("bmp", "image/bmp"),
            Map.entry("gif", "image/gif"),
            Map.entry("ico", "image/x-icon"),
            Map.entry("cur", "image/x-icon"),
            Map.entry("jpg", "image/jpeg"),
            Map.entry("jpeg", "image/jpeg"),
            Map.entry("jfif", "image/jpeg"),
            Map.entry("pjpeg", "image/jpeg"),
            Map.entry("pjp", "image/jpeg"),
            Map.entry("png", "image/png"),
            Map.entry("svg", "image/svg+xml"),
            Map.entry("tif", "image/tiff"),
            Map.entry("tiff", "image/tiff"),
            Map.entry("webp", "image/webp"),
            Map.entry("pdf", "application/pdf"),
            Map.entry("xls", "application/vnd.ms-excel"),
            Map.entry("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
            Map.entry("doc", "application/msword"),
            Map.entry("docx", "application/application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
            Map.entry("zip", "application/zip"));

    /**
     * 直接输出文件到response
     *
     * @param response
     * @param source
     * @throws IOException
     */
    public static void write(HttpServletResponse response, Path source) throws IOException {
        String ext = FilenameUtils.getExtension(source.toString()).toLowerCase();
        long size = Files.size(source);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(contentTypeMap.get(ext));
        response.setContentLength((int) size);

        Files.copy(source, response.getOutputStream());
    }

    /**
     * 以附件的方式输出文件到response
     *
     * @param request
     * @param response
     * @param source
     * @throws IOException
     */
    public static void write(HttpServletRequest request, HttpServletResponse response, Path source) throws IOException {

        String filename = source.toString();
        String ext = FilenameUtils.getExtension(filename).toLowerCase();
        String basename = FilenameUtils.getBaseName(filename);

        String contentType = contentTypeMap.get(ext);
        if (null == contentType) {
            throw new RuntimeException("Unsupported file type");
        }

        basename = getFileName(request, basename);
        response.setHeader("content-disposition", "attachment;filename=" + basename + "." + ext);
        response.setContentType(contentType);

        Files.copy(source, response.getOutputStream());
    }

    /**
     * 根据不同文件类型，从HttpServletResponse获取输出流，从而将文件以流的方式，输出到客户端
     *
     * @param request
     * @param response
     * @param basename  文件名，不带后缀扩展名
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
