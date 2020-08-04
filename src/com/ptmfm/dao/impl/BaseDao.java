package com.ptmfm.dao.impl;

import com.ptmfm.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDao {
//    使用DbUtils操作数据库
    private QueryRunner queryRunner = new QueryRunner();
/**
 * @param sql
 * @param args
 * @return int
 */

    public int update(String sql, Object ... args){
        Connection connection = JdbcUtils.getConnection();
        try {
            return queryRunner.update(connection,sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

/**
 * 查询返回一个javaBean的sql语句
 * @author ZJL
 * @date 2020/7/31 15:42
 * @param type    返回的对象类型
 * @param sql     执行的sql语句
 * @param args    sql对于的参数值
 * @return T        返回的类型的泛型
 */

    public <T> T queryForOne(Class<T> type, String sql, Object ... args){
        Connection con = JdbcUtils.getConnection();
        try {
            return queryRunner.query(con,sql,new BeanHandler<T>(type),args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询返回多个javaBean的sql语句
     * @author ZJL
     * @date 2020/7/31 15:42
     * @param type    返回的对象类型
     * @param sql     执行的sql语句
     * @param args    sql对于的参数值
     * @return T        返回的类型的泛型
     */

    public <T>List<T> queryForList(Class<T> type, String sql, Object ... args){
        Connection con = JdbcUtils.getConnection();
        try {
            return queryRunner.query(con,sql,new BeanListHandler<T>(type),args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行返回一行一列的sql语句
     * @author ZJL
     * @date 2020/7/31 15:57
     * @param sql    执行sql语句
     * @param args   sql对应的参数值
     * @return java.lang.Object
     */


    public Object queryForSingleValue(String sql, Object ... args){
        Connection conn = JdbcUtils.getConnection();

        try {
            return queryRunner.query(conn,sql,new ScalarHandler(),args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



}
