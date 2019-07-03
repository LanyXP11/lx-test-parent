package com.lx.api.order;

import com.lx.common.constant.LxResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 订单服务
 */
public interface OrderServiceApi {
    /**
     * 模仿的方法
     *
     * @param
     * @return
     */
    @GetMapping("/api/order/orderAndStock")
    public LxResponse OrderAndStock(@RequestParam("num") String num);

}
