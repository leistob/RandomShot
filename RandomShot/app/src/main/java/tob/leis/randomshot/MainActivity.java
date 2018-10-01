package tob.leis.randomshot;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import tob.leis.randomshot.fragments.ControllerFragment;
import tob.leis.randomshot.fragments.RouletteFragment;
import tob.leis.randomshot.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    public static final String CURRENT_DRAWER_POSITION = "currentDrawerPosition";
    public static final int ROULETTE_FRAGMENT_ID = 0;
    public static final int CONTROLLER_FRAGMENT_ID = 1;
    public static final int SETTINGS_FRAGMENT_ID = 3;
    public static final int ABOUT_FRAGMENT_ID = 4;

    private DrawerLayout mNavigationDrawer;
    private Toolbar mToolbar;
    private int mCurrentDrawerPosition;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawer = findViewById(R.id.drawer_layout);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mNavigationDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mNavigationDrawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState != null) {
            mCurrentDrawerPosition = savedInstanceState.getInt(CURRENT_DRAWER_POSITION);
        }
        NavigationView navigationView = findViewById(R.id.nav_view);
        if (navigationView != null) {

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    selectDrawerItem(menuItem);
                    return true;
                }
            });
            selectDrawerItem(navigationView.getMenu().getItem(mCurrentDrawerPosition));
        }
        /*

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new RouletteFragment()).commit();

        if (savedInstanceState != null) {
            //Restore the fragment's instance
            //mContent = getSupportFragmentManager().getFragment(savedInstanceState, "myFragmentName");
        }*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment newFragment = null;

        if (mCurrentDrawerPosition == menuItem.getItemId()) {
            return;
        }

        switch (menuItem.getItemId()) {
            case R.id.nav_roulette:
                mCurrentDrawerPosition = ROULETTE_FRAGMENT_ID;
                newFragment = new RouletteFragment();
                break;
            case R.id.nav_controller:
                mCurrentDrawerPosition = CONTROLLER_FRAGMENT_ID;
                newFragment = new ControllerFragment();
                break;
            case R.id.nav_settings:
                mCurrentDrawerPosition = SETTINGS_FRAGMENT_ID;
                newFragment = new SettingsFragment();
                break;
            case R.id.nav_about:
                mCurrentDrawerPosition = ABOUT_FRAGMENT_ID;
                newFragment = new ControllerFragment();
                break;
        }

        if (newFragment != null) {
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, newFragment).commit();

            menuItem.setChecked(true);
            setTitle(menuItem.getTitle());
        }
        mNavigationDrawer.closeDrawers();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_DRAWER_POSITION, mCurrentDrawerPosition);
    }
}
