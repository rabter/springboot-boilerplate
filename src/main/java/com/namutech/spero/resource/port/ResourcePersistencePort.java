package com.namutech.spero.resource.port;

import com.namutech.spero.resource.context.ResourceContext;
import com.namutech.spero.dto.InstanceCreateResultResponseDTO;

public interface ResourcePersistencePort {
    void saveInstanceWithResources(InstanceCreateResultResponseDTO result, ResourceContext context);
}
