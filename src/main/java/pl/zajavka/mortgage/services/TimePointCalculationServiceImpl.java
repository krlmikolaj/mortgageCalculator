package pl.zajavka.mortgage.services;

import org.springframework.stereotype.Service;
import pl.zajavka.mortgage.model.InputData;
import pl.zajavka.mortgage.model.Rate;
import pl.zajavka.mortgage.model.TimePoint;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class TimePointCalculationServiceImpl implements TimePointCalculationService {

    public TimePoint calculate(final BigDecimal rateNumber, final InputData inputData) {
        BigDecimal year = calculateYear(rateNumber);
        BigDecimal month = calculateMonth(rateNumber);
        LocalDate date = inputData.repaymentStartDate();
        return new TimePoint(year, month, date);
    }

    public TimePoint calculate(BigDecimal rateNumber, Rate previousRate) {
        BigDecimal year = calculateYear(rateNumber);
        BigDecimal month = calculateMonth(rateNumber);
        LocalDate date = previousRate.timePoint().date().plus(1, ChronoUnit.MONTHS);
        return new TimePoint(year, month, date);
    }

    private BigDecimal calculateYear(final BigDecimal rateNumber) {
        return rateNumber.divide(AmountsCalculationService.YEAR, RoundingMode.UP).max(BigDecimal.ONE);
    }

    private BigDecimal calculateMonth(final BigDecimal rateNumber) {
        return BigDecimal.ZERO.equals(rateNumber.remainder(AmountsCalculationService.YEAR))
            ? AmountsCalculationService.YEAR
            : rateNumber.remainder(AmountsCalculationService.YEAR);
    }

}
