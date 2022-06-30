package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CompanyProfileActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    EditText tbCompanyNameCompany, tbCompanyUserNameCompany;
    Button btnUpdateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);

        databaseHelper = new DatabaseHelper(this);

        tbCompanyNameCompany = findViewById(R.id.tbCompanyNameCompanyProfile);
        tbCompanyUserNameCompany = findViewById(R.id.tbCompanyUserNameCompanyProfile);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfileCompanyProfile);

        tbCompanyNameCompany.setText(CompanyCredentials.COMPANY.getCompanyName());
        tbCompanyUserNameCompany.setText(CompanyCredentials.COMPANY.getUsername());

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String companyName = tbCompanyNameCompany.getText().toString();
                String companyUserName = tbCompanyUserNameCompany.getText().toString();

                if (companyName.isEmpty() || companyName.length() <= 0){
                    tbCompanyNameCompany.setError("Firma adı boş bırakılamaz");
                }
                else if(companyUserName.isEmpty() || companyUserName.length() <= 0){
                    tbCompanyUserNameCompany.setError("Firma kullanıcı adı boş bırakılamaz");
                }
                else{

                    Company company = CompanyCredentials.COMPANY;
                    company.setCompanyName(companyName);
                    company.setUsername(companyUserName);
                    databaseHelper.updateCompany(company);
                    CompanyCredentials.COMPANY = company;
                    Toast.makeText(getApplicationContext(), "Profiliniz başarılı bir şekilde güncellendi",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), CompanyHomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}