package com.example.lokesh.keepmemanaged;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Assignments extends AppCompatActivity {
    ArrayList<String> assignmentS=new ArrayList<>();
    ArrayList<String> assignmentD=new ArrayList<>();
    ArrayList<String> assignmentT=new ArrayList<>();
    int j=0,i;

    final Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String title=getIntent().getExtras().getString("title");
        setContentView(R.layout.activity_assignments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        final String email;
        email=getIntent().getExtras().getString("key");

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myref=database.getReference("Main").child(email);
        myref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(context,"Assignments mail "+email,Toast.LENGTH_LONG).show();
                    if(dataSnapshot.hasChild(title))
                    {
                        for(DataSnapshot postsnapshot:dataSnapshot.child(title).getChildren())
                        {
                            assign assignme=postsnapshot.getValue(assign.class);
                            assignmentS.add(0,assignme.subject);
                            assignmentD.add(0,assignme.details);
                            assignmentT.add(0,assignme.submission);
                            j++;
                        }
                        ListView assign = (ListView) findViewById(R.id.assign);
                        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
                        for(i=0;i<j;i++)
                        {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("subject", assignmentS.get(i));
                            hashMap.put("details",assignmentD.get(i));
                            hashMap.put("date",assignmentT.get(i));
                            arrayList.add(hashMap);
                        }
                        String from[]={"subject","details","date"};
                        int to[]={R.id.tv1,R.id.tv2,R.id.iv1};
                        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), arrayList, R.layout.activty_single_item, from, to);
                        assign.setAdapter(simpleAdapter);
                    }
                    else
                    {
                        Toast.makeText(context,"No assignments till now",Toast.LENGTH_LONG).show();
                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context,"Error "+databaseError,Toast.LENGTH_LONG).show();


            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li=LayoutInflater.from(context);
                View promptView=li.inflate(R.layout.prompts,null);
                AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(context);

                alertDialogBuilder.setView(promptView);
                final EditText sub=(EditText)promptView.findViewById(R.id.edit1);
                final EditText det=(EditText)promptView.findViewById(R.id.edit2);
                final EditText subm=(EditText)promptView.findViewById(R.id.edit3);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String e1,e2,e3;
                                        e1=sub.getText().toString();
                                        e2=det.getText().toString();
                                        e3=subm.getText().toString();

                                        assign newassign=new assign(e1,e2,e3);
                                        FirebaseDatabase database=FirebaseDatabase.getInstance();
                                        DatabaseReference myref=database.getReference("Main").child(email);
                                        myref.child(title).push().setValue(newassign);
                                        Toast.makeText(context,"Saved Successfully",Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog=alertDialogBuilder.create();
                alertDialog.show();

            }
        });
    }

}
