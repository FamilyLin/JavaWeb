package com.ptmfm.service;

import com.ptmfm.pojo.Book;
import com.ptmfm.pojo.Page;

import java.util.List;

public interface BookService {

    public void addBook( Book book);

    public void deleteBookById(Integer id);

    public void updateBook(Book book);

    public Book queryBookById(Integer id);

    public List<Book> queryBooks();

    Page<Book> page(int pageNo, int pageSize);

    Page<Book> pageByPrice(int pageNo, int pageSize, int min, int max);
}
