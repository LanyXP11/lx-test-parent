package com.lx.service.mapper;

import com.lx.api.entity.OrderEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;


public interface OrderMapper {
    /**
     * 用户下单 往订单表中插入一条数据
     *
     * @param orderEntity
     * @return
     */
    @Insert(value = "INSERT INTO `order` VALUES (#{id}, #{name}, #{orderCreatetime}, #{orderMoney}, #{orderState}, #{commodityId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public int addOrder(OrderEntity orderEntity);

}
