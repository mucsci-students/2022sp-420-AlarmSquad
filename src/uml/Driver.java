package uml;

import java.util.*;

public class Driver {
    public static void main(String[] args){

        String prompt = "> ";
        
        Scanner scan = new Scanner(System.in);

        System.out.print(prompt);
        while(true){
            System.out.println("before scan.nextln");
            String input = scan.nextLine();
            System.out.println("after scan.nextln");

            switch (input){
                case "add class":
                    System.out.println("Enter class name: ");
                    input = scan.next();
                    //System.out.println("You added class " + input);
                case "":
                    System.out.print(prompt);
                case "exit":
                    return;
                default:
                    System.out.println("Please enter a valid command: ");
            }
        }
        
    }
}