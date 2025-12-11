package vue;

import java.util.Scanner;

public class Ihm {
    private Scanner scanner;

    public Ihm(){
        this.scanner = new Scanner(System.in);
    }
    public void afficherMessage(String message){
        //method pour afficher un message
        System.out.println(message);
    }

    public String demanderCaracteres(String message){
        //method pour afficher un message et demander une reponse en string
        System.out.println(message);
        return scanner.next();
    }

    public int demanderEntier(String message){
        //method pour afficher un message et demander une reponse d'entier
        System.out.println(message);
        return scanner.nextInt();
    }
    public Scanner getScanner(){
        return this.scanner;
    }
    public void emptyScanner(){
        this.scanner.next();
    }

}
