# API with Criteria4s

This is a simple API that uses the Criteria4s library to filter reviews data from two databases (mongodb and
postgresql).

## Requirements

- Docker and docker-compose
- Scala
- Sbt
- Java 8+

## How to run

1. Clone this repository
2. Run `docker-compose up` in the docker folder.

```bash
docker compose -f docker/mongodb/docker-compose.yml up -d
```

```bash
docker compose -f docker/postgresql/docker-compose.yml up -d
```

3. Launch the API from the module `server` using the following command:

Run `Main` in the `server` module.

or

```bash
sbt "project server" run
```

4. The API will be available at `http://localhost:4321`
5. **Reviews** data can be accessed at `http://localhost:4321/reviews`

## Feed data to the databases

Postgresql database is fed with data during the docker deployment. To feed data to the mongodb database, run the
following:

Run `MongoDBData` in the `datastores` module.

or

```bash
sbt "project datastores" "run"
```

## Endpoints

- `GET /reviews`: Get all reviews
- `GET /reviews?rate=<n>`: Get all reviews with rate grater or equal than `n`
- `GET /reviews?rate=<n>&owner=<name>`: Get all reviews with rate grater or equal than `n` and owner `name`

## Switching between databases

To switch between databases, change the `DBConfig` in the `server` module in the `Main` file, change the client in
the `ApiServer` file and the `ReviewRepository` in the `ReviewService` file and run the API again.