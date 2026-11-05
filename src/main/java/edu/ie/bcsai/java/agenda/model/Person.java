package edu.ie.bcsai.java.agenda.model;

/**
 * A contact stored in the agenda.
 *
 * Fields are final because a contact is replaced, never edited in place,
 * so there is no way for a stored contact to end up half-updated.
 */
public class Person {

    private final String name;
    private final String phoneNumber;

    public Person(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return name + " - " + phoneNumber;
    }
}
