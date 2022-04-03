package uml;

/**
 * Runs programs in CLI or GUI depending on runtime args
 *
 * @authors
 */
public class Driver {

    public static void main(String[] args) {

        if (args.length == 0) {
            GUIView.main(args);
        } else {
            if (args[0].equals("--cli")) {
                UMLModel model = new UMLModel();
                Caretaker caretaker = new Caretaker();
                CLIController controller = new CLIController(model, caretaker);
                controller.run();
            } else if (args[0].equals("--gui")) {
                GUIView.main(args);
            }
            else {
                System.out.println("Invalid flag. Use either --cli to start the command line interface or --gui to start the GUI interface.");
            }
        }
    }
}