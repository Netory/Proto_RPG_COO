package modele.Personnage.Strategies;

import modele.Personnage.Joueur;
import modele.Personnage.Personnage;

public class MagicAttack implements AttackStrategy {
    @Override
    public String getNom() {
        return "Attaque de classe (magique)";
    }

    @Override
    public Personnage.TypeAttaque getTypeAttaque(Joueur attaquant) {
        return Personnage.TypeAttaque.MAGIQUE;
    }

    @Override
    public int calculerAttaque(Joueur attaquant) {
        return attaquant.calculAttaque();
    }
}
