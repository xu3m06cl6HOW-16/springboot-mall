package com.yuanhao.springbootmall.dao;

import com.yuanhao.springbootmall.dto.ProductQueryParams;
import com.yuanhao.springbootmall.dto.ProductRequest;
import com.yuanhao.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(int id);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
