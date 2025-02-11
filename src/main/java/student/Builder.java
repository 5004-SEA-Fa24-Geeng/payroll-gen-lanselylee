package student;

/**
 * Utility class for building employee and time card objects from CSV data.
 */
public final class Builder {

    private Builder() {
    }

    /**
     * Initiates `employees.csv`, creating `HourlyEmployee` or `SalaryEmployee`.
     *
     * @param csv the CSV line containing employee data
     * @return an IEmployee object
     */
    public static IEmployee buildEmployeeFromCSV(String csv) {
        if (csv == null || csv.trim().isEmpty()) {
            throw new IllegalArgumentException("CSV line is empty or null.");
        }

        String[] parts = csv.split(",");
        if (parts.length != 7) {
            throw new IllegalArgumentException("Invalid CSV format: " + csv);
        }

        String type = parts[0].trim();
        String name = parts[1].trim();
        String id = parts[2].trim();

        try {
            double payRate = Double.parseDouble(parts[3].trim());
            double pretaxDeductions = Double.parseDouble(parts[4].trim());
            double ytdEarnings = Double.parseDouble(parts[5].trim());
            double ytdTaxesPaid = Double.parseDouble(parts[6].trim());

            if (type.equals("HOURLY")) {
                return new HourlyEmployee(name, id, payRate, ytdEarnings, ytdTaxesPaid, pretaxDeductions);
            } else if (type.equals("SALARY")) {
                return new SalaryEmployee(name, id, payRate, ytdEarnings, ytdTaxesPaid, pretaxDeductions);
            } else {
                throw new IllegalArgumentException("Unknown employee type: " + type);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format in CSV: " + csv, e);
        }
    }

    /**
     * Parses `time_cards.csv`, creating `TimeCard`.
     *
     * @param csv the CSV line containing time card data
     * @return an ITimeCard object
     */
    public static ITimeCard buildTimeCardFromCSV(String csv) {
        if (csv == null || csv.trim().isEmpty()) {
            throw new IllegalArgumentException("CSV line is empty or null.");
        }

        String[] parts = csv.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid CSV format: " + csv);
        }

        String employeeId = parts[0].trim();
        try {
            double hoursWorked = Double.parseDouble(parts[1].trim());
            return new TimeCard(employeeId, hoursWorked);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format in time card CSV: " + csv, e);
        }
    }
}
