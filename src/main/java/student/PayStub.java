package student;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PayStub implements IPayStub {
    private IEmployee employee;
    private double netPay;
    private double taxesPaid;

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
