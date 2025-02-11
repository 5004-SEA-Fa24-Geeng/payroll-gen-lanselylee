package student;

import java.util.List;
import java.util.LinkedList;
import java.util.stream.Collectors;

public final class PayrollGenerator {
    private static final String DEFAULT_EMPLOYEE_FILE = "resources/employees.csv";
    private static final String DEFAULT_PAYROLL_FILE = "resources/pay_stubs.csv";
    private static final String DEFAULT_TIME_CARD_FILE = "resources/time_cards.csv";

    private PayrollGenerator() {}

    public static void main(String[] args) {
        Arguments arguments = Arguments.process(args);

        List<String> employeeLines = FileUtil.readFileToList(arguments.getEmployeeFile());
        List<String> timeCards = FileUtil.readFileToList(arguments.getTimeCards());

        List<IEmployee> employees = employeeLines.stream()
                .map(Builder::buildEmployeeFromCSV)
                .collect(Collectors.toList());

        List<ITimeCard> timeCardList = timeCards.stream()
                .map(Builder::buildTimeCardFromCSV)
                .collect(Collectors.toList());

        List<IPayStub> payStubs = new LinkedList<>();

        for (ITimeCard timeCard : timeCardList) {
            IEmployee employee = findEmployeeById(employees, timeCard.getEmployeeID());
            if (employee != null) {
                IPayStub payStub = employee.runPayroll(timeCard.getHoursWorked());
                if (payStub != null) {
                    payStubs.add(payStub);
                }
            }
        }

        List<String> updatedEmployees = employees.stream()
                .map(IEmployee::toCSV)
                .collect(Collectors.toList());
        updatedEmployees.add(0, FileUtil.EMPLOYEE_HEADER);
        FileUtil.writeFile(arguments.getEmployeeFile(), updatedEmployees);

        List<String> payStubLines = payStubs.stream()
                .map(IPayStub::toCSV)
                .collect(Collectors.toList());
        payStubLines.add(0, FileUtil.PAY_STUB_HEADER);
        FileUtil.writeFile(arguments.getPayrollFile(), payStubLines);
    }

    private static IEmployee findEmployeeById(List<IEmployee> employees, String employeeID) {
        return employees.stream()
                .filter(emp -> emp.getID().equals(employeeID))
                .findFirst()
                .orElse(null);
    }
}
