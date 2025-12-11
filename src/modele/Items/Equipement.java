package modele.Items;

public class Equipement extends Item {

    private final int pv;
    private final int force;
    private final int dexterite;
    private final int constitution;
    private final int intelligence;
    private final TypeEquipement typeEquipement;

    public enum TypeEquipement {
        Arme,
        Casque,
        Plastron,
        Jambiere,
        Bottes
    }

    public Equipement(String nom,
                      String description,
                      TypeEquipement typeEquipement,
                      int pv,
                      int force,
                      int dexterite,
                      int constitution,
                      int intelligence) {
        super(nom, description);
        this.typeEquipement = typeEquipement;
        this.pv = pv;
        this.force = force;
        this.dexterite = dexterite;
        this.constitution = constitution;
        this.intelligence = intelligence;
    }

    public int getPV() {
        return pv;
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

    public TypeEquipement getTypeEquipement() {
        return typeEquipement;
    }
}
