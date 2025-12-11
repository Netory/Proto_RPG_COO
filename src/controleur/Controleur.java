package controleur;
import modele.Commande.Commande;
import vue.Ihm;

import java.util.Map;

public class Controleur {

    private Ihm ihm;

    private Map<Integer, Commande> commandes;

    public Controleur(Ihm ihm){
        this.ihm=ihm;

    }
    public void jouer(){
        String theme=ihm.demanderCaracteres("Dans quel thème voulez vous jouez, Médiéval ou Futuriste ?");
        String classp=ihm.demanderCaracteres("Vous devez maintenant creer votre personnage , pour cela choisissez sa classe parmis : Barbare, Sorcier, Archer, Assassin");

    }
}
