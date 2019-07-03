
package com.lx.api.entity;

import java.util.Date;


import lombok.Getter;
import lombok.Setter;

/**
 * 订单实体
 */
@Setter
@Getter
public class OrderEntity {

    private Long id;
    // 订单名称
    private String name;
    // 订单时间
    private Date orderCreatetime;
    // 下单金额
    private Double orderMoney;
    // 订单状态
    private int orderState;
    // 商品id
    private Long commodityId;

}
