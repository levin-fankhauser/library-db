package org.example;

public class Buch {
    private String titel;
    private String autor;
    private boolean istAusgeliehen;

    public Buch(String titel, String autor) {
        this.titel = titel;
        this.autor = autor;
        this.istAusgeliehen = false;
    }

    public String getTitel() {
        return titel;
    }

    public String getAutor() {
        return autor;
    }

    public boolean istAusgeliehen() {
        return istAusgeliehen;
    }

    public void ausleihen() {
        if (!istAusgeliehen) {
            istAusgeliehen = true;
            System.out.println("Buch \"" + titel + "\" wurde ausgeliehen.");
        } else {
            System.out.println("Buch \"" + titel + "\" ist bereits ausgeliehen.");
        }
    }

    public void zurückgeben() {
        if (istAusgeliehen) {
            istAusgeliehen = false;
            System.out.println("Buch \"" + titel + "\" wurde zurückgegeben.");
        } else {
            System.out.println("Buch \"" + titel + "\" wurde nicht ausgeliehen.");
        }
    }

    @Override
    public String toString() {
        return "\"" + titel + "\" von " + autor + (istAusgeliehen ? " (ausgeliehen)" : " (verfügbar)");
    }
}
