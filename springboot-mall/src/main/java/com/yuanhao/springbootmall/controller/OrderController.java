package com.yuanhao.springbootmall.controller;


import com.yuanhao.springbootmall.dto.CreatedOrderRequest;
import com.yuanhao.springbootmall.model.Order;
import com.yuanhao.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("users/{userId}/orders")
    public ResponseEntity<?> createdOrder(@PathVariable Integer userId,
                                          @RequestBody @Valid CreatedOrderRequest createdOrderRequest) {
        //插入數據，回傳訂單編號
        Integer orderId=orderService.createOrder(userId,createdOrderRequest);

        //訂單編號 查找訂單細項
        Order order =orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
