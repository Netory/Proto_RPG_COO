package modele.Personnage.Passifs;

import modele.Personnage.ClasseHeros;

public class PeauDure implements Observateur {

    private final int reduction;

    public PeauDure() {
        this.reduction =3;
    }

    @Override
    public void mettreAJour(Evenement e) {
        if (e.getType() != TypeEvenement.ATTAQUE_SUBIE) return;

        if (!e.getJoueur().possedeClasse(ClasseHeros.BARBARE)
                && !e.getJoueur().possedeClasse(ClasseHeros.ARCHER)) {
            return;
        }

        int valeurInitiale = e.getValeur();
        e.setValeur(e.getValeur() - reduction);
        if (e.getValeur() != valeurInitiale) {
            e.ajouterMessage("[Passif] Peau Dure : -" + reduction + " degats subis");
        }

    }
    @Override
    public String getDescription() {
        return "[Passif] Peau Dure : -"+ 3 +" degats subis";
    }
}
