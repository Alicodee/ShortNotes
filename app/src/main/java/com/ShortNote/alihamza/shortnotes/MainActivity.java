package com.ShortNote.alihamza.shortnotes;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ShortNote.alihamza.shortnotes.Data.NotesContract;
import com.ShortNote.alihamza.shortnotes.Data.NotesDbHelper;
import com.ShortNote.alihamza.shortnotes.Data.TestUtil;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity  implements NotesListAdapter.ForecastAdapterOnClickHandler,
        NavigationView.OnNavigationItemSelectedListener{
    private static final int WATER_REMINDER_PENDING_INTENT_ID = 100;
    private NotesListAdapter mAdapter;
    Toast mTost;
    ActionBarDrawerToggle ab;
    private InterstitialAd interstitialAd;

    SQLiteDatabase mDB ;
    Session session;
    Button mButton;
    int i;
 //   Cursor cursor;
    Toolbar t;
    DrawerLayout dl;
    NavigationView nv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t = (Toolbar) findViewById(R.id.tb);

       // dl = (DrawerLayout) findViewById(R.id.drawer_layout);
       // nv= (NavigationView) findViewById(R.id.navigation);
        setSupportActionBar(t);
       // ab =new ActionBarDrawerToggle(this,dl,t,R.string.open,R.string.close);
        //nv.setNavigationItemSelectedListener(this);
        //dl.addDrawerListener(ab);
       // ab.syncState();


        RecyclerView notelistRecyclerView;

        // Set local attributes to corresponding views
        notelistRecyclerView = (RecyclerView) this.findViewById(R.id.all_guests_list_view);

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        notelistRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        NotesDbHelper notelistDbHelper = new NotesDbHelper(this);
        mDB = notelistDbHelper.getWritableDatabase();


       // TestUtil.insertFakeData(mDB);
        Cursor cursor = getAllGuests();
        mAdapter = new NotesListAdapter(this,cursor,this);

        // Link the adapter to the RecyclerView
        notelistRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {


            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //do nothing, we only care about swiping
                return false;
            }


            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //get the id of the item being swiped
                long id = (long) viewHolder.itemView.getTag();
                //remove from DB
                removeGuest(id);
                //update the list
                mAdapter.swapCursor(getAllGuests());
            }

        }).attachToRecyclerView(notelistRecyclerView);

       AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

// Prepare the Interstitial Ad
        interstitialAd = new InterstitialAd(MainActivity.this);
// Insert the Ad Unit ID
        interstitialAd.setAdUnitId(getString(R.string.admob_interstitial_id));

        interstitialAd.loadAd(adRequest);
// Prepare an Interstitial Ad Listener
        interstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                // Call displayInterstitial() function
                displayInterstitial();
            }
        });
        session = new Session(this);
        if(session.isFirstTimeLaunch())
        {
            TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.button),"Click here to add your Short Notes")
                            .tintTarget(false)
                            .outerCircleColor(R.color.colorAccent),
                    new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                        @Override
                        public void onTargetClick(TapTargetView view) {
                            super.onTargetClick(view);      // This call is optional
                            addToWaitlist(view);
                        }
                    });
            Toast.makeText(MainActivity.this,"First launch",Toast.LENGTH_LONG);
            TestUtil.insertFakeData(mDB);

        }


    }
    public void displayInterstitial() {
// If Ads are loaded, show Interstitial else show nothing.
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }




    private Cursor getAllGuests(){
        return mDB.query(NotesContract.NotesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                NotesContract.NotesEntry.COLUMN_TIMESTAMP
        );
    }

    /**
     * This method is called when user clicks on the Add to waitlist button
     *
     * @param view The calling view (button)
     */
    public void addToWaitlist(View view) {
        Context context = MainActivity.this;

                /* This is the class that we want to start (and open) when the button is clicked. */
        Class destinationActivity = SecondActivity.class;


        Intent startSecondActivity  = new Intent(context,destinationActivity);
        startActivityForResult(startSecondActivity,1);


    }

    @Override
    public void onClick(String name, String defination) {


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    public interface Constants {
        String LOG = "com.example.android";
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String name = data.getStringExtra("Name");
                String def = data.getStringExtra("Defination");

                addNewGuest(name,def);
                mAdapter.swapCursor(getAllGuests());

                //              Context context = MainActivity.this;

//                NotificationCompat.Builder notifcation = new NotificationCompat.Builder(context)
//                        .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle("Alert")
//                        .setContentText(name +" Added to list")
//                        .setContentIntent(contentIntent(context))
//                        .setAutoCancel(true);
//                notifcation.setPriority(Notification.PRIORITY_HIGH);
//
//                NotificationManager manger = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                manger.notify(12, notifcation.build());
                Toast.makeText(this, name +" added", Toast.LENGTH_SHORT).show();

            }
        }
    }
    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(
                context,
                WATER_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private long addNewGuest(String name, String partySize) {
        ContentValues cv = new ContentValues();
        cv.put(NotesContract.NotesEntry.COLUMN_TTILE_NAME, name);
        cv.put(NotesContract.NotesEntry.COLUMN__DEFINATION, partySize);
        cv.put(NotesContract.NotesEntry.count,1);
        return mDB.insert(NotesContract.NotesEntry.TABLE_NAME, null, cv);
    }

    private boolean removeGuest(long id) {
       return mDB.delete(NotesContract.NotesEntry.TABLE_NAME, NotesContract.NotesEntry._ID + "=" + id, null) > 0;
    }

}
