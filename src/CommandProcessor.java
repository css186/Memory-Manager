import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

// -------------------------------------------------------------------------
/**
 * This is a class that can read in command line input and parse text file
 * 
 * Source Code Citation:
 * 
 * Title: CommandProcessor.java
 * Author: Guann-Luen Chen
 * Date: Last updated on Oct. 15, 2024
 * Code Version: 2024.10.15
 * <Revised from my own work in Project 2>
 * 
 * @author Guann-Luen Chen
 * @version 2024.11.12
 */

public class CommandProcessor {
    // ~ Fields ................................................................
    //
    // ----------------------------------------------------------
    private String file;
    private Controller controller;
    
    // ~ Constructors ..........................................................
    //
    // ----------------------------------------------------------
    /**
     * Initialization
     * @param file
     *        filename in string
     * @param controller
     *        controller object
     */
    public CommandProcessor(
        String file, 
        Controller controller) {
        this.file = file;
        this.controller = controller;
    }
    
    // ~ Public Method ........................................................
    //
    // ----------------------------------------------------------
    /**
     * Execute file parsing
     * @throws Exception
     *         from IO stream
     */
    public void execute() throws Exception {
        this.parseFile(this.file);      
    }
    
    // ----------------------------------------------------------
    /**
     * Method to parse input text file and give instruction to controller
     * based on the content of the file
     * 
     * @param filename
     *        Input file
     */
    public void parseFile(String filename) throws Exception {
        // Prepare output message
        String msg;
        try (Scanner sc = new Scanner(new File(filename))) {

            while (sc.hasNext()) {
                String cmd = sc.next().trim();
                String nextToken;
                int id = 0;

                switch (cmd) {
                    case "insert":
                        id = Integer.parseInt(sc.next().trim());
                        // Skip to next line
                        sc.nextLine();
                        
                        String title = sc.nextLine().trim();
                        String date = sc.next().trim();
                        int duration = Integer.parseInt(sc.next().trim());
                        short x = Short.parseShort(sc.next().trim());
                        short y = Short.parseShort(sc.next().trim());
                        int cost = Integer.parseInt(sc.next().trim());
                        
                        // Skip the remaining newline after the cost
                        sc.nextLine();
                        
                        // Parse the next lines for tags and description
                        String tags = sc.nextLine().trim();
                        // Split spaces using regex
                        String[] keywords = tags.split("\\s+");
                        String description = sc.nextLine().trim()
                            .replaceAll("\\s+", " ");
                        
                        // Create a new seminar object
                        Seminar newSeminar = new Seminar(id, title, date, 
                            duration, x, y, cost, keywords, description);
                        
                        // Invoke controller's insert method
                        this.controller.insert(id, newSeminar);
                        break;

                    case "print":
                        nextToken = sc.next().trim();
                        switch (nextToken) {
                            case "hashtable":
                                this.controller.printHashTable();
                                break;
                            case "blocks":
                                this.controller.printList();
                                break;
                            default:
                                System.out.println("Invalid Token");
                                break;
                        }
                        break;

                    case "search":
                        id = Integer.parseInt(sc.next().trim());
                        this.controller.search(id);
                        break;

                    case "delete":
                        id = Integer.parseInt(sc.next().trim());
                        this.controller.delete(id);
                        break;

                    default:
                        System.out.println("Invalid token");
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    // ----------------------------------------------------------
    /**
     * @return 
     *         the file
     */
    public String getFile() {
        return file;
    }
    
    // ----------------------------------------------------------
    /**
     * @param file 
     *        the file to set
     */
    public void setFile(String file) {
        this.file = file;
    }
    
    // ----------------------------------------------------------
    /**
     * @return 
     *         the controller
     */
    public Controller getController() {
        return controller;
    }
    
    // ----------------------------------------------------------
    /**
     * @param controller 
     *        the controller to set
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }
    
}
