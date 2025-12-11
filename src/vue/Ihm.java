package vue;

import java.util.Scanner;

public class Ihm {
    private Scanner scanner;

    public Ihm() {
        this.scanner = new Scanner(System.in);
    }

    public void afficherMessage(String message) {
        System.out.println(message);
    }

    public String demanderCaracteres(String message) {
        System.out.println(message);
        String ligne = scanner.nextLine();
        while (ligne != null && ligne.trim().isEmpty() && scanner.hasNextLine()) {
            ligne = scanner.nextLine();
        }
        return ligne == null ? "" : ligne.trim();
    }

    public int demanderEntier(String message) {
        System.out.println(message);
        while (true) {
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrez un nombre valide :");
            }
        }
    }

    public Scanner getScanner() {
        return this.scanner;
    }
}
