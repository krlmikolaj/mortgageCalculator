package pl.zajavka.mortgage.model;

import java.math.BigDecimal;

public record Rate(
    BigDecimal rateNumber,
    TimePoint timePoint,
    RateAmounts rateAmounts,
    MortgageResidual mortgageResidual,
    MortgageReference mortgageReference
) {}
