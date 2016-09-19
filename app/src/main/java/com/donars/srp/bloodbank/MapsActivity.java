package com.donars.srp.bloodbank;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.donars.srp.bloodbank.fetcher.Fetcher;
import com.donars.srp.bloodbank.model.BloodModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    PrimaryDrawerItem item1;
    ArrayList<Marker>markerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        markerList= new ArrayList<>();
        item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_list).withTextColor(getResources().getColor(R.color.colorAccent));
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.cast_album_art_placeholder)
                .addProfiles(
                        new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(getResources().getDrawable(R.drawable.cast_album_art_placeholder))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
        //item2 = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_settings);
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item1
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            long identifier = drawerItem.getIdentifier();
                            switch ((int) identifier) {
                                case 1: {
                                    Intent intent=new Intent(MapsActivity.this,RequestBlood.class);
                                    startActivity(intent);
                                    break;
                                }
                            }
                        }
                        return false;
                    }})
                .build();
        Fetcher fetcher=new Fetcher();
        fetcher.getData();
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        addExtraMarkers();

    }
    public void addExtraMarkers() {
        /**Create  Markers List*/
        mMap.clear();
        int i=0;
        for (BloodModel bloodModel : Fetcher.detailsList) {
            Marker marker;
            String inputString=bloodModel.getHop_name().trim();
            int MAX_CHAR=10;
            int maxLength = (inputString.length() < MAX_CHAR)?inputString.length():MAX_CHAR;
            inputString = inputString.substring(0, maxLength)+"...";
            marker = mMap.addMarker(new MarkerOptions().position(new LatLng(
                    bloodModel.getLatitude(),bloodModel.getLongitude())).title(inputString)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

            markerList.add(marker);
        }
        //  prevposition=0;
    }
}
