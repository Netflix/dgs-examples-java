package com.example.demo.scalars;

import java.time.LocalDate;

public class DateRange {
    private final LocalDate from;
    private final LocalDate to;

    public DateRange(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }
}
