package com.czl.controller.paotui;

import com.czl.controller.BaseController;
import com.czl.service.order.OrderListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import static com.czl.entity.ResultEntity.RESULT_CODE_ERROR;

/**
 * 获取订单列表一览
 *
 * @author 陈正龙
 * @date 2020/5/17 16:39
 */
@RestController
@Slf4j
@SuppressWarnings({"rawtypes", "unchecked"})
public class GetOrderListController extends BaseController {

    @Autowired
    private OrderListService orderListService;

    public Object getOrderListController(String param) {
        try {


        } catch (Exception e) {


        }

        return null;
    }
}