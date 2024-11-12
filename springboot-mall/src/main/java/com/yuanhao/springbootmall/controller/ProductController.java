package com.yuanhao.springbootmall.controller;

import com.yuanhao.springbootmall.constant.ProductCategore;
import com.yuanhao.springbootmall.dto.ProductQueryParams;
import com.yuanhao.springbootmall.dto.ProductRequest;
import com.yuanhao.springbootmall.model.Product;
import com.yuanhao.springbootmall.service.ProductService;
import com.yuanhao.springbootmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RequestMapping("/products")
@RestController
public class ProductController {

    @Autowired
    ProductService productService;


    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(
            //查詢條件 Filtering
            @RequestParam(required = false) ProductCategore category,
            @RequestParam(required = false) String search,

            //排序 Sorting
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc ") String sort,

            //分頁 Pagination
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0)Integer limit,
            @RequestParam(defaultValue = "0") @Min(0)Integer offset
            ){

        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        //取得product List
        List<Product> productList = productService.getProducts(productQueryParams);
        //取得 product總數
        Integer total=productService.countProduct(productQueryParams);

        Page<Product> page=new Page();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);//總資料數量
        page.setResults(productList);//所查詢到的商品資訊

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }


    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);

        if(product!=null){
            return ResponseEntity.ok(product);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId= productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest){

        Product product = productService.getProductById(productId);

        if(product==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build() ;
        }

        productService.updateProduct(productId,productRequest);
        return ResponseEntity.status(HttpStatus.OK).body(product);

    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){
        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
