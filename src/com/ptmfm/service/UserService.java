package com.ptmfm.service;

import com.ptmfm.pojo.User;

public interface UserService {
    /**
     * 注册用户
     * @param user

     * @return void
     */
    public void registUser(User user);

    /**
     * 登录
     * @param user

     * @return com.atguigu.pojo.User
     */
    public User login(User user);

    /**
     * 检查用户名是否可用
     * @param username

     * @return 返回true表示用户名已经存在
     */
    public boolean existsUsername(String username);

}
