package student;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HourlyEmployeeTest {

    @Test
    public void testGetName() {
        HourlyEmployee emp = new HourlyEmployee("Luffy", "s192", 30.0, 20000, 4530, 0);
        assertEquals("Luffy", emp.getName());
    }

    @Test
    public void testGetID() {
        HourlyEmployee emp = new HourlyEmployee("Luffy", "s192", 30.0, 20000, 4530, 0);
        assertEquals("s192", emp.getID());
    }

    @Test
    public void testGetPayRate() {
        HourlyEmployee emp = new HourlyEmployee("Luffy", "s192", 30.0, 20000, 4530, 0);
        assertEquals(30.0, emp.getPayRate());
    }

    @Test
    public void testGetEmployeeType() {
        HourlyEmployee emp = new HourlyEmployee("Luffy", "s192", 30.0, 20000, 4530, 0);
        assertEquals("HOURLY", emp.getEmployeeType());
    }

    @Test
    public void testGetYTDEarnings() {
        HourlyEmployee emp = new HourlyEmployee("Luffy", "s192", 30.0, 20000, 4530, 0);
        assertEquals(20000, emp.getYTDEarnings());
    }

    @Test
    public void testGetYTDTaxesPaid() {
        HourlyEmployee emp = new HourlyEmployee("Luffy", "s192", 30.0, 20000, 4530, 0);
        assertEquals(4530, emp.getYTDTaxesPaid());
    }

    @Test
    public void testGetPretaxDeductions() {
        HourlyEmployee emp = new HourlyEmployee("Luffy", "s192", 30.0, 20000, 4530, 0);
        assertEquals(0, emp.getPretaxDeductions());
    }

    @Test
    public void testRunPayroll_NoOvertime() {
        HourlyEmployee emp = new HourlyEmployee("Luffy", "s192", 30.0, 20000, 4530, 100);
        IPayStub stub = emp.runPayroll(40);  // Regular 40-hour week

        assertNotNull(stub);
        // Calculations:
        // Gross pay = 30 * 40 = 1200
        // Taxable income = 1200 - 100 = 1100
        // Tax = 1100 * 0.2265 = 249.15
        // Net pay = 1200 - 100 - 249.15 = 850.85
        assertEquals(850.85, stub.getPay(), 0.01);
        assertEquals(249.15, stub.getTaxesPaid(), 0.01);
    }

    @Test
    public void testRunPayroll_WithOvertime() {
        HourlyEmployee emp = new HourlyEmployee("Zoro", "s193", 25.0, 12000, 2500, 150);
        IPayStub stub = emp.runPayroll(45);  // 5 hours overtime

        assertNotNull(stub);
        // Calculations:
        // Regular pay = 25 * 40 = 1000
        // Overtime pay = 25 * 1.5 * 5 = 187.50
        // Gross pay = 1000 + 187.50 = 1187.50
        // Taxable income = 1187.50 - 150 = 1037.50
        // Tax = 1037.50 * 0.2265 = 235.00
        // Net pay = 1187.50 - 150 - 235.00 = 802.50
        assertEquals(802.50, stub.getPay(), 0.01);
        assertEquals(235.00, stub.getTaxesPaid(), 0.01);
    }

    @Test
    public void testRunPayroll_NegativeHours() {
        HourlyEmployee emp = new HourlyEmployee("Sanji", "s194", 30.0, 15000, 3000, 200);
        IPayStub stub = emp.runPayroll(-5);  // Negative hours

        assertNull(stub); // Should skip the pay period
    }

    @Test
    public void testToCSV() {
        HourlyEmployee emp = new HourlyEmployee("Luffy", "s192", 30.0, 20000, 4530, 0);
        assertEquals("HOURLY,Luffy,s192,30.00,0.00,20000.00,4530.00", emp.toCSV());
    }
}
