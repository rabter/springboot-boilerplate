package com.namutech.spero.service.port;

import com.namutech.spero.common.dto.ResourceContext;
import com.namutech.spero.dto.InstanceCreateResultResponseDTO;

public interface ResourcePersistencePort {
    void saveInstanceWithResources(InstanceCreateResultResponseDTO result, ResourceContext context);
}
