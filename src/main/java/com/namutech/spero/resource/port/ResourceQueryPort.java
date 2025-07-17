package com.namutech.spero.resource.port;

import com.namutech.spero.resource.context.ResourceContext;
import com.namutech.spero.dto.InstanceCreateRequestDTO;

public interface ResourceQueryPort {
    ResourceContext<InstanceCreateRequestDTO> buildInstanceProvisioningContext(InstanceCreateRequestDTO requestDTO);
    // ResourceContext buildVpcProvisioningContext(VpcCreateRequestDTO requestDTO);
}
