package com.yuanhao.springbootmall.service;

import com.yuanhao.springbootmall.dto.CreatedOrderRequest;
import com.yuanhao.springbootmall.model.Order;

public interface OrderService {

    Integer createOrder(Integer userId, CreatedOrderRequest createdOrderRequest);

    Order getOrderById(Integer orderId);
}
