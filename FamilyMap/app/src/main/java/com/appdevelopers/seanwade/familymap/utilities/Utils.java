package com.appdevelopers.seanwade.familymap.utilities;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.appdevelopers.seanwade.familymap.FMS;
import com.appdevelopers.seanwade.familymap.R;
import com.appdevelopers.seanwade.familymap.model.Event;
import com.appdevelopers.seanwade.familymap.model.Person;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This is a static class that contains usefull functions for the application.
 */
public class Utils {

    public static Drawable getGenderIcon(Activity activity, String gender) {
        if (gender.equals("m")) {
            return new IconDrawable(activity, Iconify.IconValue.fa_male)
                    .colorRes(R.color.wallet_holo_blue_light)
                    .sizeDp(40);
        }
        else {
            return new IconDrawable(activity, Iconify.IconValue.fa_female)
                    .colorRes(R.color.pink)
                    .sizeDp(40);
        }
    }

    public static List<Event> getPersonEvents(String personId) {
        List<Event> personEventList = new ArrayList<>();
        for (Event event : FMS.getInstance().getEventList()) {
            if (event.getPersonId().equals(personId)) {
                personEventList.add(event);
            }
        }
        sortEvents(personEventList);
        Collections.reverse(personEventList);
        return personEventList;
    }

    public static Person getFather(String personId) {
        for (Person person : FMS.getInstance().getPeopleList()) {
            if (person.getPersonId() != null && person.getPersonId().equals(personId)) {
                return person;
            }
        }
        return null;
    }

    public static Person getMother(String personId) {
        for (Person person : FMS.getInstance().getPeopleList()) {
            if (person.getPersonId() != null && person.getPersonId().equals(personId)) {
                return person;
            }
        }
        return null;
    }

    public static List<Person> getSibling(String personId) {
        Person mother = getMother(personId);
        Person father = getFather(personId);
        List<Person> siblings = new ArrayList<>();
        for (Person person : FMS.getInstance().getPeopleList()) {
            if (person.getFatherId().equals(father.getPersonId()) && person.getMotherId().equals(mother.getPersonId())) {
                siblings.add(person);
            }
        }
        return siblings;
    }

    public static List<Person> getChildren(String personId) {
        List<Person> childrenList = new ArrayList<>();
        for (Person person : FMS.getInstance().getPeopleList()) {
            if (person.getFatherId().equals(personId) || person.getMotherId().equals(personId)) {
                childrenList.add(person);
            }
        }
        return childrenList;
    }



    public static void sortEvents(List<Event> eventList) {
        Collections.sort(eventList, new Comparator<Event>() {

            public int compare(Event o1, Event o2) {
                return Integer.parseInt(o2.getYear()) - Integer.parseInt(o1.getYear());
            }
        });
    }


}
