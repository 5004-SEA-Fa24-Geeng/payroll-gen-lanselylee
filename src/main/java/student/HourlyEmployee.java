package student;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HourlyEmployee implements IEmployee {
    private final String name;
    private final String id;
    private final double payRate;
    private double ytdEarnings;
    private double ytdTaxesPaid;
    private final double pretaxDeductions;

    public HourlyEmployee(String name, String id, double payRate, double ytdEarnings, double ytdTaxesPaid, double pretaxDeductions) {
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
        return "HOURLY";
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

        BigDecimal regularHours = BigDecimal.valueOf(Math.min(40, hoursWorked));
        BigDecimal overtimeHours = BigDecimal.valueOf(Math.max(0, hoursWorked - 40));

        BigDecimal regularPay = BigDecimal.valueOf(payRate).multiply(regularHours);
        BigDecimal overtimePay = BigDecimal.valueOf(payRate).multiply(BigDecimal.valueOf(1.5)).multiply(overtimeHours);
        BigDecimal grossPay = regularPay.add(overtimePay).setScale(2, RoundingMode.HALF_UP);
        // System.out.println("grossPay: " + grossPay);
        BigDecimal pretaxDeductionsBD = BigDecimal.valueOf(pretaxDeductions);
        BigDecimal taxableIncome = grossPay.subtract(pretaxDeductionsBD).max(BigDecimal.ZERO);

        BigDecimal tax = taxableIncome.multiply(BigDecimal.valueOf(0.2265)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal netPay = taxableIncome.subtract(tax).setScale(2, RoundingMode.HALF_UP);

        // Debugging output before updating YTD values
        // System.out.println("Before Update - ytdEarnings: " + ytdEarnings + ", ytdTaxesPaid: " + ytdTaxesPaid);

        // Update YTD values
        ytdEarnings = BigDecimal.valueOf(ytdEarnings).add(netPay).setScale(2, RoundingMode.HALF_UP).doubleValue();
        ytdTaxesPaid = BigDecimal.valueOf(ytdTaxesPaid).add(tax).setScale(2, RoundingMode.HALF_UP).doubleValue();
        
        // Remove trailing zeros
        ytdEarnings = BigDecimal.valueOf(ytdEarnings).stripTrailingZeros().doubleValue();
        ytdTaxesPaid = BigDecimal.valueOf(ytdTaxesPaid).stripTrailingZeros().doubleValue();
        
        // Debugging output after updating YTD values
        // System.out.println("After Update - ytdEarnings: " + ytdEarnings + ", ytdTaxesPaid: " + ytdTaxesPaid);

        return new PayStub(this, netPay.doubleValue(), tax.doubleValue());
    }

    @Override
    public String toCSV() {
        return String.format("HOURLY,%s,%s,%.2f,%.2f,%.2f,%.2f", name, id, payRate, pretaxDeductions, ytdEarnings, ytdTaxesPaid);
    }
}
