package com.pyruz.egs.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceException extends  RuntimeException{
    private Integer code;
    private String message;
    private String description;
    private HttpStatus httpStatus;
}
