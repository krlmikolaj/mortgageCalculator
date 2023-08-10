package pl.zajavka.mortgage.services;

import pl.zajavka.mortgage.model.InputData;
import pl.zajavka.mortgage.model.Rate;

import java.util.List;

public interface RateCalculationService {

    List<Rate> calculate(InputData inputData);
}
