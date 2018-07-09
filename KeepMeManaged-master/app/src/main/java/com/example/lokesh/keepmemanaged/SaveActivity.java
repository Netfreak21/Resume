package com.example.lokesh.keepmemanaged;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SaveActivity extends AppCompatActivity {
    Button bt;
    String p,q,s;
    int i=0,t,time;
    int[] subId={R.id.s1,R.id.s2,R.id.s3,R.id.s4,R.id.s5,R.id.s6,R.id.s7,R.id.s8};
    int[] classId={R.id.c1,R.id.c2,R.id.c3,R.id.c4,R.id.c5,R.id.c6,R.id.c7,R.id.c8};
    EditText e1,e2,e3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        getSupportActionBar().setTitle("FILL TIME TABLE");
        getSupportActionBar().setElevation(8);
        p=getIntent().getExtras().getString("day");
        bt=(Button)findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
                int l=0;
                //ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
                ArrayList<HashMap<String,String>>arrayList=new ArrayList<>();
                for (i = 0; i < 8; i++) {
                    if (i==0)
                        time=8;
                    else if (i==1)
                        time=9;
                    else if (i==2)
                        time=10;
                    else if (i==3)
                        time=11;
                    else if (i==4)
                        time=12;
                    else if (i==5)
                        time=2;
                    else if (i==6)
                        time=3;
                    else
                        time=4;
                    e1 = (EditText) findViewById(subId[i]);
                    e2 = (EditText) findViewById(classId[i]);
                    HashMap<String, String> hashMap = new HashMap<>();
                    String p = e1.getText().toString();
                    String q=e2.getText().toString();
                    if (p.equals(""))
                        p="FREE";
                    if (q.equals(""))
                        q="FREE";
                    if (!p.equals("FREE")&&!q.equals("FREE")&&l==0)
                    {
                        if (time>=1&&time<=4)
                            t=time+12;
                        else
                            t=time;
                        l=1;
                    }
                    hashMap.put("place",p);
                    hashMap.put("venue",q);
                    arrayList.add(hashMap);
                }
                DaysTable arr=new DaysTable(arrayList);
                String email=getIntent().getExtras().getString("key");
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference myref=database.getReference("Main");
                myref.child(email).child(p).setValue(arr);

                Intent intentService=new Intent(SaveActivity.this,newservice.class) ;
                intentService.putExtra("key",email);
                intentService.putExtra("day",p);
                ArrayList<String> ans=new ArrayList<>();
                Iterator<HashMap<String,String>> iterator=arrayList.iterator();
                while (iterator.hasNext())
                {
                    HashMap<String,String> h=iterator.next();
                    ans.add(h.get("place"));
                    ans.add(h.get("venue"));
                }
                intentService.putStringArrayListExtra("array",ans);
                startService(intentService);
                Intent intent=new Intent(SaveActivity.this,DaysList.class);
                intent.putExtra("key",email);
                startActivity(intent);
                finish();
            }
        });
    }
}
