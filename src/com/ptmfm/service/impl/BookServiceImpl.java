package com.ptmfm.service.impl;

import com.ptmfm.dao.BookDao;
import com.ptmfm.dao.impl.BookDaoImpl;
import com.ptmfm.pojo.Book;
import com.ptmfm.pojo.Page;
import com.ptmfm.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    private BookDao bookDao = new BookDaoImpl();
    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    @Override
    public void deleteBookById(Integer id) {
        bookDao.deleteBookById(id);

    }

    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    @Override
    public Book queryBookById(Integer id) {
        return bookDao.queryBookById(id);
    }

    @Override
    public List<Book> queryBooks() {
        return bookDao.queryBooks();
    }

    @Override
    public Page<Book> page(int pageNo, int pageSize) {
        Page<Book> page = new Page<Book>();


        //设置每页显示的数量
        page.setPageSize(pageSize);
        //qiu总的记录数
        Integer pageTotalCount = bookDao.queryForPageTotalCount();
        page.setPageTotalCount(pageTotalCount);
        //设置总页码
        Integer pageTotal = pageTotalCount / pageSize;
        if(pageTotalCount % pageSize > 0 ){
            pageTotal+=1;
        }
        page.setPageTotal(pageTotal);

        //设置页码
        page.setPageNo(pageNo);

        //设置当前页数据开始索引
        int begin = (page.getPageNo()-1)*pageSize;
        //求当前页数据
        List<Book> items = bookDao.queryForPageItems(begin,pageSize);
        //设置当前页数据
        page.setItems(items);
        return page;
    }

    @Override
    public Page<Book> pageByPrice(int pageNo, int pageSize, int min, int max) {
        Page<Book> page = new Page<Book>();


        //设置每页显示的数量
        page.setPageSize(pageSize);
        //qiu总的记录数
        Integer pageTotalCount = bookDao.queryForPageTotalCountByPrice(min,max);
        page.setPageTotalCount(pageTotalCount);
        //设置总页码
        Integer pageTotal = pageTotalCount / pageSize;
        if(pageTotalCount % pageSize > 0 ){
            pageTotal+=1;
        }
        page.setPageTotal(pageTotal);

        //设置页码
        page.setPageNo(pageNo);

        //设置当前页数据开始索引
        int begin = (page.getPageNo()-1)*pageSize;
        //求当前页数据
        List<Book> items = bookDao.queryForPageItemsByPrice(begin,pageSize,min,max);
        //设置当前页数据
        page.setItems(items);
        return page;
    }
}
