package com.ptmfm.test;

import com.ptmfm.pojo.Book;
import com.ptmfm.pojo.Page;
import com.ptmfm.service.BookService;
import com.ptmfm.service.impl.BookServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

public class BookServiceTest {
    private BookService bookService = new BookServiceImpl();

    @Test
    public void addBook() {
        bookService.addBook(new Book(null,"xiagao","zzz",
                new BigDecimal(200),100,0,null));
    }

    @Test
    public void deleteBookById() {
        bookService.deleteBookById(22);
    }

    @Test
    public void updateBook() {
        bookService.updateBook(new Book(22,"xiagaoaaa","zzz",
                new BigDecimal(200),100,0,null));
    }

    @Test
    public void queryBookById() {
        System.out.println(bookService.queryBookById(22));
    }

    @Test
    public void queryBooks() {
        for (Book queryBook : bookService.queryBooks()) {
            System.out.println(queryBook);
        }
    }


    @Test
    public void page(){
        System.out.println(bookService.page(1, Page.PAGR_SIZE));
    }
}