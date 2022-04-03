package uml;

/**
 * Runs programs in CLI or GUI depending on runtime args
 *
 * @authors
 */
public class Driver {

    public static void main(String[] args) {

        UMLModel model = new UMLModel();
        Caretaker caretaker = new Caretaker();
        CLIController controller = new CLIController(model, caretaker);
        if (args.length == 0) {
            controller.run();
            //GUIView.main(args);
        } else {
            if (args[0].equals("--cli")) {
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