package modele.Personnage.Strategies;

import modele.Personnage.Joueur;
import modele.Personnage.Personnage;

public class MeleeAttack implements AttackStrategy {
    @Override
    public String getNom() {
        return "Attaque de classe (physique)";
    }

    @Override
    public Personnage.TypeAttaque getTypeAttaque(Joueur attaquant) {
        return Personnage.TypeAttaque.PHYSIQUE;
    }

    @Override
    public int calculerAttaque(Joueur attaquant) {
        return attaquant.calculAttaque();
    }
}
