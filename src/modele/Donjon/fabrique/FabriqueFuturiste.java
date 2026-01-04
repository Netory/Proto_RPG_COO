package modele.Donjon.fabrique;

import modele.Items.Consumable;
import modele.Items.Equipement;
import modele.Items.Item;
import modele.Items.ObjetLegendaire;
import modele.Personnage.PNJ;
import modele.Personnage.Personnage;

import java.util.Random;

public class FabriqueFuturiste implements FabriqueTheme {

    @Override
    public PNJ creerBoss() {
        return new PNJ("Mecha Titan", 220, 22, 18, 22, 18, Personnage.TypeAttaque.PHYSIQUE, "Canon plasma");
    }

    @Override
    public PNJ creerPNJ(Random random) {
        int tirage = random.nextInt(101);
        if (tirage < 30) {
            return new PNJ("Soldat cybernetique", 86, 14, 10, 12, 8, Personnage.TypeAttaque.PHYSIQUE, "Rafale");
        } else if (tirage < 55) {
            return new PNJ("Robot sentinelle", 60, 10, 15, 10, 12, Personnage.TypeAttaque.PHYSIQUE, "Laser");
        } else if (tirage < 80) {
            return new PNJ("Alien", 76, 12, 14, 10, 14, Personnage.TypeAttaque.MAGIQUE, "Psionique");
        } else {
            return new PNJ("Drone de combat", 46, 8, 18, 6, 10, Personnage.TypeAttaque.PHYSIQUE, "Missile");
        }
    }

    @Override
    public Item creerConsommable(Random random) {
        int choix = random.nextInt(3);
        switch (choix) {
            case 0:
                return new Consumable("Nanites reparatrices", "Rend 30 PV", Consumable.Effet.SOIN, 30, 0);
            case 1:
                return new Consumable("Booster musculaire", "+5 force / 3 tours", Consumable.Effet.FORCE, 5, 3);
            default:
                return new Consumable("Champ de force", "-20% degats / 2 tours", Consumable.Effet.RESISTANCE, 20, 2);
        }
    }

    @Override
    public Item creerEquipement(Random random) {
        int choix = random.nextInt(7);
        switch (choix) {
            case 0:
                return new Equipement("Pistolet laser", "+10 intelligence , +5 dexterité",
                        Equipement.TypeEquipement.Arme, 0, 10, 5, 0, 10);
            case 1:
                return new Equipement("Casque nano", "+5 constitution",
                        Equipement.TypeEquipement.Casque, 0, 0, 0, 5, 0);
            case 2:
                return new Equipement("Sabre laser", "+25 force",
                        Equipement.TypeEquipement.Arme, 0, 25, 0, 0, 0);
            case 3:
                return new Equipement("Pantalon parre-laser", "+15 constitution",
                        Equipement.TypeEquipement.Jambiere, 0, 10, 0, 15, 0);
            case 4:
                return new Equipement("Jetpack", "+10 dexterite , +5 constitution",
                        Equipement.TypeEquipement.Plastron, 0, 10, 10, 5, 0);
            case 5:
                return new Equipement("Etoile naine", "+20 force , +20 intelligence",
                        Equipement.TypeEquipement.Arme, 0, 20, 0, 0, 20);
            default:
                return new Equipement("Bottes anti-gravité", "+5 dexterite",
                        Equipement.TypeEquipement.Bottes, 0, 0, 5, 0, 0);
        }
    }

    @Override
    public Item creerObjetLegendaire() {
        return new ObjetLegendaire(
                        "Lame Photon Prototype",
                        "Une arme experimentale reajuste tes capacites et debloque un nouveau style de combat.",
                        null
                );
        }
}