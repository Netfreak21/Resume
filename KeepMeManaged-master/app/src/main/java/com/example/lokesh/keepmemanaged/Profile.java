package com.example.lokesh.keepmemanaged;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    String username,cource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final TextView x2,x4,x6,x8;
        x2=(TextView)findViewById(R.id.x2);
        x4=(TextView)findViewById(R.id.x4);
        x6=(TextView)findViewById(R.id.x6);
        x8=(TextView)findViewById(R.id.x8);
        String email=getIntent().getExtras().getString("key");
        //FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myref=database.getReference("Main");
        //Toast.makeText(Profile.this,email+"this",Toast.LENGTH_LONG).show();
        myref.child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User curr=dataSnapshot.getValue(User.class);
                username=curr.username;
                cource=curr.cource;
                x2.setText(curr.username);
                x4.setText(curr.college);
                x6.setText(curr.year);
                x8.setText(curr.cource);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Profile.this,"Failed to read value",Toast.LENGTH_LONG).show();
            }
        });
    }

//    public void timeTable(View view) {
//        Intent intent=new Intent(Profile.this,DaysList.class);
//        intent.putExtra("key",getIntent().getExtras().getString("key"));
//        intent.putExtra("username",username);
//        intent.putExtra("cource",cource);
//        startActivity(intent);
//        finish();
//    }
}
