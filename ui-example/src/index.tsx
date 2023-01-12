/*
 * Copyright 2020 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import React from 'react';
import ReactDOM from 'react-dom';
import {
    ApolloClient,
    ApolloProvider,
    createHttpLink,
    gql,
    InMemoryCache,
    NormalizedCacheObject,
    split,
    useQuery,
    useSubscription
} from '@apollo/client';

import { GraphQLWsLink } from "@apollo/client/link/subscriptions";
import { createClient } from 'graphql-ws';

const webSocketLink = new GraphQLWsLink(createClient({
    url: 'ws://localhost:8080/subscriptions',
}));


const httpLink = createHttpLink({uri:'http://localhost:8080/graphql' })
const client: ApolloClient<NormalizedCacheObject> = new ApolloClient({
    link: split((operation) => {
        return operation.operationName === "ReviewAdded"
    }, webSocketLink, httpLink),
    cache: new InMemoryCache(),
    headers: {},
    resolvers: {},
});

const App: React.FC = () => {
    const {loading, error, data} = useQuery(gql`
        {
            shows {
                title
            }
        }`);

    return loading ?
        <div>Loading...</div>
        : error ? <div>{error}</div>
            : <div>
                <h1>Movies</h1>
                <table>
                    <tr>
                        <th>Title</th>
                    </tr>
                    {data.shows.map((show: Show) => {
                        return <tr>
                            <td>{show.title}</td>
                        </tr>
                    })}
                </table>

                <h1>Subscriptions</h1>
                <SubscriptionPanel/>
            </div>
}

type Show = {
    title: String
}

const SubscriptionPanel : React.FC = () => {
    const {data, loading, error} = useSubscription(gql`
        subscription ReviewAdded {
            reviewAdded(showId: 1) {
                username
            }
        }
    `, {});


    return data?<div>{data.reviewAdded.username}: { data.reviewAdded.username}</div>:<div/>
}

ReactDOM.render(
    <ApolloProvider client={client}>
        <App/>
    </ApolloProvider>,
    document.getElementById('root'),
);
