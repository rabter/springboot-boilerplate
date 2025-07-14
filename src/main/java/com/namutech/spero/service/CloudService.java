package com.namutech.spero.service;

import com.namutech.spero.entity.Cloud;
import com.namutech.spero.repository.CloudRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloudService {

    private final CloudRepository cloudRepository;

    public Cloud getCloudById(String cloudId) {
        return cloudRepository.findById(cloudId)
                .orElseThrow(() -> new IllegalArgumentException("Cloud not found with id: " + cloudId));
    }
}
