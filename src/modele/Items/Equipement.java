package modele.Items;

public class Equipement {

    private Integer PV;
    private Integer Force;
    private Integer Dexterite;
    private Integer Constitution;
    private Integer Intelligence;
    private TypeEquipement typeEquipement;

    public enum TypeEquipement{
        Arme,
        Casque,
        Plastron,
        Jambiere,
        Bottes
    }

    public Integer getPV() {
        return PV;
    }

    public Integer getForce() {
        return Force;
    }

    public Integer getDexterite() {
        return Dexterite;
    }

    public Integer getConstitution() {
        return Constitution;
    }

    public Integer getIntelligence() {
        return Intelligence;
    }

    public TypeEquipement getTypeEquipement() {
        return typeEquipement;
    }

    public void setPV(Integer PV) {
        this.PV = PV;
    }

    public void setForce(Integer force) {
        Force = force;
    }

    public void setDexterite(Integer dexterite) {
        Dexterite = dexterite;
    }

    public void setConstitution(Integer constitution) {
        Constitution = constitution;
    }

    public void setIntelligence(Integer intelligence) {
        Intelligence = intelligence;
    }

    public void setTypeEquipement(TypeEquipement typeEquipement) {
        this.typeEquipement = typeEquipement;
    }
}
