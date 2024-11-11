package com.yuanhao.springbootmall.dto;

import com.yuanhao.springbootmall.constant.ProductCategore;

public class ProductQueryParams {

    private ProductCategore category;
    private String search;

    public ProductCategore getCategory() {
        return category;
    }

    public void setCategory(ProductCategore category) {
        this.category = category;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
