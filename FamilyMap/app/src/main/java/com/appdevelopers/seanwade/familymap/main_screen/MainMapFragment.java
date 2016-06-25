package com.appdevelopers.seanwade.familymap.main_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appdevelopers.seanwade.familymap.FMS;
import com.appdevelopers.seanwade.familymap.FilterState;
import com.appdevelopers.seanwade.familymap.R;
import com.appdevelopers.seanwade.familymap.model.Event;
import com.appdevelopers.seanwade.familymap.model.Person;
import com.appdevelopers.seanwade.familymap.person_info.PersonActivity;
import com.appdevelopers.seanwade.familymap.utilities.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.android.gms.maps.model.Polyline;

public class MainMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    private static final String ARG_EVENT_ID = "event_id";

    private FMS fms;
    private FilterState filterState;
    private GoogleMap map;
    private Marker curMarker;
    private Map<Marker, Event> eventMarkerMap;

    private TextView currentPersonText;
    private TextView currentEventText;
    private ImageView genderImage;
    private LinearLayout bottomLayout;

    private Set<Marker> maleMarkers;
    private Set<Marker> femaleMarkers;
    private List<Polyline> polyLineList;

    private String selectedEventId;
    private int mapRecurseCount = 10;




    public MainMapFragment() {
        // Required empty public constructor
    }


    public  static MainMapFragment newInstance(String eventId) {
        MainMapFragment fragment = new MainMapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EVENT_ID, eventId);
        fragment.setArguments(args);
        return fragment;
    }







    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filterState = FilterState.getInstance();
        try {
            selectedEventId = getArguments().getString(ARG_EVENT_ID);
        }
        catch (Exception e) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fms = FMS.getInstance();
        fms.buildFamilyTree();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        eventMarkerMap = new HashMap<Marker, Event>();


        currentPersonText = (TextView) v.findViewById(R.id.currentPersonText);
        currentEventText = (TextView) v.findViewById(R.id.currentEventText);
        genderImage = (ImageView) v.findViewById(R.id.genderImage);


        bottomLayout = (LinearLayout) v.findViewById(R.id.bottomMapLayout);
        bottomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = Person.getPersonById(eventMarkerMap.get(curMarker).getPersonId());
                onButtonClicked(person);
            }
        });

        return v;
    }


    public void onButtonClicked(Person person) {
        Intent intent = new Intent(getActivity(), PersonActivity.class);
        intent.putExtra("person", person);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(FilterState.getInstance().mapType);
        polyLineList = new ArrayList<>();

        fms.buildEventColorMap();

        for (Event event : fms.getEventList()) {

            Person person = Person.getPersonById(event.getPersonId());

            LatLng eventLatLng =
                    new LatLng(Double.parseDouble(event.getLatitude()), Double.parseDouble(event.getLongitude()));

            curMarker = map.addMarker(new MarkerOptions()
                    .position(eventLatLng)
                    .title(person.getName())
                    .icon(BitmapDescriptorFactory.defaultMarker(fms.getEventColorMap().get(event.getDescription()))));

            eventMarkerMap.put(curMarker, event);
        }
        currentPersonText.setText("Pick a person to see info");
        map.setOnMarkerClickListener(this);
        curMarker = null;
        if (selectedEventId != null) {
            for (Marker marker: eventMarkerMap.keySet()) {
                if (eventMarkerMap.get(marker).getEventId().equals(selectedEventId)) {
                    onMarkerClick(marker);
                    break;
                }
            }
        }
        else {
            filterState.refreshFilters();
        }



        maleMarkers = new HashSet<>();
        femaleMarkers = new HashSet<>();
        for (Marker m: eventMarkerMap.keySet()) {
            if (fms.getPeopleMap().get(eventMarkerMap.get(m).getPersonId()).getGender().equals("m")) {
                maleMarkers.add(m);
            }
            else {
                femaleMarkers.add(m);
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Event event = eventMarkerMap.get(marker);
        marker.showInfoWindow();
        genderImage.setImageDrawable(Utils.getGenderIcon(getActivity(), Person.getPersonById(event.getPersonId()).getGender()));
        currentPersonText.setText(Person.getPersonById(event.getPersonId()).getName());
        currentEventText.setText(event.toString());
        curMarker = marker;
        drawLines();
        return true;
    }

    public void onMarkerClickWithEvent(Event event) {
        for (Marker marker: eventMarkerMap.keySet()) {
            if (eventMarkerMap.get(marker).getEventId().equals(event.getEventId())) {
                onMarkerClick(marker);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        redrawMarkers();
        try {
            map.setMapType(FilterState.getInstance().mapType);
            drawLines();
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(curMarker.getPosition(), 1));

        }
        catch (Exception e) {

        }

    }

    public void redrawMarkers() {
        List<String> hiddenList = FilterState.getInstance().calcHiddenMarkers();
        for (Marker marker: eventMarkerMap.keySet()) {
            for (String hidden: hiddenList){
                if (eventMarkerMap.get(marker).getDescription().equals(hidden)) {
                    marker.setVisible(false);
                    break;
                }
                else {
                    marker.setVisible(true);
                }
            }
            if (marker.isVisible() == true) {
                for (String hidden : hiddenList) {
                    if (hidden.equals("male") && fms.getPeopleMap().get(eventMarkerMap.get(marker).getPersonId()).getGender().equals("m")) {
                        marker.setVisible(false);
                    } else if (hidden.equals("female") && fms.getPeopleMap().get(eventMarkerMap.get(marker).getPersonId()).getGender().equals("f")) {
                        marker.setVisible(false);
                    }
                }
            }
            if (hiddenList.size() == 0) {
                marker.setVisible(true);
            }
        }
    }

    public void ancestorRecurse(String personId, boolean visible) {
        Set<String> parentSet = fms.getFamilyTree().get(personId);
            for (Marker marker: eventMarkerMap.keySet()) {
                if (eventMarkerMap.get(marker).getPersonId().equals(personId)) {
                    if (visible) {
                        marker.setVisible(true);
                    }
                    else {
                        marker.setVisible(false);
                    }
                }
            }
        for (String parent: parentSet) {
            if (parent != null) {
                ancestorRecurse(parent, visible);
            }
        }
    }


    public void parentOnResumeCalled() {
        if (map != null) {
            map.setMapType(FilterState.getInstance().mapType);
        }
    }

    public void drawLines() {
        try {
            for (com.google.android.gms.maps.model.Polyline line : polyLineList) {
                line.remove();
            }
        }
        catch (Exception e) {

        }
        if (filterState.areAllEventsShown() && filterState.isShowEventLine()) {
            drawEventLines();
        }
        if (filterState.bothGendersAreShown() && filterState.isShowSpouseLine()) {
            drawSpouseLine();
        }
        if (filterState.isShowAncestorLine()) {
            drawAncestorLines();
        }
    }

    public void drawEventLines() {
        Event event = eventMarkerMap.get(curMarker);
        List<Event> eventList = Utils.getPersonEvents(event.getPersonId());
        Utils.sortEvents(eventList);
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(new LatLng(Double.parseDouble(eventList.get(0).getLatitude()), Double.parseDouble(eventList.get(0).getLongitude())));
        for (int i = 1; i < eventList.size(); i++) {
            polylineOptions.add(new LatLng(Double.parseDouble(eventList.get(i).getLatitude()), Double.parseDouble(eventList.get(i).getLongitude())));
        }
        polylineOptions.color(filterState.lineColorLifeStory).width(10);
        Polyline finalPolyLine = map.addPolyline(polylineOptions);
        polyLineList.add(finalPolyLine);
    }

    public void drawSpouseLine() {
        Event event = eventMarkerMap.get(curMarker);
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(new LatLng(Double.parseDouble(event.getLatitude()), Double.parseDouble(event.getLongitude())));

        Person person = FMS.getInstance().getPeopleMap().get(event.getPersonId());
        String spouseId = person.getSpouseId();
        if (spouseId != null) {
            for (Marker marker: eventMarkerMap.keySet()) {
                Event spouseEvent = fms.getEarliestEvent(spouseId);
                if (eventMarkerMap.get(marker).getEventId().equals(spouseEvent.getEventId())) {
                    polylineOptions.add(new LatLng(Double.parseDouble(spouseEvent.getLatitude()), Double.parseDouble(spouseEvent.getLongitude())));
                }
            }
        }
        polylineOptions.color(filterState.lineColorSpouse).width(10);
        Polyline finalLine = map.addPolyline(polylineOptions);
        polyLineList.add(finalLine);
    }

    public void drawAncestorLines() {
        Event event = eventMarkerMap.get(curMarker);
        String personId = event.getPersonId();
        familyTreeRecurse(personId);
        Set<String> parentSet = fms.getFamilyTree().get(personId);
        for (String parent: parentSet) {
            if (parent != null) {
                Event parentEvent = fms.getEarliestEvent(parent);
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.add(new LatLng(Double.parseDouble(event.getLatitude()), Double.parseDouble(event.getLongitude())));
                polylineOptions.add(new LatLng(Double.parseDouble(parentEvent.getLatitude()), Double.parseDouble(parentEvent.getLongitude())));
                polylineOptions.color(filterState.lineColorTree).width(10);
                Polyline finalLine = map.addPolyline(polylineOptions);
                polyLineList.add(finalLine);
                familyTreeRecurse(parent);
            }
        }
    }



    public void familyTreeRecurse(String personId) {
        Set<String> parentSet = fms.getFamilyTree().get(personId);
        for (String parent: parentSet) {
            if (parent != null) {
                Event parentEvent = fms.getEarliestEvent(parent);
                Event personEvent = fms.getEarliestEvent(personId);
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.add(new LatLng(Double.parseDouble(personEvent.getLatitude()), Double.parseDouble(personEvent.getLongitude())));
                polylineOptions.add(new LatLng(Double.parseDouble(parentEvent.getLatitude()), Double.parseDouble(parentEvent.getLongitude())));
                mapRecurseCount = mapRecurseCount-3;
                if (mapRecurseCount < 1) {
                    mapRecurseCount = 1;
                }
                polylineOptions.color(filterState.lineColorTree).width(mapRecurseCount);
                Polyline finalLine = map.addPolyline(polylineOptions);
                polyLineList.add(finalLine);
                familyTreeRecurse(parent);
                mapRecurseCount = mapRecurseCount+3;

            }
        }
        if (Collections.frequency(parentSet, null) == parentSet.size()) {
        }
    }

}