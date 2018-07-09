package com.example.lokesh.keepmemanaged;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    String e,u,c;
    final Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Button login=(Button)findViewById(R.id.login);
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
//        Toast.makeText(Login.this,firebaseUser.getEmail()+"",Toast.LENGTH_LONG).show();
        if (firebaseUser!=null)
        {
            e=firebaseUser.getEmail();
            Intent intent=new Intent(Login.this,DaysList.class);
            String input="",emai;
            int i;
            emai=e;
//            for(i=0;i<e.length();i++)
//            {
//                if(emai.charAt(i)>='a' && emai.charAt(i)<='z') {
//                    input += emai.charAt(i);
//                }
//            }
            for(i=0;i<e.length();i++)
            {
                if((emai.charAt(i)>='a' && emai.charAt(i)<='z')|| (emai.charAt(i)>='A' && emai.charAt(i)<='Z') || (emai.charAt(i)>='0' && emai.charAt(i)<='9')) {
                    input += emai.charAt(i);
                }
            }
            emai=input;
            final String[] user = {""};
            final String[] cource = {""};
            FirebaseDatabase database=FirebaseDatabase.getInstance();
            DatabaseReference myref=database.getReference().child("Main").child(emai);
            myref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user1=dataSnapshot.getValue(User.class);
                    u =user1.username;
                    c =user1.cource;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            intent.putExtra("key",emai);
            intent.putExtra("username", u);
            intent.putExtra("cource", c);
            //Toast.makeText(Login.this,u+" "+c,Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText email=(EditText)findViewById(R.id.email);
                final String e=email.getText().toString();
                EditText password=(EditText)findViewById(R.id.password);
                String p=password.getText().toString();
                if(TextUtils.isEmpty(e) || TextUtils.isEmpty(p))
                {
                    Toast.makeText(Login.this,"Enter credentials please",Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseAuth auth=FirebaseAuth.getInstance();
                auth.signInWithEmailAndPassword(e,p)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Login.this,task.getException()+"", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                else
                                {
                                    //Toast.makeText(Login.this,"Logging in",Toast.LENGTH_LONG).show();
                                    String input="",email;
                                    int i;
                                    email=e;
                                    for(i=0;i<e.length();i++)
                                    {
                                        if(email.charAt(i)>='a' && email.charAt(i)<='z') {
                                            input += email.charAt(i);
                                        }
                                    }
                                    email=input;
                                    //Toast.makeText(Login.this,email,Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(Login.this,DaysList.class);
                                    intent.putExtra("key",email);
                                    final String[] user = {""};
                                    final String[] cource = {""};
                                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                                    DatabaseReference myref=database.getReference().child("Main").child(email);
                                    myref.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            User user1=dataSnapshot.getValue(User.class);
                                            u=user1.username;
                                            c=user1.cource;
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    intent.putExtra("username",u);
                                    intent.putExtra("cource",c);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    public void register(View view) {
        Intent i=new Intent(Login.this,Register.class);
        startActivity(i);
    }

    public void guest(View view) {
    }
}
