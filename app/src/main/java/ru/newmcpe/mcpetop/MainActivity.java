package ru.newmcpe.mcpetop;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static FragmentManager managera;

    public static void changeFragment(Fragment toWhatChange) {
        FragmentTransaction replace = managera.beginTransaction();
        replace.remove(new MainFragment());
        replace.replace(R.id.containermain, toWhatChange);
        replace.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction replace = manager.beginTransaction();
        MainFragment mainFragment = new MainFragment();
        replace.add(R.id.containermain, mainFragment);
        replace.commit();
        managera = getFragmentManager();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            changeFragment(new MainFragment());
            final ActionBar actionBar = this.getSupportActionBar();
            actionBar.setTitle("MCPE Shop");
            actionBar.setSubtitle("");
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
        if (id == R.id.exit) {
            MainActivity.this.finishAffinity();
        }
        if (id == R.id.nav_send) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("http://vk.com/newmcpead")); //cсылка
            startActivity(intent);//старт
        }
        if (id == R.id.nav_share) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("http://vk.com/mcpe_top")); //cсылка
            startActivity(intent);//старт
        }


        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_plugins) {
            final ActionBar actionBar = this.getSupportActionBar();
            actionBar.setTitle("Плагины");
            actionBar.setSubtitle("");
            Fragment pluginsfragment = new PluginsFragment();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction replace = manager.beginTransaction();
            replace.remove(new MainFragment());
            replace.replace(R.id.containermain, pluginsfragment);
            replace.commit();
        } else if (id == R.id.nav_mods) {
            final ActionBar actionBar = this.getSupportActionBar();
            actionBar.setTitle("Моды");
            actionBar.setSubtitle("");
            Fragment pluginsfragment = new ModsFragment();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction replace = manager.beginTransaction();
            replace.remove(new MainFragment());
            replace.replace(R.id.containermain, pluginsfragment);
            replace.commit();
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("http://vk.com/mcpe_top")); //cсылка
            startActivity(intent);//старт
        } else if (id == R.id.nav_send) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse("http://vk.com/newmcpead")); //cсылка
            startActivity(intent);//старт
        } else if (id == R.id.addplugin) {
            changeFragment(new AddPluginFragment());
            final ActionBar actionBar = this.getSupportActionBar();
            actionBar.setTitle("Добавление плагина");
            actionBar.setSubtitle("");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
