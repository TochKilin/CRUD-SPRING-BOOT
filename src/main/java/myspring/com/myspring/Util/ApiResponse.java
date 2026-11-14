package myspring.com.myspring.Util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponse<T> {

    private boolean success;
    private Integer code;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    // Constructor
    public ApiResponse(boolean success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    // Default Constructor
    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    // CREATE - HTTP 201
    public static <T> ApiResponse<T> create(T data) {
        return create(data, "Create success");
    }

    public static <T> ApiResponse<T> create(T data, String message) {
        return new ApiResponse<>(
                true,
                HttpStatus.CREATED.value(),
                message,
                data
        );
    }

    // SUCCESS - HTTP 200
    public static <T> ApiResponse<T> success(T data) {
        return success(data, "Success");
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(
                true,
                HttpStatus.OK.value(),
                message,
                data
        );
    }

    // ERROR - Custom Status
    public static <T> ApiResponse<T> error(
            T data,
            String message,
            Integer status
    ) {
        return new ApiResponse<>(
                false,
                status,
                message,
                data
        );
    }

    public static <T> ApiResponse<T> error(String message, HttpStatus status) {
        return new ApiResponse<>(
                false,
                status.value(),
                message,
                null // data នឹងត្រូវកំណត់ជា null ដោយស្វ័យប្រវត្តិ
        );
    }

    // BAD REQUEST - HTTP 400
    public static <T> ApiResponse<T> badRequest(String message) {
        return error(null, message, HttpStatus.BAD_REQUEST.value());
    }

    // UNAUTHORIZED - HTTP 401
    public static <T> ApiResponse<T> unauthorized(String message) {
        return error(null, message, HttpStatus.UNAUTHORIZED.value());
    }

    // FORBIDDEN - HTTP 403
    public static <T> ApiResponse<T> forbidden(String message) {
        return error(null, message, HttpStatus.FORBIDDEN.value());
    }

    // NOT FOUND - HTTP 404
    public static <T> ApiResponse<T> notFound(String message) {
        return error(null, message, HttpStatus.NOT_FOUND.value());
    }

    // CONFLICT - HTTP 409
    public static <T> ApiResponse<T> conflict(String message) {
        return error(null, message, HttpStatus.CONFLICT.value());
    }

    // INTERNAL SERVER ERROR - HTTP 500
    public static <T> ApiResponse<T> internalServerError(String message) {
        return error(
                null,
                message,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }
}