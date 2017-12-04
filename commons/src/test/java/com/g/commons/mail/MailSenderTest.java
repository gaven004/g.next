package com.g.commons.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/mail-test-context.xml")
public class MailSenderTest {
    @Autowired
    @Qualifier("testMailSender")
    private MailSender testMailSender;

    @Test
    public void test() {
        testMailSender.sendHTMLMail("oy@52tt.com", "Hello mail", "It's a test.");
    }

}
