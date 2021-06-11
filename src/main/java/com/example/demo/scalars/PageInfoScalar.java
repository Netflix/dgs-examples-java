package com.example.demo.scalars;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.graphql.dgs.DgsScalar;
import graphql.relay.PageInfo;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

@DgsScalar(name = "PageInfo")
public class PageInfoScalar implements Coercing<PageInfo, PageInfo> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public PageInfo serialize(Object dataFetcherResult) throws CoercingSerializeException {
        if (dataFetcherResult instanceof PageInfo) {
            return (PageInfo) dataFetcherResult;
        }
        throw new CoercingSerializeException("Not a valid PageInfo");
    }

    @Override
    public PageInfo parseValue(Object input) throws CoercingParseValueException {
        return null;
    }

    @Override
    public PageInfo parseLiteral(Object input) throws CoercingParseLiteralException {
        return null;
    }
}
