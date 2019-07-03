package com.lx.service.mapper;

import com.lx.api.entity.StockEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by chenjiang on 2019/7/3
 */
public interface StockMapper {
    /**
     * 根据ID查询商品库
     *
     * @param commodityId
     * @return
     */
    @Select("SELECT id as id ,commodity_id as  commodityId, stock as stock from stock where commodity_id=#{commodityId}")
    public StockEntity selectStock(@Param("commodityId") Long commodityId);

    /**
     * 减库存操作
     *
     * @param commodityId
     * @return
     */
    @Update("update stock set stock=stock-1 where commodity_id=#{commodityId}")
    public int updateStock(@Param("commodityId") Long commodityId);
}
