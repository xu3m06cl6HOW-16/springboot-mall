package com.yuanhao.springbootmall.service.impl;

import com.yuanhao.springbootmall.dao.OrderDao;
import com.yuanhao.springbootmall.dao.ProductDao;
import com.yuanhao.springbootmall.dao.UserDao;
import com.yuanhao.springbootmall.dto.BuyItem;
import com.yuanhao.springbootmall.dto.CreatedOrderRequest;
import com.yuanhao.springbootmall.model.Order;
import com.yuanhao.springbootmall.model.OrderItem;
import com.yuanhao.springbootmall.model.Product;
import com.yuanhao.springbootmall.model.User;
import com.yuanhao.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Transactional//修改多個表時要加上該註解，避免數據不一致
    @Override
    public Integer createOrder(Integer userId, CreatedOrderRequest createdOrderRequest) {

        User user =userDao.getUserById(userId);

        if(user == null){
            log.warn("該 userId {} 不存在",userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmout = 0;
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();

        for(BuyItem buyItem : createdOrderRequest.getBuyItemList()){
            Product product =productDao.getProductById(buyItem.getProductId());

            //檢查 product 是否存在，以及庫存是否足夠
            if(product == null){
                log.warn("商品 {} 不存在",buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }else if(product.getStock() < buyItem.getQuantity()){
                log.warn("商品 {} 庫存數量不足，無法購買，剩餘庫存 {} ,欲購買數量 {}",
                        buyItem.getProductId(),product.getStock(),buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            //扣除商品庫存
            productDao.updateStock(product.getProductId(),product.getStock()- buyItem.getQuantity());



            //計算總價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmout = totalAmout+amount;

            //轉換BuyItem for OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);

        }
        Integer orderId=orderDao.createOrder(userId,totalAmout);

        orderDao.createOrderItems(orderId,orderItemList);

        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {

        Order order =orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList=orderDao.getOrderItemByOrderId(orderId);

        order.setOrderItems(orderItemList);

        return order;
    }
}
