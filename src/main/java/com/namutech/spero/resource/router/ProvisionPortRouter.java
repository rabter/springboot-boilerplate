package com.namutech.spero.resource.router;

import com.namutech.spero.resource.port.ProvisionInstancePort;
import com.namutech.spero.resource.port.ProvisionSubnetPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProvisionPortRouter {

    private final Map<String, ProvisionInstancePort> provisionInstancePortMap;
    private final Map<String, ProvisionSubnetPort> provisionSubnetPortMap;

    public ProvisionInstancePort getProvisionInstancePort(String vendor) {
        return provisionInstancePortMap.get(vendor);
    }

    public ProvisionSubnetPort getProvisionSubnetPort(String vendor) {
        return provisionSubnetPortMap.get(vendor);
    }
}
