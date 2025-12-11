package modele.Personnage;

import modele.Items.Equipement;

public abstract class Personnage{

    private Integer PV;
    private Integer Force;
    private Integer Dexterite;
    private Integer Constitution;
    private Integer Intelligence;
    private String Type_attaque;

    private Equipement Arme;
    private Equipement Casque;
    private Equipement Plastron;
    private Equipement Jambiere;
    private Equipement Bottes;


    public  Integer attaque(){
        if (getType_attaque().equalsIgnoreCase("physique")){
            return getForce()+Arme.getForce()+ Casque.getForce()+ Plastron.getForce()+ Jambiere.getForce()+ Bottes.getForce();
        }
        else {
            return getIntelligence()+ Arme.getIntelligence()+ Casque.getIntelligence()+ Plastron.getIntelligence()+ Jambiere.getIntelligence()+ Bottes.getIntelligence();
        }

    }


    public Integer getIntelligence() {
        return Intelligence;
    }

    public Integer getConstitution() {
        return Constitution;
    }

    public Integer getDexterite() {
        return Dexterite;
    }

    public Integer getForce() {
        return Force;
    }

    public Integer getPV() {
        return PV;
    }

    public String getType_attaque(){
        return Type_attaque;
    }

    public Equipement getArme() {
        return Arme;
    }

    public Equipement getCasque() {
        return Casque;
    }

    public Equipement getPlastron() {
        return Plastron;
    }

    public Equipement getJambiere() {
        return Jambiere;
    }

    public Equipement getBottes() {
        return Bottes;
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

    public void setType_attaque(String type_attaque) {
        Type_attaque = type_attaque;
    }

    public void setArme(Equipement arme) {
        Arme = arme;
    }

    public void setCasque(Equipement casque) {
        Casque = casque;
    }

    public void setPlastron(Equipement Plastron) {
        this.Plastron = Plastron;
    }

    public void setJambiere(Equipement jambiere) {
        Jambiere = jambiere;
    }

    public void setBottes(Equipement bottes) {
        Bottes = bottes;
    }
}
