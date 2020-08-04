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
import java.util.List;

public class BookServlet extends BaseServlet{
    private BookService bookService = new BookServiceImpl();

/**
 * 处理分页
 * @param req
 * @param resp
 * @return void
 */

    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数pageNO和pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGR_SIZE);
        //嗲用BookService.page(pageNo,pageSize)；Page对象
        Page<Book> page = bookService.page(pageNo,pageSize);

        page.setUrl("manager/bookServlet?action=page");

        //保存page对象到Request域中
        req.setAttribute("page",page);
        //请求转发到pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);


    }


    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),0);
        pageNo+=1;
        //获取请求的参数==封装成Book对象
        Book book = WebUtils.copyParamToBean(req.getParameterMap(),new Book());
        //调用BookService.addBook()保存图书
        bookService.addBook(book);

        //调到图书列表页面
        //bug
        //req.getRequestDispatcher("/manager/bookServlet?action=list").forward(req,resp);
        resp.sendRedirect(req.getContextPath()+"/manager/bookServlet?action=page&pageNo="+pageNo);
    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//获取请求的参数ID,图书编程

        int id = WebUtils.parseInt(req.getParameter("id"),0);


        //调用bookService.deleteBookById();删除图书
        bookService.deleteBookById(id);
        //重定向会图书列表管理页面
        //    /book/manager/bookServlet?action=list

        resp.sendRedirect(req.getContextPath()+"/manager/bookServlet?action=page&pageNo=" + req.getParameter("pageNo"));
    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//获取请求的参数==分装成为Book对象
        Book book = WebUtils.copyParamToBean(req.getParameterMap(),new Book());

        //调用BookService.updateBook(book);修改图书
        bookService.updateBook(book);
        //重定向回图书列表观念里页面
        //地址：/工程名/manager/bookServlet?action=list
        resp.sendRedirect(req.getContextPath()+"/manager/bookServlet?action=page&pageNo=" + req.getParameter("pageNo"));
    }

    protected void getBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的参数图书编号
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        //调用bookService.queryBookById查询图书
        Book book = bookService.queryBookById(id);
        //保存到图书到Request域中
        req.setAttribute("book",book);
        //请求转发到.pages/manager/book_edit.jsp页面
        req.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(req,resp);
    }
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //通过BookService查询全部图书
        List<Book> books = bookService.queryBooks();
        //把全部图书保存到request域中
        req.setAttribute("books",books);
        //请求转发到/pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
    }
}
