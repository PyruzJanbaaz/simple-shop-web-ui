package com.pyruz.egs.model.dto.base;

import com.pyruz.egs.utility.ApplicationProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetaDTO {
    private Integer code;
    private String message;

    public static MetaDTO getInstance(ApplicationProperties applicationProperties) {
        return new MetaDTO(
                HttpStatus.OK.value(),
                applicationProperties.getProperty("application.message.operation.success.text")
        );
    }

    public static MetaDTO getInstance(Integer code, String message) {
        return new MetaDTO(code, message);
    }

}
