package modele.Personnage.Personnages;

import modele.Personnage.ClasseHeros;
import modele.Personnage.Joueur;
import modele.Personnage.Personnage;

public class Sorcier extends Joueur {

    public Sorcier(String nom) {
        super(nom, 100, 5, 8, 10, 25, Personnage.TypeAttaque.MAGIQUE);
        setClassePrimera(ClasseHeros.SORCIER);
    }
}
