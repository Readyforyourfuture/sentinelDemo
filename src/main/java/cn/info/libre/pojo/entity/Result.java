package cn.info.libre.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> ok() {
        HttpStatus ok = HttpStatus.OK;
        Result<T> result = new Result<>();
        result.setCode(ok.value());
        result.setMessage(ok.getReasonPhrase());
        return result;
    }

    public static <T> Result<T> ok(T data) {
        Result<T> ok = ok();
        ok.setData(data);
        return ok;
    }

    public static <T> Result<T> error400() {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        Result<T> result = new Result<>();
        result.setCode(badRequest.value());
        result.setMessage(badRequest.getReasonPhrase());
        return result;
    }

    public static <T> Result<T> error400(String msg) {
        Result<T> result = error400();
        result.setMessage(msg);
        return result;
    }

    public static <T> Result<T> error500() {
        HttpStatus badRequest = HttpStatus.INTERNAL_SERVER_ERROR;
        Result<T> result = new Result<>();
        result.setCode(badRequest.value());
        result.setMessage(badRequest.getReasonPhrase());
        return result;
    }

    public static <T> Result<T> error500(String msg) {
        Result<T> result = error500();
        result.setMessage(msg);
        return result;
    }

}
