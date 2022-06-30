package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    public static final String SHARED_PREFERENCES = "sharedPreferences";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    private String emailSharedPreferences = null;
    private String passwordSharedPreferences = null;

    DatabaseHelper databaseHelper;

    EditText tbEmail, tbPassword;
    Button btnLogin;
    TextView lblRedirectRegister, lblRedirectCompanyLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);

        tbEmail = findViewById(R.id.tbEmailLogin);
        tbPassword = findViewById(R.id.tbPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        lblRedirectRegister = findViewById(R.id.lblRedirectRegisterLogin);
        lblRedirectCompanyLogin = findViewById(R.id.lblRedirectCompanyLogin);

        getSharedPreferencesData();
        boolean sharedPreferencesState = false;
        if (emailSharedPreferences != null && passwordSharedPreferences != null){
            sharedPreferencesState = true;
            tbEmail.setText(emailSharedPreferences);
            tbPassword.setText(passwordSharedPreferences);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email, password;

                email = tbEmail.getText().toString();
                password = tbPassword.getText().toString();

                if (tbEmail.getText().toString().isEmpty() || tbEmail.getText().toString().equals(null)){
                    tbEmail.setError("Email boş bırakılamaz");
                }
                else if(!isEmailValid(tbEmail.getText().toString())){
                    tbEmail.setError("Hatalı email formatı");
                }
                else if(tbPassword.getText().toString().isEmpty() || tbPassword.getText().toString().equals(null)){
                    tbPassword.setError("Parola boş bırakılamaz");
                }
                else{
                    if (checkUser(email, password)){
                        saveEmailAndPasswordSharedPreferences(email, password);
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        DialogBox.showDialog(getSupportFragmentManager(),"Uyarı","Email veya parola hatalı");
                    }
                }
            }
        });

        if (sharedPreferencesState){
            btnLogin.performClick();
        }

        lblRedirectRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        lblRedirectCompanyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CompanyLoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private boolean checkUser(String email, String password){

        List<User> users = databaseHelper.getUsers();
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)){
                Credentials.USER = new User(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber(), user.getGender(), user.getPassword());
                return true;
            }
        }
        return false;
    }

    private boolean checkIfUserInSystem(String email){
        List<User> users = databaseHelper.getUsers();
        for (User user : users) {
            if (user.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    private static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void saveEmailAndPasswordSharedPreferences(String email, String password){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, password);

        editor.apply();
    }

    public void getSharedPreferencesData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        emailSharedPreferences = sharedPreferences.getString(EMAIL, null);
        passwordSharedPreferences = sharedPreferences.getString(PASSWORD, null);
    }
}