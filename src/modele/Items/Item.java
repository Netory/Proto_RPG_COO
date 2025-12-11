package modele.Items;

public abstract class Item {
    private final String nom;
    private final String description;

    protected Item(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String descriptionCourte() {
        return nom + " : " + description;
    }
}
