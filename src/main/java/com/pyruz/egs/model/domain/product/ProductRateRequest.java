package com.pyruz.egs.model.domain.product;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductRateRequest {

    @NotNull
    private Integer productId;

    @NotNull
    private Integer rate;
}
