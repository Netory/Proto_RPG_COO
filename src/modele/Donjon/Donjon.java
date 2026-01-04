package modele.Donjon;

import modele.Donjon.fabrique.ChoixTheme;
import modele.Donjon.fabrique.FabriqueTheme;
import modele.Items.Item;
import modele.Personnage.PNJ;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    // Une seule salle (parmi 1..10) contiendra l'arme/objet legendaire
    private final int numeroSalleLegendaire;

    public Donjon(Theme theme) {
        this.theme = theme;
        this.fabrique = ChoixTheme.of(theme);

        // Choix aleatoire d'une salle entre 1 et 10 (incluse)
        this.numeroSalleLegendaire = random.nextInt(10) + 1;

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

            // Une seule arme/objet legendaire dans une salle aleatoire
            if (i == numeroSalleLegendaire) {
                salle.ajouterItem(creerArmeLegendaire());
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

   
    private Item creerArmeLegendaire() {

        return fabrique.creerObjetLegendaire();
       /* switch (theme) {
            case MEDIEVAL:
                return new ObjetLegendaire(
                        "Epee du Serment Brise",
                        "Une lame ancienne chargee d'une volonte etrange... Elle t'ouvre une nouvelle voie.",
                        null
                );
            case FUTURISTE:
                return new ObjetLegendaire(
                        "Lame Photon Prototype",
                        "Une arme experimentale reajuste tes capacites et debloque un nouveau style de combat.",
                        null
                );
            case HORREUR_FANTASTIQUE:
                return new ObjetLegendaire(
                        "Faux des Murmures",
                        "Elle pulse dans ta main... et quelque chose en toi change. Une nouvelle voie s'eveille.",
                        null
                );
            default:
                return new ObjetLegendaire(
                        "Arme legendaire",
                        "Une arme dont tu ignores encore les effets.",
                        null
                );*/
    }
}

