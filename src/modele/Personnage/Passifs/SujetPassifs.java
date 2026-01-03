package modele.Personnage.Passifs;

import java.util.ArrayList;
import java.util.List;

public class SujetPassifs implements Sujet {

    private final List<Observateur> observateurs = new ArrayList<>();

    @Override
    public void attacher(Observateur o) {
        if (o != null && !observateurs.contains(o)) {
            observateurs.add(o);
        }
    }

    @Override
    public void detacher(Observateur o) {
        observateurs.remove(o);
    }

    @Override
    public void notifierObservateurs(Evenement evenement) {
        List<Observateur> copie = new ArrayList<>(observateurs);
        for (Observateur o : copie) {
            o.mettreAJour(evenement);
        }
    }
}
