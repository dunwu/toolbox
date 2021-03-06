package io.github.dunwu.toolbox.mail.service.impl;

import io.github.dunwu.toolbox.mail.dto.MailDTO;
import io.github.dunwu.toolbox.mail.service.MailService;
import io.github.dunwu.util.mapper.BeanMapper;
import io.github.dunwu.web.config.DunwuMailExtProperties;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2019-01-09
 */
@Service
public class MailServiceImpl implements MailService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final JavaMailSender javaMailSender;
    private final ExecutorService mailExecutorService;
    private final MailProperties mailProperties;
    private final DunwuMailExtProperties mailExtProperties;

    public MailServiceImpl(JavaMailSender javaMailSender,
                           @Qualifier("mailExecutorService") ExecutorService mailExecutorService,
                           MailProperties mailProperties, DunwuMailExtProperties mailExtProperties) {
        this.javaMailSender = javaMailSender;
        this.mailExecutorService = mailExecutorService;
        this.mailProperties = mailProperties;
        this.mailExtProperties = mailExtProperties;
    }

    @Override
    public void send(MailDTO mailDTO) {
        if (mailDTO.getHtml()) {
            mailExecutorService.execute(() -> sendMimeMessage(mailDTO));
        } else {
            mailExecutorService.execute(() -> sendSimpleMessage(mailDTO));
        }
    }

    @Override
    public void sendBatch(List<MailDTO> mailDTOS, boolean html) {
        if (html) {
            mailExecutorService.execute(() -> sendMimeMessage(mailDTOS));
        } else {
            mailExecutorService.execute(() -> sendSimpleMessage(mailDTOS));
        }
    }

    private void sendSimpleMessage(MailDTO mailDTO) {
        SimpleMailMessage simpleMailMessage = BeanMapper.map(mailDTO, SimpleMailMessage.class);
        if (StringUtils.isBlank(mailDTO.getFrom())) {
            simpleMailMessage.setFrom(mailExtProperties.getFrom());
        }

        try {
            javaMailSender.send(simpleMailMessage);
            if (log.isDebugEnabled()) {
                log.debug("发送 SIMPLE 邮件成功");
            }
        } catch (MailException e) {
            log.error("发送 SIMPLE 邮件失败", e);
        }
    }

    private void sendSimpleMessage(List<MailDTO> mailDTOS) {
        if (CollectionUtils.isEmpty(mailDTOS)) {
            return;
        }

        List<SimpleMailMessage> simpleMailMessages = BeanMapper.mapList(mailDTOS, SimpleMailMessage.class);
        for (SimpleMailMessage simpleMailMessage : simpleMailMessages) {
            if (StringUtils.isBlank(simpleMailMessage.getFrom())) {
                simpleMailMessage.setFrom(mailExtProperties.getFrom());
            }
        }

        try {
            javaMailSender.send(simpleMailMessages.toArray(new SimpleMailMessage[] {}));
            if (log.isDebugEnabled()) {
                log.debug("批量发送 SIMPLE 邮件成功");
            }
        } catch (MailException e) {
            log.error("批量发送 SIMPLE 邮件失败", e);
        }
    }

    private void sendMimeMessage(MailDTO mailDTO) {
        try {
            MimeMessage mimeMessage = fillMimeMessage(mailDTO);
            javaMailSender.send(mimeMessage);
            if (log.isDebugEnabled()) {
                log.debug("发送 MIME 邮件成功");
            }
        } catch (MessagingException | MailException e) {
            log.error("发送 MIME 邮件失败", e);
        }
    }

    private void sendMimeMessage(List<MailDTO> mailDTOS) {
        List<MimeMessage> messages = new ArrayList<>();
        for (MailDTO mailDTO : mailDTOS) {
            MimeMessage mimeMessage = null;
            try {
                mimeMessage = fillMimeMessage(mailDTO);
            } catch (MessagingException e) {
                log.error("批量发送 MIME 邮件失败", e);
            }
            messages.add(mimeMessage);
        }

        javaMailSender.send(messages.toArray(new MimeMessage[] {}));
        if (log.isDebugEnabled()) {
            log.debug("批量发送 MIME 邮件成功");
        }
    }

    private MimeMessage fillMimeMessage(MailDTO mailDTO) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, mailProperties.getDefaultEncoding()
                                                                                                 .toString());

        if (StringUtils.isBlank(mailDTO.getFrom())) {
            messageHelper.setFrom(mailExtProperties.getFrom());
        } else {
            messageHelper.setFrom(mailDTO.getFrom());
        }
        messageHelper.setTo(mailDTO.getTo());
        messageHelper.setSubject(mailDTO.getSubject());
        messageHelper.setText(mailDTO.getText(), true);

        // 添加邮件附件
        if (mailDTO.getFilenames() != null && mailDTO.getFilenames().length > 0) {
            for (String filename : mailDTO.getFilenames()) {
                messageHelper.addAttachment(filename, new File(filename));
            }
        }

        return mimeMessage;
    }
}
