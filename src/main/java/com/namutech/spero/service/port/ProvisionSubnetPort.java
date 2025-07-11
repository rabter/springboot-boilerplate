package com.namutech.spero.service.port;

import com.namutech.spero.dto.SubnetCreateRequestDTO;

public interface ProvisionSubnetPort {
    void createSubnet(SubnetCreateRequestDTO subnetCreateRequestDto);
    void deleteSubnet(String subnetId);
}
