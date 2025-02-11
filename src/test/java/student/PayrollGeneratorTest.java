package student;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PayrollGeneratorTest {

    @Test
    public void testPayrollProcessing() {
        List<String> employeesData = List.of(
                "HOURLY,John Doe,s101,20.00,100,5000,1000",
                "SALARY,Jane Smith,s102,96000,500,75000,15000"
        );

        List<String> timeCardsData = List.of(
                "s101,40",
                "s102,0"
        );

        List<IEmployee> employees = employeesData.stream()
                .map(Builder::buildEmployeeFromCSV)
                .toList();

        List<ITimeCard> timeCards = timeCardsData.stream()
                .map(Builder::buildTimeCardFromCSV)
                .toList();

        List<IPayStub> payStubs = employees.stream()
                .map(emp -> {
                    double hoursWorked = timeCards.stream()
                            .filter(tc -> tc.getEmployeeID().equals(emp.getID()))
                            .mapToDouble(ITimeCard::getHoursWorked)
                            .findFirst()
                            .orElse(0.0);
                    return emp.runPayroll(hoursWorked);
                })
                .toList();

        assertEquals(2, payStubs.size());
        assertEquals(541.45, payStubs.get(0).getPay(), 0.01);
        assertEquals(2707.25, payStubs.get(1).getPay(), 0.01);
    }
}
