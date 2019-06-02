package com.schelas.schelasvans.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.schelas.schelasvans.R;
import com.schelas.schelasvans.controller.About;
import com.schelas.schelasvans.controller.AlocaPass;
import com.schelas.schelasvans.controller.AlocaVeic;
import com.schelas.schelasvans.controller.ListDestino;
import com.schelas.schelasvans.controller.ListPassageiro;
import com.schelas.schelasvans.controller.ListRelatorio;
import com.schelas.schelasvans.controller.ListVeiculo;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.destool);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        if (id == R.id.action_about) {
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_rotas) {

        } else if (id == R.id.nav_relatorios) {
            Intent intent = new Intent(this, ListRelatorio.class);
            startActivity(intent);
        } else if (id == R.id.nav_passageiros) {
            Intent intent = new Intent(this, ListPassageiro.class);
            startActivity(intent);
        } else if (id == R.id.nav_veiculos) {
            Intent intent = new Intent(this, ListVeiculo.class);
            startActivity(intent);
        } else if (id == R.id.nav_dest) {
            Intent intent = new Intent(this, ListDestino.class);
            startActivity(intent);
        } else if (id == R.id.nav_passVeic) {
            Intent intent = new Intent(this, AlocaPass.class);
            startActivity(intent);
        } else if (id == R.id.nav_veicDest) {
            Intent intent = new Intent(this, AlocaVeic.class);
            startActivity(intent);
        } else if (id == R.id.nav_about){
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
