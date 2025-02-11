package student;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SalaryEmployeeTest {

    @Test
    public void testGetName() {
        SalaryEmployee emp = new SalaryEmployee("Nami", "s195", 96000, 50000, 10000, 500);
        assertEquals("Nami", emp.getName());
    }

    @Test
    public void testGetID() {
        SalaryEmployee emp = new SalaryEmployee("Nami", "s195", 96000, 50000, 10000, 500);
        assertEquals("s195", emp.getID());
    }

    @Test
    public void testGetPayRate() {
        SalaryEmployee emp = new SalaryEmployee("Nami", "s195", 96000, 50000, 10000, 500);
        assertEquals(96000, emp.getPayRate());
    }

    @Test
    public void testGetEmployeeType() {
        SalaryEmployee emp = new SalaryEmployee("Nami", "s195", 96000, 50000, 10000, 500);
        assertEquals("SALARY", emp.getEmployeeType());
    }

    @Test
    public void testGetYTDEarnings() {
        SalaryEmployee emp = new SalaryEmployee("Nami", "s195", 96000, 50000, 10000, 500);
        assertEquals(50000, emp.getYTDEarnings());
    }

    @Test
    public void testGetYTDTaxesPaid() {
        SalaryEmployee emp = new SalaryEmployee("Nami", "s195", 96000, 50000, 10000, 500);
        assertEquals(10000, emp.getYTDTaxesPaid());
    }

    @Test
    public void testGetPretaxDeductions() {
        SalaryEmployee emp = new SalaryEmployee("Nami", "s195", 96000, 50000, 10000, 500);
        assertEquals(500, emp.getPretaxDeductions());
    }

    @Test
    public void testRunPayroll_Normal() {
        SalaryEmployee emp = new SalaryEmployee("Nami", "s195", 96000, 50000, 10000, 500);
        IPayStub stub = emp.runPayroll(0);  // Hours don't matter for salary employees

        assertNotNull(stub);

        // Calculate expected values:
        // Base pay per period = 96000 / 24 = 4000
        // Taxable amount = 4000 - 500 = 3500
        // Tax amount = 3500 * 0.2265 = 792.75
        // Net pay = 4000 - 500 - 792.75 = 2707.25

        assertEquals(2707.25, stub.getPay(), 0.01);
        assertEquals(792.75, stub.getTaxesPaid(), 0.01);
    }

    @Test
    public void testRunPayroll_NegativeHours() {
        SalaryEmployee emp = new SalaryEmployee("Zoro", "s196", 120000, 75000, 15000, 1000);
        IPayStub stub = emp.runPayroll(-5);  // Negative hours

        assertNull(stub); // Should skip the pay period
    }

    @Test
    public void testToCSV() {
        SalaryEmployee emp = new SalaryEmployee("Nami", "s195", 96000, 50000, 10000, 500);
        assertEquals("SALARY,Nami,s195,96000.00,500.00,50000.00,10000.00", emp.toCSV());
    }
}
