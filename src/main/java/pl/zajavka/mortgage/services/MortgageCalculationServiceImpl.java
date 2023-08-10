package pl.zajavka.mortgage.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zajavka.mortgage.model.InputData;
import pl.zajavka.mortgage.model.Rate;

import java.util.List;

@Service
@AllArgsConstructor
public class MortgageCalculationServiceImpl implements MortgageCalculationService {

    private final RateCalculationService rateCalculationService;

    private final PrintingService printingService;

    private final SummaryService summaryService;

    @Override
    public void calculate(InputData inputData) {
        printingService.printIntroInformation(inputData);

        List<Rate> rates = rateCalculationService.calculate(inputData);

        printingService.printSummary(summaryService.calculateSummary(rates));
        printingService.printSchedule(rates, inputData);
    }

}
