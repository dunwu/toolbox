package io.github.dunwu.toolbox.fs.dto;

import io.github.dunwu.util.code.CryptoUtil;
import lombok.Data;
import lombok.ToString;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-15
 */
@Data
@ToString
public class CryptoDTO {
    private CryptoUtil.SymmetricCryptoEnum type;
    private String key;
    private String plaintext;
    private String ciphertext;
}
