package com.namutech.spero.resource.port;

import com.namutech.spero.dto.SubnetCreateRequestDTO;

public interface ProvisionSubnetPort {
    void createSubnet(SubnetCreateRequestDTO subnetCreateRequestDto);
    void deleteSubnet(String subnetId);
}
