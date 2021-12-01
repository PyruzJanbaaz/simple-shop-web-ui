package com.pyruz.egs.model.dto.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseDTO<T> {
    private MetaDTO meta;
    private T data;
    private Integer total;
}
