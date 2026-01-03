package modele.Personnage.Strategies;

import modele.Personnage.Joueur;
import modele.Personnage.Personnage;

public interface AttackStrategy {
    String getNom();
    Personnage.TypeAttaque getTypeAttaque(Joueur attaquant);
    int calculerAttaque(Joueur attaquant);
}
