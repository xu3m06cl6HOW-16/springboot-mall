package com.yuanhao.springbootmall.dao.impl;

import com.yuanhao.springbootmall.dao.OrderDao;
import com.yuanhao.springbootmall.model.Order;
import com.yuanhao.springbootmall.model.OrderItem;
import com.yuanhao.springbootmall.rowmapper.OrderItemRowMapper;
import com.yuanhao.springbootmall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {

        String sql = "INSERT INTO `order`(user_id, total_amount, created_date, last_modified_date) " +
                "VALUES(:userId, :totalAmount, :createdDate, :lastModifiedDate)";

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("totalAmount", totalAmount);

        map.put("createdDate", new Date());
        map.put("lastModifiedDate", new Date());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int orderId = keyHolder.getKey().intValue();

        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {

        //使用 batchUpdate 一次性加入數據，效率更高

        String sql="INSERT INTO order_item(order_id, product_id, quantity, amount) " +
                "VALUES (:orderId, :productId, :quantity, :amount)";

        MapSqlParameterSource[] params = new MapSqlParameterSource[orderItemList.size()];

        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);

            params[i] = new MapSqlParameterSource();
            params[i].addValue("orderId", orderId);
            params[i].addValue("productId", orderItem.getProductId());
            params[i].addValue("quantity", orderItem.getQuantity());
            params[i].addValue("amount", orderItem.getAmount());
        }

        namedParameterJdbcTemplate.batchUpdate(sql, params);

    }

    @Override
    public Order getOrderById(Integer orderId) {

        String sql="SELECT order_id, user_id, total_amount, created_date, last_modified_date " +
                "FROM `order` WHERE order_id = :orderId";

        Map<String,Object> map=new HashMap<>();
        map.put("orderId", orderId);

        List<Order> orderList = namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource(map), new OrderRowMapper());

        if(orderList.size() > 0){
            return orderList.get(0);
        }else{
            return null;
        }

    }

    @Override
    public List<OrderItem> getOrderItemByOrderId(Integer orderId) {

        String sql="SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity," +
                " oi.amount, p.product_name, p.image_url " +
                "FROM order_item as oi " +
                "LEFT JOIN product as p ON oi.product_id = p.product_id " +
                "WHERE oi.order_id = :orderId";

        Map<String,Object> map=new HashMap<>();

        map.put("orderId", orderId);
        return namedParameterJdbcTemplate.query(sql,map, new OrderItemRowMapper());

    }
}
