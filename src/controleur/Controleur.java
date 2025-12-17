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
        while (true) {
        String c = classe.trim().toLowerCase(Locale.ROOT);
            if ("barbare".equals(c)) {
                joueur= new Barbare(nom);
                break;
            } else if ("sorcier".equals(c)) {
                joueur= new Sorcier(nom);
                break;
            } else if ("archer".equals(c)) {
                joueur= new Archer(nom);
                break;
            } else if ("assassin".equals(c)) {
                joueur= new Assassin(nom);
                break;
            }
            else{
                ihm.afficherMessage("Classe inconnue.");
                 classe = ihm.demanderCaracteres("Classe (Barbare / Sorcier / Archer / Assassin) :");
                 c = classe.trim().toLowerCase(Locale.ROOT);
            }
        }
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
        ihm.afficherMessage("Ennemis et objets detectes.");
        while (joueur.estVivant() && (resteEnnemis(salle) || !salle.getLoot().isEmpty())) {
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
            joueur.recevoirDegats(degats);
            ihm.afficherMessage(ennemi.getNom() + " attaque (" + ennemi.calculAttaque() + " bruts) et vous inflige " + (degats-joueur.calculReductionDefense()) + " degats. PV: " + joueur.getPv());
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
        // Consommable commun diff selon le  thème
        if (theme == Donjon.Theme.MEDIEVAL) {
            joueur.ajouterObjet(new Consumable("Pomme", "+10 PV", Consumable.Effet.SOIN, 10, 0));
        } else {
            joueur.ajouterObjet(new Consumable("Injection med", "+10 PV", Consumable.Effet.SOIN, 10, 0));
        }

        // Arme de start adaptée à la classe
        Equipement arme;
        if (joueur instanceof Barbare) {
            arme = new Equipement(theme == Donjon.Theme.MEDIEVAL ? "Hache de bois" : "Marteau energetique",
                    "+6 force", Equipement.TypeEquipement.Arme, 0, 6, 0, 0, 0);
        } else if (joueur instanceof Sorcier) {
            arme = new Equipement(theme == Donjon.Theme.MEDIEVAL ? "Baton d'apprenti" : "Gant arcane",
                    "+6 intelligence", Equipement.TypeEquipement.Arme, 0, 0, 0, 0, 6);
        } else if (joueur instanceof Archer) {
            arme = new Equipement(theme == Donjon.Theme.MEDIEVAL ? "Arc court" : "Arc plasma",
                    "+6 dexterite", Equipement.TypeEquipement.Arme, 0, 0, 6, 0, 0);
        } else if (joueur instanceof Assassin) {
            arme = new Equipement(theme == Donjon.Theme.MEDIEVAL ? "Dague affutee" : "Lame monomoleculaire",
                    "+4 force, +2 dexterite", Equipement.TypeEquipement.Arme, 0, 4, 2, 0, 0);
        } else {
            arme = new Equipement("Arme simple", "+5 force", Equipement.TypeEquipement.Arme, 0, 5, 0, 0, 0);
        }
        joueur.ajouterObjet(arme);
    }

    private Donjon.Theme demanderTheme() {
        while (true) {
            String theme = ihm.demanderCaracteres("Choisissez un theme (Medieval/Futuriste) :");
            if (theme == null) {
                continue;
            }
            String t = theme.trim().toLowerCase(Locale.ROOT);
            if (t.startsWith("m")) {
                return Donjon.Theme.MEDIEVAL;
            }
            if (t.startsWith("f")) {
                return Donjon.Theme.FUTURISTE;
            }
            ihm.afficherMessage("Reponse invalide.");
        }
    }
}
