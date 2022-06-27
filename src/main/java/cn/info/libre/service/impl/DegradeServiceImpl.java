package cn.info.libre.service.impl;


import cn.info.libre.service.DegradeService;
import cn.info.libre.utils.ExceptionUtil;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DegradeServiceImpl implements DegradeService {

    private String KEY = "degradeOrder";

    @Override
    @SentinelResource(value = "degradeOrder", blockHandler = "blockExceptionHandleDegrade", blockHandlerClass = {ExceptionUtil.class},
            fallback = "fallbackExceptionHandleDegrade", fallbackClass = {ExceptionUtil.class})
    public String degradeOrder(String orderId) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("线程休眠时中断");
        }
        log.info("degrade 注解获取到订单ID:{}", orderId);
        return "return degrade orderInfo:" + orderId;
    }

    @Override
    public String degradeOrder2(String orderId) {
        Entry entry = null;
        try {
            entry = SphU.entry(KEY);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("线程休眠时中断");
            }
            log.info("degrade try-catch获取到订单ID:{}", orderId);
            return "return degrade orderInfo:" + orderId;
        } catch (BlockException be) {
            log.error("degradeOrder2接口被熔断，", be);
            return "degradeOrder2接口被熔断";
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }
}
