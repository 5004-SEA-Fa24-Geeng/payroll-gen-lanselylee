package student;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents an hourly employee with their details and payroll processing.
 */
public class HourlyEmployee implements IEmployee {
    /** The name of the employee. */
    private final String name;
    /** The ID of the employee. */
    private final String id;
    /** The pay rate of the employee. */
    private final double payRate;
    /** Year-to-date earnings of the employee. */
    private double ytdEarnings;
    /** Year-to-date taxes paid by the employee. */
    private double ytdTaxesPaid;
    /** Pre-tax deductions for the employee. */
    private final double pretaxDeductions;

    /**
     * Constructs an HourlyEmployee with the specified details.
     *
     * @param name the name of the employee
     * @param id the ID of the employee
     * @param payRate the pay rate of the employee
     * @param ytdEarnings year-to-date earnings of the employee
     * @param ytdTaxesPaid year-to-date taxes paid by the employee
     * @param pretaxDeductions pre-tax deductions for the employee
     */
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
        if (hoursWorked < 0) {
            return null;
        }

        BigDecimal regularHours = BigDecimal.valueOf(Math.min(40, hoursWorked));
        BigDecimal overtimeHours = BigDecimal.valueOf(Math.max(0, hoursWorked - 40));

        BigDecimal regularPay = BigDecimal.valueOf(payRate).multiply(regularHours);
        BigDecimal overtimePay = BigDecimal.valueOf(payRate).multiply(BigDecimal.valueOf(1.5)).multiply(overtimeHours);
        BigDecimal grossPay = regularPay.add(overtimePay).setScale(2, RoundingMode.HALF_UP);
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
        
        return new PayStub(this, netPay.doubleValue(), tax.doubleValue());
    }

    @Override
    public String toCSV() {
        return String.format("HOURLY,%s,%s,%.2f,%.2f,%.2f,%.2f", name, id, payRate, pretaxDeductions, ytdEarnings, ytdTaxesPaid);
    }
}
