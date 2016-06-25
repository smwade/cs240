package com.appdevelopers.seanwade.familymap;

import android.util.ArraySet;

import com.appdevelopers.seanwade.familymap.model.Event;
import com.appdevelopers.seanwade.familymap.model.Person;
import com.appdevelopers.seanwade.familymap.model.User;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This is the application singlton that contains the global information.
 */
public class FMS {

    private static FMS instance = null;
    private User user;
    private String serverPort;
    private String serverHost;
    private List<Person> peopleList;
    private List<Event> eventList;

    private Map<String, Person> peopleMap;  // done
    private Map<String, Event> eventMap; //done
    private Map<String, Set<Event>> allEventMap;
    public Set<String> eventTypes; //done
    private Map<String, Float> eventColorMap; //done
    private Map<String, Set<String>> familyTree; //done
    private Set<String> momAncestors;
    private Set<String> dadAncestors;

    private List<Float> colorList;

    private boolean logedIn;


//    private Map<EventType, MapColor> eventColorMap;
//    private Map<PersonID, set<PersonId>> familyTree;
//    private Set<personID> momAncestors;
//    private Set<personID> dadAncestor;
    
    private FMS() {
        peopleList = new LinkedList<>();
        eventList = new LinkedList<>();
        colorList = new ArrayList<>();
        colorList.add(BitmapDescriptorFactory.HUE_BLUE);
        colorList.add(BitmapDescriptorFactory.HUE_RED);
        colorList.add(BitmapDescriptorFactory.HUE_GREEN);
        colorList.add(BitmapDescriptorFactory.HUE_MAGENTA);
        colorList.add(BitmapDescriptorFactory.HUE_ORANGE);
        colorList.add(BitmapDescriptorFactory.HUE_AZURE);
        colorList.add(BitmapDescriptorFactory.HUE_YELLOW);
        colorList.add(BitmapDescriptorFactory.HUE_VIOLET);
        colorList.add(BitmapDescriptorFactory.HUE_ROSE);
        logedIn = false;
    }

    public static FMS getInstance() {
        if (instance == null) {
            instance = new FMS();
        }
        return instance;
    }

    public Map<String, Person> getPeopleMap() {
        return peopleMap;
    }

    public void setPeopleMap(Map<String, Person> peopleMap) {
        this.peopleMap = peopleMap;
    }

    public Map<String, Event> getEventMap() {
        return eventMap;
    }

    public void setEventMap(Map<String, Event> eventMap) {
        this.eventMap = eventMap;
    }

    public Set<String> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes() {
        eventTypes = new HashSet<>();
        for (Event event: eventList) {
            eventTypes.add(event.getDescription());
        }
    }

    public Map<String, Float> getEventColorMap() {
        return eventColorMap;
    }

    public void setEventColorMap(Map<String, Float> eventColorMap) {
        this.eventColorMap = eventColorMap;
    }

    public Map<String, Set<String>> getFamilyTree() {
        return familyTree;
    }

    public void setFamilyTree(Map<String, Set<String>> familyTree) {
        this.familyTree = familyTree;
    }

    public Set<String> getMomAncestors() {
        return momAncestors;
    }

    public void setMomAncestors(Set<String> momAncestors) {
        this.momAncestors = momAncestors;
    }

    public Set<String> getDadAncestors() {
        return dadAncestors;
    }

    public void setDadAncestors(Set<String> dadAncestors) {
        this.dadAncestors = dadAncestors;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public List<Float> getColorList() {
        return colorList;
    }

    public void setColorList(List<Float> colorList) {
        this.colorList = colorList;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public boolean isLogedIn() {
        return logedIn;
    }

    public void setLogedIn(boolean logedIn) {
        this.logedIn = logedIn;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
        eventMap = new HashMap<>();
        for (Event event: eventList) {
            if (!eventMap.containsKey(event.getEventId())) {
                eventMap.put(event.getEventId(), event);
            }
        }
        setEventTypes();

    }

    public List<Person> getPeopleList() {
        return peopleList;
    }

    public void setPeopleList(List<Person> peopleList) {
        this.peopleList = peopleList;
        peopleMap = new HashMap<>();
        for (Person person: peopleList) {
            if (!peopleMap.containsKey(person.getPersonId())) {
                peopleMap.put(person.getPersonId(), person);
            }
        }
    }

    public void sortEvents(List<Event> eventList) {
        Collections.sort(eventList, new Comparator<Event>() {

            public int compare(Event o1, Event o2) {
                return Integer.parseInt(o2.getYear()) - Integer.parseInt(o1.getYear());
            }
        });
    }

    public Event getEarliestEvent(String personId) {
        Event shortestEvent = new Event();
        shortestEvent.setYear("99999");
        for (Event event: eventList) {
            if (event.getPersonId().equals(personId)) {
                if (Integer.parseInt(event.getYear()) < Integer.parseInt(shortestEvent.getYear())) {
                    shortestEvent = event;
                }
            }
        }
        return shortestEvent;
    }

//    public void buildFamilyTree() {
//        familyTree = new HashMap<>();
//        for (Person person: peopleList) {
//            if (!familyTree.containsKey(person.getPersonId())) {
//                familyTree.put(person.getPersonId(), new HashSet<String>());
//            }
//            if (person.getFatherId() != null) {
//                familyTree.get(person.getPersonId()).add(person.getFatherId());
//            }
//            if (person.getMotherId() != null) {
//                familyTree.get(person.getPersonId()).add(person.getMotherId());
//            }
//            if (person.getSpouseId() != null) {
//                familyTree.get(person.getPersonId()).add(person.getSpouseId());
//            }
//        }
//    }

    public void buildEventColorMap() {
        int count = 0;
        eventColorMap = new HashMap<>();
        for (String event: eventTypes) {
            count = count % 9;
            eventColorMap.put(event, colorList.get(count));
            count++;
        }
    }

    public void buildAllEventMap() {
        for (Event event: eventList) {
            if (!allEventMap.containsKey(event.getDescription())) {
                allEventMap.put(event.getDescription(),new HashSet<Event>());
            }
            allEventMap.get(event.getDescription()).add(event);
        }
    }

    public void buildFamilyTree() {
        familyTree = new HashMap<>();
        for (Person person: peopleList) {
            String personId = person.getPersonId();
            if (!familyTree.containsKey(personId)) {
                Set<String> parentSet = new HashSet<>();
                parentSet.add(person.getFatherId());
                parentSet.add(person.getMotherId());
                familyTree.put(personId, parentSet);
            }
        }
    }




}

