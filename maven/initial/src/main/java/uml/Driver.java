package uml;

/**
 * Runs programs in CLI or GUI depending on runtime args
 *
 * @authors
 */
public class Driver {

    public static void main(String[] args) {
        if (args.length == 0) {
            CLIController.main(args);
        } else {
            if (args[0].equals("--cli")) {
                CLIController.main(args);
            } else if (args[0].equals("--gui")) {
                GUIController.main(args);
            }
            else {
                System.out.println("Invalid flag. Use either --cli to start the command line interface or --gui to start the GUI interface.");
            }
        }
    }
}