package modele.Personnage.Personnages;

import modele.Personnage.Joueur;
import modele.Personnage.Personnage;

public class Assassin extends Joueur {
    public Assassin(String nom) {
        super(nom, 110, 15, 20, 10, 8, Personnage.TypeAttaque.PHYSIQUE);
    }
}
