package com.ptmfm.dao.impl;

import com.ptmfm.dao.OrderDao;
import com.ptmfm.pojo.Order;

public class OrderDaoImpl  extends BaseDao implements OrderDao {
    @Override
    public int saveOrder(Order order) {
        String sql = "insert into t_order(`order_id`,`create_time`,`price`,`status`,`user_id`) values(?,?,?,?,?)";

        return update(sql,order.getOrderId(),order.getCreateTime(),order.getPrice(),order.getStatus(),order.getUserId());
    }
}
