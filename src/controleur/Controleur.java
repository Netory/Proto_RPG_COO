package controleur;

import modele.Donjon.Donjon;
import modele.Donjon.Salle;
import modele.Items.Item;
import modele.Items.Equipement;
import modele.Items.Consumable;
import modele.Personnage.Joueur;
import modele.Personnage.PNJ;
import modele.Personnage.Personnage;
import modele.Personnage.Personnages.Archer;
import modele.Personnage.Personnages.Assassin;
import modele.Personnage.Personnages.Barbare;
import modele.Personnage.Personnages.Sorcier;
import vue.Ihm;

import java.util.List;
import java.util.Locale;

public class Controleur {

    private final Ihm ihm;
    private Joueur joueur;
    private Donjon donjon;

    public Controleur(Ihm ihm) {
        this.ihm = ihm;
    }

    public void jouer() {
        ihm.afficherMessage("=== Prototype RPG ===");
        initialiserPartie();
        boucleJeu();
    }

    private void initialiserPartie() {
        Donjon.Theme themeChoisi = demanderTheme();
        String nom = ihm.demanderCaracteres("Choisissez le nom de votre heros :");
        String classe = ihm.demanderCaracteres("Classe (Barbare / Sorcier / Archer / Assassin) :");
        classe = classe.trim().toUpperCase(Locale.ROOT);
        while (true) {
        String c = classe;
            if ("BARBARE".equals(c)) {
                joueur= new Barbare(nom);
                break;
            } else if ("SORCIER".equals(c)) {
                joueur= new Sorcier(nom);
                break;
            } else if ("ARCHER".equals(c)) {
                joueur= new Archer(nom);
                break;
            } else if ("ASSASSIN".equals(c)) {
                joueur= new Assassin(nom);
                break;
            }
            else{
                ihm.afficherMessage("Classe inconnue.");
                classe = ihm.demanderCaracteres("Classe (Barbare / Sorcier / Archer / Assassin) :");
                classe = classe.trim().toUpperCase(Locale.ROOT);
                 //pour que la boucle continue et que l'on conserve la variable classe
            }
        }
        //pour l'affichage
        classe=classe.trim().toLowerCase(Locale.ROOT);
        classe = classe.substring(0, 1).toUpperCase() + classe.substring(1);


        donjon = new Donjon(themeChoisi);
        donnerObjetsDepart(themeChoisi);
        ihm.afficherMessage("Bienvenue chère "+ classe + "! Vous êtes " + joueur);
    }

    private void boucleJeu() {
        boolean enCours = true;
        while (enCours && joueur.estVivant()) {
            ihm.afficherMessage("Que voulez-vous faire ?\n"
                    + "1. Afficher l'inventaire\n"
                    + "2. Entrer dans le donjon");
            int choix = ihm.demanderEntier("Votre choix :");
            switch (choix) {
                case 1:
                    ihm.afficherMessage(joueur.afficherInventaire());
                    break;
                case 2:
                    explorerDonjon();
                    enCours = false; // Boucle de jeu 
                    break;
                default:
                    ihm.afficherMessage("Choix invalide.");
                    break;
            }
            if (!joueur.estVivant()) {
                ihm.afficherMessage("Vous etes mort. Partie terminee.");
            }
        }
    }

    private void explorerDonjon() {
        for (Salle salle : donjon.getSalles()) {
            if (!joueur.estVivant()) break;
            ihm.afficherMessage("\nSalle " + salle.getNumero());
            gererSalle(salle);
        }
        if (joueur.estVivant()) {
            ihm.afficherMessage("Felicitations, vous avez termine le donjon !");
        }
    }

    private void gererSalle(Salle salle) {
        ihm.afficherMessage("Ennemis et objets detectés.");
        while (joueur.estVivant() && (resteEnnemis(salle) )){  //|| !salle.getLoot().isEmpty())) {
            afficherEnnemis(salle.getEnnemis());
            afficherLoot(salle.getLoot());
            afficherStatsJoueur();
            ihm.afficherMessage("Actions :\n"
                    + "1. Attaquer un ennemi\n"
                    + "2. Ramasser un objet\n"
                    + "3. Consulter l'inventaire");
            int choix = ihm.demanderEntier("Choix :");
            if (choix == 1) {
                if (!resteEnnemis(salle)) {
                    ihm.afficherMessage("Aucun ennemi a attaquer.");
                    continue;
                }
                int idx = ihm.demanderEntier("Indice de l'ennemi a attaquer :");
                if (idx < 0 || idx >= salle.getEnnemis().size()) {
                    ihm.afficherMessage("Indice invalide.");
                    continue;
                }
                PNJ cible = salle.getEnnemis().get(idx);
                if (!cible.estVivant()) {
                    ihm.afficherMessage("Cet ennemi est deja vaincu.");
                    continue;
                }
                int degats = calculDegats(joueur, cible);
                cible.recevoirDegats(degats);
                ihm.afficherMessage("Vous attaquez (" + joueur.calculAttaque() + " bruts) et infligez " + degats + " a " + cible.getNom() + ". PV restants: " + cible.getPv());
                riposte(salle.getEnnemis());
            } else if (choix == 2) {
                if (salle.getLoot().isEmpty()) {
                    ihm.afficherMessage("Aucun objet a ramasser.");
                    continue;
                }
                int idobj = ihm.demanderEntier("Indice de l'objet a ramasser :");
                if (idobj < 0 || idobj >= salle.getLoot().size()) {
                    ihm.afficherMessage("Indice invalide.");
                    continue;
                }
                Item pris = salle.getLoot().remove(idobj);
                joueur.ajouterObjet(pris);
                ihm.afficherMessage("Vous ramassez : " + pris.descriptionCourte());
            } else if (choix == 3) {
                if (joueur.getInventaire().isEmpty()) {
                    ihm.afficherMessage("Inventaire vide, rien a utiliser.");
                } else {
                    ihm.afficherMessage(joueur.afficherInventaire());
                    int idxUse = ihm.demanderEntier("Indice de l'objet a utiliser (-1 pour annuler) :");
                    if (idxUse >= 0) {
                        if (joueur.utiliserObjet(idxUse)) {
                            afficherStatsJoueur();
                            ihm.afficherMessage("Objet utilise.");
                        } else {
                            ihm.afficherMessage("Impossible d'utiliser cet objet.");
                        }
                    }
                }
            } else {
                ihm.afficherMessage("Choix invalide.");
            }
            joueur.decrementerBuffs();
            salle.getEnnemis().removeIf(p -> !p.estVivant());
        }
        if (joueur.estVivant()) {
            ihm.afficherMessage("Salle terminee.");
        }
    }

    private void riposte(List<PNJ> ennemis) {
        for (PNJ ennemi : ennemis) {
            if (!ennemi.estVivant()) continue;
            int degats = calculDegats(ennemi, joueur);
            joueur.recevoirDegats(Math.max(1,degats));
            ihm.afficherMessage(ennemi.getNom() + " attaque (" + ennemi.calculAttaque() + " bruts) et vous inflige " + (Math.max(1,degats-joueur.calculReductionDefense())) + " degats. PV: " + joueur.getPv());
            if (!joueur.estVivant()) break;
        }
    }

    private int calculDegats(Personnage attaquant, Personnage cible) {
        int base = attaquant.calculAttaque();
        int reduction = (attaquant.getTypeAttaque() == Personnage.TypeAttaque.MAGIQUE)
                ? cible.getConstitution() / 3
                : cible.getConstitution() / 2;
        return Math.max(1, base - reduction);
    }

    private void afficherEnnemis(List<PNJ> ennemis) {
        for (int i = 0; i < ennemis.size(); i++) {
            PNJ p = ennemis.get(i);
            String status = p.estVivant() ? (p.getPv() + " PV") : "KO";
            ihm.afficherMessage("[" + i + "] " + p.getNom() + " - " + status);
        }
    }

    private boolean resteEnnemis(Salle salle) {
        return salle.getEnnemis().stream().anyMatch(PNJ::estVivant);
    }

    private void afficherLoot(List<Item> loot) {
        if (loot.isEmpty()) {
            ihm.afficherMessage("Objets au sol : aucun");
            return;
        }
        StringBuilder sb = new StringBuilder("Objets au sol :\n");
        for (int i = 0; i < loot.size(); i++) {
            sb.append("[").append(i).append("] ").append(loot.get(i).descriptionCourte()).append("\n");
        }
        ihm.afficherMessage(sb.toString());
    }

    private void afficherStatsJoueur() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vos stats : ");
        sb.append("PV ").append(joueur.getPv()).append("/").append(joueur.getPvMax());
        sb.append(" | FOR ").append(joueur.getForce()).append(" (+").append(joueur.getBonusForceTotal()).append(" items)");
        sb.append(" | DEX ").append(joueur.getDexterite()).append(" (+").append(joueur.getBonusDexteriteTotal()).append(" items)");
        sb.append(" | CON ").append(joueur.getConstitution()).append(" (+").append(joueur.getBonusForceTotal()).append(" items)");
        sb.append(" | INT ").append(joueur.getIntelligence()).append(" (+").append(joueur.getBonusIntelligenceTotal()).append(" items)");
        ihm.afficherMessage(sb.toString());
    }

    private void donnerObjetsDepart(Donjon.Theme theme) {
        // Consommable commun diff selon le  thème et eqpuipement de start
        Equipement arme;
        // Affiche la valeur brute de l'Enum (pas le nom du joueur)
        //System.out.println("DEBUG VRAIE VALEUR ENUM : " + joueur.getClassePrimera());
        //System.out.println("DEBUG CASE ATTENDU    : " + ClasseHeros.BARBARE); 



        switch (theme) {
            case MEDIEVAL:
                joueur.ajouterObjet(new Consumable("Pomme", "+10 PV", Consumable.Effet.SOIN, 10, 0));
                switch (joueur.getClassePrimera()) {
                    case BARBARE:
                        arme = new Equipement("Hache de bois", "+6 force", Equipement.TypeEquipement.Arme, 0, 6, 0, 0, 0);
                        break;
                    case SORCIER:
                        arme = new Equipement("Baton d'apprenti", "+6 intelligence", Equipement.TypeEquipement.Arme, 0, 0, 0, 0, 6);
                        break;
                    case ARCHER:
                        arme = new Equipement("Arc court", "+6 dexterite", Equipement.TypeEquipement.Arme, 0, 0, 6, 0, 0);
                        break;
                    case ASSASSIN:
                        arme = new Equipement("Dague affutee", "+4 force, +2 dexterite", Equipement.TypeEquipement.Arme, 0, 4, 2, 0, 0);
                        break;
                    default:
                        arme = new Equipement("Poings nus", "+0 force", Equipement.TypeEquipement.Arme, 0, 0, 0, 0, 0);
                        break;
                }
                break;

            case FUTURISTE:
                joueur.ajouterObjet(new Consumable("Injection med", "+10 PV", Consumable.Effet.SOIN, 10, 0));
                switch (joueur.getClassePrimera()) {
                    case BARBARE:
                        arme = new Equipement("Marteau energetique", "+6 force", Equipement.TypeEquipement.Arme, 0, 6, 0, 0, 0);
                        break;
                    case SORCIER:
                        arme = new Equipement("Gant arcane", "+6 intelligence", Equipement.TypeEquipement.Arme, 0, 0, 0, 0, 6);
                        break;
                    case ARCHER:
                        arme = new Equipement("Arc plasma", "+6 dexterite", Equipement.TypeEquipement.Arme, 0, 0, 6, 0, 0);
                        break;
                    case ASSASSIN:
                        arme = new Equipement("Lame monomoleculaire", "+4 force, +2 dexterite", Equipement.TypeEquipement.Arme, 0, 4, 2, 0, 0);
                        break;
                    default:
                        //System.out.println("il passe par là pas normal !!!!!!! c'est futuriste");
                        arme = new Equipement("Poings nus", "+0 force", Equipement.TypeEquipement.Arme, 0, 0, 0, 0, 0);
                        break;
                }
                break;

            case HORREUR_FANTASTIQUE:
                joueur.ajouterObjet(new Consumable("Potion suspecte", "+10 PV", Consumable.Effet.SOIN, 10, 0));
                switch (joueur.getClassePrimera()) {    
                    case BARBARE:
                        //System.out.println("il passe par là bien !!!!! c'est le barbarre");
                        arme = new Equipement("Machete sanglante", "+6 force", Equipement.TypeEquipement.Arme, 0, 6, 0, 0, 0);
                        break;
                    case SORCIER:
                        arme = new Equipement("Grimoire tanné", "+6 intelligence", Equipement.TypeEquipement.Arme, 0, 0, 0, 0, 6);
                        break;
                    case ARCHER:
                        arme = new Equipement("Shotgun rouillé", "+6 dexterite", Equipement.TypeEquipement.Arme, 0, 0, 6, 0, 0);
                        break;
                    case ASSASSIN:
                        arme = new Equipement("Scalpel rouillé", "+4 force, +2 dexterite", Equipement.TypeEquipement.Arme, 0, 4, 2, 0, 0);
                        break;
                    default:
                        //System.out.println("il passe par là pas normal !!!!!!! c'est horreur fantastique default");
                        arme = new Equipement("Poings nus", "+0 force", Equipement.TypeEquipement.Arme, 0, 0, 0, 0, 0);
                        break;
                }
                break;

            default:
               // System.out.println("il passe par là pas normal !!!!!!! c'est le default theme");
                arme = new Equipement("Poings nus", "+0 force", Equipement.TypeEquipement.Arme, 0, 0, 0, 0, 0);//il passe pas par là
        }
        joueur.ajouterObjet(arme);
    }

    private Donjon.Theme demanderTheme() {
        while (true) {
            ihm.afficherMessage("\nChoisissez un thème de donjon :");
            ihm.afficherMessage("1 - MEDIEVAL");
            ihm.afficherMessage("2 - FUTURISTE");
            ihm.afficherMessage("3 - HORREUR_FANTASTIQUE");
            int choix = ihm.demanderEntier("Votre choix :");
            switch (choix){
                case 1:
                    return Donjon.Theme.MEDIEVAL;
                case 2:
                    return Donjon.Theme.FUTURISTE;
                case 3:
                    return Donjon.Theme.HORREUR_FANTASTIQUE;
                default:
                    ihm.afficherMessage("Reponse invalide. Reessayez.");
                    break;
            }
        }
    }
}
