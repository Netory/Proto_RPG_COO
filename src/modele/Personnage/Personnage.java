package modele.Personnage;

import modele.Items.Equipement;

/**
 * c'est le Pattern de Méthode Template 
 */
public abstract class Personnage {

    public enum TypeAttaque {
        PHYSIQUE, MAGIQUE
    }

    private String nom;
    private int pvMax;
    private int pv;
    private int force;
    private int dexterite;
    private int constitution;
    private int intelligence;
    private TypeAttaque typeAttaque = TypeAttaque.PHYSIQUE;

    private Equipement arme;
    private Equipement casque;
    private Equipement plastron;
    private Equipement jambiere;
    private Equipement bottes;

    public Personnage(String nom,
                      int pv,
                      int force,
                      int dexterite,
                      int constitution,
                      int intelligence,
                      TypeAttaque typeAttaque) {
        this.nom = nom;
        this.pvMax = pv;
        this.pv = pv;
        this.force = force;
        this.dexterite = dexterite;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.typeAttaque = typeAttaque;
    }

    public int calculAttaque() {
        if (typeAttaque == TypeAttaque.MAGIQUE) {
            return intelligence + bonusEquipIntelligence();
        }
        return force + bonusEquipForce();
    }

    public int calculReductionDefense() {
        return Math.max(0, (dexterite + constitution) / 4);
    }

    public void equiper(Equipement equipement) {
        if (equipement == null) return;
        switch (equipement.getTypeEquipement()) {
            case Arme:
                this.arme = equipement;
                if (equipement.getIntelligence() > 0) {
                    setTypeAttaque(TypeAttaque.MAGIQUE);
                } else {
                    setTypeAttaque(TypeAttaque.PHYSIQUE);
                }
                break;

            case Casque:
                this.casque = equipement;
                break;
            case Plastron:
                this.plastron = equipement;
                break;
            case Jambiere:
                this.jambiere = equipement;
                break;
            case Bottes:
                this.bottes = equipement;
                break;
            default:
                break;
        }
    }

    public void recevoirDegats(int degatsBruts) {
        int reduit = Math.max(1, degatsBruts);
        this.pv = Math.max(0, this.pv - reduit);
    }

    public boolean estVivant() {
        return this.pv > 0;
    }

    private int bonusEquipForce() {
        int bonus = 0;
        if (arme != null) bonus += arme.getForce();
        if (casque != null) bonus += casque.getForce();
        if (plastron != null) bonus += plastron.getForce();
        if (jambiere != null) bonus += jambiere.getForce();
        if (bottes != null) bonus += bottes.getForce();
        return bonus;
    }

    private int bonusEquipIntelligence() {
        int bonus = 0;
        if (arme != null) bonus += arme.getIntelligence();
        if (casque != null) bonus += casque.getIntelligence();
        if (plastron != null) bonus += plastron.getIntelligence();
        if (jambiere != null) bonus += jambiere.getIntelligence();
        if (bottes != null) bonus += bottes.getIntelligence();
        return bonus;
    }
private int bonusEquipDexterite() {
        int bonus = 0;
        if (arme != null) bonus += arme.getDexterite();
        if (casque != null) bonus += casque.getDexterite();
        if (plastron != null) bonus += plastron.getDexterite();
        if (jambiere != null) bonus += jambiere.getDexterite();
        if (bottes != null) bonus += bottes.getDexterite();
        return bonus;
    }
private int bonusEquipConstitution() {
        int bonus = 0;
        if (arme != null) bonus += arme.getConstitution();
        if (casque != null) bonus += casque.getConstitution();
        if (plastron != null) bonus += plastron.getConstitution();
        if (jambiere != null) bonus += jambiere.getConstitution();
        if (bottes != null) bonus += bottes.getConstitution();
        return bonus;
    }


    public String getNom() {
        return nom;
    }

    public int getPv() {
        return pv;
    }

    public int getPvMax() {
        return pvMax;
    }

    public int getForce() {
        return force;
    }

    public int getDexterite() {
        return dexterite;
    }

    public int getConstitution() {
        return constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public TypeAttaque getTypeAttaque() {
        return typeAttaque;
    }

    public Equipement getArme() {
        return arme;
    }

    public Equipement getCasque() {
        return casque;
    }

    public Equipement getPlastron() {
        return plastron;
    }

    public Equipement getJambiere() {
        return jambiere;
    }

    public Equipement getBottes() {
        return bottes;
    }

    public int getBonusForceTotal() {
        return bonusEquipForce();
    }

    public int getBonusIntelligenceTotal() {
        return bonusEquipIntelligence();
    }
    public int getBonusDexteriteTotal() {
        return bonusEquipDexterite();
    }
    public int getBonusConstitutionTotal() {
        return bonusEquipConstitution();
    }


    public void soigner(int quantite) {
        pv = Math.min(pvMax, pv + Math.max(0, quantite));
    }

    public void setTypeAttaque(TypeAttaque typeAttaque) {
        if (typeAttaque != null) {
            this.typeAttaque = typeAttaque;
        }
    }


    @Override
    public String toString() {
        return nom + " [PV " + pv + "/" + pvMax + ", FOR " + force + ", DEX " + dexterite
                + ", CON " + constitution + ", INT " + intelligence + ", " + typeAttaque + "]";
    }
}
