Java DGS Framework example
=====

This repository is an example application for the [DGS Framework](https://netflix.github.io/dgs).
The example is a standalone GraphQL server in Java.

It shows the following features:
* [Datafetchers](https://github.com/Netflix/dgs-examples-java/blob/main/src/main/java/com/example/demo/datafetchers/ShowsDatafetcher.java#L26)
* [Mutations](https://github.com/Netflix/dgs-examples-java/blob/main/src/main/java/com/example/demo/datafetchers/ReviewsDataFetcher.java#L50) 
* [DataLoader to prevent the N+1 problem](https://github.com/Netflix/dgs-examples-java/blob/main/src/main/java/com/example/demo/datafetchers/ReviewsDataFetcher.java#L40)
* [Query testing](https://github.com/Netflix/dgs-examples-java/blob/main/src/test/java/com/example/demo/ShowsDatafetcherTest.java#L61)
* [Using a generated Query API](https://github.com/Netflix/dgs-examples-java/blob/main/src/test/java/com/example/demo/ShowsDatafetcherTest.java#L89)  
* [File Upload](https://github.com/Netflix/dgs-examples-java/blob/main/src/main/java/com/example/demo/datafetchers/ArtworkUploadDataFetcher.java#L22)
* [Using the Gradle codegen plugin](https://github.com/Netflix/dgs-examples-java/blob/main/build.gradle.kts#L45)
* [A custom instrumentation implementation](https://github.com/Netflix/dgs-examples-java/blob/main/src/main/java/com/example/demo/instrumentation/ExampleTracingInstrumentation.java#L20)
* [Subscriptions](https://github.com/Netflix/dgs-examples-java/blob/main/src/main/java/com/example/demo/datafetchers/ReviewsDataFetcher.java#L60)
* [Testing a subscription](https://github.com/Netflix/dgs-examples-java/blob/main/src/test/java/com/example/demo/ReviewSubscriptionTest.java#L46)  
* [Registering an optional scalar from graphql-java](https://github.com/Netflix/dgs-examples-java/blob/main/src/main/java/com/example/demo/scalars/DateTimeScalar.java#L16)

Other examples
---

There are other examples of using the DGS framework as well:

* [Kotlin implementation of this example](https://github.com/Netflix/dgs-examples-kotlin)
* [Federation examples (with Apollo Gateway)](https://github.com/Netflix/dgs-federation-example)

Shows and Reviews
----

This example is built around two main types: [Show](https://github.com/Netflix/dgs-examples-java/blob/main/src/main/resources/schema/schema.graphqls#L14) and [Review](https://github.com/Netflix/dgs-examples-java/blob/main/src/main/resources/schema/schema.graphqls#L22).
A `Show` represents a series or movie you would find on Netflix.
For ease of running the demo, the list of shows is hardcoded in [ShowsServiceImpl](https://github.com/Netflix/dgs-examples-java/blob/main/src/main/java/com/example/demo/services/ShowsServiceImpl.java).
A show can have `Reviews`.
Again, for ease of running the demo, a list of reviews is generated during startup for each show in [DefaultReviewsService](https://github.com/Netflix/dgs-examples-java/blob/main/src/main/java/com/example/demo/services/DefaultReviewsService.java).

Reviews can also be added by users of the API using a [mutation](https://github.com/Netflix/dgs-examples-java/blob/main/src/main/resources/schema/schema.graphqls#L6), and a [GraphQL Subscription](https://github.com/Netflix/dgs-examples-java/blob/main/src/main/resources/schema/schema.graphqls#L11) is available to watch for added reviews.

There's also a mutation available to add [Artwork](https://github.com/Netflix/dgs-examples-java/blob/main/src/main/resources/schema/schema.graphqls#L7) for a show, demonstrating file uploads.
Uploaded files are stored in a folder `uploaded-images` in the work directory where ethe application is started.

Starting the example
----

The example requires Java 11.
Run the application in an IDE using its [main class](https://github.com/Netflix/dgs-examples-java/blob/main/src/main/java/com/example/demo/DemoApplication.java) or using Gradle: 

```
./gradlew bootRun
```

Interact with the application using GraphiQL on http://localhost:8080/graphiql.
