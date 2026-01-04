package modele.Donjon.fabrique;

import modele.Items.Consumable;
import modele.Items.Equipement;
import modele.Items.Item;
import modele.Items.ObjetLegendaire;
import modele.Personnage.PNJ;
import modele.Personnage.Personnage;

import java.util.Random;

public class FabriqueMedievale implements FabriqueTheme {

    @Override
    public PNJ creerBoss() {
        return new PNJ("Dragon", 250, 25, 15, 25, 15, Personnage.TypeAttaque.PHYSIQUE, "Souffle de feu");
    }

    @Override
    public PNJ creerPNJ(Random random) {
        int tirage = random.nextInt(101);
        if (tirage < 30) { // 30%
            return new PNJ("Chevalier errant", 90, 15, 8, 12, 5, Personnage.TypeAttaque.PHYSIQUE, "Coup d'epee");
        } else if (tirage < 55) { // +25% = 55
            return new PNJ("Sorciere", 30, 6, 10, 10, 20, Personnage.TypeAttaque.MAGIQUE, "Trait de feu");
        } else if (tirage < 75) { // +20% = 75
            return new PNJ("Vautour", 40, 10, 18, 8, 5, Personnage.TypeAttaque.PHYSIQUE, "Serres acerees");
        } else if (tirage < 90) { // +15% = 90
            return new PNJ("Rat enrage", 36, 8, 12, 6, 3, Personnage.TypeAttaque.PHYSIQUE, "Morsure");
        } else { // 10%
            return new PNJ("Gobelin", 56, 12, 12, 8, 6, Personnage.TypeAttaque.PHYSIQUE, "Coup de massue");
        }
    }

    @Override
    public Item creerConsommable(Random random) {
        int choix = random.nextInt(3);
        switch (choix) {
            case 0:
                return new Consumable("Potion de soin", "Rend 30 PV", Consumable.Effet.SOIN, 30, 0);
            case 1:
                return new Consumable("Potion de force", "+5 force / 3 tours", Consumable.Effet.FORCE, 5, 3);
            default:
                return new Consumable("Potion de resistance", "-20% degats / 2 tours", Consumable.Effet.RESISTANCE, 20, 2);
        }
    }

    @Override
    public Item creerEquipement(Random random) {
        int choix = random.nextInt(7);
        switch (choix) {
            case 0:
                return new Equipement("Hache rouillee", "+10 force , +5 constitution",
                        Equipement.TypeEquipement.Arme, 0, 10, 0, 5, 0);
            case 1:
                return new Equipement("Casque de cuir", "+5 constitution",
                        Equipement.TypeEquipement.Casque, 0, 0, 0, 5, 0);
            case 2:
                return new Equipement("Arc en vif doré", "+25 force , +5 dexterité",
                        Equipement.TypeEquipement.Arme, 0, 25, 5, 0, 0);
            case 3:
                return new Equipement("Plastron de maille", ", +15 constitution , -3 dexterite",
                        Equipement.TypeEquipement.Plastron, 0, 0, -3, 15, 0);
            case 4:
                return new Equipement("Jambière de paladin", "+10 constitution , +10 dexterite",
                        Equipement.TypeEquipement.Jambiere, 0, 10, 10, 10, 0);
            case 5:
                return new Equipement("Grimoire ancien", "+20 intelligence",
                        Equipement.TypeEquipement.Arme, 0, 0, 0, 0, 20);
            default:
                return new Equipement("Bottes renforcees", "+5 dexterite",
                        Equipement.TypeEquipement.Bottes, 0, 0, 5, 0, 0);
        }
    }
    @Override
    public Item creerObjetLegendaire() {
        return new ObjetLegendaire(
                        "Epee du Serment Brise",
                        "Une lame ancienne chargee d'une volonte etrange... Elle t'ouvre une nouvelle voie.",
                        null
                );
            }
}
