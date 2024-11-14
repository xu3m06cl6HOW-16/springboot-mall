package com.yuanhao.springbootmall.dao;

import com.yuanhao.springbootmall.model.Order;
import com.yuanhao.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userId,Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemByOrderId(Integer orderId);
}
