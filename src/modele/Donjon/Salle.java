package modele.Donjon;

import modele.Items.Item;
import modele.Personnage.PNJ;

import java.util.ArrayList;
import java.util.List;

public class Salle {
    private final List<PNJ> ennemis = new ArrayList<>();
    private final List<Item> loot = new ArrayList<>();
    private final int numero;

    public Salle(int numero) {
        this.numero = numero;
    }

    public void ajouterEnnemi(PNJ pnj) {
        ennemis.add(pnj);
    }

    public void ajouterItem(Item item) {
        loot.add(item);
    }

    public List<PNJ> getEnnemis() {
        return ennemis;
    }

    public List<Item> getLoot() {
        return loot;
    }

    public int getNumero() {
        return numero;
    }

    public boolean estTerminee() {
        return ennemis.stream().noneMatch(PNJ::estVivant);
    }
}
