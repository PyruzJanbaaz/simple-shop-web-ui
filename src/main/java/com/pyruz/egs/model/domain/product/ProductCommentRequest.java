package com.pyruz.egs.model.domain.product;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductCommentRequest {

    @NotNull
    private Integer productId;

    @NotBlank
    private String comment;
}
