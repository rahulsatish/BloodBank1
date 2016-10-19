package com.donars.srp.bloodbank;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.donars.srp.bloodbank.fetcher.Details;
import com.donars.srp.bloodbank.fetcher.Fetcher;
import com.donars.srp.bloodbank.model.BloodModel;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleApiClient.ConnectionCallbacks {

    private static GoogleMap mMap;
    PrimaryDrawerItem item1;
    PrimaryDrawerItem itemstats, itemsignout;
    static ArrayList<Marker> markerList;
    /* GPS Constant Permission */
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;

    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Iconify.with(new FontAwesomeModule());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        markerList = new ArrayList<>();
        item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_list).withTextColor(getResources().getColor(R.color.colorAccent)).withIcon(new IconDrawable(this, FontAwesomeIcons.fa_list));
        itemstats = new PrimaryDrawerItem().withIdentifier(2).withName("Your Stats").withTextColor(getResources().getColor(R.color.colorAccent)).withIcon(new IconDrawable(this, FontAwesomeIcons.fa_bar_chart));
        itemsignout = new PrimaryDrawerItem().withIdentifier(3).withName("Sign out").withTextColor(getResources().getColor(R.color.colorAccent)).withIcon(new IconDrawable(this, FontAwesomeIcons.fa_sign_out));
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName(Details.user.getName()).withEmail(Details.user.getUsername()).withIcon(getResources().getDrawable(R.drawable.profile3))
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
                        item1,
                        itemstats,
                        itemsignout
                )

                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            long identifier = drawerItem.getIdentifier();
                            switch ((int) identifier) {
                                case 1: {
                                    Intent intent = new Intent(MapsActivity.this, RequestBlood.class);
                                    startActivity(intent);
                                    break;
                                }
                                case 2: {
                                    Intent intent = new Intent(MapsActivity.this, UserStats.class);
                                    startActivity(intent);
                                    break;
                                }
                                case 3: {
                                    Intent intent = new Intent(MapsActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                    break;
                                }
                            }
                        }
                        return false;
                    }
                })
                .build();
        Fetcher fetcher = new Fetcher();
        fetcher.getData();
        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();
        }
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
        mMap.setOnInfoWindowClickListener(this);
        check_Permission();
        mMap.setMyLocationEnabled(true);
    }

    public static void addExtraMarkers() {
        /**Create  Markers List*/
        mMap.clear();
        int i = 0;
        for (BloodModel bloodModel : Fetcher.detailsList) {
            Marker marker;
            String inputString = bloodModel.getHop_name().trim();
            int MAX_CHAR = 25;
            int maxLength = (inputString.length() < MAX_CHAR) ? inputString.length() : MAX_CHAR;
            inputString = inputString.substring(0, maxLength) + "...";
            marker = mMap.addMarker(new MarkerOptions().position(new LatLng(
                    bloodModel.getLatitude(), bloodModel.getLongitude())).title(inputString)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
           // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(bloodModel.getLatitude(), bloodModel.getLongitude()),12));
            markerList.add(marker);
        }
        //  prevposition=0;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void check_Permission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSION_ACCESS_COARSE_LOCATION);
        }
    }
    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }
    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        int i = 0;
        for (Marker m : markerList) {
            if (m.getPosition().equals(marker.getPosition())) {
//                m.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.country));
                break;
            }
            i++;
        }
        //System.out.println("In info window click");
        Intent intent=new Intent(MapsActivity.this,BloodDonationActivity.class);
        intent.putExtra("position",i);
        startActivity(intent);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        check_Permission();
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            // Get latitude of the current location
            double latitude = mLastLocation.getLatitude();

            // Get longitude of the current location
            double longitude = mLastLocation.getLongitude();

            // Create a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);

            // Show the current location in Google Map
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            // Zoom in the Google Map
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
            mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!"));
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
