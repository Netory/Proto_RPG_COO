package modele.Personnage.Personnages;

import modele.Personnage.ClasseHeros;
import modele.Personnage.Joueur;
import modele.Personnage.Personnage;

public class Barbare extends Joueur {

    public Barbare(String nom) {
        super(nom, 150, 20, 10, 15, 5, Personnage.TypeAttaque.PHYSIQUE);
        setClassePrimera(ClasseHeros.BARBARE);
    }
}
