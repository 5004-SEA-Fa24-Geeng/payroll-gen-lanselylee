package student;

/**
 * Handles command line arguments.
 */
public final class Arguments {
    /** The employee file path. */
    private String employeeFile;
    /** The payroll file path. */
    private String payrollFile;
    /** The time cards file path. */
    private String timeCards;

    private static final String DEFAULT_EMPLOYEE_FILE = "resources/employees.csv";
    private static final String DEFAULT_PAYROLL_FILE = "resources/pay_stubs.csv";
    private static final String DEFAULT_TIME_CARD_FILE = "resources/time_cards.csv";

    private Arguments() {
        this.employeeFile = DEFAULT_EMPLOYEE_FILE;
        this.payrollFile = DEFAULT_PAYROLL_FILE;
        this.timeCards = DEFAULT_TIME_CARD_FILE;
    }

    public String getEmployeeFile() {
        return employeeFile;
    }

    public String getPayrollFile() {
        return payrollFile;
    }

    public String getTimeCards() {
        return timeCards;
    }

    /**
     * Processes command line arguments.
     *
     * @param args the command line arguments
     * @return an Arguments object containing the file paths
     */
    public static Arguments process(String[] args) {
        Arguments arguments = new Arguments();
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-e":
                    if (i + 1 < args.length) {
                        arguments.employeeFile = args[i + 1];
                    }
                    break;
                case "-t":
                    if (i + 1 < args.length) {
                        arguments.timeCards = args[i + 1];
                    }
                    break;
                case "-o":
                    if (i + 1 < args.length) {
                        arguments.payrollFile = args[i + 1];
                    }
                    break;
                default:
                    break;
            }
        }
        return arguments;
    }
}
