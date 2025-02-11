package student;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a pay stub for an employee, implementing the IPayStub interface.
 * This class contains information about the employee's net pay, taxes paid,
 * and provides functionality to format the pay stub as a CSV string.
 */
public class PayStub implements IPayStub {
    private IEmployee employee;
    private double netPay;
    private double taxesPaid;

    /**
     * Constructs a new PayStub with the specified employee and payment details.
     *
     * @param employee the employee associated with this pay stub
     * @param netPay the net pay for the employee
     * @param taxesPaid the taxes paid by the employee
     */
    public PayStub(IEmployee employee, double netPay, double taxesPaid) {
        this.employee = employee;
        this.netPay = roundToTwoDecimalPlaces(netPay);
        this.taxesPaid = roundToTwoDecimalPlaces(taxesPaid);
    }

    /**
     * Returns the net pay for the employee.
     *
     * @return the net pay
     */
    @Override
    public double getPay() {
        return netPay;
    }

    /**
     * Returns the taxes paid by the employee.
     *
     * @return the taxes paid
     */
    @Override
    public double getTaxesPaid() {
        return taxesPaid;
    }

    /**
     * Returns a CSV representation of the pay stub data.
     *
     * @return a string in CSV format
     */
    @Override
    public String toCSV() {
        return String.format("%s,%.2f,%.2f,%.2f,%.2f",
                employee.getName(), netPay, taxesPaid,
                employee.getYTDEarnings(), employee.getYTDTaxesPaid());
    }

    /**
     * Rounds a given value to two decimal places.
     *
     * @param value the value to round
     * @return the rounded value
     */
    private double roundToTwoDecimalPlaces(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
