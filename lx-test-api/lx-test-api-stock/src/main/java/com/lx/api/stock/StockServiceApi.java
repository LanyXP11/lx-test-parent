package com.lx.api.stock;

import com.lx.common.constant.LxResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 库存服务对外api
 */
public interface StockServiceApi {
    /**
     * 减库存
     *
     * @param commodityId
     * @return
     */
    @RequestMapping("/api/stock/inventoryReduction")
    public LxResponse inventoryReduction(@RequestParam("commodityId") Long commodityId);


}
