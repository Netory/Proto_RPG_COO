package modele.Items;

import modele.Personnage.Joueur;

public class Consumable extends Item {
    public enum Effet {
        SOIN, FORCE, RESISTANCE
    }

    private final Effet effet;
    private final int valeur;
    private final int dureeTours;

    public Consumable(String nom, String description, Effet effet, int valeur, int dureeTours) {
        super(nom, description);
        this.effet = effet;
        this.valeur = valeur;
        this.dureeTours = dureeTours;
    }

    public void appliquer(Joueur joueur) {
        switch (effet) {
            case SOIN:
                joueur.soigner(valeur);
                break;
            case FORCE:
                joueur.soigner(0);
                break;
            case RESISTANCE:
                joueur.soigner(0);
                break;
            default:
                break;
        }
    }

    public Effet getEffet() {
        return effet;
    }

    public int getValeur() {
        return valeur;
    }

    public int getDureeTours() {
        return dureeTours;
    }
}
