package cn.info.libre.utils;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionUtil {

    public static String blockExceptionHandle(String orderId, BlockException blockException) {
        String format = String.format("order block:%s，%s", orderId, blockException.getClass().getCanonicalName());
        log.info(format);
        return format;

    }

    public static String fallbackExceptionHandle(String orderId, Throwable t) {
        String format = String.format("order fallback:%s，%s", orderId, t.getClass().getCanonicalName());
        log.info(format);
        return format;
    }

    public static String blockExceptionHandleDegrade(String orderId, BlockException blockException) {
        String format = String.format("order degrade block:%s，%s", orderId, blockException.getClass().getCanonicalName());
        log.info(format);
        return format;

    }

    public static String fallbackExceptionHandleDegrade(String orderId, Throwable t) {
        String format = String.format("order degrade fallback:%s，%s", orderId, t.getClass().getCanonicalName());
        log.info(format);
        return format;
    }
}
