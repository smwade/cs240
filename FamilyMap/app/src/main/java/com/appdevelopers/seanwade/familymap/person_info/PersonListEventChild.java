package com.appdevelopers.seanwade.familymap.person_info;

import android.graphics.drawable.Drawable;

import com.appdevelopers.seanwade.familymap.model.Event;
import com.appdevelopers.seanwade.familymap.model.Person;
import com.appdevelopers.seanwade.familymap.utilities.Utils;

/**
 * Created by seanwade on 3/21/16.
 */
public class PersonListEventChild {

    private String eventString;
    private String personName;
    private String type;
    public Person person;
    public Event event;

    public PersonListEventChild(String eventString, String personName, String type, Person person, Event event) {
        this.eventString = eventString;
        this.personName = personName;
        this.type = type;
        this.event = event;
        this.person = person;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEventString() {
        return eventString;
    }

    public void setEventString(String eventString) {
        this.eventString = eventString;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
