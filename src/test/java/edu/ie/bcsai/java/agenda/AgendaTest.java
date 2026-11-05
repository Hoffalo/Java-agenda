package edu.ie.bcsai.java.agenda;

import edu.ie.bcsai.java.agenda.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AgendaTest {

    // The agenda writes to the console; capturing it keeps test output clean and lets us check what was printed.
    private final ByteArrayOutputStream console = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void captureConsole() {
        originalOut = System.out;
        System.setOut(new PrintStream(console));
    }

    @AfterEach
    void restoreConsole() {
        System.setOut(originalOut);
    }

    private static Scanner inputOf(String... lines) {
        return new Scanner(String.join("\n", lines) + "\n");
    }

    private static ArrayList<Person> contactsNamed(String... names) {
        ArrayList<Person> contacts = new ArrayList<>();
        for (String name : names) {
            contacts.add(new Person(name, "600000000"));
        }
        return contacts;
    }

    private static List<String> namesOf(ArrayList<Person> contacts) {
        return contacts.stream().map(Person::getName).toList();
    }

    @Test
    void readContactBuildsPersonFromTypedNameAndPhone() {
        Scanner scanner = inputOf("Ana", "611222333");

        Person person = Agenda.readContact(scanner);

        assertEquals("Ana 611222333", person.getName() + " " + person.getPhoneNumber());
    }

    @Test
    void readContactAsksAgainWhenNameIsBlank() {
        Scanner scanner = inputOf("   ", "Ana", "611222333");

        Person person = Agenda.readContact(scanner);

        assertEquals("Ana", person.getName());
    }

    @Test
    void readContactReturnsNullWhenInputEndsEarly() {
        Scanner scanner = inputOf("Ana");

        Person person = Agenda.readContact(scanner);

        assertNull(person);
    }

    @Test
    void listContactsNumbersEachContactFromOne() {
        ArrayList<Person> contacts = contactsNamed("Ana", "Luis");

        Agenda.listContacts(contacts);

        assertTrue(console.toString().contains("1. Ana - 600000000")
                && console.toString().contains("2. Luis - 600000000"));
    }

    @Test
    void listContactsSaysSoWhenAgendaIsEmpty() {
        Agenda.listContacts(new ArrayList<>());

        assertTrue(console.toString().contains("The agenda is empty."));
    }

    @Test
    void deleteContactRemovesTheContactAtTheTypedPosition() {
        ArrayList<Person> contacts = contactsNamed("Ana", "Luis", "Marta");

        Agenda.deleteContact(inputOf("2"), contacts);

        assertEquals(List.of("Ana", "Marta"), namesOf(contacts));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "4", "-1", "abc", ""})
    void deleteContactKeepsAllContactsWhenPositionIsInvalid(String typed) {
        ArrayList<Person> contacts = contactsNamed("Ana", "Luis", "Marta");

        Agenda.deleteContact(inputOf(typed), contacts);

        assertEquals(List.of("Ana", "Luis", "Marta"), namesOf(contacts));
    }

    @Test
    void deleteContactOnEmptyAgendaDeletesNothing() {
        boolean deleted = Agenda.deleteContact(inputOf("1"), new ArrayList<>());

        assertFalse(deleted);
    }
}
