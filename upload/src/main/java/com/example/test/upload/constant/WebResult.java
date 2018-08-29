package com.example.test.upload.constant;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ceshi
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2018/8/2917:17
 */
public class WebResult {
    @JsonProperty
    private String url;

    @JsonProperty
    private boolean success;

    @JsonProperty
    private Object data;

    @JsonProperty
    private String message;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "WebResult{" +
                "url='" + url + '\'' +
                ", success=" + success +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
