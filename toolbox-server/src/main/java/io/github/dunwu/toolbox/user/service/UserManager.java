package io.github.dunwu.toolbox.user.service;

import io.github.dunwu.core.DataResult;
import io.github.dunwu.toolbox.user.dto.LoginInfoDTO;
import io.github.dunwu.toolbox.user.dto.UserInfoDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-13
 */
public interface UserManager {

    DataResult<Map<String, String>> register(LoginInfoDTO registerUserDTO);

    DataResult<UserInfoDTO> login(HttpServletRequest request, HttpServletResponse response, Map<String, String> map);
}
