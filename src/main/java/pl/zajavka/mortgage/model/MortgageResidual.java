package pl.zajavka.mortgage.model;

import java.math.BigDecimal;

public record MortgageResidual(BigDecimal residualAmount, BigDecimal residualDuration) {}
