package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class AdminLoginActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    EditText tbUsername, tbPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        databaseHelper = new DatabaseHelper(this);

        tbUsername = findViewById(R.id.tbUsernameLoginAdmin);
        tbPassword = findViewById(R.id.tbPasswordLoginAdmin);
        btnLogin = findViewById(R.id.btnLoginAdmin);

        tbUsername.setText("admin");
        tbPassword.setText("admin");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = tbUsername.getText().toString();
                String password = tbPassword.getText().toString();

                if (userName.isEmpty() || userName.length() <= 0){
                    tbUsername.setError("Kullanıcı adı boş bırakılamaz");
                }
                else if(password.isEmpty() || password.length() <= 0){
                    tbPassword.setError("Parola boş bırakılamaz");
                }
                else{

                    if (checkAdmin(userName, password)){
                        Intent intent = new Intent(getApplicationContext(), AdminHomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        DialogBox.showDialog(getSupportFragmentManager(),"Uyarı","Kullanıcı veya parola hatalı");
                    }
                }
            }
        });
    }

    private boolean checkAdmin(String userName, String password){

        List<Admin> admins = databaseHelper.getAdmins();
        for (Admin admin : admins) {
            if (admin.getUserName().equals(userName) && admin.getPassword().equals(password)){
                AdminCredentials.ADMIN = new Admin(admin.getId(), admin.getFirstName(), admin.getLastName(), admin.getUserName(), admin.getPassword());
                return true;
            }
        }
        return false;
    }
}