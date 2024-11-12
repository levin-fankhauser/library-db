# Modul 450 - Testing Applications

### Projekt einrichten
```bash
git clone https://github.com/levin-fankhauser/library.git
```

#### Projekt starten in IntelliJ
Maven Projekt reloaden

### Tests starten
```bash
mvn test
```

### Build starten
```bash
mvn clean install
```

### Mutation-Tests ausführen
```bash
mvn org.pitest:pitest-maven:mutationCoverage
```