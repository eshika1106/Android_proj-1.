package com.example.bullsappandroidproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static com.example.bullsappandroidproject.Login.ISLOGGEDIN;
import static com.example.bullsappandroidproject.Login.SHARED_PREFS;
import static com.example.bullsappandroidproject.Login.USERNAME;

public class UserProfile extends AppCompatActivity {


    private ImageView mimageview;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    Button btlocation, update, logout;
    TextView textview1, ta, ti;
    EditText tn, te, tp;
    FusedLocationProviderClient fusedLocationProviderClient;
    User curr_user;
    DBHelper db;
    String curr_name, curr_username, curr_id, curr_amount, curr_email, curr_phone;
    TextInputEditText n1;
    TextView insurance1;
    Button binsurance1;

    private String name[] = {"LIABILITY COVERAGE", "NEW CAR REPLACEMENT COVERAGE", "COMPREHENSIVE COVERAGE", "COLLISION COVERAGE", "MEDICAL PAYMENTS COVERAGE", "TOWING AND LABOR COST COVERAGE"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mimageview = findViewById(R.id.profile_image);
        btlocation = findViewById(R.id.blocation);
        update = findViewById(R.id.btn_update);
        textview1 = findViewById(R.id.location);
        tn = findViewById(R.id.tv_name);
        te = findViewById(R.id.tv_email);
        tp = findViewById(R.id.tv_phone);
        ta = findViewById(R.id.payment_label);
        ti = findViewById(R.id.amount12);
        logout = findViewById(R.id.log);


        loadData();
        db = new DBHelper(this);
        loadData();
        curr_user = new User();
        curr_user = db.getUser(curr_username);
        curr_name = curr_user.getName();
        curr_amount = curr_user.getAmount();
        curr_phone = curr_user.getPhone();
        curr_email = curr_user.getEmail();
        curr_amount = curr_user.getAmount();
        curr_id = String.valueOf(curr_user.getId());
        set_values();


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        btlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (ActivityCompat.checkSelfPermission(UserProfile.this
//                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                    getLocation();
//
//                } else {
//                    ActivityCompat.requestPermissions(UserProfile.this
//                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
//                }
                List<Double> latLng = getLocationV2();
                Log.d("lul", latLng.toString());
                textview1.setText(latLng.toString());
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatevalues();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_out();
            }
        });
    }

    private void log_out() {
        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ISLOGGEDIN, "false");
        editor.putString(USERNAME, "email");
        editor.commit();
        startActivity(new Intent(this, Login.class));

    }

    private void updatevalues() {
        String nm, eml, ph;
        nm = tn.getText().toString().trim();
        eml = te.getText().toString().trim();
        ph = tp.getText().toString().trim();

        curr_user.setName(nm);
        curr_user.setPhone(ph);
        curr_user.setEmail(eml);
        try {
            db.updateUser(curr_user);
            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, UserProfile.class));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("OOPS Password change", e.toString());
        }


    }

    private void set_values() {
        tn.setText(curr_name);
        te.setText(curr_email);
        tp.setText(curr_phone);
        ta.setText(curr_amount);
        ti.setText(curr_id);
    }

    private List<Double> getLocationV2() {
        GpsTracker gpsTracker = new GpsTracker(this);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            List<Double> latLng = new ArrayList<>();
            latLng.add(new Double(latitude));
            latLng.add(new Double(longitude));
            return latLng;
        } else {
            gpsTracker.showSettingsAlert();
            return null;
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(UserProfile.this,
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );

                        textview1.setText(Html.fromHtml(
                                "<font color= '#6200EE'><b>Locality :</b><br></font>"
                                        + addresses.get(0).getLocality()
                        ));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void takePicture(View view) {
        Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (imageTakeIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mimageview.setImageBitmap(imageBitmap);
        }
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        curr_username = sharedPreferences.getString(USERNAME, "null");
        //   Toast.makeText(this, curr_email, Toast.LENGTH_SHORT).show();

    }


    public void generate(View view) {
        Random rand = new Random();
        int number = rand.nextInt(8000) + 1;
        TextView myText = (TextView) findViewById(R.id.payment_label);
        String myString = String.valueOf(number);
        myText.setText(myString);
    }

    public void names(View view) {
        insurance1 = (TextView) findViewById(R.id.insurance);
        binsurance1 = (Button) findViewById(R.id.binsurance);
        binsurance1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int num = random.nextInt(name.length);
                insurance1.setText((name[num]));

            }
        });
    }
}