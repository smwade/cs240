package com.appdevelopers.seanwade.familymap.person_info;

import com.appdevelopers.seanwade.familymap.FMS;
import com.appdevelopers.seanwade.familymap.model.Event;
import com.appdevelopers.seanwade.familymap.model.Person;
import com.appdevelopers.seanwade.familymap.utilities.Utils;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seanwade on 3/21/16.
 */
public class TopEvent implements ParentObject {

    private List<Object> childrenList;
    private List<Object> parentList;
    public String title;
    private Person person;
    private FMS fms;

    public TopEvent(Person person, String title) {
        childrenList = new ArrayList<>();
        this.person = person;
        this.title = title;
        if (title.equals("event")) {
            List<Event> personEventList = Utils.getPersonEvents(person.getPersonId());
            for (Event event : personEventList) {
                String eventString = event.toString();
                String name = person.getFirstName() + " " + person.getLastName();
                childrenList.add(new PersonListEventChild(eventString, name, "event", null, event));
            }
        }
        else {
           parentList = new ArrayList<>();
            if (Utils.getFather(person.getFatherId()) != null) {
                parentList.add(new PersonListEventChild(Utils.getFather(person.getFatherId()).getName(), "FATHER", "male", person, null));
            }
            if (Utils.getMother(person.getMotherId()) != null) {
                parentList.add(new PersonListEventChild(Utils.getMother(person.getMotherId()).getName(), "MOTHER", "female", person, null));
            }
        }
    }

    @Override
    public List<Object> getChildObjectList() {
        if (title.equals("event")) {
            return childrenList;
        }
        else {
            return parentList;
        }
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        childrenList = list;
        parentList = list;
    }
}
