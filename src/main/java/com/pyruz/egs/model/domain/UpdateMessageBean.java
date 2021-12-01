package com.pyruz.egs.model.domain;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class UpdateMessageBean {
    @NotBlank
    private String id;
    @NotBlank
    private String message;
}

