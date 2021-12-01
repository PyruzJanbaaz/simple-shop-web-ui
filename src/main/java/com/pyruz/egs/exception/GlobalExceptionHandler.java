package com.pyruz.egs.exception;

import com.pyruz.egs.model.dto.base.BaseDTO;
import com.pyruz.egs.model.dto.base.MetaDTO;
import com.pyruz.egs.utility.ApplicationMessages;
import com.pyruz.egs.utility.ApplicationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    final ApplicationProperties applicationProperties;
    final ApplicationMessages applicationMessages;

    public GlobalExceptionHandler(ApplicationProperties applicationProperties, ApplicationMessages applicationMessages) {
        this.applicationProperties = applicationProperties;
        this.applicationMessages = applicationMessages;
    }

    // --> ServiceLevelValidation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<BaseDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        BaseDTO baseDTO = new BaseDTO();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            MetaDTO metaDTO = new MetaDTO(
                    HttpStatus.BAD_REQUEST.value(),
                    String.format(
                            applicationMessages.getProperty("application.message.validation.error.text"),
                            error.getField()
                    )
            );
            baseDTO.setMeta(metaDTO);
            break;
        }
        return new ResponseEntity<>(baseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public final ResponseEntity<BaseDTO> handleMissingParameterException(MissingServletRequestParameterException ex, WebRequest request) {
        Object convertedFieldName = applicationMessages.getProperty(ex.getParameterName());
        MetaDTO metaDTO = MetaDTO.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(
                        String.format(
                                applicationMessages.getProperty("application.message.missing.parameter.text"),
                                convertedFieldName == null ? ex.getParameterName() : convertedFieldName.toString()
                        )
                ).build();
        return new ResponseEntity<>(BaseDTO.builder().meta(metaDTO).build(), HttpStatus.BAD_REQUEST);
    }


    // --> Handler not found exceptions
    @ExceptionHandler(NoHandlerFoundException.class)
    public Object handleNoHandlerFoundException(NoHandlerFoundException ex, WebRequest request,RedirectAttributes redirectAttributes) {
        String requestedWithHeader = request.getHeader("X-Requested-With");
        if (requestedWithHeader == null) {
            redirectAttributes.addAttribute("code", HttpStatus.BAD_REQUEST.value());
            redirectAttributes.addAttribute("message", ApplicationMessages.getInstance().getProperty("application.message.internal.server.error"));
            redirectAttributes.addAttribute("description", ex.getMessage());
            return "redirect:/exceptionPage";
        } else {
            BaseDTO baseDTO = BaseDTO.builder()
                    .meta(
                            MetaDTO.builder()
                                    .code(HttpStatus.BAD_REQUEST.value())
                                    .message(ex.getMessage())
                                    .build()
                    )
                    .build();
            return new ResponseEntity<>(baseDTO, HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(Exception.class)
    public Object handleGlobalException(Exception ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String requestedWithHeader = request.getHeader("X-Requested-With");
        if (requestedWithHeader == null) {
            redirectAttributes.addAttribute("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            redirectAttributes.addAttribute("message", ApplicationMessages.getInstance().getProperty("application.message.internal.server.error"));
            redirectAttributes.addAttribute("description", ex.getMessage());
            return "redirect:/exceptionPage";
        } else {
            BaseDTO baseDTO = BaseDTO.builder()
                    .meta(
                            MetaDTO.builder()
                                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                    .message(ex.getMessage())
                                    .build()
                    )
                    .build();
            return new ResponseEntity<>(baseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Object handleAccessException(Exception ex, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String requestedWithHeader = request.getHeader("X-Requested-With");
        if (requestedWithHeader == null) {
            redirectAttributes.addAttribute("code", HttpStatus.FORBIDDEN.value());
            redirectAttributes.addAttribute("message", ApplicationMessages.getInstance().getProperty("application.message.access.denied"));
            return "redirect:/exceptionPage";
        } else {
            BaseDTO baseDTO = BaseDTO.builder()
                    .meta(
                            MetaDTO.builder()
                                    .code(HttpStatus.FORBIDDEN.value())
                                    .message(ex.getMessage())
                                    .build()
                    )
                    .build();
            return new ResponseEntity<>(baseDTO, HttpStatus.FORBIDDEN);
        }
    }

    // --> Custom exceptions
    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<BaseDTO> handleServiceException(ServiceException ex, WebRequest request) {
        BaseDTO baseDTO = BaseDTO.builder()
                .meta(
                        MetaDTO.builder()
                                .code(ex.getCode())
                                .message(ex.getMessage())
                                .build()
                )
                .build();
        return new ResponseEntity<>(
                baseDTO,
                ex.getCode() == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.resolve(ex.getCode())
        );
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public String handleHttpClientErrorException(HttpClientErrorException ex, HttpServletRequest request, final RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("code", ex.getStatusCode().value());
        redirectAttributes.addAttribute("message", ex.getStatusCode().getReasonPhrase());
        redirectAttributes.addAttribute("description", ex.getStatusText());
        return "redirect:/exceptionPage";
    }

}
