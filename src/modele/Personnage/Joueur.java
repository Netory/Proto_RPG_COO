package modele.Personnage;

import modele.Items.Consumable;
import modele.Items.Equipement;
import modele.Items.Item;
import modele.Personnage.Passifs.*;
import modele.Personnage.Strategies.AttackStrategy;
import modele.Personnage.Strategies.MagicAttack;
import modele.Personnage.Strategies.MeleeAttack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Joueur extends Personnage {

    private ClasseHeros classePrimera;
    private ClasseHeros classeSegunda;

    private final List<Item> inventaire = new ArrayList<>();
    private final SujetPassifs sujetPassifs = new SujetPassifs();
    private final Set<Class<?>> passifsDejaAjoutes = new HashSet<>();

    private final List<AttackStrategy> strategies = new ArrayList<>();
    private AttackStrategy strategieCourante;

    private int bonusForceTours;
    private int bonusForceValeur;

    private int bonusResistanceTours;
    private int bonusResistancePourcent;

    private int bonusConstitutionTours;
    private int bonusConstitutionValeur;

    public Joueur(String nom,
                  int pv,
                  int force,
                  int dexterite,
                  int constitution,
                  int intelligence,
                  TypeAttaque typeAttaque) {
        super(nom, pv, force, dexterite, constitution, intelligence, typeAttaque);
    }

    public Evenement declencher(TypeEvenement type, Personnage source, Personnage cible, int valeur, Item item) {
        Evenement e = new Evenement(type, this, source, cible, valeur, item);
        sujetPassifs.notifierObservateurs(e);
        return e;
    }

    //STRATEGIES
    public List<AttackStrategy> getStrategies() {
        return Collections.unmodifiableList(strategies);
    }

    public AttackStrategy getStrategieCourante() {
        return strategieCourante;
    }

    public void setStrategieCourante(AttackStrategy strategieCourante) {
        if (strategieCourante == null) return;
        ajouterStrategieUnique(strategieCourante);
        AttackStrategy trouvee = trouverStrategieParClasse(strategieCourante.getClass());
        if (trouvee == null) return;
        this.strategieCourante = trouvee;
        // met a jour le type d'attaque du joueur pour le reste des calculs
        setTypeAttaque(trouvee.getTypeAttaque(this));
    }

    private void ajouterStrategieUnique(AttackStrategy strat) {
        if (strat == null) return;
        for (AttackStrategy s : strategies) {
            if (s.getClass() == strat.getClass()) {
                return;
            }
        }
        strategies.add(strat);
        if (strategieCourante == null) {
            setStrategieCourante(strat);
        }
    }

    private AttackStrategy trouverStrategieParClasse(Class<? extends AttackStrategy> clazz) {
        if (clazz == null) return null;
        for (AttackStrategy s : strategies) {
            if (s.getClass() == clazz) {
                return s;
            }
        }
        return null;
    }

    private void initialiserStrategiePourClasse(ClasseHeros classe) {
        if (classe == null) return;
        switch (classe) {
            case BARBARE:
            case ARCHER:
            case ASSASSIN:
                ajouterStrategieUnique(new MeleeAttack());
                break;
            case SORCIER:
                ajouterStrategieUnique(new MagicAttack());
                break;
            default:
                break;
        }
    }

    // COMBAT 
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

    //CLASSES
    public void setClassePrimera(ClasseHeros classe) {
        this.classePrimera = classe;
        initialiserPassifsPourClasse(classe);
        initialiserStrategiePourClasse(classe);
    }

    public ClasseHeros getClassePrimera() {
        return classePrimera;
    }

    public void setClasseSegunda(ClasseHeros classe) {
        this.classeSegunda = classe;
        initialiserPassifsPourClasse(classe);
        initialiserStrategiePourClasse(classe);
    }

    public ClasseHeros getClasseSegunda() {
        return classeSegunda;
    }

    public boolean possedeClasse(ClasseHeros c) {
        if (c == null) return false;
        return c == classePrimera || c == classeSegunda;
    }

    //BUFFS
    public void decrementerBuffs() {
        if (bonusForceTours > 0) bonusForceTours--;
        if (bonusResistanceTours > 0) bonusResistanceTours--;
        if (bonusConstitutionTours > 0) bonusConstitutionTours--;
    }

    public void appliquerSoin(int valeur) {
        soigner(valeur);
    }

    public void appliquerConstitution(int valeur, int tours) {
        bonusConstitutionValeur = valeur;
        bonusConstitutionTours = tours;
    }

    public void appliquerForce(int valeur, int tours) {
        bonusForceValeur = valeur;
        bonusForceTours = tours;
    }

    public void appliquerResistance(int pourcent, int tours) {
        bonusResistancePourcent = pourcent;
        bonusResistanceTours = tours;
    }

    public void retirerBonusCombat() {
        bonusForceValeur = 0;
        bonusForceTours = 0;
        bonusConstitutionValeur = 0;
        bonusConstitutionTours = 0;
    }

    //INVENTAIRE
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

    //PASSIFS
    private void ajouterPassifUnique(Observateur passif) {
        if (passif == null) return;
        if (passifsDejaAjoutes.add(passif.getClass())) {
            sujetPassifs.attacher(passif);
        }
    }

    public void initialiserPassifsPourClasse(ClasseHeros classe) {
        if (classe == null) return;

        // all classes
        ajouterPassifUnique(new Regeneration());

        switch (classe) {
            case BARBARE:
                ajouterPassifUnique(new PeauDure());
                ajouterPassifUnique(new ContreAttaque());
                ajouterPassifUnique(new CriDeGuerre());
                break;
            case SORCIER:
                ajouterPassifUnique(new BouclierMagique());
                break;
            case ARCHER:
                ajouterPassifUnique(new PeauDure());
                ajouterPassifUnique(new Adrenaline());
                break;
            case ASSASSIN:
                ajouterPassifUnique(new ContreAttaque());
                ajouterPassifUnique(new Adrenaline());
                break;
            default:
                break;
        }
    }
}
