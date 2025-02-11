package student;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a pay stub for an employee.
 */
public class PayStub implements IPayStub {
    /** The employee associated with this pay stub. */
    private IEmployee employee;
    /** The net pay for the current pay period. */
    private double netPay;
    /** The taxes paid for the current pay period. */
    private double taxesPaid;

    /**
     * Constructs a PayStub with the specified employee, net pay, and taxes paid.
     *
     * @param employee the employee associated with this pay stub
     * @param netPay the net pay for the current pay period
     * @param taxesPaid the taxes paid for the current pay period
     */
    public PayStub(IEmployee employee, double netPay, double taxesPaid) {
        this.employee = employee;
        this.netPay = roundToTwoDecimalPlaces(netPay);
        this.taxesPaid = roundToTwoDecimalPlaces(taxesPaid);
    }

    @Override
    public double getPay() {
        return netPay;
    }

    @Override
    public double getTaxesPaid() {
        return taxesPaid;
    }

    @Override
    public String toCSV() {
        return String.format("%s,%.2f,%.2f,%.2f,%.2f",
                employee.getName(), netPay, taxesPaid,
                employee.getYTDEarnings(), employee.getYTDTaxesPaid());
    }

    private double roundToTwoDecimalPlaces(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
