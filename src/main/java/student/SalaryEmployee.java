package student;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SalaryEmployee implements IEmployee {
    private final String name;
    private final String id;
    private final double payRate;
    private double ytdEarnings;
    private double ytdTaxesPaid;
    private final double pretaxDeductions;

    public SalaryEmployee(String name, String id, double payRate, double ytdEarnings, double ytdTaxesPaid, double pretaxDeductions) {
        this.name = name;
        this.id = id;
        this.payRate = payRate;
        this.ytdEarnings = ytdEarnings;
        this.ytdTaxesPaid = ytdTaxesPaid;
        this.pretaxDeductions = pretaxDeductions;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public double getPayRate() {
        return payRate;
    }

    @Override
    public String getEmployeeType() {
        return "SALARY";
    }

    @Override
    public double getYTDEarnings() {
        return ytdEarnings;
    }

    @Override
    public double getYTDTaxesPaid() {
        return ytdTaxesPaid;
    }

    @Override
    public double getPretaxDeductions() {
        return pretaxDeductions;
    }

    @Override
    public IPayStub runPayroll(double hoursWorked) {
        if (hoursWorked < 0) return null;

        BigDecimal grossPay = BigDecimal.valueOf(payRate).divide(BigDecimal.valueOf(24), 2, RoundingMode.HALF_UP);
        BigDecimal pretaxDeductionsBD = BigDecimal.valueOf(pretaxDeductions);
        BigDecimal taxableIncome = grossPay.subtract(pretaxDeductionsBD).max(BigDecimal.ZERO);

        BigDecimal tax = taxableIncome.multiply(BigDecimal.valueOf(0.2265)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal netPay = taxableIncome.subtract(tax).setScale(2, RoundingMode.HALF_UP);

        // Update YTD values
        ytdEarnings = BigDecimal.valueOf(ytdEarnings).add(netPay).setScale(2, RoundingMode.HALF_UP).doubleValue();
        ytdTaxesPaid = BigDecimal.valueOf(ytdTaxesPaid).add(tax).setScale(2, RoundingMode.HALF_UP).doubleValue();

        // Remove trailing zeros
        ytdEarnings = BigDecimal.valueOf(ytdEarnings).stripTrailingZeros().doubleValue();
        ytdTaxesPaid = BigDecimal.valueOf(ytdTaxesPaid).stripTrailingZeros().doubleValue();

        System.out.println("After Update - ytdEarnings: " + ytdEarnings + ", ytdTaxesPaid: " + ytdTaxesPaid);

        return new PayStub(this, netPay.doubleValue(), tax.doubleValue());
    }

    @Override
    public String toCSV() {
        return String.format("SALARY,%s,%s,%.2f,%.2f,%.2f,%.2f", name, id, payRate, pretaxDeductions, ytdEarnings, ytdTaxesPaid);
    }
}
