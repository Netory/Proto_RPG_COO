package modele.Items;

import modele.Personnage.ClasseHeros;

public class ObjetLegendaire extends Item {

    private final ClasseHeros classeOctroyee;

    public ObjetLegendaire(String nom, String description, ClasseHeros classeOctroyee) {
        super(nom, description);
        this.classeOctroyee = classeOctroyee;
    }

    public ClasseHeros getClasseOctroyee() {
        return classeOctroyee;
    }
}
