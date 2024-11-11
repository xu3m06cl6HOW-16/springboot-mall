package com.yuanhao.springbootmall.dao.impl;

import com.yuanhao.springbootmall.dao.ProductDao;
import com.yuanhao.springbootmall.dto.ProductRequest;
import com.yuanhao.springbootmall.model.Product;
import com.yuanhao.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProductDaoImpl implements ProductDao {


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public List<Product> getProducts() {
        String sql = "select product_id,product_name,category,image_url," +
                "price,stock,description,created_date,last_modified_date from product";

        Map<String,Object> map = new HashMap<>();
        List<Product> productsList = namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());

        return productsList;
    }

    @Override
    public Product getProductById(int productId) {
        String sql = "select * from product where product_id = :productId";

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("productId", productId);
        List<Product> productList= namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());
        if(productList.size()>0){
            return productList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {


        String sql="INSERT INTO product (product_name,category,image_url,price," +
                "stock,description,created_date,last_modified_date)" +
                "VALUES (:productName, :category, :imageUrl," +
                ":price, :stock, :description, :createdDate, :lastModifiedDate)";

        System.out.println("語法沒問題!");

        Map<String,Object> map=new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now=new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();//儲存資料庫最後生成的Id

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

        int productId=keyHolder.getKey().intValue();

        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {

        String sql="UPDATE product SET product_name=:productName,category=:category,image_url=:imageUrl," +
                "price=:price,stock=:stock,description=:description,last_modified_date=:lastModifiedDate WHERE product_id = :productId";

        Map<String,Object> map=new HashMap<>();
        map.put("productId", productId);

        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        map.put("lastModifiedDate", new Date());
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql="DELETE FROM product WHERE product_id = :productId";

        Map<String,Object> map=new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql,map);
    }


}