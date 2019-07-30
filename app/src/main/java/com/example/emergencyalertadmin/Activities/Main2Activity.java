package com.example.emergencyalertadmin.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.emergencyalertadmin.Fragment.AddCategoryFragment;
import com.example.emergencyalertadmin.Fragment.AddUserFragment;
import com.example.emergencyalertadmin.ChangeFragment;
import com.example.emergencyalertadmin.Fragment.PushNotificationFragment;
import com.example.emergencyalertadmin.Fragment.RemoveCategoryFragment;
import com.example.emergencyalertadmin.R;
import com.example.emergencyalertadmin.Fragment.RemoveUserFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth auth;
    FirebaseUser user;
     private RecyclerView mMainList;
     private FirebaseFirestore mFirestore;

     private TextView usernameTextView;
     private TextView usermailTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

       /* mMainList=findViewById(R.id.main_list);
        mFirestore=FirebaseFirestore.getInstance();
        mFirestore.collection("Notifications").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d("TAG", "error: " + e.getMessage());
                    for (DocumentChange doc : queryDocumentSnapshots.getDocuments()){
                        if(doc.getType()==DocumentChange.Type.ADDED){
                            String notification = doc.getDocument().getString("notification");
                            Log.d("TAG","Notification: "+ notification);
                        }


                    }

                }
            }
        });
*/



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user == null){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

        //giriş yapan kişinin adını ve mail adresini göster
        View navHeader = navigationView.getHeaderView(0);
        usernameTextView = navHeader.findViewById(R.id.nav_username);
        usermailTextView = navHeader.findViewById(R.id.nav_usermail);

        usernameTextView.setText(user.getDisplayName());
        usermailTextView.setText(user.getEmail());


        ChangeFragment changeFragment = new ChangeFragment(this);
        changeFragment.change(new PushNotificationFragment());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            createDialog_backpress();

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setCheckable(true);
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.push_notification ) {

            ChangeFragment changeFragment = new ChangeFragment(this);
            changeFragment.change(new PushNotificationFragment());
            
        } else if (id == R.id.addUser) {
            ChangeFragment changeFragment = new ChangeFragment(this);
            changeFragment.change(new AddUserFragment());
        } else if (id == R.id.remove_user) {
            ChangeFragment changeFragment = new ChangeFragment(this);
            changeFragment.change(new RemoveUserFragment());
        }else if (id == R.id.addCategory) {
            ChangeFragment changeFragment = new ChangeFragment(this);
            changeFragment.change(new AddCategoryFragment());
        } else if (id == R.id.removeCategory) {
            ChangeFragment changeFragment = new ChangeFragment(this);
            changeFragment.change(new RemoveCategoryFragment());
        }else if (id == R.id.exit) {
           createDialog();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    private void createDialog() {
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
        alertDlg.setMessage("Çıkış yapmak istediğinizden emin misiniz?");
        alertDlg.setCancelable(false);

        alertDlg.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Main2Activity.super.onBackPressed();
                logout();
            }
        });
        alertDlg.setNegativeButton("Iptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



            }
        });
        alertDlg.create().show();
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent= new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    private void createDialog_backpress() {
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
        alertDlg.setMessage("Uygulamadan çıkış yapmak istediğinizden emin misiniz?");
        alertDlg.setCancelable(false);

        alertDlg.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDlg.setNegativeButton("Iptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



            }
        });
        alertDlg.create().show();
    }
}
