package com.example.lokesh.keepmemanaged;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    Spinner spinner1;
    Button button;
    String a, b;
    String y, z;
    EditText et1, et2;
    String[] courses = {"NONE", "B.Tech.", "M.Tech.", "MCA", "M.Sc.", "MSW"};
    ArrayList<String> years ;
    ArrayAdapter<String> arrayAdapter1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        years = new ArrayList<String>() ;
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        spinner1 = (Spinner) findViewById(R.id.spinner2);
        spinner = (Spinner) findViewById(R.id.spinner1);

        if (savedInstanceState != null) {
            a = savedInstanceState.getString(String.valueOf(et1));
            b = savedInstanceState.getString(String.valueOf(et2));
        }
        if (a != null) {
            et1.setText(a);
        }
        if (b != null) {
            et2.setText(b);
        }

        String s=getIntent().getStringExtra("v");
        button=(Button)findViewById(R.id.bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email,password;
                final String e,p;
                email=(EditText)findViewById(R.id.email);
                password=(EditText)findViewById(R.id.password);
                e=email.getText().toString();
                p=password.getText().toString();
                if(TextUtils.isEmpty(e) || TextUtils.isEmpty(p))
                {
                    Toast.makeText(Register.this,"Please Fill details",Toast.LENGTH_LONG).show();
                    return;
                }
                FirebaseAuth auth=FirebaseAuth.getInstance();
                auth.createUserWithEmailAndPassword(e,p)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful())
                                {
                                    Toast.makeText(Register.this,task.getException()+"",Toast.LENGTH_LONG).show();
                                    return;
                                }
                                else
                                {
                                    String username,college,year,cource,email,pass;
                                    int at,i;
                                    at=e.indexOf('@');
                                    username= et1.getText().toString();
                                    college=et2.getText().toString();
                                    year=z;
                                    cource=y;
                                    pass=p;
                                    String input="";
                                    email=e;
                                    for(i=0;i<e.length();i++)
                                    {
                                        if((email.charAt(i)>='a' && email.charAt(i)<='z')|| (email.charAt(i)>='A' && email.charAt(i)<='Z') || (email.charAt(i)>='0' && email.charAt(i)<='9')) {
                                            input += email.charAt(i);
                                        }
                                    }
                                    email=input;
                                    Toast.makeText(Register.this,"Registration Successfull",Toast.LENGTH_LONG).show();
                                    //Toast.makeText(Register.this,"Successfully Registered",Toast.LENGTH_LONG).show();
                                    FirebaseDatabase database=FirebaseDatabase.getInstance();
                                    DatabaseReference myref=database.getReference("Main");
                                    User userDetails=new User(username,college,year,cource,pass);
                                    myref.child(email).setValue(userDetails);
                                    //myref.push().setValue(userDetails);
                                    finish();
                                }
                            }
                        });
            }

        });
        spinner = (Spinner) findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, courses);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner1.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner1:
                y = courses[position];
                if(position==0)
                {
                    years.clear();
                    arrayAdapter1 = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, years);
                    arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner1.setAdapter(arrayAdapter1);
                    spinner1.setEnabled(false);
                    break;
                }
                spinner1.setEnabled(true);
                if (position == 2 || position == 4 || position == 5) {
                    years.clear();
                    years.add(0,"2nd Year");
                    years.add(0,"1st Year");
                } else if (position == 3) {
                    years.clear();
                    years.add(0, "3rd Year");
                    years.add(0, "2nd Year");
                    years.add(0, "1st Year");
                }
                else if (position == 1){
                    years.clear();
                    years.add(0,"4th Year");
                    years.add(0,"3rd Year");
                    years.add(0,"2nd Year");
                    years.add(0,"1st Year");
                }
                arrayAdapter1 = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, years);
                arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(arrayAdapter1);

                break;
            case R.id.spinner2:
                z = years.get(position);
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    public void back(View view) {
        Intent i=new Intent(Register.this,Login.class);
        startActivity(i);
        finish();
    }
}