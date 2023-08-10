package pl.zajavka.mortgage.model;

import lombok.Builder;
import lombok.With;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Map;

@With
public record InputData(
    LocalDate repaymentStartDate,
    BigDecimal wiborPercent,
    BigDecimal amount,
    BigDecimal monthsDuration,
    MortgageType rateType,
    BigDecimal marginPercent,
    BigDecimal overpaymentProvisionPercent,
    BigDecimal overpaymentProvisionMonths,
    BigDecimal overpaymentStartMonth,
    Map<Integer, BigDecimal> overpaymentSchema,
    String overpaymentReduceWay,
    boolean mortgagePrintPayoffsSchedule,
    Integer mortgageRateNumberToPrint
) {

    @Builder
    public InputData {
    }

    private static final BigDecimal PERCENT = new BigDecimal("100");

    public BigDecimal wiborPercent() {
        return wiborPercent.divide(PERCENT, 4, RoundingMode.HALF_UP);
    }

    public BigDecimal marginPercent() {
        return marginPercent.divide(PERCENT, 4, RoundingMode.HALF_UP);
    }

    public BigDecimal overpaymentProvisionPercent() {
        return overpaymentProvisionPercent.divide(PERCENT, 4, RoundingMode.HALF_UP);
    }

    public BigDecimal interestPercent() {
        return marginPercent().add(wiborPercent());
    }

    public BigDecimal interestToDisplay() {
        return wiborPercent().add(marginPercent());
    }

}
