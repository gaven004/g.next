package com.g;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.g.commons.utils.MailSender;

@SpringBootTest(properties = "spring.main.lazy-initialization=true")
class MailSenderTest {
    @Autowired
    MailSender mailSender;

    @Test
    void sendMail() {
        assertNotNull(mailSender);
        mailSender.sendHTMLMail("13808821842@139.com", "Hello mail", "It's a test.");
    }
}
