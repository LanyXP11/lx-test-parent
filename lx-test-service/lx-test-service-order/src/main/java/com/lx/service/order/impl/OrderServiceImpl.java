package com.lx.service.order.impl;

import com.alibaba.fastjson.JSON;
import com.codingapi.tx.annotation.ITxTransaction;
import com.codingapi.tx.annotation.TxTransaction;
import com.lx.api.entity.OrderEntity;
import com.lx.api.order.OrderServiceApi;
import com.lx.common.constant.LxResponse;
import com.lx.common.exception.MessageException;
import com.lx.service.feign.StockFeign;
import com.lx.service.mapper.OrderMapper;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by chenjiang on 2019/7/3
 */
@RestController
@Slf4j
@SuppressWarnings("all")
public class OrderServiceImpl implements OrderServiceApi, ITxTransaction {
    @Autowired
    private StockFeign stockFeign;
    @Autowired
    private OrderMapper orderMapper;

    @GetMapping("/api/order/orderAndStock")
    @Transactional
    @TxTransaction(isStart = true)
    public LxResponse OrderAndStock(@RequestParam("num") String num) {
        if (StringUtils.isBlank(num)) {
            return new LxResponse().setError("NUM不允许为空");
        }
        OrderEntity entity = new OrderEntity();
        entity.setCommodityId(30l);
        entity.setName("恭喜您下单了");
        entity.setOrderCreatetime(new Date());
        entity.setOrderMoney(300d);
        entity.setOrderState(0);
        //往订单表中添加一条记录
        int i = orderMapper.addOrder(entity);
        if (i < 0) {
            throw new MessageException(9010, "下单失败");
        }
        //调用库存服务对商品进行减库存操作
        LxResponse lxResponse = stockFeign.inventoryReduction(30l);
        if (checkResultResponse(lxResponse)) {
            throw new MessageException(lxResponse.getError().getCode(), lxResponse.getError().getMessage());
        }
        Long result = 1 / Long.valueOf(num);
        lxResponse = new LxResponse();

        lxResponse.setData("下单成功");

        log.info("调用订单服务最终返回值是:[{}]", JSON.toJSONString(lxResponse));
        return lxResponse;
    }

    @GetMapping("/api/order/OrderTest")
    public LxResponse OrderTest(@RequestParam("orderId") String orderId) {
        LxResponse respon = new LxResponse();
        respon.setData("test sueccess " + orderId);
        return respon;
    }

    protected Boolean checkResultResponse(LxResponse response) {
        return null != response && true == response.isSuccess() && null != response.getData();
    }
}
