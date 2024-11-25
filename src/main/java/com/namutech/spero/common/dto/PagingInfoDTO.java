package com.namutech.spero.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PagingInfo {
    private int pageNumber;
    private int pageSize;
    private int offset;
    private int totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;
}
