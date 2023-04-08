package org.example.common;

/**
 * @author Christy Guo
 * @create 2023-03-27 11:16 PM
 */
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Data
public class R<T> {

    private Integer code; // 1 for success, 0 or other numbers for failure
    private String msg; // error message
    private T data; // data
    private Map<String, Object> dynamicData = new HashMap<>(); // dynamic data

    public static <T> R<T> success(T object) {
        R<T> r = new R<>();
        r.setData(object);
        r.setCode(1);
        return r;
    }

    public static <T> R<T> error(String msg) {
        R<T> r = new R<>();
        r.setMsg(msg);
        r.setCode(0);
        return r;
    }

    public R<T> addDynamicData(String key, Object value) {
        this.dynamicData.put(key, value);
        return this;
    }

}