package modele.Personnage;

public class PNJ extends Personnage {

    private final String attaqueSpeciale;

    public PNJ(String nom,
               int pv,
               int force,
               int dexterite,
               int constitution,
               int intelligence,
               TypeAttaque typeAttaque,
               String attaqueSpeciale) {
        super(nom, pv, force, dexterite, constitution, intelligence, typeAttaque);
        this.attaqueSpeciale = attaqueSpeciale;
    }

    public String getAttaqueSpeciale() {
        return attaqueSpeciale;
    }
}
