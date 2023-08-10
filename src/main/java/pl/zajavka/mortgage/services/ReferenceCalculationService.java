package pl.zajavka.mortgage.services;

import pl.zajavka.mortgage.model.InputData;
import pl.zajavka.mortgage.model.MortgageReference;
import pl.zajavka.mortgage.model.Rate;
import pl.zajavka.mortgage.model.RateAmounts;

public interface ReferenceCalculationService {

    MortgageReference calculate(RateAmounts rateAmounts, InputData inputData);

    MortgageReference calculate(RateAmounts rateAmounts, final InputData inputData, Rate previousRate);

}
