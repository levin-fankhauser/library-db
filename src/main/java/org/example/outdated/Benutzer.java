package org.example.outdated;

import java.util.ArrayList;
import java.util.List;

public class Benutzer {
    private String name;
    private List<Buch> ausgelieheneBücher;

    public Benutzer(String name) {
        this.name = name;
        this.ausgelieheneBücher = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void buchAusleihen(Buch buch) {
        if (!buch.istAusgeliehen()) {
            buch.ausleihen();
            ausgelieheneBücher.add(buch);
        } else {
            System.out.println("Das Buch \"" + buch.getTitel() + "\" ist derzeit nicht verfügbar.");
        }
    }

    public void buchZurückgeben(Buch buch) {
        if (ausgelieheneBücher.contains(buch)) {
            buch.zurückgeben();
            ausgelieheneBücher.remove(buch);
        } else {
            System.out.println(name + " hat das Buch \"" + buch.getTitel() + "\" nicht ausgeliehen.");
        }
    }

    public void zeigeAusgelieheneBücher() {
        if (ausgelieheneBücher.isEmpty()) {
            System.out.println(name + " hat keine Bücher ausgeliehen.");
        } else {
            System.out.println(name + " hat folgende Bücher ausgeliehen:");
            for (Buch buch : ausgelieheneBücher) {
                System.out.println("- " + buch.getTitel());
            }
        }
    }
}
