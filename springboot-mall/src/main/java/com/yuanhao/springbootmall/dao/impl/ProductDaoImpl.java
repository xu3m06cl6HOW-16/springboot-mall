package com.yuanhao.springbootmall.dao.impl;

import com.yuanhao.springbootmall.dao.ProductDao;
import com.yuanhao.springbootmall.model.Product;
import com.yuanhao.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDaoImpl implements ProductDao {


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(int productId) {
        String sql = "select * from product where product_id = :productId";

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("productId", productId);
        List<Product> productList= namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());
        System.out.println("有執行!");
        if(productList.size()>0){
            return productList.get(0);
        }else{
            return null;
        }
    }
}
