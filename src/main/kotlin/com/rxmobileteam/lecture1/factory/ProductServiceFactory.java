package com.rxmobileteam.lecture1.factory;

import com.rxmobileteam.lecture1.data.ProductDao;
import com.rxmobileteam.lecture1.service.ProductService;

/**
 * {@link ProductServiceFactory} is used to create an instance of {@link ProductService}
 * <p>
 * TODO: 1. Implement method {@link ProductServiceFactory#createProductService()}
 */
public class ProductServiceFactory {

    /**
     * Create a new instance of {@link ProductService}
     *
     * @return ProductService
     */
    public ProductService createProductService() {
        return new ProductService(new ProductDao());
    }
}
