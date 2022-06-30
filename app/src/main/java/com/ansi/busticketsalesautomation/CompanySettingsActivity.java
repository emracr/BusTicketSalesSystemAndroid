package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CompanySettingsActivity extends AppCompatActivity {

    TextView lblUsername;

    Button btnMyProfile, btnChangeMyPassword, btnExit;

    Dialog exitDialog;
    Button btnExitYes, btnExitNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_settings);

        lblUsername = findViewById(R.id.lblUsernameActivityCompanySettings);

        lblUsername.setText(CompanyCredentials.COMPANY.getCompanyName() + " Turizm");
        btnMyProfile = findViewById(R.id.btnMyProfileActivitySettingsCompany);
        btnChangeMyPassword = findViewById(R.id.btnChangePasswordActivitySettingsCompany);
        btnExit = findViewById(R.id.btnExitActivitySettingsCompany);

        btnMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CompanyProfileActivity.class);
                startActivity(intent);
            }
        });

        btnChangeMyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CompanyChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialog.show();
            }
        });

        exitDialog = new Dialog(this);
        exitDialog.setContentView(R.layout.exit_dialog);
        exitDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.exit_dialog_background));
        exitDialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);
        exitDialog.setCancelable(false);
        exitDialog.getWindow().getAttributes().windowAnimations = R.style.ExitDialogAnimation;

        btnExitYes = exitDialog.findViewById(R.id.btnExitYes);
        btnExitNo = exitDialog.findViewById(R.id.btnExitNo);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialog.show();
            }
        });

        btnExitYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialog.dismiss();
                finishAffinity();
            }
        });
        btnExitNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitDialog.dismiss();
            }
        });

    }
}