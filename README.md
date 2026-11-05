# Java Agenda

A command-line contact agenda. Contacts are kept in memory in an `ArrayList<Person>` while the program runs.

## Menu

```
1. Add contact      -> asks for a name and a phone number
2. List contacts    -> shows every contact, numbered from 1
3. Delete contact   -> shows the list and deletes the number you type
0. Exit
```

## Structure

| File | Package | What it holds |
|---|---|---|
| `src/main/java/edu/ie/bcsai/java/agenda/Agenda.java` | `edu.ie.bcsai.java.agenda` | `main` (the menu loop) and the methods `readContact`, `listContacts`, `deleteContact` |
| `src/main/java/edu/ie/bcsai/java/agenda/model/Person.java` | `edu.ie.bcsai.java.agenda.model` | The `Person` class with `name` and `phoneNumber` |
| `src/test/java/edu/ie/bcsai/java/agenda/AgendaTest.java` | | JUnit 5 tests for the three methods |

## How to run

**From an IDE (IntelliJ, Eclipse, VS Code):** open the folder as a Maven project and run `Agenda.main`.

**With Maven:**
```
mvn package
java -jar target/agenda-1.0.jar
```

**With only the JDK (no Maven):**
```
javac -d out src/main/java/edu/ie/bcsai/java/agenda/model/Person.java src/main/java/edu/ie/bcsai/java/agenda/Agenda.java
java -cp out edu.ie.bcsai.java.agenda.Agenda
```

Requires Java 17 or newer.
