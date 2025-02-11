package student;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents an hourly employee, implementing the IEmployee interface.
 * This class contains information about the employee's name, ID, pay rate,
 * year-to-date earnings, year-to-date taxes paid, and pretax deductions.
 */
public class HourlyEmployee implements IEmployee {
    private final String name;
    private final String id;
    private final double payRate;
    private double ytdEarnings;
    private double ytdTaxesPaid;
    private final double pretaxDeductions;

    /**
     * Constructs a new HourlyEmployee with the specified details.
     *
     * @param name the name of the employee
     * @param id the ID of the employee
     * @param payRate the hourly pay rate of the employee
     * @param ytdEarnings the year-to-date earnings of the employee
     * @param ytdTaxesPaid the year-to-date taxes paid by the employee
     * @param pretaxDeductions the pretax deductions for the employee
     */
    public HourlyEmployee(String name, String id, double payRate, double ytdEarnings, double ytdTaxesPaid, double pretaxDeductions) {
        this.name = name;
        this.id = id;
        this.payRate = payRate;
        this.ytdEarnings = ytdEarnings;
        this.ytdTaxesPaid = ytdTaxesPaid;
        this.pretaxDeductions = pretaxDeductions;
    }

    /**
     * Returns the name of the employee.
     *
     * @return the name of the employee
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the ID of the employee.
     *
     * @return the ID of the employee
     */
    @Override
    public String getID() {
        return id;
    }

    /**
     * Returns the pay rate of the employee.
     *
     * @return the pay rate of the employee
     */
    @Override
    public double getPayRate() {
        return payRate;
    }

    /**
     * Returns the type of the employee.
     *
     * @return a string representing the employee type
     */
    @Override
    public String getEmployeeType() {
        return "HOURLY";
    }

    /**
     * Returns the year-to-date earnings of the employee.
     *
     * @return the year-to-date earnings
     */
    @Override
    public double getYTDEarnings() {
        return ytdEarnings;
    }

    /**
     * Returns the year-to-date taxes paid by the employee.
     *
     * @return the year-to-date taxes paid
     */
    @Override
    public double getYTDTaxesPaid() {
        return ytdTaxesPaid;
    }

    /**
     * Returns the pretax deductions for the employee.
     *
     * @return the pretax deductions
     */
    @Override
    public double getPretaxDeductions() {
        return pretaxDeductions;
    }

    /**
     * Runs the payroll for the employee based on hours worked.
     *
     * @param hoursWorked the number of hours worked by the employee
     * @return an IPayStub object containing the payroll details
     */
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

    /**
     * Returns a CSV representation of the employee's data.
     *
     * @return a string in CSV format
     */
    @Override
    public String toCSV() {
        return String.format("HOURLY,%s,%s,%.2f,%.2f,%.2f,%.2f", name, id, payRate, pretaxDeductions, ytdEarnings, ytdTaxesPaid);
    }
}
