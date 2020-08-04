package com.ptmfm.test;

import com.ptmfm.pojo.User;
import com.ptmfm.service.UserService;
import com.ptmfm.service.impl.UserServiceImpl;
import org.junit.Test;

public class UserServiceImplTest {
    UserService userService = new UserServiceImpl();

    @Test
    public void registUser() {
        userService.registUser(new User(null,"bbj168","55555","asdasdqw@qq.com"));

    }

    /**
     * 如果返回null说明登陆失败
     * @param

     * @return void
     */

    @Test
    public void login() {
        System.out.println(userService.login(new User(null,"bbj168","55555",null)));
    }

    @Test
    public void existUsername() {
        if(userService.existsUsername("bbj168")){
            System.out.println("用户名已存在");
        }else{
            System.out.println("用户名可用");
        }
    }
}