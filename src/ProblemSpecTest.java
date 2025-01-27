import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import student.TestCase;

/**
 * @author Guann-Luen Chen
 * @version 2024.11.14
 */
public class ProblemSpecTest extends TestCase {
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing here
    }


    /**
     * Read contents of a file into a string
     * 
     * @param path
     *            File name
     * @return the string
     * @throws IOException
     *         from IO stream
     */
    static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
    }


    /**
     * This method is simply to get coverage of the class declaration.
     * @throws Exception 
     *         from byte stream or from IO stream
     */
    public void testMInitx() throws Exception {
        SemManager sem = new SemManager();
        assertNotNull(sem);
//        SemManager.main(null);
    }


    /**
     * Full parser test
     * @throws Exception 
     *         from byte stream or from IO stream
     */
    public void testparserfull() throws Exception {
        String[] args = new String[3];
        args[0] = "512";
        args[1] = "4";
        args[2] = "P4Sample_input.txt";

        SemManager.main(args);
        String output = systemOut().getHistory();
        String referenceOutput = readFile("P4Sample_output.txt");
        assertFuzzyEquals(referenceOutput, output);
    }


    /**
     * Simple parser test (input only)
     * @throws Exception 
     *         from byte stream or from IO stream
     */
    public void testparserinput() throws Exception {
        String[] args = new String[3];
        args[0] = "2048";
        args[1] = "16";
        args[2] = "P4SimpSample_input.txt";

        SemManager.main(args);
        String output = systemOut().getHistory();
        String referenceOutput = readFile("P4SimpSample_output.txt");
        assertFuzzyEquals(referenceOutput, output);
    }
}
