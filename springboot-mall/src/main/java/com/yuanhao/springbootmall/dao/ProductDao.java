package com.yuanhao.springbootmall.dao;

import com.yuanhao.springbootmall.dto.ProductRequest;
import com.yuanhao.springbootmall.model.Product;

public interface ProductDao {

    Product getProductById(int id);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
