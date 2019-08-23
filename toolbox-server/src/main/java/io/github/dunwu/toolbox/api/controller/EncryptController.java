package io.github.dunwu.toolbox.api.controller;

import io.github.dunwu.core.AppCode;
import io.github.dunwu.core.DataResult;
import io.github.dunwu.core.ResultUtil;
import io.github.dunwu.toolbox.fs.dto.CryptoDTO;
import io.github.dunwu.util.code.CryptoUtil;
import io.github.dunwu.util.code.ICrypto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-15
 */
@RestController
@RequestMapping("crypto")
public class EncryptController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("encrypt")
    public DataResult<CryptoDTO> encrypt(@RequestBody CryptoDTO cryptoDTO) {
        ICrypto crypto;
        try {
            crypto = CryptoUtil.getInstace(cryptoDTO.getType()
                                                    .getValue(), cryptoDTO.getKey());
        } catch (Exception e) {
            log.error(AppCode.ERROR_ENCODE.getMessage(), e);
            return ResultUtil.failDataResult(AppCode.ERROR_ENCODE.getCode(), e.getMessage());
        }


        String ciphertext;
        try {
            ciphertext = crypto.encryptToString(cryptoDTO.getPlaintext());
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            log.error(AppCode.ERROR_ENCODE.getMessage(), e);
            return ResultUtil.failDataResult(AppCode.ERROR_ENCODE.getCode(), e.getMessage());
        }

        cryptoDTO.setCiphertext(ciphertext);
        return ResultUtil.successDataResult(cryptoDTO);
    }

    @PostMapping("decrypt")
    public DataResult<CryptoDTO> decrypt(@RequestBody CryptoDTO cryptoDTO) {
        ICrypto crypto = null;
        try {
            crypto = CryptoUtil.getInstace(cryptoDTO.getType()
                                                    .getValue(), cryptoDTO.getKey());
        } catch (Exception e) {
            log.error(AppCode.ERROR_DECODE.getMessage(), e);
        }

        if (crypto == null) {
            return ResultUtil.failDataResult(AppCode.ERROR_DECODE);
        }

        String plaintext = null;
        try {
            plaintext = crypto.decryptToString(cryptoDTO.getCiphertext());
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            log.error(AppCode.ERROR_DECODE.getMessage(), e);
        }

        if (plaintext == null) {
            return ResultUtil.failDataResult(AppCode.ERROR_DECODE);
        }

        cryptoDTO.setPlaintext(plaintext);
        return ResultUtil.successDataResult(cryptoDTO);
    }
}
