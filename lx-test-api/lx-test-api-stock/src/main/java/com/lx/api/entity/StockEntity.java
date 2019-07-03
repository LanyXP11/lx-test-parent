package com.lx.api.entity;

import lombok.Data;

@Data
public class StockEntity {

    private Long id;
    // 商品id
    private Long commodityId;
    // 库存
    private Long stock;
}
