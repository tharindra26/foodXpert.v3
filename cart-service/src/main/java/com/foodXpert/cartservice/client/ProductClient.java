package com.foodXpert.cartservice.client;

import com.foodXpert.cartservice.dto.ProductAvailabilityResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "product-service", url = "http://localhost:8080/api/v1/products")
public interface ProductClient {
    @RequestMapping(method = RequestMethod.GET, value = "/{productId}/availability")
    ProductAvailabilityResponseDTO checkProductAvailability(@PathVariable String productId);
}
