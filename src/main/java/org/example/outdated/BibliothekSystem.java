package org.example.outdated;

import java.util.ArrayList;
import java.util.List;

public class BibliothekSystem {
    private List<Bibliothek> bibliotheken;

    public BibliothekSystem() {
        bibliotheken = new ArrayList<>();
    }

    public void bibliothekHinzufügen(Bibliothek bibliothek) {
        bibliotheken.add(bibliothek);
        System.out.println("Bibliothek \"" + bibliothek.getName() + "\" wurde dem System hinzugefügt.");
    }

    public Bibliothek findeBibliothek(String name) {
        for (Bibliothek bibliothek : bibliotheken) {
            if (bibliothek.getName().equals(name)) {
                return bibliothek;
            }
        }
        System.out.println("Bibliothek \"" + name + "\" wurde nicht gefunden.");
        return null;
    }

    public void zeigeAlleBibliotheken() {
        if (bibliotheken.isEmpty()) {
            System.out.println("Es gibt keine Bibliotheken im System.");
        } else {
            System.out.println("Bibliotheken im System:");
            for (Bibliothek bibliothek : bibliotheken) {
                System.out.println("- " + bibliothek.getName());
            }
        }
    }
}
