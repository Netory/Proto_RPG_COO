package modele.Donjon.fabrique;

import modele.Items.Consumable;
import modele.Items.Equipement;
import modele.Items.Item;
import modele.Personnage.PNJ;
import modele.Personnage.Personnage;

import java.util.Random;

public class FabriqueHorreurFantastique implements FabriqueTheme {

    @Override
    public PNJ creerBoss() {
        return new PNJ("Ancien abominable", 260, 24, 12, 24, 22, Personnage.TypeAttaque.MAGIQUE, "Hurlement cosmique");
    }

    @Override
    public PNJ creerPNJ(Random random) {
        int tirage = random.nextInt(101);
        if (tirage < 30) {
            return new PNJ("Cultiste", 70, 10, 10, 10, 14, Personnage.TypeAttaque.MAGIQUE, "Rituel noir");
        } else if (tirage < 55) {
            return new PNJ("Spectre", 55, 8, 16, 8, 16, Personnage.TypeAttaque.MAGIQUE, "Toucher glacial");
        } else if (tirage < 80) {
            return new PNJ("Loup-garou", 95, 16, 14, 14, 6, Personnage.TypeAttaque.PHYSIQUE, "Dechirure");
        } else if (tirage < 92) {
            return new PNJ("Goule", 60, 12, 10, 12, 6, Personnage.TypeAttaque.PHYSIQUE, "Morsure putride");
        } else {
            return new PNJ("Vampire", 80, 14, 16, 10, 12, Personnage.TypeAttaque.PHYSIQUE, "Drain de sang");
        }
    }

    @Override
    public Item creerConsommable(Random random) {
        int choix = random.nextInt(3);
        switch (choix) {
            case 0:
                return new Consumable("Sang ancien", "Rend 30 PV", Consumable.Effet.SOIN, 30, 0);
            case 1:
                return new Consumable("Essence de terreur", "+6 force / 3 tours", Consumable.Effet.FORCE, 6, 3);
            default:
                return new Consumable("Peau d'ombre", "-25% degats / 2 tours", Consumable.Effet.RESISTANCE, 25, 2);
        }
    }

    @Override
    public Item creerEquipement(Random random) {
        int choix = random.nextInt(7);
        switch (choix) {
            case 0:
                return new Equipement("Dague maudite", "+18 force , -5 constitution",
                        Equipement.TypeEquipement.Arme, 0, 18, 0, -5, 0);
            case 1:
                return new Equipement("Masque d'os", "+10 constitution",
                        Equipement.TypeEquipement.Casque, 0, 0, 0, 10, 0);
            case 2:
                return new Equipement("Grimoire interdit", "+22 intelligence",
                        Equipement.TypeEquipement.Arme, 0, 0, 0, 0, 22);
            case 3:
                return new Equipement("Manteau de brume", "+12 dexterite , +5 constitution",
                        Equipement.TypeEquipement.Plastron, 0, 0, 12, 5, 0);
            case 4:
                return new Equipement("Jambieres rituelles", "+14 constitution",
                        Equipement.TypeEquipement.Jambiere, 0, 0, 0, 14, 0);
            case 5:
                return new Equipement("Bottes silencieuses", "+10 dexterite",
                        Equipement.TypeEquipement.Bottes, 0, 0, 10, 0, 0);
            default:
                return new Equipement("Talisman profane", "+10 intelligence , +5 constitution",
                        Equipement.TypeEquipement.Casque, 0, 0, 0, 5, 10);
        }
    }
}
