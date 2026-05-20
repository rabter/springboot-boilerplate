package com.namutech.spero.common.dto;

import com.namutech.spero.common.util.PagingUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Pageable;

@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseSearchDTO {
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Builder.Default
    private int pageNumber = DEFAULT_PAGE_NUMBER;

    @Builder.Default
    private int pageSize = DEFAULT_PAGE_SIZE;

    public int getPageNumber() {
        return pageNumber < 1 ? DEFAULT_PAGE_NUMBER : pageNumber;
    }

    public int getPageSize() {
        return pageSize < 1 ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public Pageable toPageable() {
        return PagingUtil.toPageable(getPageNumber(), getPageSize());
    }
}
