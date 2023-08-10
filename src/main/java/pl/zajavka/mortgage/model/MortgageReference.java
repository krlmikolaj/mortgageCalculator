package pl.zajavka.mortgage.model;

import java.math.BigDecimal;

public record MortgageReference(BigDecimal referenceAmount, BigDecimal referenceDuration) {}
