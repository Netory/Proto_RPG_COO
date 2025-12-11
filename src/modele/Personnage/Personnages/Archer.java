package modele.Personnage.Personnages;

import modele.Personnage.Joueur;
import modele.Personnage.Personnage;

public class Archer extends Joueur {
    public Archer(String nom) {
        super(nom, 120, 12, 18, 12, 10, Personnage.TypeAttaque.PHYSIQUE);
    }
}
