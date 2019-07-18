package com.lx.ribbo.controller;

import com.lx.ribbo.service.RibbonOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenjiang on 2019/7/18
 */
@RestController
public class RibbonOrderController {
    @Autowired
    RibbonOrderService ribbonOrderService;
    @GetMapping("/order/info")
    public String orderInfo(@RequestParam("id") String orderId) {
        return ribbonOrderService.orderInfo(orderId);
    }

}
