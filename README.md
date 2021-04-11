# Inventory app - backend

## Project setup

Before running application:

```
docker-compose up
```

## Get frontend up and running
[Clone frontend from here](https://github.com/borzah/inventory-system-frontend)

## Swagger documentation

http://localhost:8080/api/swagger-ui.html


### Test users:

| Username         |   Password | Role   |
|------------------|------------|--------|
|  admin@mail.com  | password   | Admin  |
|  john@mail.com  | password   | User    |

## In case of liquibase checksum error

Try following gradle liquibase commands:

```
clearChecksums

dropAll
```

If this doesn't help:
```
docker-compose down

docker-compose up
```
