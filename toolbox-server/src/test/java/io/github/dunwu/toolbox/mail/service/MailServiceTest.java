package io.github.dunwu.toolbox.mail.service;

import io.github.dunwu.toolbox.mail.dto.MailDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testSendMail() {
        MailDTO mailDTO = new MailDTO();
        mailDTO.setFrom("forbreak@163.com")
               .setTo(new String[] {"forbreak@163.com"})
               .setSubject("test")
               .setText("hello world")
               .setHtml(true);
        mailService.send(mailDTO);
    }
}
