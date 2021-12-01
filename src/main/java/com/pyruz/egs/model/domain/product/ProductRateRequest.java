package com.pyruz.egs.model.domain.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductRateRequest {

    @NotNull
    @ApiModelProperty(name = "productId", value = "12", required = true)
    private Integer productId;

    @NotNull
    @ApiModelProperty(name = "rate", value = "1-5", required = true)
    private Integer rate;
}
