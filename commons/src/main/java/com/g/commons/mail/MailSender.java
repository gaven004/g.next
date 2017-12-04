package com.g.commons.mail;

import java.io.IOException;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;

import freemarker.template.TemplateException;

/**
 * 邮件发送工具类<br/>
 * 支持发送简单文本邮件或HTML邮件， 参数在SPRING配置文件中设定
 * 异步邮件，需要配置Spring Task
 *
 * @version 0.0.1, Gaven, 2015-2-12
 * @version 0.0.2, Gaven, 2017-11-29, 移除模板的支持，还原核心邮件功能
 */
public class MailSender {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(MailSender.class);

    private String from; // 一般需要与邮箱设定的用户相同，取决于服务器的限定

    private String subject; // 默认的邮件主题

    private JavaMailSender mailSender;

    public MailSender() {
    }

    public MailSender(String from, JavaMailSender mailSender) throws IOException {
        this.from = from;
        this.mailSender = mailSender;
    }

    public MailSender(String from, String subject, JavaMailSender mailSender) throws IOException {
        this.from = from;
        this.subject = subject;
        this.mailSender = mailSender;
    }

    /**
     * 发送简单的文本邮件
     *
     * @param to
     *            收件人
     * @param subject
     *            主题
     * @param msg
     *            邮件内容
     */
    public void sendMail(final String to, final String msg) {
        sendMail(from, to, subject, msg, false);
    }

    public void sendMail(final String to, final String subject, final String msg) {
        sendMail(from, to, subject, msg, false);
    }

    /**
     * 异步发送简单的文本邮件
     */
    @Async
    public void sendMailAsync(final String to, final String msg) {
        sendMail(to, msg);
    }

    @Async
    public void sendMailAsync(final String to, final String subject, final String msg) {
        sendMail(to, subject, msg);
    }

    /**
     * 按模板发送HTML格式邮件
     *
     * @param to
     *            收件人
     * @param subject
     *            主题
     * @param msg
     *            邮件内容
     * @throws TemplateException
     * @throws IOException
     */
    public void sendHTMLMail(final String to, final String msg) {
        sendMail(from, to, subject, msg, true);
    }

    public void sendHTMLMail(final String to, final String subject, final String msg) {
        sendMail(from, to, subject, msg, true);
    }

    /**
     * 异步按模板发送HTML格式邮件
     *
     * @throws TemplateException
     * @throws IOException
     */
    @Async
    public void sendHTMLMailAsync(final String to, final String msg) {
        sendHTMLMail(to, msg);
    }

    @Async
    public void sendHTMLMailAsync(final String to, final String subject, final String msg) {
        sendHTMLMail(to, subject, msg);
    }

    /**
     * 发送邮件
     *
     * @param mailSender
     *            Spring Java mail sender
     * @param from
     *            发件人
     * @param to
     *            收件人
     * @param subject
     *            主题
     * @param msg
     *            内容
     * @param isHtml
     *            是否以HTML格式发送
     */
    private void sendMail(final String from, final String to, final String subject, final String msg,
            final boolean isHtml) {
        if (logger.isInfoEnabled()) {
            logger.info("sendMail() - start, from={}, to={}, subject={}", from, to, subject); //$NON-NLS-1$
        }

        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
                message.setFrom(from);
                message.setTo(to);
                message.setSubject(subject);
                message.setText(msg, isHtml);
            }
        };

        try {
            mailSender.send(preparator);
        } catch (MailException e) {
            logger.warn("sendMail() - error", e);
            throw e;
        }

        if (logger.isInfoEnabled()) {
            logger.info("sendMail() - end"); //$NON-NLS-1$
        }
    }
}
