package com.namutech.spero.common.client.dto;

import com.namutech.spero.dto.BillingDTO;
import lombok.Data;

import java.util.List;

@Data
public class BillingListResponseDTO {
    private List<BillingDTO> data;
}
