package pl.zajavka.mortgage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.zajavka.mortgage.configuration.CalculatorConfiguration;
import pl.zajavka.mortgage.model.MortgageType;
import pl.zajavka.mortgage.model.Overpayment;
import pl.zajavka.mortgage.services.InputDataRepository;
import pl.zajavka.mortgage.services.MortgageCalculationService;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class MortgageCalculator {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(CalculatorConfiguration.class);

        InputDataRepository inputDataRepository = context.getBean(InputDataRepository.class);
        final var inputData = inputDataRepository.read();
        if (inputData.isEmpty()) {
            return;
        }

        var overpaymentSchema = new TreeMap<>(
            Map.of(
                5, BigDecimal.valueOf(12000),
                19, BigDecimal.valueOf(10000),
                28, BigDecimal.valueOf(11000),
                64, BigDecimal.valueOf(16000),
                78, BigDecimal.valueOf(18000)
            )
        );

        var updatedInputData = inputData.get()
            .withAmount(new BigDecimal("296192.11"))
            .withMonthsDuration(BigDecimal.valueOf(360))
            .withOverpaymentReduceWay(Overpayment.REDUCE_PERIOD)
            .withRateType(MortgageType.DECREASING)
            .withOverpaymentSchema(overpaymentSchema);

        MortgageCalculationService mortgageCalculationService = context.getBean(MortgageCalculationService.class);
        mortgageCalculationService.calculate(updatedInputData);
    }
}
