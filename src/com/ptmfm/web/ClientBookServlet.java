package com.ptmfm.web;

import com.ptmfm.pojo.Book;
import com.ptmfm.pojo.Page;
import com.ptmfm.service.BookService;
import com.ptmfm.service.impl.BookServiceImpl;
import com.ptmfm.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClientBookServlet extends BaseServlet {
    private BookService bookService = new BookServiceImpl();


    /**
     * 处理分页
     * @param req
     * @param resp
     * @return void
     */

    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("经过了前台的clientBookServlet程序");
        //获取请求的参数pageNO和pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGR_SIZE);

        //调用BookService.page(pageNo,pageSize)；Page对象
        Page<Book> page = bookService.page(pageNo,pageSize);
        page.setUrl("client/bookServlet?action=page");

        //保存page对象到Request域中
        req.setAttribute("page",page);
        //请求转发到pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);


    }

    /**
     * 处理分页
     * @param req
     * @param resp
     * @return void
     */

    protected void pageByPrice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("经过了前台的clientBookServlet程序");
        //获取请求的参数pageNO和pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGR_SIZE);
        int min = WebUtils.parseInt(req.getParameter("min"),0);
        int max = WebUtils.parseInt((req.getParameter("max")),Integer.MAX_VALUE);

        //调用BookService.page(pageNo,pageSize)；Page对象
        Page<Book> page = bookService.pageByPrice(pageNo,pageSize,min,max);

        StringBuilder sb = new StringBuilder("client/bookServlet?action=pageByPrice");
//如果有最小价格
        if(req.getParameter("min") != null){
            sb.append("&min=").append(req.getParameter("min"));
        }
        if(req.getParameter("max") != null){
            sb.append("&max=").append(req.getParameter("max"));
        }

        page.setUrl(sb.toString());

        //保存page对象到Request域中
        req.setAttribute("page",page);
        //请求转发到pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);


    }

}
