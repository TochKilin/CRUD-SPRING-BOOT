package myspring.com.myspring.Exceptions;

import myspring.com.myspring.Util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicate(DuplicateResourceException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(ex.getMessage(), HttpStatus.CONFLICT));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    // handle exception validation DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException exe){
        Map<String, String> errors = new HashMap<>();
        exe.getBindingResult().getFieldErrors().forEach(item ->
//                    item.getField();
//                      item.getDefaultMessage()
                errors.put(item.getField(), item.getDefaultMessage())

                );
        Map<String, Object> res = new HashMap<>();
          res.put("success", false);
          res.put("code", HttpStatus.BAD_REQUEST.value());
          res.put("message", "Validation fails");
          res.put("timestamp", LocalDateTime.now());
        res.put("errors",errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }
    @ExceptionHandler(Exception.class)
        public ResponseEntity<?> handleException(Exception ex){


        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Something wrong", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}

