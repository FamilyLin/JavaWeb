package com.ptmfm.test;

import com.ptmfm.dao.BookDao;
import com.ptmfm.dao.impl.BookDaoImpl;
import com.ptmfm.pojo.Book;
import com.ptmfm.pojo.Page;
import org.junit.Test;

import java.math.BigDecimal;

public class BookDaoTest {
    private BookDao bookDao = new BookDaoImpl();

    @Test
    public void addBook() {
        bookDao.addBook(new Book(null,"好书一本","ZJL",new BigDecimal(9999),
                10000,0,null));
    }

    @Test
    public void deleteBookById() {
        bookDao.deleteBookById(21);
    }

    @Test
    public void updateBook() {
        bookDao.updateBook(new Book(21,"一本好书", "ZJLL",new BigDecimal(9999),
                10000,0,null));

    }

    @Test
    public void queryBookById() {
        System.out.println(bookDao.queryBookById(21));
    }

    @Test
    public void queryBooks() {
        for(Book queryBook : bookDao.queryBooks()){
            System.out.println(queryBook);
        }
    }


    @Test
    public void queryForPageTotalCount() {
        System.out.println(bookDao.queryForPageTotalCount());
    }

    @Test
    public void queryForPageTotalCountByPrice() {
        System.out.println(bookDao.queryForPageTotalCountByPrice(10,50));
    }

    @Test
    public void queryForPageItems() {
        for(Book book:bookDao.queryForPageItems(8, Page.PAGR_SIZE)){
            System.out.println(book);
        }
    }

    @Test
    public void queryForPageItemsByPrice() {
        for(Book book:bookDao.queryForPageItemsByPrice(0, Page.PAGR_SIZE,10,50)){
            System.out.println(book);
        }
    }
}