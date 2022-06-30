package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AdminCompaniesActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    LinearLayout companiesLayout;
    Button btnFindCompany;
    EditText tbCompanyName, tbCompanyNameAddCompanyDialog, tbUsernameAddCompanyDialog, tbPasswordAddCompany, tbPasswordConfirmAddCompany;
    TextView lblAddNewCompany, tbCompanyNameCompanyUpdateAdmin, tbUserNameCompanyUpdateAdmin;

    Dialog addCompanyDialog, deleteCompanyDialog, updateCompanyDialog;
    Button btnAdd, btnCancel, btnDeleteYes, btnDeleteNo, btnUpdateYes, btnUpdateCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_companies);

        databaseHelper = new DatabaseHelper(this);

        companiesLayout = findViewById(R.id.companiesLayoutActivityCompaniesAdmin);
        btnFindCompany = findViewById(R.id.btnFindCompanyCompaniesAdmin);
        tbCompanyName = findViewById(R.id.tbCompanyNameSearchCompanyAdminActivity);
        lblAddNewCompany = findViewById(R.id.lblAddNewCompany);

        companiesLayout.removeAllViews();
        List<CompanyDto> companies = databaseHelper.getCompaniesAndExpeditions();
        getCompanies(companies);

        btnFindCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String companyName = tbCompanyName.getText().toString();
                if (companyName.isEmpty() || companyName.equals(null)){
                    companiesLayout.removeAllViews();
                    List<CompanyDto> companies = databaseHelper.getCompaniesAndExpeditions();
                    getCompanies(companies);
                }
                else{
                    companiesLayout.removeAllViews();
                    List<CompanyDto> companies = databaseHelper.getCompanyAndExpeditionsByCompanyName(companyName);
                    getCompanies(companies);
                }
            }
        });

        lblAddNewCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCompanyDialog.show();
            }
        });

        addCompanyDialog = new Dialog(this);
        addCompanyDialog.setContentView(R.layout.add_new_company_dialog);
        addCompanyDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_background));
        addCompanyDialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);
        addCompanyDialog.setCancelable(false);
        addCompanyDialog.getWindow().getAttributes().windowAnimations = R.style.AddCompanyDialogAnimation;

        btnAdd = addCompanyDialog.findViewById(R.id.btnAddDialogAddCompany);
        btnCancel = addCompanyDialog.findViewById(R.id.btnCancelDialogAddCompany);

        deleteCompanyDialog = new Dialog(this);
        deleteCompanyDialog.setContentView(R.layout.company_delete_dialog);
        deleteCompanyDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_background));
        deleteCompanyDialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);
        deleteCompanyDialog.setCancelable(false);
        deleteCompanyDialog.getWindow().getAttributes().windowAnimations = R.style.DeleteCompanyDialogAnimation;

        updateCompanyDialog = new Dialog(this);
        updateCompanyDialog.setContentView(R.layout.admin_company_update_dialog);
        updateCompanyDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_background));
        updateCompanyDialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);
        updateCompanyDialog.setCancelable(false);
        updateCompanyDialog.getWindow().getAttributes().windowAnimations = R.style.UpdateCompanyDialogAnimation;

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tbCompanyNameAddCompanyDialog = addCompanyDialog.findViewById(R.id.tbCompanyNameAddCompanyAdmin);
                tbUsernameAddCompanyDialog = addCompanyDialog.findViewById(R.id.tbUsernameAddCompanyAdmin);
                tbPasswordAddCompany = addCompanyDialog.findViewById(R.id.tbPasswordAddCompanyAdmin);
                tbPasswordConfirmAddCompany = addCompanyDialog.findViewById(R.id.tbPasswordConfirmAddCompanyAdmin);

                String companyName = tbCompanyNameAddCompanyDialog.getText().toString();
                String companyUsername = tbUsernameAddCompanyDialog.getText().toString();
                String companyPassword = tbPasswordAddCompany.getText().toString();
                String companyPasswordConfirm = tbPasswordConfirmAddCompany.getText().toString();

                if (companyName.isEmpty() || companyName.equals(null)){
                    tbCompanyNameAddCompanyDialog.setError("Firma adı boş bırakılamaz");
                }
                else if (companyUsername.isEmpty() || companyUsername.equals(null)){
                    tbUsernameAddCompanyDialog.setError("Firma kullanıcı adı boş bırakılamaz");
                }
                else if (companyPassword.isEmpty() || companyPassword.equals(null)){
                    tbPasswordAddCompany.setError("Parola boş bırakılamaz");
                }
                else if (companyPasswordConfirm.isEmpty() || companyPasswordConfirm.equals(null)){
                    tbPasswordConfirmAddCompany.setError("Parola tekrarı boş bırakılamaz");
                }
                else if (!companyPassword.equals(companyPasswordConfirm)){
                    tbPasswordConfirmAddCompany.setError("Parolalar uyuşmuyor");
                }
                else{

                    Company company = new Company(companyName, companyUsername, companyPassword, null);
                    databaseHelper.insertCompany(company);
                    Toast.makeText(getApplicationContext(), "Firma başarılı bir şekilde eklendi", Toast.LENGTH_SHORT).show();
                    refreshActivity();
                    clearTextBoxAddNewCompany();
                    addCompanyDialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCompanyDialog.dismiss();
            }
        });
    }

    private void getCompanies(List<CompanyDto> companies){
        if (companies.size() > 0){
            for (CompanyDto company : companies) {
                LinearLayout linearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0,30,0,10);
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setBackgroundResource(R.drawable.company_box_background);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                LinearLayout layoutCompany = new LinearLayout(this);
                LinearLayout.LayoutParams layoutCompanyParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutCompany.setLayoutParams(layoutCompanyParams);
                layoutCompanyParams.setMargins(30,30,0,0);
                layoutCompany.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbBus = new ImageView(this);
                LinearLayout.LayoutParams pbBusParams = new LinearLayout.LayoutParams(40, 40);
                pbBus.setLayoutParams(pbBusParams);
                pbBus.setPadding(0,0,0,0);
                pbBus.setImageResource(R.drawable.company);
                layoutCompany.addView(pbBus);

                TextView lblCompany = new TextView(this);
                LinearLayout.LayoutParams lblCompanyParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblCompany.setLayoutParams(lblCompanyParams);
                lblCompany.setTextSize(14);
                lblCompany.setTypeface(null, Typeface.BOLD);
                lblCompanyParams.setMargins(15,5,0,0);
                lblCompany.setText("Firma adı: " + company.getCompany().getCompanyName());
                lblCompany.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutCompany.addView(lblCompany);
                linearLayout.addView(layoutCompany);

                LinearLayout layoutExpeditionCount = new LinearLayout(this);
                LinearLayout.LayoutParams layoutExpeditionCountParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutExpeditionCount.setLayoutParams(layoutExpeditionCountParams);
                layoutExpeditionCountParams.setMargins(30,30,0,35);
                layoutExpeditionCount.setOrientation(LinearLayout.HORIZONTAL);

                ImageView pbExpedition = new ImageView(this);
                LinearLayout.LayoutParams pbExpeditionParams = new LinearLayout.LayoutParams(40, 40);
                pbExpedition.setLayoutParams(pbExpeditionParams);
                pbExpedition.setPadding(0,0,0,0);
                pbExpedition.setImageResource(R.drawable.bus);
                layoutExpeditionCount.addView(pbExpedition);

                TextView lblExpeditionCount = new TextView(this);
                LinearLayout.LayoutParams lblExpeditionCountParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblExpeditionCount.setLayoutParams(lblExpeditionCountParams);
                lblExpeditionCount.setTextSize(14);
                lblExpeditionCount.setTypeface(null, Typeface.BOLD);
                lblExpeditionCountParams.setMargins(15,0,0,0);
                lblExpeditionCount.setText("Sefer sayısı: " + String.valueOf(company.getExpeditions().size()));
                lblExpeditionCount.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutExpeditionCount.addView(lblExpeditionCount);
                linearLayout.addView(layoutExpeditionCount);

                /* Start From City Layout */
                LinearLayout layoutProcess = new LinearLayout(this);
                LinearLayout.LayoutParams layoutProcessParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutProcess.setLayoutParams(layoutProcessParams);
                layoutProcessParams.setMargins(10,10,10,25);
                layoutProcess.setGravity(Gravity.CENTER);
                layoutProcess.setOrientation(LinearLayout.HORIZONTAL);

                Button btnCompanyDelete = new Button(this);
                LinearLayout.LayoutParams btnCompanyDeleteParams = new LinearLayout.LayoutParams(250, 60);
                btnCompanyDelete.setLayoutParams(btnCompanyDeleteParams);
                btnCompanyDelete.setText("Sil");
                btnCompanyDelete.setTextSize(13);
                btnCompanyDeleteParams.setMargins(20,0,20,0);
                btnCompanyDelete.setPadding(0,0,0,0);;
                btnCompanyDelete.setTextColor(getResources().getColor(R.color.white));
                btnCompanyDelete.setBackgroundResource(R.drawable.delete_company_button_design);
                btnCompanyDelete.setAllCaps(false);
                btnCompanyDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteCompanyDialog.show();

                        btnDeleteYes = deleteCompanyDialog.findViewById(R.id.btnDeleteYesCompaniesAdmin);
                        btnDeleteNo = deleteCompanyDialog.findViewById(R.id.btnDeleteNoCompaniesAdmin);

                        btnDeleteYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                databaseHelper.deleteCompany(company.getCompany().getId());
                                Toast.makeText(getApplicationContext(), "Firma başarılı bir şekilde silindi", Toast.LENGTH_SHORT).show();
                                refreshActivity();
                                deleteCompanyDialog.dismiss();
                            }
                        });

                        btnDeleteNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                deleteCompanyDialog.dismiss();
                            }
                        });
                    }
                });
                layoutProcess.addView(btnCompanyDelete);

                Button btnCompanyUpdate = new Button(this);
                LinearLayout.LayoutParams btnCompanyUpdateParams = new LinearLayout.LayoutParams(250, 60);
                btnCompanyUpdate.setLayoutParams(btnCompanyUpdateParams);
                btnCompanyUpdate.setText("Güncelle");
                btnCompanyUpdate.setTextSize(13);
                btnCompanyUpdateParams.setMargins(20,0,20,0);
                btnCompanyUpdate.setPadding(0,0,0,0);;
                btnCompanyUpdate.setTextColor(getResources().getColor(R.color.gray_dark2));
                btnCompanyUpdate.setBackgroundResource(R.drawable.edit_company_button_design);
                btnCompanyUpdate.setAllCaps(false);
                btnCompanyUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        btnUpdateYes = updateCompanyDialog.findViewById(R.id.btnUpdateDialogUpdateCompanyAdmin);
                        btnUpdateCancel = updateCompanyDialog.findViewById(R.id.btnCancelDialogUpdateCompanyAdmin);
                        tbCompanyNameCompanyUpdateAdmin = updateCompanyDialog.findViewById(R.id.tbCompanyNameCompanyUpdateAdmin);
                        tbUserNameCompanyUpdateAdmin = updateCompanyDialog.findViewById(R.id.tbUserNameCompanyUpdateAdmin);

                        tbCompanyNameCompanyUpdateAdmin.setText(company.getCompany().getCompanyName());
                        tbUserNameCompanyUpdateAdmin.setText(company.getCompany().getUsername());

                        btnUpdateYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String tbCompanyName = tbCompanyNameCompanyUpdateAdmin.getText().toString();
                                String tbCompanyUsername = tbUserNameCompanyUpdateAdmin.getText().toString();

                                if (tbCompanyName.isEmpty() || tbCompanyName.length() <= 0){
                                    tbCompanyNameCompanyUpdateAdmin.setError("Firma adı boş bırakılamaz");
                                }
                                else if(tbCompanyUsername.isEmpty() || tbCompanyUsername.length() <= 0){
                                    tbUserNameCompanyUpdateAdmin.setError("Firma kullanıcı adı boş bırakılamaz");
                                }
                                else{
                                    Company companyUpdate = databaseHelper.getCompanyById(company.getCompany().getId());
                                    companyUpdate.setCompanyName(tbCompanyName);
                                    companyUpdate.setUsername(tbCompanyUsername);
                                    databaseHelper.updateCompany(companyUpdate);
                                    Toast.makeText(getApplicationContext(), "Firma başarılı bir şekilde güncellendi", Toast.LENGTH_SHORT).show();
                                    refreshActivity();
                                    updateCompanyDialog.dismiss();
                                }
                            }
                        });

                        btnUpdateCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                updateCompanyDialog.dismiss();
                            }
                        });

                        updateCompanyDialog.show();
                    }
                });
                layoutProcess.addView(btnCompanyUpdate);


                linearLayout.addView(layoutProcess);
                companiesLayout.addView(linearLayout);
            }
        }
        else{
            ImageView pbWarning = new ImageView(this);
            LinearLayout.LayoutParams pbWarningParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            pbWarning.setLayoutParams(pbWarningParams);
            pbWarningParams.gravity= Gravity.CENTER;
            pbWarning.setImageResource(R.drawable.warning);
            pbWarning.setPadding(0,80,0,0);
            companiesLayout.addView(pbWarning);

            TextView lblCompaniesNotFound = new TextView(this);
            LinearLayout.LayoutParams lblCompaniesNotFoundParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblCompaniesNotFound.setLayoutParams(lblCompaniesNotFoundParams);
            lblCompaniesNotFound.setTextSize(15);
            lblCompaniesNotFound.setTypeface(null, Typeface.BOLD);
            lblCompaniesNotFound.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            lblCompaniesNotFoundParams.setMargins(30,30,30,10);
            lblCompaniesNotFound.setText("Sistemde kayıtlı otobüs firması bulunamadı !");
            lblCompaniesNotFound.setTextColor(getResources().getColor(R.color.gray_dark2));
            companiesLayout.addView(lblCompaniesNotFound);
        }
    }

    private void refreshActivity(){
        companiesLayout.removeAllViews();
        tbCompanyName.setText("");
        tbCompanyNameCompanyUpdateAdmin.setText("");
        btnFindCompany.performClick();
    }

    private void clearTextBoxAddNewCompany(){
        tbCompanyNameAddCompanyDialog.setText("");
        tbUsernameAddCompanyDialog.setText("");
        tbPasswordAddCompany.setText("");
        tbPasswordConfirmAddCompany.setText("");
    }
}