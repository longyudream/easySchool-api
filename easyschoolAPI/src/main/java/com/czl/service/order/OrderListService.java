package com.czl.service.order;

import com.czl.entity.RequestParamModel;
import com.czl.entity.ResultEntity;

/**
 * @author 陈正龙
 * @date 2020/5/17 18:16
 */
public interface OrderListService {
    ResultEntity getOrderList(String param);
}
