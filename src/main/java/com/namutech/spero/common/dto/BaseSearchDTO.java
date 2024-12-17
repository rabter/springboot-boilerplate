package com.namutech.spero.common.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseSearchDTO {
    private static final int DEFAULT_PAGE_SIZE = 10;

    private int pageNumber;

    @Builder.Default
    private int pageSize = DEFAULT_PAGE_SIZE;

}
