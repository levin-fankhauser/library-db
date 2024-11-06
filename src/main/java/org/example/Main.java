package org.example;

public class Main {
	public static void main(String[] args) {
		// BibliothekSystem erstellen
		BibliothekSystem system = new BibliothekSystem();

		// Zwei Bibliotheken erstellen
		Bibliothek stadtBibliothek = new Bibliothek("LiestalBibliothek");
		Bibliothek uniBibliothek = new Bibliothek("BaselBibliothek");

		// Bibliotheken zum System hinzufügen
		system.bibliothekHinzufügen(stadtBibliothek);
		system.bibliothekHinzufügen(uniBibliothek);

		// Benutzer erstellen
		Benutzer benutzer = new Benutzer("Max Mustermann");

		// Bücher erstellen
		Buch buch1 = new Buch("1984", "George Orwell");
		Buch buch2 = new Buch("Die Verwandlung", "Franz Kafka");

		// Buch zur Stadtbibliothek hinzufügen
		stadtBibliothek.buchHinzufügen(buch1);

		// Buch zur Universitätsbibliothek hinzufügen
		uniBibliothek.buchHinzufügen(buch2);

		// Alle Bibliotheken anzeigen
		system.zeigeAlleBibliotheken();

		// Bücher in der Stadtbibliothek anzeigen
		stadtBibliothek.zeigeAlleBücher();

		// Ein Buch aus der Stadtbibliothek ausleihen
		benutzer.buchAusleihen(stadtBibliothek.findeBuch("1984"));
		benutzer.zeigeAusgelieheneBücher();

		// Buch zurückgeben
		benutzer.buchZurückgeben(stadtBibliothek.findeBuch("1984"));

		// Alle Bücher der Stadtbibliothek anzeigen
		stadtBibliothek.zeigeAlleBücher();
	}
}
