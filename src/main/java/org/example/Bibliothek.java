package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bibliothek {
    private final String name;
    private final List<Buch> bücher;

    public Bibliothek(String bilbiothekenname) {
        this.name = bilbiothekenname;
        this.bücher = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void buchHinzufügen(Buch buch) {
        bücher.add(buch);
        System.out.println("Buch \"" + buch.getTitel() + "\" wurde zur Bibliothek \"" + name + "\" hinzugefügt.");
    }

    public void buchLöschen(String titel) {
        for (Iterator<Buch> iterator = bücher.iterator(); iterator.hasNext(); ) {
            Buch buch = iterator.next();
            if (buch.getTitel().equals(titel)) {
                iterator.remove();
                System.out.println("Buch \"" + titel + "\" wurde aus der Bibliothek \"" + name + "\" gelöscht.");
                return;
            }
        }
        System.out.println("Buch \"" + titel + "\" wurde nicht gefunden.");
    }

    public void zeigeAlleBücher() {
        if (bücher.isEmpty()) {
            System.out.println("Die Bibliothek \"" + name + "\" enthält keine Bücher.");
        } else {
            System.out.println("Alle Bücher in der Bibliothek \"" + name + "\":");
            for (Buch buch : bücher) {
                System.out.println(buch);
            }
        }
    }

    public Buch findeBuch(String titel) {
        for (Buch buch : bücher) {
            if (buch.getTitel().equals(titel)) {
                return buch;
            }
        }
        System.out.println("Buch \"" + titel + "\" wurde in der Bibliothek \"" + name + "\" nicht gefunden.");
        return null;
    }
}
