import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import student.TestCase;

/**
 * Test cases for Command Processor
 * 
 * @author Guann-Luen Chen
 * @version 2024.11.14
 */

public class CommandProcessorTest extends TestCase {
    // ~ Fields ................................................................
    //
    // ----------------------------------------------------------
    private CommandProcessor testProcessor;
    private Controller controller;
    private String inputPath;
    private String outputPath;
    
    // ~ Set up ............................................................
    //
    /**
     * Sets up the tests that follow. In general, used for initialization
     * 
     */
    public void setUp() {
        this.inputPath = "./P4Sample_input.txt";
        this.outputPath = "./P4Sample_output.txt";
        this.controller = new Controller(512, 4);
        this.testProcessor = new CommandProcessor(
            this.inputPath, 
            this.controller);

    }
 
    // ~ Test Method ........................................................
    //
    // ----------------------------------------------------------
    /**
     * Test input case 1
     * @throws Exception
     *         Throw Exception if file does not exist 
     */
    public void testSample() throws Exception {
        this.testProcessor.execute();
        assertNotNull(this.testProcessor.getController());
        assertNotNull(this.controller);
    }
    
    // ----------------------------------------------------------
    /**
     * Test input case 2
     * @throws Exception
     *         Throw Exception if file does not exist 
     */
    public void testSampleOutput() throws Exception {
        this.testProcessor.parseFile(inputPath);
        String outputContent = new String(Files.readAllBytes(
            Paths.get(this.outputPath)), StandardCharsets.UTF_8);
        outputContent = outputContent + "\n";
        String outputHistory = systemOut().getHistory();
        assertEquals(outputContent, outputHistory);
    }
    
    // ----------------------------------------------------------
    /**
     * Test empty file
     * @throws Exception
     *         Throw Exception if file does not exist        
     */
    public void testEmptyFile() throws Exception {
        File emptyFile = new File("./test_file.txt");
        assertTrue(emptyFile.exists());
        testProcessor.parseFile(emptyFile.getAbsolutePath());
        assertFalse(controller.search(1));
        assertFalse(controller.search(2));
    }
    
}