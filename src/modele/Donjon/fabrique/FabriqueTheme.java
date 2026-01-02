package modele.Donjon.fabrique;


import modele.Items.Item;
import modele.Personnage.PNJ;

import java.util.Random;

public interface FabriqueTheme {

    PNJ creerBoss();

    PNJ creerPNJ(Random random);

    Item creerEquipement(Random random);

    Item creerConsommable(Random random);
}
