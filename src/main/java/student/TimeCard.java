package student;

/**
 * Represents a time card for an employee.
 */
public class TimeCard implements ITimeCard {
    /** The ID of the employee. */
    private String employeeID;

    /** The number of hours worked by the employee. */
    private double hoursWorked;

    /**
     * Constructs a TimeCard with the specified employee ID and hours worked.
     *
     * @param employeeID the ID of the employee
     * @param hoursWorked the number of hours worked
     */
    public TimeCard(String employeeID, double hoursWorked) {
        this.employeeID = employeeID;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public String getEmployeeID() {
        return employeeID;
    }

    @Override
    public double getHoursWorked() {
        return hoursWorked;
    }
}
