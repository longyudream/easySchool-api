package com.czl.service.order.impl;

import com.czl.entity.RequestParamModel;
import com.czl.entity.ResultEntity;
import com.czl.service.order.OrderListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *service中统一对返回值重新封装
 * @author 陈正龙
 * @date 2020/5/17 18:18
 */
@Service
@Slf4j
public class OrderListServiceImpl implements OrderListService {
    /**
     * 获取订单列表数据
      * @param param 传入数据
     * @return
     */
    @Override
    public ResultEntity getOrderList(String param) {
        ResultEntity resultEntity = new ResultEntity();


        
        return resultEntity;
    }
}
