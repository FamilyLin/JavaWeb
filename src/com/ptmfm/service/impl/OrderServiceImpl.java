package com.ptmfm.service.impl;

import com.ptmfm.dao.BookDao;
import com.ptmfm.dao.OrderDao;
import com.ptmfm.dao.OrderItemDao;
import com.ptmfm.dao.impl.BookDaoImpl;
import com.ptmfm.dao.impl.OrderDaoImpl;
import com.ptmfm.dao.impl.OrderItemDaoImpl;
import com.ptmfm.pojo.*;
import com.ptmfm.service.OrderService;

import java.util.Date;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();
    @Override
    public String createOrder(Cart cart, Integer userId) {
//订单号===唯一性
        String orderId = System.currentTimeMillis() + "" + userId;
//        创建一个订单对象
        Order order = new Order(orderId,new Date(),cart.getTotalPrice(),0,userId);
//       保存订单
        orderDao.saveOrder(order);
//遍历购物车中每一个商品项转换为订单项保存到数据库中
        for(Map.Entry<Integer, CartItem>entry : cart.getItems().entrySet()){
            CartItem cartItem = entry.getValue();
            OrderItem orderItem = new OrderItem(null,cartItem.getName(),cartItem.getCount(),cartItem.getPrice(),cartItem.getTotalPrice(),orderId);
            orderItemDao.saveOrderItem(orderItem);

            Book book = bookDao.queryBookById(cartItem.getId());
            book.setSales(book.getSales() + cartItem.getCount());
            book.setStock(book.getStock() - cartItem.getCount());
            bookDao.updateBook(book);
        }

        cart.clear();

        return orderId;
    }
}
