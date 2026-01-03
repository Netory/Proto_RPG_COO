package modele.Personnage.Passifs;

import modele.Personnage.ClasseHeros;

public class CriDeGuerre implements Observateur {

    private final int force=2;
    private final int constitution=2;
    private boolean actif = false;
    private String description = "[Passif] Cri de Guerre : +" + force + " FOR, +" + constitution + " CON (combat)";

    public CriDeGuerre() {
    }

    @Override
    public void mettreAJour(Evenement e) {
        if (!e.getJoueur().possedeClasse(ClasseHeros.BARBARE)) return;

        if (e.getType() == TypeEvenement.DEBUT_COMBAT && !actif) {
            
            e.getJoueur().appliquerForce(force, 9999);
            e.getJoueur().appliquerConstitution(constitution, 9999);

            actif = true;
            e.ajouterMessage("[Passif] Cri de Guerre : +" + force + " FOR et +" + constitution + " CON pour ce combat");
            return;
        }

        if (e.getType() == TypeEvenement.FIN_COMBAT) {
            e.getJoueur().retirerBonusCombat();
            actif = false;
            e.ajouterMessage("[Passif] Cri de Guerre se dissipe en fin de combat");
        }
    }
    @Override
    public String getDescription() {
        return description;
    }
}
