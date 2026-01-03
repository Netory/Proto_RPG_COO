package modele.Personnage.Passifs;

import modele.Personnage.ClasseHeros;
import modele.Personnage.Personnage;

public class ContreAttaque implements Observateur {

    private final int pourcentage;

    public ContreAttaque(int pourcentage) {
        this.pourcentage = Math.max(10, Math.min(20, pourcentage));
    }

    @Override
    public void mettreAJour(Evenement e) {
        if (e.getType() != TypeEvenement.ATTAQUE_SUBIE) return;

        if (!e.getJoueur().possedeClasse(ClasseHeros.BARBARE)
                && !e.getJoueur().possedeClasse(ClasseHeros.ASSASSIN)) {
            return;
        }

        Personnage attaquant = e.getSource();
        if (attaquant == null || !attaquant.estVivant()) return;

        int degatsSubis = e.getValeur();
        if (degatsSubis <= 0) return;

        int riposte = Math.max(1, (degatsSubis * pourcentage) / 100);
        attaquant.recevoirDegats(riposte);
    }
}
