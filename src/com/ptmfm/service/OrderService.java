package com.ptmfm.service;

import com.ptmfm.pojo.Cart;

public interface OrderService {
    public String createOrder(Cart cart, Integer userId);
}
