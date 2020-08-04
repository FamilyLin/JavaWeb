package com.ptmfm.web;

import com.ptmfm.pojo.User;
import com.ptmfm.service.UserService;
import com.ptmfm.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//      获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
//        调用userService.login()登录处理业务
        User loginUser = userService.login(new User(null, username, password, null));
        if(loginUser == null){
//           把错误信息和回显的表单项信息，保存到request域中
            req.setAttribute("msg","用户名或密码错误");
            req.setAttribute("username",username);

//           跳回登录页面
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);



        }else{
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req,resp);
        }

    }
}
