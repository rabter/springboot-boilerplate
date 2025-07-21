package com.namutech.spero.resource.port;

import com.namutech.spero.dto.InstanceCreateRequestDTO;
import com.namutech.spero.resource.context.ResourceContext;
import com.namutech.spero.dto.InstanceCreateResultResponseDTO;

public interface PersistenceInstancePort {
    void saveInstanceWithResources(InstanceCreateResultResponseDTO result, ResourceContext<InstanceCreateRequestDTO> context);
}
