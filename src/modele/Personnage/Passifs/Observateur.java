package modele.Personnage.Passifs;

public interface Observateur {
    void mettreAJour(Evenement evenement);
    String getDescription();
}
