package com.czl.mapper.business.order;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 陈正龙
 * @date 2020/5/20 18:12
 */
public interface OrderMapper {

    /**
     * 根据条件查询订单列表
     * @author 陈正龙
     * @date 2020/5/20 18:14
     */
    List getOrderList(@Param("params") Map condition);
}
