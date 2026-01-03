package modele.Personnage.Passifs;

public interface Sujet {
    void attacher(Observateur o);
    void detacher(Observateur o);
    void notifierObservateurs(Evenement evenement);
}
