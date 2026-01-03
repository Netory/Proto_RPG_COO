package modele.Personnage.Passifs;

import modele.Personnage.ClasseHeros;

public class Adrenaline implements Observateur {

    private final int pourcentage;

    public Adrenaline(int pourcentage) {
        this.pourcentage = Math.max(10, Math.min(20, pourcentage));
    }

    @Override
    public void mettreAJour(Evenement e) {
        if (e.getType() != TypeEvenement.ATTAQUE_EFFECTUEE) return;

        if (!e.getJoueur().possedeClasse(ClasseHeros.ASSASSIN)
                && !e.getJoueur().possedeClasse(ClasseHeros.ARCHER)) {
            return;
        }

        int base = e.getJoueur().getForce();
        int bonus = Math.max(1, (base * pourcentage) / 100);
        e.getJoueur().appliquerForce(bonus, 1);
    }
}
