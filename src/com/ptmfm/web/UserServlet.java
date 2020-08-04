package com.ptmfm.web;

import com.ptmfm.pojo.User;
import com.ptmfm.service.UserService;
import com.ptmfm.service.impl.UserServiceImpl;
import com.ptmfm.utils.WebUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;


public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();

    protected void ajaxExistsUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//获取请求的参数username
        String username = req.getParameter("username");
//        调用userService.existsUsername();
        boolean existsUsername = userService.existsUsername(username);
//        把返回的结果封装成为map对象
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("existsUsername",existsUsername);

        Gson gson = new Gson();
        String json = gson.toJson(resultMap);

        resp.getWriter().write(json);
    }

    /**注销
 * @param req
 * @param resp
 * @return void
 */

    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        销毁Session中用户登录的信息，或者直接销毁Session
        req.getSession().invalidate();
//        重定向到首页（或登录页面）
        resp.sendRedirect(req.getContextPath());
    }

    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
//           保存用户登录的信息到Session域中
            req.getSession().setAttribute("user",loginUser);
//            跳回成功页面
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req,resp);
        }
    }



    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

       String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
       req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");


        User user =  WebUtils.copyParamToBean(req.getParameterMap(),new User());



        if(token!= null&& token.equalsIgnoreCase(code)){
            if (userService.existsUsername(username)){
                System.out.println("用户名[" + username + "]已存在");
//把回显信息，保存到request域中
                req.setAttribute("msg","用户名已存在");
                req.setAttribute("username",username);
                req.setAttribute("email",email);

                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
            }else{
                userService.registUser(new User(null,username,password,email));
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req,resp);
            }
        }else{
            //把回显信息，保存到request域中
            req.setAttribute("msg","验证码错误");
            req.setAttribute("username",username);
            req.setAttribute("email",email);

            System.out.println("验证码[" + code + "]错误");
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req,resp);
        }
    }




//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String action = req.getParameter("action");
//        try {
//            Method method = this.getClass().getDeclaredMethod(action,HttpServletRequest.class,HttpServletResponse.class);
//            method.invoke(this,req,resp);
//        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
//
//    }

}
