package edu.ie.bcsai.java.agenda;

import edu.ie.bcsai.java.agenda.model.Person;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Command-line agenda: add, list and delete contacts kept in memory.
 */
public class Agenda {

    private static final String OPTION_ADD = "1";
    private static final String OPTION_LIST = "2";
    private static final String OPTION_DELETE = "3";
    private static final String OPTION_EXIT = "0";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Person> contacts = new ArrayList<>();

        boolean running = true;
        while (running) {
            printMenu();
            String option = readLine(scanner, "Choose an option: ");
            // A null line means the input stream ended (e.g. Ctrl+D), so there is nothing left to read.
            if (option == null) {
                break;
            }

            switch (option) {
                case OPTION_ADD -> {
                    Person person = readContact(scanner);
                    if (person != null) {
                        contacts.add(person);
                        System.out.println("Contact added.");
                    }
                }
                case OPTION_LIST -> listContacts(contacts);
                case OPTION_DELETE -> deleteContact(scanner, contacts);
                case OPTION_EXIT -> running = false;
                default -> System.out.println("Invalid option, please try again.");
            }
        }

        System.out.println("Goodbye!");
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== AGENDA =====");
        System.out.println(OPTION_ADD + ". Add contact");
        System.out.println(OPTION_LIST + ". List contacts");
        System.out.println(OPTION_DELETE + ". Delete contact");
        System.out.println(OPTION_EXIT + ". Exit");
    }

    /**
     * Asks the user for a name and a phone number and builds a Person from them.
     * Both fields are required, so the user is asked again until they type something.
     *
     * @return the new contact, or null if the input ended before it was complete
     */
    public static Person readContact(Scanner scanner) {
        String name = readRequiredLine(scanner, "Name: ");
        if (name == null) {
            return null;
        }
        String phoneNumber = readRequiredLine(scanner, "Phone number: ");
        if (phoneNumber == null) {
            return null;
        }
        return new Person(name, phoneNumber);
    }

    /**
     * Prints every contact with its position in the list, starting at 1.
     * The positions are what the user types to delete a contact.
     */
    public static void listContacts(ArrayList<Person> contacts) {
        if (contacts.isEmpty()) {
            System.out.println("The agenda is empty.");
            return;
        }
        System.out.println("Contacts:");
        for (int i = 0; i < contacts.size(); i++) {
            System.out.println((i + 1) + ". " + contacts.get(i));
        }
    }

    /**
     * Shows the contacts and removes the one whose number the user types.
     *
     * @return true if a contact was removed
     */
    public static boolean deleteContact(Scanner scanner, ArrayList<Person> contacts) {
        if (contacts.isEmpty()) {
            System.out.println("The agenda is empty, there is nothing to delete.");
            return false;
        }

        listContacts(contacts);
        String input = readLine(scanner, "Number of the contact to delete: ");
        if (input == null) {
            return false;
        }

        int position;
        try {
            position = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("That is not a number. No contact was deleted.");
            return false;
        }

        if (position < 1 || position > contacts.size()) {
            System.out.println("There is no contact with number " + position + ". No contact was deleted.");
            return false;
        }

        // The user sees positions starting at 1, the list counts from 0.
        Person removed = contacts.remove(position - 1);
        System.out.println("Deleted: " + removed);
        return true;
    }

    /**
     * @return the trimmed line the user typed, or null if the input has ended
     */
    private static String readLine(Scanner scanner, String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            return null;
        }
        return scanner.nextLine().trim();
    }

    private static String readRequiredLine(Scanner scanner, String prompt) {
        String line = readLine(scanner, prompt);
        while (line != null && line.isEmpty()) {
            System.out.println("This field cannot be empty.");
            line = readLine(scanner, prompt);
        }
        return line;
    }
}
