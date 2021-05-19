package com.example.demo.scalars;

import com.netflix.graphql.dgs.DgsScalar;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@DgsScalar(name = "DateRange")
public class DateRangeScalar implements Coercing<DateRange, String> {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @Override
    public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
        DateRange range = (DateRange) dataFetcherResult;

        return range.getFrom().format(formatter) + "-" + range.getTo().format(formatter);
    }

    @Override
    public DateRange parseValue(Object input) throws CoercingParseValueException {
        String[] split = ((String) input).split("-");
        LocalDate from = LocalDate.parse(split[0], formatter);
        LocalDate to = LocalDate.parse(split[1], formatter);
        return new DateRange(from, to);
    }

    @Override
    public DateRange parseLiteral(Object input) throws CoercingParseLiteralException {
        String[] split = ((StringValue) input).getValue().split("-");
        LocalDate from = LocalDate.parse(split[0], formatter);
        LocalDate to = LocalDate.parse(split[1], formatter);
        return new DateRange(from, to);
    }
}
