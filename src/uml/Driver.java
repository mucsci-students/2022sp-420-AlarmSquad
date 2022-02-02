package uml;

import java.util.*;

/**
 * Get user input, then use switch statement to process commands.
 * Issue: when user just presses enter as input.
 */
public class Driver {

    public static void main(String[] args){

        String prompt = "> ";
        
        Scanner scan = new Scanner(System.in);

        System.out.print(prompt);
        while(true){
            
            String input = scan.nextLine();


            if (input.equals(null)){
                System.out.print(prompt);
            }
            else{
                switch (input){
                    case "add class":
                        System.out.println("Enter class name: ");
                        input = scan.next();
                        //System.out.println("You added class " + input);
                    case "exit":
                        return;
                    default:
                        System.out.println("Please enter a valid command: ");
                }
            }
        }
        
    }
}