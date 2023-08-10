package pl.zajavka.mortgage.services;

import org.springframework.stereotype.Service;
import pl.zajavka.mortgage.model.*;

import java.math.BigDecimal;

@Service
public class ReferenceCalculationServiceImpl implements ReferenceCalculationService {

    @Override
    public MortgageReference calculate(RateAmounts rateAmounts, InputData inputData) {
        if (BigDecimal.ZERO.equals(inputData.amount())) {
            return new MortgageReference(BigDecimal.ZERO, BigDecimal.ZERO);
        }

        return new MortgageReference(inputData.amount(), inputData.monthsDuration());
    }

    @Override
    public MortgageReference calculate(RateAmounts rateAmounts, final InputData inputData, Rate previousRate) {
        if (BigDecimal.ZERO.equals(previousRate.mortgageResidual().residualAmount())) {
            return new MortgageReference(BigDecimal.ZERO, BigDecimal.ZERO);
        }

        return switch (inputData.overpaymentReduceWay()) {
            case Overpayment.REDUCE_RATE -> reduceRateMortgageReference(rateAmounts, previousRate.mortgageResidual());
            case Overpayment.REDUCE_PERIOD -> new MortgageReference(inputData.amount(), inputData.monthsDuration());
            default -> throw new MortgageException("Case not handled");
        };

    }

    private MortgageReference reduceRateMortgageReference(final RateAmounts rateAmounts, final MortgageResidual previousResidual) {
        if (rateAmounts.overpayment().amount().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal residualAmount = calculateResidualAmount(previousResidual.residualAmount(), rateAmounts);
            return new MortgageReference(residualAmount, previousResidual.residualDuration().subtract(BigDecimal.ONE));
        }

        return new MortgageReference(previousResidual.residualAmount(), previousResidual.residualDuration());
    }

    private BigDecimal calculateResidualAmount(final BigDecimal residualAmount, final RateAmounts rateAmounts) {
        return residualAmount
            .subtract(rateAmounts.capitalAmount())
            .subtract(rateAmounts.overpayment().amount())
            .max(BigDecimal.ZERO);
    }

}
