package com.yuanhao.springbootmall.service.impl;

import com.yuanhao.springbootmall.dao.OrderDao;
import com.yuanhao.springbootmall.dao.ProductDao;
import com.yuanhao.springbootmall.dto.BuyItem;
import com.yuanhao.springbootmall.dto.CreatedOrderRequest;
import com.yuanhao.springbootmall.model.OrderItem;
import com.yuanhao.springbootmall.model.Product;
import com.yuanhao.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderDao orderDao;

    @Transactional//修改多個表時要加上該註解，避免數據不一致
    @Override
    public Integer createOrder(Integer userId, CreatedOrderRequest createdOrderRequest) {

        int totalAmout = 0;
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();

        for(BuyItem buyItem : createdOrderRequest.getBuyItemList()){
            Product product =productDao.getProductById(buyItem.getProductId());

            //計算總價錢
            int amout = buyItem.getQuantity() * product.getPrice();
            totalAmout = totalAmout+amout;

            //轉換BuyItem for OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmout(amout);

            orderItemList.add(orderItem);

        }
        Integer orderId=orderDao.createOrder(userId,totalAmout);

        orderDao.createOrderItems(orderId,orderItemList);

        return orderId;
    }
}
