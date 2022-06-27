package cn.info.libre.service;

import javax.servlet.http.HttpServletResponse;

public interface FlowLimitService {

    void open(String filePath, String fileName, HttpServletResponse response);

    String queryOrder(String orderId);

    String queryOrder2(String orderId);
}
