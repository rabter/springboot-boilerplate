package com.namutech.spero.resource.router;

import com.namutech.spero.resource.port.ProvisionInstancePort;
import com.namutech.spero.resource.port.ProvisionSubnetPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProvisionPortRouter {

    /**
     * ProvisionInstancePort를 구현한 구현체의 Bean을 vendor 이름으로 매핑합니다.
     * 예: "aws", "azure", "gcp" 등, @Service("aws") 어노테이션을 기반으로 Spring이 자동으로 Bean 이름을 설정합니다.
     */
    private final Map<String, ProvisionInstancePort> provisionInstancePortMap;
    private final Map<String, ProvisionSubnetPort> provisionSubnetPortMap;

    public ProvisionInstancePort getProvisionInstancePort(String vendor) {
        return provisionInstancePortMap.get(vendor);
    }

    public ProvisionSubnetPort getProvisionSubnetPort(String vendor) {
        return provisionSubnetPortMap.get(vendor);
    }
}
