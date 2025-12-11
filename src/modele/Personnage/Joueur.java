package modele.Personnage;

import modele.Items.Consumable;
import modele.Items.Equipement;
import modele.Items.Item;

import java.util.ArrayList;
import java.util.List;

public class Joueur extends Personnage {

    private final List<Item> inventaire = new ArrayList<>();
    private int bonusForceTours;
    private int bonusForceValeur;
    private int bonusResistanceTours;
    private int bonusResistancePourcent;

    public Joueur(String nom,
                  int pv,
                  int force,
                  int dexterite,
                  int constitution,
                  int intelligence,
                  TypeAttaque typeAttaque) {
        super(nom, pv, force, dexterite, constitution, intelligence, typeAttaque);
    }

    @Override
    public int calculAttaque() {
        int base = super.calculAttaque();
        if (bonusForceTours > 0) {
            return base + bonusForceValeur;
        }
        return base;
    }

    @Override
    public void recevoirDegats(int degatsBruts) {
        int degats = Math.max(1, degatsBruts - calculReductionDefense());
        if (bonusResistanceTours > 0) {
            degats = Math.max(1, degats - (degats * bonusResistancePourcent) / 100);
        }
        super.recevoirDegats(degats);
    }

    public void decrementerBuffs() {
        if (bonusForceTours > 0) bonusForceTours--;
        if (bonusResistanceTours > 0) bonusResistanceTours--;
    }

    public void appliquerSoin(int valeur) {
        soigner(valeur);
    }

    public void appliquerForce(int valeur, int tours) {
        bonusForceValeur = valeur;
        bonusForceTours = tours;
    }

    public void appliquerResistance(int pourcent, int tours) {
        bonusResistancePourcent = pourcent;
        bonusResistanceTours = tours;
    }

    public void ajouterObjet(Item item) {
        if (item != null) {
            inventaire.add(item);
        }
    }

    public List<Item> getInventaire() {
        return inventaire;
    }

    public String afficherInventaire() {
        if (inventaire.isEmpty()) {
            return "Inventaire vide.";
        }
        StringBuilder sb = new StringBuilder("Inventaire:\n");
        for (int i = 0; i < inventaire.size(); i++) {
            sb.append("[").append(i).append("] ").append(inventaire.get(i).descriptionCourte()).append("\n");
        }
        return sb.toString();
    }

    public boolean utiliserObjet(int index) {
        if (index < 0 || index >= inventaire.size()) return false;
        Item item = inventaire.get(index);
        if (item instanceof Consumable) {
            Consumable consumable = (Consumable) item;
            switch (consumable.getEffet()) {
                case SOIN:
                    appliquerSoin(consumable.getValeur());
                    break;
                case FORCE:
                    appliquerForce(consumable.getValeur(), consumable.getDureeTours());
                    break;
                case RESISTANCE:
                    appliquerResistance(consumable.getValeur(), consumable.getDureeTours());
                    break;
                default:
                    break;
            }
            inventaire.remove(index);
            return true;
        }
        if (item instanceof Equipement) {
            Equipement equipement = (Equipement) item;
            equiper(equipement);
            inventaire.remove(index);
            return true;
        }
        return false;
    }
}
