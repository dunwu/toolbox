package io.github.dunwu.toolbox.user.mapper.dao.impl;

import io.github.dunwu.annotation.Dao;
import io.github.dunwu.data.dao.BaseDao;
import io.github.dunwu.toolbox.user.entity.LoginInfo;
import io.github.dunwu.toolbox.user.mapper.LoginInfoMapper;
import io.github.dunwu.toolbox.user.mapper.dao.LoginInfoDao;

/**
 * <p>
 * 登录信息表 DAO 实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-07
 */
@Dao
public class LoginInfoDaoImpl extends BaseDao<LoginInfoMapper, LoginInfo> implements LoginInfoDao {

}
