package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class AdminHomeActivity extends AppCompatActivity {

    LinearLayout layout_companies, layout_users, layout_tickets, layout_ticket_reservation, layout_campaigns, layout_settings, layout_exit_button;

    TextView lblWelcomeAdmin;

    Dialog exitDialog;
    Button btnExitYes, btnExitNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        layout_companies = findViewById(R.id.layout_companies_activity_admin_home);
        layout_users = findViewById(R.id.layout_users_activity_admin_home);
        layout_tickets = findViewById(R.id.layout_tickets_activity_admin_home);
        layout_ticket_reservation = findViewById(R.id.layout_ticket_reservation_activity_admin_home);
        layout_campaigns = findViewById(R.id.layout_campaigns_admin_home);
        layout_settings = findViewById(R.id.layout_settings_admin_home);
        layout_exit_button = findViewById(R.id.layout_exit_button_activity_admin_home);

        lblWelcomeAdmin = findViewById(R.id.lblWelcomeAdmin);
        if (AdminCredentials.ADMIN != null){
            lblWelcomeAdmin.setText(AdminCredentials.ADMIN.getFirstName().toUpperCase(Locale.ROOT) + " " + AdminCredentials.ADMIN.getLastName().toUpperCase(Locale.ROOT));
        }


        layout_companies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminCompaniesActivity.class);
                startActivity(intent);
            }
        });

        layout_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminUserActivity.class);
                startActivity(intent);
            }
        });

        layout_tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminTicketsActivity.class);
                startActivity(intent);
            }
        });

        layout_ticket_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminReservationActivity.class);
                startActivity(intent);
            }
        });

        layout_campaigns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminCampaignsActivity.class);
                startActivity(intent);
            }
        });

        layout_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminSettingsActivity.class);
                startActivity(intent);
            }
        });

        layout_exit_button.setOnClickListener(new View.OnClickListener() {
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