package com.ptmfm.service.impl;

import com.ptmfm.dao.UserDao;
import com.ptmfm.dao.impl.UserDaoImpl;
import com.ptmfm.pojo.User;
import com.ptmfm.service.UserService;

public class UserServiceImpl implements UserService {

//    需要操作数据库
    private UserDao userDao = new UserDaoImpl();

    @Override
    public void registUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public User login(User user) {
        return userDao.queryUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public boolean existsUsername(String username) {
        if(userDao.queryUserByUsername(username) == null){
//            等于null说明没差到，可用
            return false;
        }
        return true;
    }
}
