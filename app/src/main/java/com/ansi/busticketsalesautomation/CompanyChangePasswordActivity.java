package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CompanyChangePasswordActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    EditText tbOldPassword, tbNewPassword, tbNewPasswordConfirm;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_change_password);

        databaseHelper = new DatabaseHelper(this);

        tbOldPassword = findViewById(R.id.tbOldPasswordActivityChangePasswordCompany);
        tbNewPassword = findViewById(R.id.tbNewPasswordActivityChangePasswordCompany);
        tbNewPasswordConfirm = findViewById(R.id.tbNewPasswordConfirmActivityChangePasswordCompany);
        btnUpdate = findViewById(R.id.btnUpdateActivityChangePasswordCompany);

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
                else if(!oldPassword.equals(CompanyCredentials.COMPANY.getPassword())){
                    tbOldPassword.setError("Mecut parola hatalı");
                }
                else{
                    Company company = CompanyCredentials.COMPANY;
                    company.setPassword(newPassword);
                    databaseHelper.updateCompany(company);
                    CompanyCredentials.COMPANY = company;
                    Toast.makeText(getApplicationContext(), "Parolanız başarılı bir şekilde güncellenmiştir.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), CompanyHomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}