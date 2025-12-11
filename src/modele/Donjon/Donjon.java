package modele.Donjon;

import modele.Items.Consumable;
import modele.Items.Equipement;
import modele.Items.Item;
import modele.Personnage.PNJ;
import modele.Personnage.Personnage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Donjon {

    public enum Theme {
        MEDIEVAL,
        FUTURISTE
    }

    private final Theme theme;
    private final List<Salle> salles = new ArrayList<>();
    private final Random random = new Random();

    public Donjon(Theme theme) {
        this.theme = theme;
        genererSalles();
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
        if (theme == Theme.MEDIEVAL) {
            return new PNJ("Dragon", 250, 25, 15, 25, 15, Personnage.TypeAttaque.PHYSIQUE, "Souffle de feu");
        }
        return new PNJ("Mecha Titan", 220, 22, 18, 22, 18, Personnage.TypeAttaque.PHYSIQUE, "Canon plasma");
    }

    private PNJ genererPNJ() {
        if (theme == Theme.MEDIEVAL) {
            int tirage = random.nextInt(100);
            if (tirage < 30) { // 30%
                return new PNJ("Chevalier errant", 120, 15, 8, 12, 5, Personnage.TypeAttaque.PHYSIQUE, "Coup d'epee");
            } else if (tirage < 55) { // +25% = 55
                return new PNJ("Sorciere", 90, 6, 10, 10, 20, Personnage.TypeAttaque.MAGIQUE, "Trait de feu");
            } else if (tirage < 75) { // +20% = 75
                return new PNJ("Vautour", 60, 10, 18, 8, 5, Personnage.TypeAttaque.PHYSIQUE, "Serres acerees");
            } else if (tirage < 90) { // +15% = 90
                return new PNJ("Rat enrage", 50, 8, 12, 6, 3, Personnage.TypeAttaque.PHYSIQUE, "Morsure");
            } else { // 10%
                return new PNJ("Gobelin", 80, 12, 12, 8, 6, Personnage.TypeAttaque.PHYSIQUE, "Coup de massue");
            }
        } else {
            int tirage = random.nextInt(100);
            if (tirage < 30) {
                return new PNJ("Soldat cybernetique", 110, 14, 10, 12, 8, Personnage.TypeAttaque.PHYSIQUE, "Rafale");
            } else if (tirage < 55) {
                return new PNJ("Robot sentinelle", 90, 10, 15, 10, 12, Personnage.TypeAttaque.PHYSIQUE, "Laser");
            } else if (tirage < 80) {
                return new PNJ("Alien", 100, 12, 14, 10, 14, Personnage.TypeAttaque.MAGIQUE, "Psionique");
            } else {
                return new PNJ("Drone de combat", 70, 8, 18, 6, 10, Personnage.TypeAttaque.PHYSIQUE, "Missile");
            }
        }
    }

    private Item genererItem() {
        if (random.nextBoolean()) {
            return genererEquipement();
        }
        return genererConsommable();
    }

    private Item genererConsommable() {
        int choix = random.nextInt(3);
        if (theme == Theme.MEDIEVAL) {
            switch (choix) {
                case 0:
                    return new Consumable("Potion de soin", "Rend 30 PV", Consumable.Effet.SOIN, 30, 0);
                case 1:
                    return new Consumable("Potion de force", "+5 force / 3 tours", Consumable.Effet.FORCE, 5, 3);
                default:
                    return new Consumable("Potion de resistance", "-20% degats / 2 tours", Consumable.Effet.RESISTANCE, 20, 2);
            }
        } else {
            switch (choix) {
                case 0:
                    return new Consumable("Nanites reparatrices", "Rend 25 PV", Consumable.Effet.SOIN, 25, 0);
                case 1:
                    return new Consumable("Booster musculaire", "+5 force / 3 tours", Consumable.Effet.FORCE, 5, 3);
                default:
                    return new Consumable("Champ de force", "-20% degats / 2 tours", Consumable.Effet.RESISTANCE, 20, 2);
            }
        }
    }

    private Item genererEquipement() {
        int choix = random.nextInt(3);
        if (theme == Theme.MEDIEVAL) {
            switch (choix) {
                case 0:
                    return new Equipement("Hache rouillee", "+10 force", Equipement.TypeEquipement.Arme, 0, 10, 0, 0, 0);
                case 1:
                    return new Equipement("Casque de cuir", "+2 constitution", Equipement.TypeEquipement.Casque, 0, 0, 0, 2, 0);
                default:
                    return new Equipement("Bottes renforcees", "+2 dexterite", Equipement.TypeEquipement.Bottes, 0, 0, 2, 0, 0);
            }
        } else {
            switch (choix) {
                case 0:
                    return new Equipement("Pistolet laser", "+8 force", Equipement.TypeEquipement.Arme, 0, 8, 0, 0, 0);
                case 1:
                    return new Equipement("Casque nano", "+2 constitution", Equipement.TypeEquipement.Casque, 0, 0, 0, 2, 0);
                default:
                    return new Equipement("Bottes maglev", "+3 dexterite", Equipement.TypeEquipement.Bottes, 0, 0, 3, 0, 0);
            }
        }
    }
}
