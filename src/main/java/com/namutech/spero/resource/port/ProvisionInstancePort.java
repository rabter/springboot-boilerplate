package com.namutech.spero.resource.port;

import com.namutech.spero.dto.InstanceCreateRequestDTO;
import com.namutech.spero.resource.context.ResourceContext;
import com.namutech.spero.dto.InstanceCreateResultResponseDTO;

public interface ProvisionInstancePort {
    // CSP 추가 시 포트는 그대로, 어댑터만 추가
    InstanceCreateResultResponseDTO createInstance(ResourceContext<InstanceCreateRequestDTO> context);
    void startInstance(String instanceId);
    void stopInstance(String instanceId);
    void deleteInstance(String instanceId);
    String getInstanceStatus(String instanceId);
}
