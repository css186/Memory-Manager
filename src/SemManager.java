
/**
 * This project will utilize a buffer pool from a memory manager
 * that could store serialized object data parsed from text files
 * The position and data size in the buffer pool is stored using
 * a hash table using simple hash function and quadratic probing
 * to deal with collision. 
 */

/**
 * The class containing the main method.
 *
 * @author Guann-Luen Chen
 * @version 2024.11.14
 */

// On my honor:
// - I have not used source code obtained from another current or
//   former student, or any other unauthorized source, either
//   modified or unmodified.
//
// - All source code and documentation used in my program is
//   either my original work, or was derived by me from the
//   source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
//   anyone other than my partner (in the case of a joint
//   submission), instructor, ACM/UPE tutors or the TAs assigned
//   to this course. I understand that I may discuss the concepts
//   of this program with other students, and that another student
//   may help me debug my program so long as neither of us writes
//   anything during the discussion or modifies any computer file
//   during the discussion. I have violated neither the spirit nor
//   letter of this restriction.


public class SemManager {
    /**
     * Main method
     * @param args
     *        Command line parameters
     * @throws Exception
     *         from byte stream or from IO stream
     */
    public static void main(String[] args) throws Exception {
        // This is the main file for the program.
        int memorySize = Integer.parseInt(args[0]);
        int hashSize = Integer.parseInt(args[1]);
        String filename = args[2];
        Controller controller = new Controller(memorySize, hashSize);
        CommandProcessor cp = new CommandProcessor(filename, controller);
        cp.execute();
        
        
        
        
    }
}
