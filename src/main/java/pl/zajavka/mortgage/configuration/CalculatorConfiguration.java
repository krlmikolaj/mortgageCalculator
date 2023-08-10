package pl.zajavka.mortgage.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.zajavka.mortgage.model.Rate;
import pl.zajavka.mortgage.model.RateAmounts;
import pl.zajavka.mortgage.model.Summary;
import pl.zajavka.mortgage.services.SummaryService;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

@Configuration
@ComponentScan(basePackages = "pl.zajavka")
public class CalculatorConfiguration {

    @Bean
    public static SummaryService create() {
        return rates -> {
            BigDecimal interestSum = calculate(rates, rate -> rate.rateAmounts().interestAmount());
            BigDecimal overpaymentProvisionSum = calculate(rates, rate -> rate.rateAmounts().overpayment().provisionAmount());
            BigDecimal totalLostSum = interestSum.add(overpaymentProvisionSum);
            BigDecimal totalCapital = calculate(rates, rate -> totalCapital(rate.rateAmounts()));
            return new Summary(interestSum, overpaymentProvisionSum, totalLostSum, totalCapital);
        };
    }

    private static BigDecimal totalCapital(final RateAmounts rateAmounts) {
        return rateAmounts.capitalAmount().add(rateAmounts.overpayment().amount());
    }

    private static BigDecimal calculate(final List<Rate> rates, Function<Rate, BigDecimal> function) {
        return rates.stream()
            .reduce(BigDecimal.ZERO, (sum, next) -> sum.add(function.apply(next)), BigDecimal::add);
    }

}
