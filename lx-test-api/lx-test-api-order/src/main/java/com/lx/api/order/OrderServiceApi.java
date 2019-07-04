package com.lx.api.order;

import com.lx.common.constant.LxResponse;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 订单服务
 */
@Api(tags = "订单服务")
public interface OrderServiceApi {
    /**
     * 模仿的方法
     *
     * @param
     * @return
     */
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "num", dataType = "String", required = true, value = "测试分布式事务制造异常的参数")})
    @ApiResponse(code = 200, message = "下单成功")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "下单成功"), @ApiResponse(code = 999, message = "系统异常")})
    @ApiOperation("测试分布式事务的方法")
    @GetMapping("/api/order/orderAndStock")
    LxResponse OrderAndStock(@RequestParam("num") String num);

}
