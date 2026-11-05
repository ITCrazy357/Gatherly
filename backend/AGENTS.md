# Gatherly backend conventions

- Read `../docs/backend-architecture.md` before adding business code.
- Keep `BackendApplication` in the root package `com.gatherly`.
- Group code by feature: `com.gatherly.<feature>`, with `controller`, `dto`,
  `service`, `model`, and `repository` packages when needed.
- Keep controllers focused on HTTP and business rules in services. Use
  constructor injection. Do not add empty classes or packages for future work.
- Do not recreate the removed ping/hello/OOP practice files as application code.
- New business endpoints use `/api/v1`; use `/actuator/health` for health checks.
- Never hardcode credentials. Local settings live in the ignored root `.env`.
- Preserve PostgreSQL volumes. Keep Hibernate `ddl-auto=none` and
  `open-in-view=false`; introduce Flyway when adding the first database schema.
- Use JDK 21 and the checked-in Maven Wrapper. From the repository root,
  run `./scripts/backend.ps1 -Task verify` for a clean build and tests.
  This currently requires the local PostgreSQL service started with
  `docker compose up -d --wait postgres`.
- The product scope is not finalized. Confirm the first business capability
  with the user before implementing domain-specific features.
