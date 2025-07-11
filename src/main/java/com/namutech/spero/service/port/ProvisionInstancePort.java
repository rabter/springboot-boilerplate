package com.namutech.spero.service.port;

import com.namutech.spero.common.dto.ResourceContext;
import com.namutech.spero.dto.InstanceCreateResultResponseDTO;
import com.namutech.spero.dto.InstanceRequestDTO;

public interface ProvisionInstancePort {
    // CSP 추가 시 포트는 그대로, 어댑터만 추가
    InstanceCreateResultResponseDTO createInstance(ResourceContext context);
    void startInstance(String instanceId);
    void stopInstance(String instanceId);
    void deleteInstance(String instanceId);
    String getInstanceStatus(String instanceId);
}
