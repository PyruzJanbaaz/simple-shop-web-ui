package com.pyruz.egs.model.domain.category;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CategoryUpdateRequest {

    @NotNull
    private Integer id;

    @NotBlank
    private String title;
}
