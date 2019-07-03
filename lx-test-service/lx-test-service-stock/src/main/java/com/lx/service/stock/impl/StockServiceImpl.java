package com.lx.service.stock.impl;

import com.codingapi.tx.annotation.TxTransaction;
import com.lx.api.entity.StockEntity;
import com.lx.api.stock.StockServiceApi;
import com.lx.common.constant.LxError;
import com.lx.common.constant.LxResponse;
import com.lx.service.mapper.StockMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenjiang on 2019/7/3
 */
@RestController
@Slf4j
@SuppressWarnings("all")
public class StockServiceImpl implements StockServiceApi {
    @Autowired
    private StockMapper stockMapper;

    /**
     * @param commodityId
     * @return
     */
    @TxTransaction
    @Transactional
    @RequestMapping("/api/stock/inventoryReduction")
    public LxResponse inventoryReduction(@RequestParam("commodityId") Long commodityId) {
        if (commodityId == null) {
            return new LxResponse().setError("商品ID不允许为空");
        }
        StockEntity stockEntity = stockMapper.selectStock(commodityId);
        if (stockEntity == null) {
            return new LxResponse().setError("商品ID不存在");
        }
        if (stockEntity.getStock() <= 0) {
            return new LxResponse().setError("当前商品已经买完啦!");
        }
        int updateStockResult = stockMapper.updateStock(commodityId);
        if (updateStockResult <= 0) {
            return new LxResponse().setError("修改库存失败!");
        }
        return new LxResponse().setData("修改库存成功!");
    }
}
