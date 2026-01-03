package modele.Personnage.Passifs;

import modele.Personnage.ClasseHeros;

public class Adrenaline implements Observateur {

    private final int buff =2;
    private String description = "[Passif] Adrenaline : Force +" + buff + " (1 tour)";

    public Adrenaline() {
    }

    @Override
    public void mettreAJour(Evenement e) {
        if (e.getType() != TypeEvenement.ATTAQUE_EFFECTUEE) return;

        if (!e.getJoueur().possedeClasse(ClasseHeros.ASSASSIN)
                && !e.getJoueur().possedeClasse(ClasseHeros.ARCHER)) {
            return;
        }

        e.getJoueur().appliquerForce(buff, 1);
        e.ajouterMessage("[Passif] Adrenaline : +" + buff + " FOR pour 1 tour");
        
    }
    @Override
    public String getDescription() {
        return description;
    }
}
