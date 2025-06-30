# springboot-boilerplate
springboot boilerplate

## 구동방식
### 1. dev 환경

application.yml dev 프로파일을 사용합니다.
``` yaml
spring:
  profiles: dev
```

application-dev.yml 파일을 수정하여 환경변수를 설정합니다.

```yaml
datasource:
  url: ${DEV_DB_URL}
  username: ${DEV_DB_USERNAME}
  password: ${DEV_DB_PASSWORD}
  driver-class-name: org.mariadb.jdbc.Driver

spero:
  security:
    jwt-url: ""
```

### 2. sso 환경

application.yml sso 프로파일을 사용합니다.
``` yaml
spring:
  profiles: sso
```
application-sso.yml 파일을 수정하여 환경변수를 설정합니다.

### 3. kafka 환경

application.yml kafka 프로파일을 사용합니다.
``` yaml
spring:
  profiles: kafka
```
application-kafka.yml 파일을 수정하여 환경변수를 설정합니다.


