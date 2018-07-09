package com.example.lokesh.keepmemanaged;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mindorks.placeholderview.PlaceHolderView;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class TimeTable extends AppCompatActivity {

    ListView listView;
    String subject[]={"","","","","","","",""};
    String classes[]={"","","","","","","",""};
    int time[]={8,9,10,11,12,2,3,4};
    String str,p,q,key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        p = getIntent().getExtras().getString("day");
        key=getIntent().getExtras().getString("key");
//        android.app.ActionBar ab=getActionBar();
        ActionBar ab=getSupportActionBar();
        ab.setTitle(p);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myref=database.getReference("Main").child(key).child(p);
        //Toast.makeText(TimeTable.this,"Data Base worked",Toast.LENGTH_LONG).show();
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DaysTable table=dataSnapshot.getValue(DaysTable.class);
                //Toast.makeText(TimeTable.this,"This one is working",Toast.LENGTH_SHORT).show();
                //ArrayList<HashMap<String,String>>arr=new ArrayList<>();
                ArrayList<HashMap<String,String>>arr=table.arr;
                Iterator<HashMap<String,String>>itr=arr.iterator();
                int ind=0;
                while(itr.hasNext())
                {
                    HashMap<String,String>hm=itr.next();
//                    String key= String.valueOf(hm.keySet());
//                    String val=String.valueOf(hm.values());
                    String k,val;
                    k=hm.get("place");
                    val=hm.get("venue");
                    subject[ind]=k;
                    classes[ind]=val;
                    ind++;
                    //Toast.makeText(TimeTable.this,k+val,Toast.LENGTH_SHORT).show();
                }
                // DISPLAYING TIME TABLE BY LIST VIEW
                listView = (ListView) findViewById(R.id.lv);
                ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
                for (int i = 0; i < 8; i++) {
                    str = (time[i] + 1) + "";
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("name", subject[i]);
                    hashMap.put("image", classes[i]);
                    hashMap.put("time", time[i] + "-" + str);
                    arrayList.add(hashMap);
                }
                String[] from = {"time", "name", "image"};
                int[] to = {R.id.iv1, R.id.tv1, R.id.tv2};
                SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), arrayList, R.layout.activty_single_item, from, to);
                listView.setAdapter(simpleAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m=getMenuInflater();
        m.inflate(R.menu.menu_create,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_favorite)
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TimeTable.this);
            alertDialogBuilder.setMessage("Are you sure, You want to delete");
            alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int id) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    Query applesQuery = ref.child("Main").child(key);
                    final ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
                    HashMap<String,String> has=new HashMap<>();
                    for(int i=0;i<8;i++)
                    {
                        has.put("place","");
                        has.put("venue","");
                        arrayList.add(has);
                    }
                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postsnap:dataSnapshot.getChildren()) {
                                String x = postsnap.getKey();
                                if (x.equals(p)&&postsnap.exists())
                                {
                                    for (DataSnapshot ch:postsnap.getChildren())
                                        ch.getRef().setValue(arrayList);

                                }
                            }
//                            Intent inte=new Intent(TimeTable.this,DaysList.class);
//                            inte.putExtra("key",key);
//                            startActivity(inte);
                            finish();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            AlertDialog alertDialog=alertDialogBuilder.create();
            alertDialog.show();
        }
        else if(item.getItemId()==R.id.share)
        {
            FirebaseDatabase database=FirebaseDatabase.getInstance();
            DatabaseReference myref=database.getReference("Main").child(key).child(p);
            myref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    DaysTable table=dataSnapshot.getValue(DaysTable.class);
                    //Toast.makeText(TimeTable.this,"This one is working",Toast.LENGTH_SHORT).show();
                    //ArrayList<HashMap<String,String>>arr=new ArrayList<>();
                    ArrayList<HashMap<String,String>>arr=table.arr;
                    Iterator<HashMap<String,String>>itr=arr.iterator();
                    int ind=0;
                    String share=p+"\n";
                    while(itr.hasNext())
                    {
                        HashMap<String,String>hm=itr.next();
//                    String key= String.valueOf(hm.keySet());
//                    String val=String.valueOf(hm.values());
                        String k,val,t;
                        t=time[ind]+"";
                        if(time[ind]==12)
                            t=t+"-1";
                        else
                            t=t+"-"+(time[ind]+1+"");
                        k=hm.get("place");
                        val=hm.get("venue");
                        share=share+t+"\t\t"+k+"\t\t"+val+"\n";
                        ind++;
                        //Toast.makeText(TimeTable.this,k+val,Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(TimeTable.this,share,Toast.LENGTH_LONG).show();
                    Intent sendIntent=new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,share);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        return true;
    }
}


