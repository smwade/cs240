package com.appdevelopers.seanwade.familymap.map;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.appdevelopers.seanwade.familymap.MainActivity;
import com.appdevelopers.seanwade.familymap.R;
import com.appdevelopers.seanwade.familymap.filter.FilterActivity;
import com.appdevelopers.seanwade.familymap.main_screen.LoginFragment;
import com.appdevelopers.seanwade.familymap.main_screen.MainMapFragment;
import com.appdevelopers.seanwade.familymap.model.Event;
import com.appdevelopers.seanwade.familymap.model.Person;
import com.appdevelopers.seanwade.familymap.search.SearchActivity;
import com.appdevelopers.seanwade.familymap.settings.SettingsActivity;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

public class MapActivity extends AppCompatActivity {

    private MainMapFragment mapFragment;
    private android.widget.Toolbar toolbar;
    private MenuItem doubleUpArrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        android.support.v4.app.FragmentManager fm = this.getSupportFragmentManager();
        mapFragment = (MainMapFragment) fm.findFragmentById(R.id.map_fragment);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.map_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Map");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        Event event = (Event) intent.getSerializableExtra("event");

        if (mapFragment == null) {
            mapFragment = MainMapFragment.newInstance(event.getEventId());
            fm.beginTransaction()
                    .add(R.id.map_fragment, mapFragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapFragment.parentOnResumeCalled();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_person, menu);

        doubleUpArrow = menu.findItem(R.id.double_up_arrow);

        Drawable doubleUpIcon = new IconDrawable(this, Iconify.IconValue.fa_arrow_up)
                .sizeDp(24).color(Color.WHITE);

        doubleUpArrow.setIcon(doubleUpIcon);

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.double_up_arrow) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
