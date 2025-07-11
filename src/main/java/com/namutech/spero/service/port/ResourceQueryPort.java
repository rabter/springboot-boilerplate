package com.namutech.spero.service.port;

import com.namutech.spero.common.dto.ResourceContext;
import com.namutech.spero.dto.InstanceCreateRequestDTO;

public interface ResourceQueryPort {
    ResourceContext buildProvisioningContext(String cloudId, InstanceCreateRequestDTO requestDto);
}
