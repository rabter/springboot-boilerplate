package com.namutech.spero.common.util;

import com.namutech.spero.common.dto.PagingInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;

public class PagingUtil {

    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;

    public static PagingInfoDTO buildPagingInfo(Page<?> page) {
        return PagingInfoDTO.from(page);
    }

    public static Pageable toPageable(int pageNumber, int pageSize) {
        int normalizedPageNumber = pageNumber < 1 ? DEFAULT_PAGE_NUMBER : pageNumber;
        int normalizedPageSize = pageSize < 1 ? DEFAULT_PAGE_SIZE : pageSize;

        return PageRequest.of(normalizedPageNumber - 1, normalizedPageSize);
    }

    public static <T, R> Page<R> map(Page<T> source, Function<T, R> mapper) {
        List<R> content = source.getContent()
                .stream()
                .map(mapper)
                .toList();

        return new PageImpl<>(content, source.getPageable(), source.getTotalElements());
    }
}
