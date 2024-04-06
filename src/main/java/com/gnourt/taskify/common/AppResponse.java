package com.gnourt.taskify.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class AppResponse<T> {
    private Integer statusCode;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private T data;
    private String token;



    public AppResponse(Integer statusCode, String message,String token) {
        this.statusCode = statusCode;
        this.message = message;
        this.token = token;
    }

    public AppResponse(Integer statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }



    public static <T> AppResponse<T> empty() {
        return success(null);
    }

    public static <T> AppResponse<T> success(T data) {
        return AppResponse.<T>builder()
                .message("success")
                .statusCode(200)
                .data(data)
                .build();
    }

    public static <T> AppResponse<T> error() {
        return AppResponse.<T>builder()
                .message("error")
                .build();
    }
}
