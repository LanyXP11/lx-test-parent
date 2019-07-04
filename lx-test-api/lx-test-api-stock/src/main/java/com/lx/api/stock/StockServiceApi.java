package com.lx.api.stock;

import com.lx.common.constant.LxResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = "库存服务")
public interface StockServiceApi {
    /**
     * 减库存
     *
     * @param commodityId
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", required = true, name = "commodityId", dataType = "Long", value = "商品id")})
    @RequestMapping("/api/stock/inventoryReduction")
    @ApiOperation("根据商品ID减库方法")
    public LxResponse inventoryReduction(@RequestParam("commodityId") Long commodityId);


}
