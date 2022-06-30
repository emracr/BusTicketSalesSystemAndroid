package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminChangePasswordActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    EditText tbOldPassword, tbNewPassword, tbNewPasswordConfirm;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_change_password);

        databaseHelper = new DatabaseHelper(this);

        tbOldPassword = findViewById(R.id.tbOldPasswordActivityChangePasswordAdmin);
        tbNewPassword = findViewById(R.id.tbNewPasswordActivityChangePasswordAdmin);
        tbNewPasswordConfirm = findViewById(R.id.tbNewPasswordConfirmActivityChangePasswordAdmin);
        btnUpdate = findViewById(R.id.btnUpdateActivityChangePasswordAdmin);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String oldPassword, newPassword, newPasswordConfirm;

                oldPassword = tbOldPassword.getText().toString();
                newPassword = tbNewPassword.getText().toString();
                newPasswordConfirm = tbNewPasswordConfirm.getText().toString();

                if(oldPassword.isEmpty() || oldPassword.equals(null)){
                    tbOldPassword.setError("Mevcut parola boş bırakılamaz");
                }
                else if(newPassword.isEmpty() || newPassword.equals(null)){
                    tbNewPassword.setError("Yeni parola boş bırakılamaz");
                }
                else if(newPasswordConfirm.isEmpty() || newPasswordConfirm.equals(null)){
                    tbNewPasswordConfirm.setError("Yeni parola tekrarı boş bırakılamaz");
                }
                else if(!newPassword.equals(newPasswordConfirm)){
                    tbNewPasswordConfirm.setError("Parolalar uyuşmuyor");
                }
                else if(!oldPassword.equals(AdminCredentials.ADMIN.getPassword())){
                    tbOldPassword.setError("Mecut parola hatalı");
                }
                else{
                    Admin admin = AdminCredentials.ADMIN;
                    admin.setPassword(newPassword);
                    databaseHelper.updateAdmin(admin);
                    AdminCredentials.ADMIN = admin;
                    Toast.makeText(getApplicationContext(), "Parolanız başarılı bir şekilde güncellenmiştir.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), AdminHomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}