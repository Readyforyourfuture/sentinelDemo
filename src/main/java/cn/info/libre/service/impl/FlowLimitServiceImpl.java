package cn.info.libre.service.impl;

import cn.info.libre.service.FlowLimitService;
import cn.info.libre.utils.ExceptionUtil;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.jodconverter.DocumentConverter;
import org.jodconverter.LocalConverter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Slf4j
@Service
public class FlowLimitServiceImpl implements FlowLimitService {

    private String KEY = "queryOrder";

    @Override
    public void open(String filePath, String fileName, HttpServletResponse response) {
        String intPath = filePath + File.separator + fileName;
        try {
            FileInputStream inputStream = new FileInputStream(intPath);
            DocumentConverter documentConverter = LocalConverter.builder().build();
        } catch (FileNotFoundException e) {
            log.error("文件展示异常：", e);
        }

    }


    @Override
    @SentinelResource(value = "queryOrder", blockHandler = "blockExceptionHandle", blockHandlerClass = {ExceptionUtil.class},
            fallback = "fallbackExceptionHandle", fallbackClass = {ExceptionUtil.class})
    public String queryOrder(String orderId) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("线程休眠时中断");
        }
        log.info("注解获取到订单ID:{}", orderId);
        return "return orderInfo:" + orderId;
    }

    @Override
    public String queryOrder2(String orderId) {
        Entry entry = null;
        try {
            entry = SphU.entry(KEY);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("线程休眠时中断");
            }
            log.info("try-catch获取到订单ID:{}", orderId);
            return "return orderInfo:" + orderId;
        } catch (BlockException be) {
            log.error("queryOrderInfo2接口被限流，", be);
            return "queryOrderInfo2接口被限流";
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }
}
