Java DGS Framework example (basic features)
=====

This repository is an example application for the [DGS Framework](https://netflix.github.io/dgs).
The example is a standalone GraphQL server in Java.

It shows the following features:
* Datafetchers
* Mutations
* DataLoader to prevent the N+1 problem
* Query testing
* Using a generated Query API
* File Upload
* Using the Gradle codegen plugin
* A custom instrumentation implementation
* Subscriptions
* Testing a subscription
* Registering an optional scalar from graphql-java


Shows and Reviews
----

This example is built around two main types: Show and Review.
A `Show` represents a series or movie you would find on Netflix.
For ease of running the demo, the list of shows is hardcoded in ShowsServiceImpl.
A show can have `Reviews`.
Again, for ease of running the demo, a list of reviews is generated during startup for each show in DefaultReviewsService.

Reviews can also be added by users of the API using a mutation, and a GraphQL Subscription is available to watch for added reviews.

There's also a mutation available to add Artwork for a show, demonstrating file uploads.
Uploaded files are stored in a folder `uploaded-images` in the work directory where the application is started.

Starting the example
----

The example requires Java 11.
Run the application in an IDE using its main class or using Gradle: 

```
./gradlew bootRun
```

Interact with the application using GraphiQL on http://localhost:8080/graphiql.
