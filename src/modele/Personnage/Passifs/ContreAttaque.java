package modele.Personnage.Passifs;

import modele.Personnage.ClasseHeros;
import modele.Personnage.Personnage;

public class ContreAttaque implements Observateur {


    public ContreAttaque() {
      
    }

    @Override
    public void mettreAJour(Evenement e) {
        if (e.getType() != TypeEvenement.ATTAQUE_SUBIE) return;

        if (!e.getJoueur().possedeClasse(ClasseHeros.ASSASSIN)
                && !e.getJoueur().possedeClasse(ClasseHeros.BARBARE)) {
            return;
        }

        Personnage attaquant = e.getSource();
        if (attaquant == null || !attaquant.estVivant()) return;

        int degatsSubis = e.getValeur();
        if (degatsSubis <= 0) return; // si bouclier a tout annule, pas de riposte

        int riposte =Math.max(1,(degatsSubis/2));
        attaquant.recevoirDegats(riposte);
        e.ajouterMessage("[Passif] Contre-Attaque : riposte " + riposte + " degats sur " + attaquant.getNom());
    }
    @Override
    public String getDescription() {
        return "[Passif] Contre-Attaque : Riposte 50% des degats subis";
    }
}
