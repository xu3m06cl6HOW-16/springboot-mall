package com.yuanhao.springbootmall.service;

import com.yuanhao.springbootmall.dto.ProductRequest;
import com.yuanhao.springbootmall.model.Product;

public interface ProductService {

    Product getProductById(int productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
