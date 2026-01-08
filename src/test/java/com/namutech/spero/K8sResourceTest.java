package com.namutech.spero;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.namutech.spero.dto.WorkloadResourcesResponseDTO;
import com.namutech.spero.entity.ServiceDesiredK8sResource;
import com.namutech.spero.service.ServiceDesiredK8sResourceService;
import com.namutech.spero.service.ServiceObservedK8sResourceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@Transactional
//@Rollback
@Commit
//@Disabled // @Disabled 어노테이션은 테스트를 비활성화합니다. 필요에 따라 제거하세요.
public class K8sResourceTest {

    @Autowired
    private ServiceObservedK8sResourceService serviceObservedK8sResourceService;

    @Autowired
    private ServiceDesiredK8sResourceService serviceDesiredK8sResourceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("워크로드 트리 조회 테스트")
    public void getWorkloadResourcesTree() throws JsonProcessingException {
        Long serviceId = 101L;
        String cloudId = "CLOUD-ONPREM-001";
        String namespace = "spero-prod";
        WorkloadResourcesResponseDTO workloadResourcesResponseDTO = serviceObservedK8sResourceService.getWorkloadResourcesTree(serviceId, cloudId, namespace);

        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(workloadResourcesResponseDTO);
        log.info("WorkloadResourcesResponseDTO: {}", json );
    }

    @Test
    @DisplayName("detailYaml 조회")
    public void getObservedYaml() {
        Long observedK8sResourceId = 1L;
        String observedYaml = serviceObservedK8sResourceService.getObservedYaml(observedK8sResourceId);

        log.info("Observed YAML: {}", observedYaml);
    }

    @Test
    @DisplayName("Desired Yaml 조회")
    public void getLatestDesiredYaml() {
        Long desiredK8sResourceId = 1L;
        String desiredYaml = serviceDesiredK8sResourceService.getLatestDesiredYaml(desiredK8sResourceId);

        log.info("Desired YAML: {}", desiredYaml);
    }

    @Test
    @DisplayName("Desired YAML 수정")
    public void createServiceDesiredK8sResource() {
        Long serviceId = 101L;
        String cloudId = "CLOUD-ONPREM-001";
        String namespace = "spero-prod";
        String kind = "DEPLOYMENT";
        String name = "bff-api";
        String updatedDesiredYaml = """
                apiVersion: apps/v1
                kind: Deployment
                metadata:
                  name: bff-api
                  namespace: spero-prod
                  labels:
                    app: bff-api
                    spero.io/serviceId: "101"
                spec:
                  replicas: 4
                  selector:
                    matchLabels:
                      app: bff-api
                  template:
                    metadata:
                      labels:
                        app: bff-api
                        spero.io/serviceId: "101"
                    spec:
                      containers:
                      - name: bff-api
                        image: harbor.local/spero/bff-api:1.0.0
                        ports:
                        - containerPort: 8080
                
                """;
        ServiceDesiredK8sResource serviceDesiredK8sResource = serviceDesiredK8sResourceService.createServiceDesiredK8sResource(
                serviceId, cloudId, namespace, kind, name, updatedDesiredYaml);
    }
}
