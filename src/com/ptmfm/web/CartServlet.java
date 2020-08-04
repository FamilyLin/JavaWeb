package com.ptmfm.web;

import com.ptmfm.pojo.Book;
import com.ptmfm.pojo.Cart;
import com.ptmfm.pojo.CartItem;
import com.ptmfm.service.BookService;
import com.ptmfm.service.impl.BookServiceImpl;
import com.ptmfm.utils.WebUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartServlet extends BaseServlet {

    private BookService bookService = new BookServiceImpl();


    protected void updateCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        获取请求的参数  商品编号。商品数量
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        int count = WebUtils.parseInt(req.getParameter("count"), 1);
//        获取Cart购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null){
            cart.updateCount(id,count);
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }

    protected void clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null) {
            cart.clear();
            resp.sendRedirect(req.getHeader("Referer"));
        }
//
    }

    protected void deleteItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        获取商品编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
//        获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart != null) {
            //        删除购物车商品项
            cart.deleteItem(id);
//        重定向回原来购物车展示页面
            resp.sendRedirect(req.getHeader("Referer"));
        }

    }

    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("加入购物车");
//        System.out.println("商品编号"+req.getParameter("id"));

//        获取请求的参数 商品编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
//        调用bookService.queryBookById(id)：book得到图书的信息
        Book book = bookService.queryBookById(id);
//        把图书的信息，转换为cartItem商品向
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
//        调用cart.addItem()添加商品向
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            req.getSession().setAttribute("cart", cart);
        }
        cart.addItem(cartItem);
        System.out.println(cart);
//        重定向回商品页面
//        请求头中的Referer是浏览器发送给服务器的地址
        resp.sendRedirect(req.getHeader("Referer"));

        req.getSession().setAttribute("lastName",cartItem.getName());


    }


    protected void ajaxAddItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("加入购物车");
//        System.out.println("商品编号"+req.getParameter("id"));

//        获取请求的参数 商品编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
//        调用bookService.queryBookById(id)：book得到图书的信息
        Book book = bookService.queryBookById(id);
//        把图书的信息，转换为cartItem商品向
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
//        调用cart.addItem()添加商品向
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            req.getSession().setAttribute("cart", cart);
        }
        cart.addItem(cartItem);
        System.out.println(cart);

        req.getSession().setAttribute("lastName",cartItem.getName());
//返回购物车总的商品数量和最后一个添加的商品名称
        Map<String, Object> resultMap = new HashMap<String,Object>();

        resultMap.put("totalCount",cart.getTotalCount());
        resultMap.put("lastName",cartItem.getName());

        Gson gson = new Gson();
        String resultMapJsonString = gson.toJson(resultMap);
        resp.getWriter().write(resultMapJsonString);
    }

}
