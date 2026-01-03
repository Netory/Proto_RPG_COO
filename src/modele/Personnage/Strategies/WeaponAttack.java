package modele.Personnage.Strategies;

import modele.Personnage.Joueur;
import modele.Personnage.Personnage;

public class WeaponAttack implements AttackStrategy {
    @Override
    public String getNom() {
        return "Attaque d'arme";
    }

    @Override
    public Personnage.TypeAttaque getTypeAttaque(Joueur attaquant) {
        if (attaquant.getArme() != null && attaquant.getArme().getIntelligence() > 0) {
            return Personnage.TypeAttaque.MAGIQUE;
        }
        return Personnage.TypeAttaque.PHYSIQUE;
    }

    @Override
    public int calculerAttaque(Joueur attaquant) {
        return attaquant.calculAttaque();
    }
}
