package com.czl.controller.paotui;

import com.czl.controller.BaseController;
import com.czl.entity.ResultEntity;
import com.czl.service.order.OrderListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/getOrderList")
    public Object getOrderListController(@RequestBody String param) {
        log.debug("进入到getOrderListController中....");
        ResultEntity resultEntity = new ResultEntity();
        try {
            log.debug("================================================开始进行格式校验===================================================");
            resultEntity = this.inputValid(param, "getOrderList");
            // 判断校验结果
            if (ResultEntity.RESULT_CODE_ERROR == resultEntity.getStatus()) {
                return resultEntity;
            }
            log.debug("================================================格式校验通过===================================================");
            resultEntity = orderListService.getOrderList(param);
            log.debug("controller层准备返回的数据是: "+ resultEntity);
        } catch (Exception e) {
            // EBC00IF000000=失败！
            log.debug("程序内部出现异常.... ");
            resultEntity.setStatus(ResultEntity.RESULT_CODE_ERROR);
            resultEntity.setMessageId("EBC00IF000000");
            return resultEntity;

        }

        return resultEntity;
    }
}