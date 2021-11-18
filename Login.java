package com.example.helping_hand.Login;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helping_hand.HomeScreen;
import com.example.helping_hand.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    LoginDatabase db;
    Button login;
    EditText name, pass, conpass;
    TextView tvsignup, logininstead;
    int failedcounter = 5, signupconter = 1;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        db = new LoginDatabase(this);
        login = (Button) findViewById(R.id.BLogin);
        name = (EditText) findViewById(R.id.ETUsername);
        pass = (EditText) findViewById(R.id.ETPassword);
        conpass = (EditText) findViewById(R.id.ETConfirmPassword);
        tvsignup = (TextView) findViewById(R.id.SignUp);
        logininstead = (TextView) findViewById(R.id.TVlogininstead);

        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conpass.setVisibility(View.VISIBLE);
                signup();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInExistingUser(name.getText().toString(), pass.getText().toString());
//                check(name.getText().toString(), pass.getText().toString());
            }
        });
        logininstead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset(0);
            }
        });
    }

    private void signup() {
        reset(1);

        if (signupconter == 1) {
            name.getText().clear();
            pass.getText().clear();
            signupconter++;
        }

        String username = name.getText().toString();
        String password = pass.getText().toString();
        String confirmpassword = conpass.getText().toString();

        if (username.equals("") || password.equals("") || confirmpassword.equals("")) {
            Toast.makeText(getApplicationContext(), "please fill all the fields", Toast.LENGTH_SHORT).show();
        } else {
            if (password.equals(confirmpassword)) {
                createNewUser(username, password);

            } else {
                Toast.makeText(getApplicationContext(), "Password Mismatch", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createNewUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(getApplicationContext(), "Sign Up Successfull", Toast.LENGTH_SHORT).show();
                    reset(0);

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(Login.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signInExistingUser(String email, String password) {

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please Fill All the fields", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(this, "Username Or Password Wrong", Toast.LENGTH_SHORT).show();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(Login.this, "Sign success.", Toast.LENGTH_SHORT).show();

                                Intent intentHome = new Intent(Login.this, HomeScreen.class);
                                startActivity(intentHome);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(Login.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                            }
                        }
                    });
        }
    }


    void reset(int a) {
        //0 = reset to login 1 = set to signup
        if (a == 1) {
            tvsignup.setTextSize(30);
            login.setVisibility(View.GONE);
            login.setEnabled(false);
            tvsignup.setBackgroundResource(R.drawable.rounded_edittext);
            name.setHint("Enter New Email");
            pass.setHint("Enter New Password MaxLength=10");
            logininstead.setVisibility(View.VISIBLE);
        } else {
            login.setVisibility(View.VISIBLE);
            tvsignup.setTextSize(15);
            name.setHint("Email");
            pass.setHint("Password");
            tvsignup.setBackgroundResource(R.color.white);
            login.setEnabled(true);
            conpass.setVisibility(View.GONE);
            logininstead.setVisibility(View.GONE);
        }
    }
}
    //
//    void check(String Sname, String Spass) {
//
//        boolean checkname1 = db.checkNamePass(Sname, Spass);
//        if (checkname1 == true) {
//            USERNAME = Sname;
//            Intent intentHome = new Intent(Login.this, HomeScreen.class);
//            startActivity(intentHome);
//            failedcounter = 5;
//            finish();
//        } else if (Sname.isEmpty() || Spass.isEmpty()) {
//            Toast.makeText(this, "Please Fill All the fields", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Username Or Password Wrong", Toast.LENGTH_SHORT).show();
//        }
//    }


    //                boolean checkname1 = db.checkname(username);
//                if (checkname1 == false) {
//                    Boolean insert = db.insertData(username, password);
//
//                    if (insert == true) {
//                        Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
//                        reset(0);
//                        failedcounter = 5;
//                        signupconter = 1;
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), "User Exists", Toast.LENGTH_SHORT).show();
//                }

