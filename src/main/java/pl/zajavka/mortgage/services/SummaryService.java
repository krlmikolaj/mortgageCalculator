package pl.zajavka.mortgage.services;

import pl.zajavka.mortgage.model.Rate;
import pl.zajavka.mortgage.model.Summary;

import java.util.List;

@FunctionalInterface
public interface SummaryService {

    Summary calculateSummary(List<Rate> rates);
}
