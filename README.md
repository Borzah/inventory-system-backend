# Inventory app - backend

## Project setup

Before running application:

```
docker-compose up
```

## Get frontend up and running
[Clone frontend from here](https://github.com/borzah/inventory-system-frontend)

### In case of liquibase checksum error

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

## Swagger documentation

http://localhost:8080/swagger-ui


### Test users:

| Username         |   Password | Role   |
|------------------|------------|--------|
|  admin@mail.com  | password   | Admin  |
|  john@mail.com  | password   | User    |
|  anna@mail.com  | password   | User    |
|  mike@mail.com  | password   | User    |
|  jane@mail.com  | password   | User    |

