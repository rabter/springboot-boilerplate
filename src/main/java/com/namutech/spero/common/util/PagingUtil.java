package com.namutech.spero.common.util;

import com.namutech.spero.common.dto.PagingInfoDTO;
import org.springframework.data.domain.Page;

public class PagingUtil {

    public static PagingInfoDTO createPagingInfo(Page<?> page) {
        return PagingInfoDTO.builder()
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }
}
