package com.czl.service.order.impl;

import com.czl.base.constants.SystemConstants;
import com.czl.entity.ResultEntity;
import com.czl.mapper.business.order.OrderMapper;
import com.czl.service.order.OrderListService;
import com.czl.utils.JsonConversionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * service中统一对返回值重新封装
 *
 * @author 陈正龙
 * @date 2020/5/17 18:18
 */
@Service
@Slf4j
public class OrderListServiceImpl implements OrderListService {

    @Autowired
    private OrderMapper orderMapper;
    /**
     * 获取订单列表数据
     * @param param 传入数据
     * @return
     */
    @Override
    public ResultEntity getOrderList(String param) {
        ResultEntity resultEntity = new ResultEntity();
        Map condition = JsonConversionUtil.getBodyNode(param, "o_id", "o_type", "startTime", "o_endTime", "o_sexLimit", "o_isBig", "u_uid", "page", "limit");
        log.debug("从前端获取封装的查询条件: " + condition);
        // 判断是否有分页条件
        Integer page = (Integer) condition.get("page");
        Integer limit = (Integer) condition.get("limit");
        page = page == null || page == 0 ? SystemConstants. DEFAULT_PAGE_INDEX : page;
        limit = limit == null || limit == 0 ? SystemConstants. DEFAULT_PAGE_SIZE : limit;
        condition.put("page",page);
        condition.put("limit",limit);
        log.debug("准备携带进dao层的查询条件数据为: "+condition);
        List orderList = orderMapper.getOrderList(condition);
        log.debug("OrderMapper查询到的结果: "+ orderList);
        return resultEntity;
    }

}
