package com.appdevelopers.seanwade.familymap.model;

import com.appdevelopers.seanwade.familymap.FMS;

import java.io.Serializable;

/**
 * Created by seanwade on 3/16/16.
 */
public class Person implements Serializable{

    private static final long serialVersionUID = 1L;

    private String descendant;
    private String personId;
    private String firstName;
    private String lastName;
    private String gender;
    private String spouseId;
    private String fatherId;
    private String motherId;

    public Person(String descendant, String personId, String firstName, String lastName, String gender, String spouseId) {
        this.descendant = descendant;
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.spouseId = spouseId;
    }

    public Person(String descendant, String personId, String firstName, String lastName, String gender, String spouseId, String fatherId, String motherId) {
        this.descendant = descendant;
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.spouseId = spouseId;
        this.fatherId = fatherId;
        this.motherId = motherId;
    }

    public Person() {

    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSpouseId() {
        return spouseId;
    }

    public void setSpouseId(String spouseId) {
        this.spouseId = spouseId;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getMotherId() {
        return motherId;
    }

    public void setMotherId(String motherId) {
        this.motherId = motherId;
    }

    public static Person getPersonById(String personId) {
        for (Person person: FMS.getInstance().getPeopleList()) {
            if (person.getPersonId().equals(personId)) {
                return person;
            }
        }
        return null;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "descendant='" + descendant + '\'' +
                ", personId='" + personId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", spouseId='" + spouseId + '\'' +
                ", fatherId='" + fatherId + '\'' +
                ", motherId='" + motherId + '\'' +
                '}';
    }
}
