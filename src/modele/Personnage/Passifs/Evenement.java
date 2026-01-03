package modele.Personnage.Passifs;

import modele.Items.Item;
import modele.Personnage.Joueur;
import modele.Personnage.Personnage;

public class Evenement {

    private final TypeEvenement type;
    private final Joueur joueur;
    private final Personnage source;
    private final Personnage cible;
    private int valeur;
    private final Item item;

    public Evenement(TypeEvenement type,
                     Joueur joueur,
                     Personnage source,
                     Personnage cible,
                     int valeur,
                     Item item) {
        this.type = type;
        this.joueur = joueur;
        this.source = source;
        this.cible = cible;
        this.valeur = valeur;
        this.item = item;
    }

    public TypeEvenement getType() { return type; }
    public Joueur getJoueur() { return joueur; }
    public Personnage getSource() { return source; }
    public Personnage getCible() { return cible; }
    public int getValeur() { return valeur; }
    public Item getItem() { return item; }

    public void setValeur(int valeur) {
        this.valeur = Math.max(0, valeur);
    }
}
