package com.pyruz.egs.model.domain.category;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class CategoryNewRequest {

    @NotBlank
    private String title;
}
