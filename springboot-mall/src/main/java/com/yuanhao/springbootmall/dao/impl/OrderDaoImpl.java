package com.yuanhao.springbootmall.dao.impl;

import com.yuanhao.springbootmall.dao.OrderDao;
import com.yuanhao.springbootmall.model.OrderItem;
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
    public Integer createOrder(Integer userId, Integer totalAmout) {

        String sql = "INSERT INTO `order`(user_id, total_amout, created_date, last_modified_date) " +
                "VALUES(:userId, :totalAmout, :createdDate, :lastModifiedDate);)";

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("totalAmout", totalAmout);

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

        String sql="INSERT INTO order_item(order_id, product_id, quantity, amout) " +
                "VALUES (:orderId, :productId, :quantity, :amout);)";

        MapSqlParameterSource[] params = new MapSqlParameterSource[orderItemList.size()];

        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);

            params[i] = new MapSqlParameterSource();
            params[i].addValue("orderId", orderId);
            params[i].addValue("productId", orderItem.getProductId());
            params[i].addValue("quantity", orderItem.getQuantity());
            params[i].addValue("amout", orderItem.getAmout());
        }

        namedParameterJdbcTemplate.batchUpdate(sql, params);

    }
}
