package io.github.dunwu.toolbox.fs.mapper.dao.impl;

import io.github.dunwu.annotation.Dao;
import io.github.dunwu.data.dao.BaseDao;
import io.github.dunwu.toolbox.fs.entity.FileContent;
import io.github.dunwu.toolbox.fs.mapper.FileContentMapper;
import io.github.dunwu.toolbox.fs.mapper.dao.FileContentDao;

/**
 * <p>
 * 文件内容表 DAO 实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-08
 */
@Dao
public class FileContentDaoImpl extends BaseDao<FileContentMapper, FileContent> implements FileContentDao {

}
