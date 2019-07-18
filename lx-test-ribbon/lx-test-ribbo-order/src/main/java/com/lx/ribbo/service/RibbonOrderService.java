package com.lx.ribbo.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by chenjiang on 2019/7/18
 */
@Service
public class RibbonOrderService {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "orderInfoError")
    public String orderInfo(String orderId) {
        return restTemplate.getForObject("http://lx-order:8010/api/order/OrderTest?orderId=" + orderId, String.class);
    }

    public String orderInfoError(String orderId) {
        return "sorry, error ribbon hystrix!";
    }
}
