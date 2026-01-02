package modele.Donjon;


import modele.Items.Item;
import modele.Personnage.PNJ;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import modele.Donjon.fabrique.ChoixTheme;
import modele.Donjon.fabrique.FabriqueTheme;

public class Donjon {

    public enum Theme {
        MEDIEVAL,
        FUTURISTE,
        HORREUR_FANTASTIQUE
    }
    private final Theme theme;
    private final FabriqueTheme fabrique;
    private final List<Salle> salles = new ArrayList<>();
    private final Random random = new Random();

    public Donjon(Theme theme) {
        this.theme = theme;
        this.fabrique = ChoixTheme.of(theme);
        genererSalles();
    }

    public Theme getTheme() {
        return theme;
    }

    public List<Salle> getSalles() {
        return salles;
    }

    private void genererSalles() {
        for (int i = 1; i <= 10; i++) {
            Salle salle = new Salle(i);
            int nbEnnemis = 1 + random.nextInt(4);
            for (int e = 0; e < nbEnnemis; e++) {
                salle.ajouterEnnemi(genererPNJ());
            }
            int nbObjets = random.nextInt(3);
            for (int o = 0; o < nbObjets; o++) {
                salle.ajouterItem(genererItem());
            }
            salles.add(salle);
        }
        // Boss final
        Salle boss = new Salle(11);
        boss.ajouterEnnemi(genererBoss());
        salles.add(boss);
    }

    private PNJ genererBoss() {
        return fabrique.creerBoss();
    }

    private PNJ genererPNJ() {
        return fabrique.creerPNJ(random);
    }

    private Item genererItem() {
        if (random.nextBoolean()) {
            return genererEquipement();
        }
        return genererConsommable();
    }

    private Item genererConsommable() {
        return fabrique.creerConsommable(random);
    }

    private Item genererEquipement() {
        return fabrique.creerEquipement(random);
    }
}
