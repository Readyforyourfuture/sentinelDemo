package cn.info.libre.controller;

import cn.info.libre.pojo.entity.Result;
import cn.info.libre.service.FlowLimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/libre")
public class FlowLimitController {

    @Autowired
    private FlowLimitService flowLimitService;

    /**
     * 注解方式定义资源
     *
     * @param orderId
     * @return
     */
    @GetMapping("/queryOrderInfo")
    @ResponseBody
    public Object getOrderInfo1(@RequestParam(value = "orderId") String orderId) {
        String order = flowLimitService.queryOrder(orderId);
        return Result.ok(order);
    }

    /**
     * try-catch方式定义资源
     *
     * @param orderId
     * @return
     */
    @GetMapping("/queryOrderInfo2")
    @ResponseBody
    public Object getOrderInfo2(@RequestParam(value = "orderId") String orderId) {
        String s = flowLimitService.queryOrder2(orderId);
        return Result.ok(s);
    }
}
