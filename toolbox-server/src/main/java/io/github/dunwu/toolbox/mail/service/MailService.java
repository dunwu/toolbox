package io.github.dunwu.toolbox.mail.service;

import io.github.dunwu.toolbox.mail.dto.MailDTO;

import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-28
 */
public interface MailService {

    void send(MailDTO mailDTO);

    void sendBatch(List<MailDTO> mailDTO, boolean html);
}
