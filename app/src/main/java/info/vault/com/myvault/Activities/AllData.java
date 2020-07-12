package info.vault.com.myvault.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import info.vault.com.myvault.AdapterClass.AllRecordeAdapter;
import info.vault.com.myvault.Common.Constants;
import info.vault.com.myvault.DatabaseHandler.DBHelper;
import info.vault.com.myvault.Fragments.All_Data_fragment;
import info.vault.com.myvault.Fragments.ChangeMyPin;
import info.vault.com.myvault.Fragments.ChangePassword;
import info.vault.com.myvault.Listeners.HomePageListener;
import info.vault.com.myvault.Listeners.itemClickListener;
import info.vault.com.myvault.R;


public class AllData extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, HomePageListener {

    boolean homeFragment = true;
    DrawerLayout drawer;
    ListView mDrawerList;
    AlertDialog dialog;
    private ActionBar actionBar;
    Bundle pBundle = new Bundle();
    private ActionBarDrawerToggle toggle;
    FloatingActionButton fab;
    RecyclerView mrecyclerView;
    DBHelper dbHelper;
    itemClickListener itemClick;
    AllRecordeAdapter allRecordeAdapter;
    boolean yes=false;
    String keyVal = "EMPTY";
    String tag = "HOME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_data);
        dbHelper = new DBHelper(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        actionBar = getSupportActionBar();
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);



        setUpFragments();
    }

    private void setUpFragments() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment homeFragment = fm.findFragmentByTag(tag);

        if (homeFragment == null) {
            FragmentTransaction ft = fm.beginTransaction();
            homeFragment = new All_Data_fragment();
            pBundle.putString("pooja", keyVal);
            homeFragment.setArguments(pBundle);
            ft.add(R.id.am_full_container, homeFragment, tag);
            ft.commit();
        }
    }


    @Override
    public void onBackPressed() {

        toggle.setDrawerIndicatorEnabled(true);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            if(homeFragment)
                callAlert();
            else {
                actionBar.setDisplayHomeAsUpEnabled(false);
                switchFragment(Constants.HOME_FRAGMENT,null);
                toggle.syncState();
            }
        }
    }

    public void callAlert() {
        Log.d("db", "logout");

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        View mView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        TextView i_yes = mView.findViewById(R.id.dai_yes);
        TextView i_no = mView.findViewById(R.id.dai_no);


        i_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                finish();

            }
        });
        i_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });

        mBuilder.setView(mView);
        dialog = mBuilder.create();

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        int frag_id = 0;
        switch (id) {
            case R.id.c_password:
                //   Toast.makeText(MainActivity.this, "Alphabates", Toast.LENGTH_SHORT).show();
                frag_id = Constants.CHANGE_PASSWORD;
                break;
            case R.id.c_pin:
                //  Toast.makeText(MainActivity.this, "Table", Toast.LENGTH_SHORT).show();
                frag_id = Constants.CHANGE_PIN;
                break;
//            case R.id.c_export:
//                //  Toast.makeText(MainActivity.this, "Table", Toast.LENGTH_SHORT).show();
//                frag_id = Constants.EXPORT_EXCEL;
//                break;
        }
        if (frag_id != 0)
            switchFragment(frag_id, null);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void switchFragment(int fragment_id, Bundle pBundle) {
        boolean addFragment = false;
        Fragment normFragment = null;
        FragmentManager fm = getSupportFragmentManager();

        String tag = "";
        String title = getString(R.string.app_name);

        // Default value for all fragments except home fragment
        homeFragment = false;

        switch (fragment_id) {
            case Constants.HOME_FRAGMENT:            // Home Fragment
                tag = "HOME";
                normFragment = fm.findFragmentByTag(tag);
                if (normFragment == null) {
                    normFragment = new All_Data_fragment();
                    addFragment = true;
                    Log.d("TAG","HOME FRAGEMNT");
                }
                homeFragment = true;
                break;
            case Constants.CHANGE_PASSWORD:
                tag="NUMBERS";
                normFragment=fm.findFragmentByTag(tag);
                if(normFragment==null)
                {
                    normFragment=new ChangePassword();
                    addFragment=true;
                }
                homeFragment=true;
                break;

            case Constants.CHANGE_PIN:
                tag="NUMBERS";
                normFragment=fm.findFragmentByTag(tag);
                if(normFragment==null)
                {
                    normFragment=new ChangeMyPin();
                    addFragment=true;
                }
                homeFragment=true;
                break;
//            case Constants.EXPORT_EXCEL:
////                tag="EXPORT";
////                normFragment=fm.findFragmentByTag(tag);
////                if(normFragment==null)
////                {
////                    normFragment=new ExportToExcel();
////                    addFragment=true;
////                }
////                homeFragment=true;
//                new ExportIntoExcel(getApplicationContext());
//                break;
            default:
                homeFragment=true;
        }
        FragmentTransaction ft = fm.beginTransaction();
        if (normFragment != null) {
            if (addFragment) {
                if (pBundle != null) {
                    normFragment.setArguments(pBundle);
                    Log.d("VT", "Adding bundle");
                }
                ft.add(R.id.am_full_container, normFragment, tag);
                Log.d("VT", "Adding Fragment: " + tag);
            } else {
                ft.replace(R.id.am_full_container, normFragment, tag);
                Log.d("VT", "Replacing Fragment: " + tag);
            }
            ft.commit();
        }
        if (!homeFragment)
            resetHomePage();

        // set the toolbar title to the fragment title
//        actionBar.setTitle(title);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void resetHomePage() {
        homeFragment = false;
        toggle.setDrawerIndicatorEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


}
