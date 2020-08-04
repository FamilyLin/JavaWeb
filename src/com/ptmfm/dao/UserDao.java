package com.ptmfm.dao;

import com.ptmfm.pojo.User;

public interface UserDao {



    /**
     * 根据用户名查询用户信息
     *  @author ZJL
     * @date 2020/7/31 16:14
     * @param username
     * @return 如果返回null，表示没有这个用户
     */
    public User queryUserByUsername(String username);

    /**
     * 根据用户名和密码查询用户信息
     * @param username
     * @param password
     * @return 如果返回null表示用户名或密码错误
     */

    public User queryUserByUsernameAndPassword(String username,String password);


    /**
     * 保存用户信息
     * @author ZJL
     * @date 2020/7/31 16:16
     * @param user
     * @return int
     */

    public int saveUser(User user);
}
