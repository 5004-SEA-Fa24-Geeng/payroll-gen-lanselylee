package student;

/**
 * Interface representing a time card for an employee.
 * This interface defines the methods to retrieve the employee ID
 * and the number of hours worked.
 */
public interface ITimeCard {
    
    /**
     * Returns the employee ID associated with this time card.
     *
     * @return the employee ID
     */
    String getEmployeeID();

    /**
     * Returns the number of hours worked recorded on this time card.
     *
     * @return the number of hours worked
     */
    double getHoursWorked();
}
