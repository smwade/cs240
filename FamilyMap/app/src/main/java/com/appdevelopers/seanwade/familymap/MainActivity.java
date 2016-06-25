package com.appdevelopers.seanwade.familymap;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.appdevelopers.seanwade.familymap.filter.FilterActivity;
import com.appdevelopers.seanwade.familymap.main_screen.LoginFragment;
import com.appdevelopers.seanwade.familymap.main_screen.MainMapFragment;
import com.appdevelopers.seanwade.familymap.person_info.PersonActivity;
import com.appdevelopers.seanwade.familymap.search.SearchActivity;
import com.appdevelopers.seanwade.familymap.settings.SettingsActivity;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

public class MainActivity extends AppCompatActivity {

    private LoginFragment loginFragment;
    private MainMapFragment mapFragment;
    private Toolbar toolbar;

    private MenuItem searchIcon;
    private MenuItem filterIcon;
    private MenuItem settingsIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v4.app.FragmentManager fm = this.getSupportFragmentManager();
        loginFragment = (LoginFragment) fm.findFragmentById(R.id.mainFrameLayout);
        MainMapFragment mainMapFragmet = (MainMapFragment) fm.findFragmentById(R.id.mainFrameLayout);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        if (FMS.getInstance().isLogedIn() == true) {
            if (mapFragment == null) {
                mapFragment = MainMapFragment.newInstance(null);
                fm.beginTransaction()
                        .add(R.id.mainFrameLayout, mapFragment)
                        .commit();
            }
        }
        else {
            if (loginFragment == null) {
                loginFragment = LoginFragment.newInstance();
                fm.beginTransaction()
                        .add(R.id.mainFrameLayout, loginFragment)
                        .commit();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        searchIcon = menu.findItem(R.id.toolbar_search);
        filterIcon = menu.findItem(R.id.toolbar_filter);
        settingsIcon = menu.findItem(R.id.toolbar_settings);

        Drawable searchIc = new IconDrawable(this, Iconify.IconValue.fa_search)
                .sizeDp(24).color(Color.WHITE);
        Drawable filterIc= new IconDrawable(this, Iconify.IconValue.fa_filter)
                .sizeDp(24).color(Color.WHITE);
        Drawable settingsIc = new IconDrawable(this, Iconify.IconValue.fa_gear)
                .sizeDp(24).color(Color.WHITE);

        searchIcon.setIcon(searchIc);
        filterIcon.setIcon(filterIc);
        settingsIcon.setIcon(settingsIc);

        searchIcon.setVisible(false);
        filterIcon.setVisible(false);
        settingsIcon.setVisible(false);

        if (FMS.getInstance().isLogedIn() == true) {
            setIconsVisible();
        }

        return super.onCreateOptionsMenu(menu);
    }

    public void setIconsVisible() {
        searchIcon.setVisible(true);
        filterIcon.setVisible(true);
        settingsIcon.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.toolbar_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.toolbar_filter) {
            Intent intent = new Intent(this, FilterActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.toolbar_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
