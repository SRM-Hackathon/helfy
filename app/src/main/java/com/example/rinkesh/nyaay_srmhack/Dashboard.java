package com.example.rinkesh.nyaay_srmhack;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.example.rinkesh.nyaay_srmhack.R.id.error;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , View.OnClickListener {

 /*   private Button buttonChoose;
    private Button buttonUpload;

    private EditText editText;

    public static final String UPLOAD_URL = "";

    private int PICK_PDF_REQUEST = 1;

    private static final int STORAGE_CODE_PERMISSION = 101;

    private Uri filePath;  */

    String date,place,cat,type,state;
    EditText edate,eplace,estate;
    AutoCompleteTextView etype,ecat;
    Boolean check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        edate = (EditText) findViewById(R.id.whenedit);
        eplace = (EditText) findViewById(R.id.whenedit);
        ecat = (AutoCompleteTextView) findViewById(R.id.category);
        etype = (AutoCompleteTextView) findViewById(R.id.typetext);
        estate = (EditText) findViewById(R.id.state);

        String[] types = {"Criminal","Civil","Commercial"};
        String[] cato = {"Rape","Murder","Frogery","Chain Snachhing","Robbery","Rental issues","Assult"};

        ArrayAdapter<String> adapterstatetype = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice,types);
        AutoCompleteTextView acTextViewstatetype = (AutoCompleteTextView) findViewById(R.id.typetext);
        acTextViewstatetype.setThreshold(1);
        acTextViewstatetype.setAdapter(adapterstatetype);

        ArrayAdapter<String> adapterstatecat = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice,cato);
        AutoCompleteTextView acTextViewstatecat = (AutoCompleteTextView) findViewById(R.id.category);
        acTextViewstatecat.setThreshold(1);
        acTextViewstatecat.setAdapter(adapterstatecat);

        requestStoragePermission();

      /*  buttonChoose = (Button) findViewById(R.id.button);
        buttonUpload = (Button) findViewById(R.id.button);

        editText = (EditText) findViewById(R.id.editcourt);

        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);  */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this, Bot.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setdata() {

        date = edate.getText().toString().trim();
        place = eplace.getText().toString().trim();
        state = estate.getText().toString().trim();
        type = etype.getEditableText().toString().trim();
        cat = ecat.getEditableText().toString().trim();

    }

    public boolean emptycheck(){

        if (date.equals(null) || TextUtils.isEmpty(date)) {

            Toast.makeText(this,"Enter Date to continue",Toast.LENGTH_SHORT).show();
            return false;

        } else if (place.equals(null) || TextUtils.isEmpty(place)) {

            Toast.makeText(this,"Enter Place to continue",Toast.LENGTH_SHORT).show();
            return false;

        } else if (cat.equals(null) || TextUtils.isEmpty(cat)) {

            Toast.makeText(this,"Choose Category to continue",Toast.LENGTH_SHORT).show();
            return false;

        } else if (type.equals(null) || TextUtils.isEmpty(type)) {

            Toast.makeText(this,"Choose Type to continue",Toast.LENGTH_SHORT).show();
            return false;

        } else if (state.equals(null) || TextUtils.isEmpty(state)) {

            Toast.makeText(this,"Enter Sate to continue",Toast.LENGTH_SHORT).show();
            return false;

        }  else {

            return true;

        }

    }

    private void requestStoragePermission() {
    }

    public void confirmupload(View view) {

        setdata();

        check = emptycheck();

        if (check==true){

            request();

        }

    }

    public void request() {

        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://www.sastimasti.me/nyaay/insertdata.php?data="+date+"&place="+place+"&cat="+cat+"&type="+type+"&state="+state;
        String goodurl = url.replace(" ", "%20");
        StringRequest getRequest = new StringRequest(Request.Method.GET, goodurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    Intent i = new Intent(Dashboard.this,Mycases.class);
                    startActivity(i);
                    finish();

            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error","error"+error.toString());
                    }
                }

        );

        queue.add(getRequest);

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
        getMenuInflater().inflate(R.menu.dashboard, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.dashboard) {
            // Handle the camera action
        } else if (id == R.id.status) {

            Intent statusintent = new Intent(this, Status.class);
            startActivity(statusintent);

        } else if (id == R.id.mycase) {

            Intent statusintent = new Intent(this, Mycases.class);
            startActivity(statusintent);

        } else if (id == R.id.causelist) {

            Intent causelistIntent = new Intent(this, CauseList.class);
            startActivity(causelistIntent);

        } else if (id == R.id.moreinfo) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

   /* public void uploadMultiPart() {

        String name = editText.getText().toString().trim();

        String path = FilePath.getPath(this, filePath);

        if (path == null) {

            Toast.makeText(this, "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show();
        } else {
            try {
                String uploadId = UUID.randomUUID().toString();

                new MultiPartUploadRequest(this, uploadId, UPLOAD_URL)
                        .addFileToUpload(path, "pdf")
                        .addParameter("name", name)
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showFileChooser() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
        }
    }

    private void requestStoragePermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_PERMISSION_CODE) {


        }
        if (requestCode == STORAGE_PERMISSION_CODE) {


            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {

                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    */


    @Override
    public void onClick(View view) {

    }




}