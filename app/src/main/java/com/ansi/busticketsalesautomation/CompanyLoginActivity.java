package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CompanyLoginActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    EditText tbUsername, tbPassword;
    Button btnLogin;
    TextView lblRedirectAdminLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_login);

        databaseHelper = new DatabaseHelper(this);

        tbUsername = findViewById(R.id.tbUsernameLoginCompany);
        tbPassword = findViewById(R.id.tbPasswordLoginCompany);
        btnLogin = findViewById(R.id.btnLoginCompany);
        lblRedirectAdminLogin = findViewById(R.id.lblRedirectAdminLogin);

        tbUsername.setText("kamilkoc");
        tbPassword.setText("1111");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username, password;

                username = tbUsername.getText().toString();
                password = tbPassword.getText().toString();

                if (username.isEmpty() || username.equals(null)){
                    tbUsername.setError("Kullanıcı adı boş bırakılamaz");
                }
                else if(password.isEmpty() || password.equals(null)){
                    tbPassword.setError("Parola boş bırakılamaz");
                }
                else{
                    if (checkUser(username, password)){
                        Intent intent = new Intent(getApplicationContext(), CompanyHomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        DialogBox.showDialog(getSupportFragmentManager(),"Uyarı","Kullanıcı adı veya parola hatalı");
                    }
                }

            }
        });

        lblRedirectAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkUser(String username, String password){
        List<Company> companies = databaseHelper.getCompanies();
        for (Company company : companies) {
            if (company.getUsername().equals(username) && company.getPassword().equals(password)){
                CompanyCredentials.COMPANY = company;
                return true;
            }
        }
        return false;
    }
}