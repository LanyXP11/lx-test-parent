package com.lx.service.feign;

import com.lx.api.stock.StockServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "lx-stock")
public interface StockFeign extends StockServiceApi {
}
