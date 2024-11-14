package com.yuanhao.springbootmall.service;

import com.yuanhao.springbootmall.dto.CreatedOrderRequest;

public interface OrderService {

    Integer createOrder(Integer userId, CreatedOrderRequest createdOrderRequest);
}
