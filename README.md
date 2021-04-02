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
