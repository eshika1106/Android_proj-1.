package com.example.bullsappandroidproject;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    EditText fn , un, eiid , phNo, pwd;
    Button go;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        un = (EditText) findViewById(R.id.username12);
        fn = (EditText) findViewById(R.id.name12);
        eiid = (EditText) findViewById(R.id.email12);
        phNo = (EditText) findViewById(R.id.phoner12);
        pwd = (EditText) findViewById(R.id.password12);
        go = (Button) findViewById(R.id.button);
        DB = new DBHelper(this);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = un.getText().toString().trim();
                String pass = pwd.getText().toString().trim();
                String eid = eiid.getText().toString().trim();
                String phone = phNo.getText().toString().trim();
                String fname = fn.getText().toString().trim();

                if(user.equals("")||pass.equals(""))
                    Toast.makeText(SignUp.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    if(pass.equals(pass)){
                        Boolean checkUser = DB.checkUser(user);
                        if(checkUser==false){
                            Boolean insert = DB.insertData(user, pass, fname, eid, phone);
                            if(insert==true){
                                Toast.makeText(SignUp.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),Login.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(SignUp.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(SignUp.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignUp.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                } }
        });

    }
}
