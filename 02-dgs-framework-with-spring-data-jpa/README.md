Java DGS Framework example (with Spring Data JPA)
=====

This repository is an example application for the [DGS Framework](https://netflix.github.io/dgs). 
The example is a standalone GraphQL server in Java that interacts with a PostgreSQL database via Spring Data JPA.

It shows the following features:
* Datafetchers
* Mutations
* DataLoader to prevent the N+1 problem
* Lombok for model classes
* Spring Data JPA


Agencies, Agents, and Properties
----

This example is built around three main types: Agencies, Agents, and Properties.

Each real estate agency has an ID, a name, a tax code, and is associated with N agents and N properties. 

Each real estate agent has a UUID, a full name and is associated with only one agency. 

Each property has an ID, a name, a type (enumeration), coordinates, a purchase date and is associated with only one real estate agency.
Each property can be created, updated or deleted using a mutation.


The schema file is located in the following path.
```sh
/src/main/resources/schema/schema.graphql
```

Starting the example
----

The example requires Java 17 and PostgreSQL 14.

Create a PostgreSQL database, using the database name, username, and password contained in `application.yml`. 

Run the application in an IDE using its main class or using Gradle: 

```
./gradlew bootRun
```

Interact with the application using GraphiQL on http://localhost:8081/graphiql.

