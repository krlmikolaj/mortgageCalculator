package pl.zajavka.mortgage.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TimePoint(BigDecimal year, BigDecimal month, LocalDate date) {}
