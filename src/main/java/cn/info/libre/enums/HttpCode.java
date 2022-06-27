package cn.info.libre.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum HttpCode {
    SUCCESS(200, "请求成功"),

    BAD_PARAM(400, "参数错误"),

    REQ_NOT_FOUND(404, "请求资源找不到"),

    SERVER_ERROR(500, "服务器内部错误");

    private Integer code;
    private String msg;
}
