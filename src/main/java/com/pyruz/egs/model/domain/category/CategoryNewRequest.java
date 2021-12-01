package com.pyruz.egs.model.domain.category;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class CategoryNewRequest {

    @NotBlank
    @ApiModelProperty(name = "title", value = "Category title...", required = true)
    private String title;
}
