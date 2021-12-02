package com.pyruz.egs.model.domain.product;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductNewRequest {
    @NotNull
    private Integer categoryId;

    @NotNull
    private Float price;

    @NotBlank
    private String title;
}
