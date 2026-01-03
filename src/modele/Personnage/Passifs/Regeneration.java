package modele.Personnage.Passifs;

public class Regeneration implements Observateur {

    private final int soin;

    public Regeneration() {
        this.soin = 5; // fixe
    }

    @Override
    public void mettreAJour(Evenement e) {
        if (e.getType() != TypeEvenement.DEBUT_TOUR) return;


        e.getJoueur().appliquerSoin(soin);
        e.ajouterMessage("[Passif] Regeneration : +" + soin + " PV");


    }
    @Override
    public String getDescription() {
        return "[Passif] Régénération : +" + soin + " PV au début de chaque tour";
    }
}
