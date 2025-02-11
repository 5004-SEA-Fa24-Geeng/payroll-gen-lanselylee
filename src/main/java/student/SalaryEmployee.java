package student;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a salaried employee, implementing the IEmployee interface.
 * This class contains information about the employee's name, ID, salary,
 * year-to-date earnings, year-to-date taxes paid, and pretax deductions.
 */
public class SalaryEmployee implements IEmployee {
    private final String name;
    private final String id;
    private final double salary;
    private double ytdEarnings;
    private double ytdTaxesPaid;
    private final double pretaxDeductions;

    /**
     * Constructs a new SalaryEmployee with the specified details.
     *
     * @param name the name of the employee
     * @param id the ID of the employee
     * @param salary the annual salary of the employee
     * @param ytdEarnings the year-to-date earnings of the employee
     * @param ytdTaxesPaid the year-to-date taxes paid by the employee
     * @param pretaxDeductions the pretax deductions for the employee
     */
    public SalaryEmployee(String name, String id, double salary, double ytdEarnings, double ytdTaxesPaid, double pretaxDeductions) {
        this.name = name;
        this.id = id;
        this.salary = salary;
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
     * Returns the salary of the employee.
     *
     * @return the annual salary of the employee
     */
    @Override
    public double getPayRate() {
        return salary;
    }

    /**
     * Returns the type of the employee.
     *
     * @return a string representing the employee type
     */
    @Override
    public String getEmployeeType() {
        return "SALARY";
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
     * Runs the payroll for the employee based on a fixed salary.
     *
     * @param hoursWorked the number of hours worked by the employee (not used for salary employees)
     * @return an IPayStub object containing the payroll details
     */
    @Override
    public IPayStub runPayroll(double hoursWorked) {
        BigDecimal monthlySalary = BigDecimal.valueOf(salary).divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);
        BigDecimal grossPay = monthlySalary.setScale(2, RoundingMode.HALF_UP);
        BigDecimal pretaxDeductionsBD = BigDecimal.valueOf(pretaxDeductions);
        BigDecimal taxableIncome = grossPay.subtract(pretaxDeductionsBD).max(BigDecimal.ZERO);

        BigDecimal tax = taxableIncome.multiply(BigDecimal.valueOf(0.2265)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal netPay = taxableIncome.subtract(tax).setScale(2, RoundingMode.HALF_UP);

        // Update YTD values
        ytdEarnings = BigDecimal.valueOf(ytdEarnings).add(netPay).setScale(2, RoundingMode.HALF_UP).doubleValue();
        ytdTaxesPaid = BigDecimal.valueOf(ytdTaxesPaid).add(tax).setScale(2, RoundingMode.HALF_UP).doubleValue();

        return new PayStub(this, netPay.doubleValue(), tax.doubleValue());
    }

    /**
     * Returns a CSV representation of the employee's data.
     *
     * @return a string in CSV format
     */
    @Override
    public String toCSV() {
        return String.format("SALARY,%s,%s,%.2f,%.2f,%.2f,%.2f", name, id, salary, pretaxDeductions, ytdEarnings, ytdTaxesPaid);
    }
}
