/*
 * Students, build off this class. We are providing one sample test case as file reading is new to
 * you.
 * 
 * NOTE: you may end up changing this completely depending on how you setup your project.
 * 
 * we are just using .main() as we know that is an entry point that we specified.
 * 
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import student.PayrollGenerator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestPayrollGenerator {

    @TempDir
    static Path tempDir;


    @Test
    public void testFinalPayStub() throws IOException {
        // copy employees_original.csv into tempDir
        Path employees = tempDir.resolve("employees.csv");
        Files.copy(Paths.get("resources/original/employees_original.csv"), employees);

        // get the path of the paystubs.csv
        Path payStubs = tempDir.resolve("paystubs.csv");

        String[] args = {"-e", employees.toString(), "-t", "resources/time_cards.csv", 
                "-o", payStubs.toString()};

        // run main method
        PayrollGenerator.main(args);

        // Read the expected pay stubs from a file
        Path expectedPayStubsPath = Paths.get("resources/original/pay_stubs_solution_to_original.csv");
        String expectedPayStubs = Files.readString(expectedPayStubsPath);
        String actualPayStubs = Files.readString(payStubs);

        // Split the CSV data into lines and parse the last line for comparison
        String[] expectedLines = expectedPayStubs.trim().split("\n");
        String[] actualLines = actualPayStubs.trim().split("\n");

        // Assuming the last line contains the relevant numeric value
        double expectedValue = Double.parseDouble(expectedLines[expectedLines.length - 1].split(",")[1].trim());
        double actualValue = Double.parseDouble(actualLines[actualLines.length - 1].split(",")[1].trim());

        assertEquals(expectedValue, actualValue, 0.01); // Allowing a small delta for floating point comparison

        // you could also read lines and compared the lists
    }


}
