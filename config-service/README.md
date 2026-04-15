# Eco-Travel Config Service

Centralized configuration provider for all microservices.

## Specs
- **Port:** 8888
- **Profiles:** `native` (local files), `git` (remote repo).

## Logic
- **Storage:** Serves `.properties` files from `config-repo`.
- **Boot Order:** Must be started **first** so other services can fetch their settings.

## Usage
Verify config via: `http://localhost:8888/{service-name}/{profile}`

## Run
`mvn spring-boot:run`