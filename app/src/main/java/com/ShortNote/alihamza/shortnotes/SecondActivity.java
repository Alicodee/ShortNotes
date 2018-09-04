package com.ShortNote.alihamza.shortnotes;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ShortNote.alihamza.shortnotes.Data.NotesDbHelper;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;

public class SecondActivity extends AppCompatActivity {
    TextInputLayout textInputLayout1;
    TextInputLayout  textInputLayout2;
    int i=0;
    EditText mName;
    EditText mDefination;
    Button mButton;
    SQLiteDatabase mDB;
    Session session;
    Toolbar tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textInputLayout1 = (TextInputLayout) findViewById(R.id.textinput1);
        textInputLayout2 =(TextInputLayout) findViewById(R.id.textinput2);
        mName = (EditText) findViewById(R.id.name_text_edit);
        mDefination = (EditText) findViewById(R.id.defination_text_edit);
        mButton = (Button) findViewById(R.id.button1);
        tb = (Toolbar) findViewById(R.id.tb);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        mButton.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           onAddButtonClicked();
                                       }
                                   }
        );




        NotesDbHelper helper = new NotesDbHelper(this);
        mDB = helper.getWritableDatabase();

       session = new Session(this);
        if(session.isFirstTimeLaunch())
        {
            new TapTargetSequence(this)
                    .targets(
                            TapTarget.forView(findViewById(R.id.name_text_edit), "Enter a Title"),
                            TapTarget.forView(findViewById(R.id.defination_text_edit), "Enter Description of title"),
                            TapTarget.forView(findViewById(R.id.button1),"Click here to Save your Short Notes")
                                    .tintTarget(false)
                                    .outerCircleColor(R.color.colorAccent)).start();
            session.setFirstTimeLaunch(false);
        }




    }


    public void onAddButtonClicked(){
        // String[] strings = new String[2];
        //strings[0] = mName.getText().toString();
        //strings[1] = mDefination.getText().toString();


        if(mName.length() ==0 || mDefination.length() ==0){
            if(mName.length() == 0){
                textInputLayout1.setError("This field needs to be filled");
            }
            if(mDefination.length() == 0){
                textInputLayout2.setError("This field needs to be filled");
            }
        }
       else {
            Intent intent = new Intent();
            intent.putExtra("Name" , mName.getText().toString());
            intent.putExtra("Defination" , mDefination.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
