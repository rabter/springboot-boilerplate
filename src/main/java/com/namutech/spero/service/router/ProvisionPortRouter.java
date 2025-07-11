package com.namutech.spero.service.router;

import com.namutech.spero.service.port.ProvisionInstancePort;
import com.namutech.spero.service.port.ProvisionSubnetPort;
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
