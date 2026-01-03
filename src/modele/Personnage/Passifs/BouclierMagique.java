package modele.Personnage.Passifs;

import modele.Personnage.ClasseHeros;

public class BouclierMagique implements Observateur {

    private boolean disponible = true;
    private boolean actifCeTour = false;

    @Override
    public void mettreAJour(Evenement e) {
        if (!e.getJoueur().possedeClasse(ClasseHeros.SORCIER)) return;

        if (e.getType() == TypeEvenement.DEBUT_COMBAT) {
            disponible = true;
            actifCeTour = false;
            return;
        }

        if (e.getType() == TypeEvenement.DEBUT_TOUR) {
            actifCeTour = false;
            return;
        }

        if (e.getType() == TypeEvenement.ATTAQUE_SUBIE && disponible && !actifCeTour) {
            e.setValeur(0);
            actifCeTour = true;
            disponible = false;
        }
    }
}
