package com.yuanhao.springbootmall.dao;

import com.yuanhao.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userId,Integer totalAmout);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
