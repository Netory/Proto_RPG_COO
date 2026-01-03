package modele.Personnage.Passifs;

import modele.Personnage.ClasseHeros;

public class CriDeGuerre implements Observateur {

    private final int forcePourcentage;
    private final int constitutionPourcentage;
    private boolean actif = false;

    public CriDeGuerre(int forcePourcentage, int constitutionPourcentage) {
        this.forcePourcentage = Math.max(2, Math.min(3, forcePourcentage));
        this.constitutionPourcentage = Math.max(5, Math.min(10, constitutionPourcentage));
    }

    @Override
    public void mettreAJour(Evenement e) {
        if (!e.getJoueur().possedeClasse(ClasseHeros.BARBARE)) return;

        if (e.getType() == TypeEvenement.DEBUT_COMBAT && !actif) {
            int bonusForce = Math.max(1, (e.getJoueur().getForce() * forcePourcentage) / 100);
            int bonusConstitution = Math.max(1, (e.getJoueur().getConstitution() * constitutionPourcentage) / 100);

            e.getJoueur().appliquerForce(bonusForce, 9999);
            e.getJoueur().appliquerConstitution(bonusConstitution, 9999);
            actif = true;
            return;
        }

        if (e.getType() == TypeEvenement.FIN_COMBAT) {
            e.getJoueur().retirerBonusCombat();
            actif = false;
        }
    }
}
