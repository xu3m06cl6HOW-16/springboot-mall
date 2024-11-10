package com.yuanhao.springbootmall.service;

import com.yuanhao.springbootmall.dao.ProductDao;
import com.yuanhao.springbootmall.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Override
    public Product getProductById(int productId) {
        return productDao.getProductById(productId);
    }
}
