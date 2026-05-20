# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
# Build
./gradlew build

# Run (uses dev profile by default)
./gradlew bootRun

# Run all tests
./gradlew test

# Run a single test class
./gradlew test --tests "com.namutech.spero.BillingTest"

# Run a single test method
./gradlew test --tests "com.namutech.spero.BillingTest.testMethodName"
```

### Local Kafka (required for `kafka` profile)
```bash
docker-compose up -d
```

## Spring Profiles

| Profile | Purpose | Security |
|---------|---------|----------|
| `dev` (default) | Development with MariaDB | CSRF disabled, all requests permitted |
| `sso` | Production with SSO | OAuth2 JWT (RS256) via `spero.security.jwk-url`; HTTPS on port 8443 |
| `kafka` | Kafka-enabled mode | Same as `dev` |

Each profile has its own `application-{profile}.yml`. Required environment variables:
- **dev/kafka**: `DEV_DB_URL`, `DEV_DB_USERNAME`, `DEV_DB_PASSWORD`
- **sso**: `JWK_URL`, `ALLOWED_ORIGINS`
- **base**: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `ALLOWED_ORIGINS`

## Project Structure

This is a multi-project Gradle build. `spero-commonlib` is a Git submodule included as a [composite build](https://docs.gradle.org/current/userguide/composite_builds.html) via `settings.gradle`. It publishes as `com.namutech.spero.commonlib:commonlib:0.0.1-commonlib-SNAPSHOT`.

```
src/main/java/com/namutech/spero/
├── common/              # Shared infrastructure
│   ├── ApiResponse.java          # Standard response wrapper {success, data, paging}
│   ├── ApiErrorResponse.java     # Error response wrapper
│   ├── client/                   # HTTP clients (Sync: RestTemplate, Async: WebClient)
│   ├── config/                   # Spring configs (Security, CORS, QueryDSL, Kafka)
│   ├── converter/                # JPA AttributeConverters for enums
│   ├── dto/                      # BaseSearchDTO, PagingInfoDTO
│   ├── exception/                # GlobalExceptionHandler (@RestControllerAdvice)
│   ├── service/GenericService    # Abstract paginated QueryDSL service
│   └── util/                     # PagingUtil, PredicateBuilderHelper, CommonUtil
├── resource/            # Hexagonal architecture for cloud provisioning
│   ├── port/                     # Interfaces: ProvisionInstancePort, PersistenceInstancePort, QueryInstancePort
│   ├── adapter/                  # Implementations per vendor: aws/, azure/, vmware/, ovirt/, persistence/
│   ├── router/ProvisionPortRouter # Routes vendor string → correct Port bean
│   └── context/ResourceContext   # Carries Cloud entity + request DTO + vendor-specific attributes
├── service/             # Business services (BillingService, ConfigService, EquipmentService, ResourceManagerService)
├── controller/          # REST controllers
├── entity/              # JPA entities
├── repository/          # Spring Data JPA repos + QueryDSL custom repos
├── dto/                 # Request/response DTOs
├── enums/               # VendorType, ConfigGroup, EquipmentStatus
└── kafka/               # producer/ and consumer/ for Kafka events
```

## Key Architectural Patterns

### Hexagonal Architecture (Cloud Provisioning)

The `resource/` package implements ports-and-adapters for multi-cloud/hypervisor instance management:

1. `ResourceManagerService` orchestrates: query DB → provision via CSP → persist result
2. `ProvisionPortRouter` holds a `Map<String, ProvisionInstancePort>` auto-populated by Spring using bean names — each adapter is annotated `@Service("aws")`, `@Service("azure")`, etc.
3. `ResourceContext<T>` passes a `Cloud` entity, the request DTO, and a vendor-specific attribute map (`EnumMap<ResourceAttribute, Object>`) through the flow.
4. To add a new cloud vendor: implement `ProvisionInstancePort` with `@Service("vendorName")` — no router changes needed.

### Paging Pattern

All paginated list endpoints use this stack:

- **Search condition DTO** extends `BaseSearchDTO` (which provides `pageNumber` (1-based, default 1) and `pageSize` (default 10) via `@SuperBuilder`)
- **Service** extends `GenericService<Entity, QEntity, ConditionDTO>` and calls `findAll(condition, QEntity.entity, predicate)` which handles 1→0 page index conversion internally
- **Response** wraps data in `ApiResponse<T>` with `PagingInfoDTO` built via `PagingUtil.buildPagingInfo(page)` (converts back to 1-based page number)
- **Predicate building** uses `PredicateBuilderHelper` static methods: `eq()`, `like()` (case-insensitive contains), `in()`, `between()`

### QueryDSL

Q-classes are generated via annotation processing. After adding/modifying entities, rebuild to regenerate them. `ConfigRepository` uses the `ConfigRepositoryCustom` interface pattern for complex custom queries.

### Enum Persistence

Enums stored in DB use `AttributeConverter` (not `@Enumerated`): see `ConfigGroupConverter`, `EquipmentStatusConverter`. When adding a new enum that needs DB persistence, implement `AttributeConverter<EnumType, String>`.

### External API Clients

Three HTTP client abstractions exist in `common/client/`:
- `SyncExternalApiClient` — synchronous via `RestTemplate`
- `AsyncExternalApiClient` — reactive via `WebClient` returning `Mono<T>`
- `RestTemplateClient` — lower-level `RestTemplate` wrapper

## Testing

All tests are `@SpringBootTest` integration tests that hit a real database. Tests use `@Transactional @Rollback` to avoid persisting test data. There are no mock-based unit tests — do not introduce them.
