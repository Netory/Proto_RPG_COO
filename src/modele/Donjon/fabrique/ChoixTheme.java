package modele.Donjon.fabrique;

import modele.Donjon.Donjon;


public final class ChoixTheme {

    private ChoixTheme() { }

    public static FabriqueTheme of(Donjon.Theme theme) {
        if (theme == Donjon.Theme.MEDIEVAL) {
            return new FabriqueMedievale();
        }
        if (theme == Donjon.Theme.FUTURISTE) {
            return new FabriqueFuturiste();
        }
        return new FabriqueHorreurFantastique();
    }
}
