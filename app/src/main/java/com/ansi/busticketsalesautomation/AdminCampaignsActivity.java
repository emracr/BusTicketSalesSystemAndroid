package com.ansi.busticketsalesautomation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Typeface;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdminCampaignsActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    final Calendar myCalendar= Calendar.getInstance();

    LinearLayout campaignsLayout;
    TextView lblAddCampaign;
    EditText tbCampaignTitle, tbCampaignContent, tbCampaignStart, tbCampaignDeadline;
    EditText tbCampaignUpdateTitle, tbCampaignUpdateContent, tbCampaignUpdateStart, tbCampaignUpdateDeadline;

    Dialog campaignRemoveDialog, campaignAddDialog, campaignUpdateDialog;
    Button btnCampaignRemoveYes, btnCampaignRemoveNo, btnAddCampaignYes, btnAddCampaignCancel, btnUpdateCampaignYes, btnUpdateCampaignCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_campaigns);

        databaseHelper = new DatabaseHelper(this);

        campaignsLayout = findViewById(R.id.campaignsLayoutActivityCompaniesAdmin);
        lblAddCampaign = findViewById(R.id.lblAddNewCampaign);

        campaignRemoveDialog = new Dialog(this);
        campaignRemoveDialog.setContentView(R.layout.campaign_remove_dialog);
        campaignRemoveDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.ticket_cancel_dialog_background));
        campaignRemoveDialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);
        campaignRemoveDialog.setCancelable(false);
        campaignRemoveDialog.getWindow().getAttributes().windowAnimations = R.style.CampaignRemoveDialogAnimation;

        campaignAddDialog = new Dialog(this);
        campaignAddDialog.setContentView(R.layout.admin_campaign_add_dialog);
        campaignAddDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.ticket_cancel_dialog_background));
        campaignAddDialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);
        campaignAddDialog.setCancelable(false);
        campaignAddDialog.getWindow().getAttributes().windowAnimations = R.style.CampaignAddDialogAnimation;

        campaignUpdateDialog = new Dialog(this);
        campaignUpdateDialog.setContentView(R.layout.admin_campaign_update_dialog);
        campaignUpdateDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.ticket_cancel_dialog_background));
        campaignUpdateDialog.getWindow().setLayout(600, ViewGroup.LayoutParams.WRAP_CONTENT);
        campaignUpdateDialog.setCancelable(false);
        campaignUpdateDialog.getWindow().getAttributes().windowAnimations = R.style.CampaignUpdateDialogAnimation;

        tbCampaignTitle = campaignAddDialog.findViewById(R.id.tbCampaignTitleCampaignsAdmin);
        tbCampaignContent = campaignAddDialog.findViewById(R.id.tbCampaignContentCampaignsAdmin);
        tbCampaignStart = campaignAddDialog.findViewById(R.id.tbCampaignStartCampaignsAdmin);
        tbCampaignDeadline= campaignAddDialog.findViewById(R.id.tbCampaignDeadlineCampaignsAdmin);

        loadDateTimerPickerForAddCampaign();

        lblAddCampaign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAddNewCampaign();
                btnAddCampaignYes = campaignAddDialog.findViewById(R.id.btnAddCampaignCampaignsAdmin);
                btnAddCampaignCancel = campaignAddDialog.findViewById(R.id.btnCancelCampaignCampaignsAdmin);

                btnAddCampaignYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String title = tbCampaignTitle.getText().toString();
                        String content = tbCampaignContent.getText().toString();
                        String startDate = tbCampaignStart.getText().toString();
                        String deadLine = tbCampaignDeadline.getText().toString();

                        if (title.isEmpty() || title.length() <= 0){
                            tbCampaignTitle.setError("Kampanya başlığı boş bırakılamaz");
                        }
                        else if (content.isEmpty() || content.length() <= 0){
                            tbCampaignTitle.setError("Kampanya içeriği boş bırakılamaz");
                        }
                        else if (startDate.isEmpty() || startDate.length() <= 0){
                            tbCampaignTitle.setError("Kampanya başlangıç tarihi boş bırakılamaz");
                        }
                        else if (deadLine.isEmpty() || deadLine.length() <= 0){
                            tbCampaignTitle.setError("Kampanya bitiş tarihi boş bırakılamaz");
                        }
                        else{
                            Campaign campaign = new Campaign(title, content, startDate, deadLine, 1);
                            databaseHelper.insertCampaign(campaign);
                            Toast.makeText(getApplicationContext(), "Kampanya başarılı bir şekilde eklendi", Toast.LENGTH_SHORT).show();
                            refreshActivity();
                            campaignAddDialog.dismiss();
                        }

                        campaignAddDialog.dismiss();
                    }
                });

                btnAddCampaignCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        campaignAddDialog.dismiss();
                    }
                });

                campaignAddDialog.show();
            }
        });

        listCampaigns();

    }

    private void listCampaigns(){

        campaignsLayout.removeAllViews();
        List<Campaign> campaigns = databaseHelper.getCampaigns();

        if (campaigns.size() > 0){
            for (Campaign campaign : campaigns) {

                LinearLayout linearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(35,40,35,15);
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setBackgroundResource(R.drawable.campaign_box_background);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                LinearLayout layoutTitleAndEdit = new LinearLayout(this);
                LinearLayout.LayoutParams layoutTitleAndEditParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutTitleAndEditParams.setMargins(0,0,0,0);
                layoutTitleAndEdit.setLayoutParams(layoutTitleAndEditParams);
                layoutTitleAndEdit.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.addView(layoutTitleAndEdit);

                TextView lblTitle = new TextView(this);
                LinearLayout.LayoutParams lblTitleParams = new LinearLayout.LayoutParams(350, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblTitle.setLayoutParams(lblTitleParams);
                lblTitle.setTextSize(17);
                lblTitle.setTypeface(null, Typeface.BOLD);
                lblTitleParams.setMargins(30,25,0,0);
                lblTitle.setText(campaign.getTitle());
                lblTitle.setTextColor(getResources().getColor(R.color.gray_dark));
                layoutTitleAndEdit.addView(lblTitle);

                LinearLayout layoutDeleteAndEdit = new LinearLayout(this);
                LinearLayout.LayoutParams layoutDeleteAndEditParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutDeleteAndEditParams.setMargins(0,30,30,0);
                layoutDeleteAndEdit.setGravity(Gravity.END);
                layoutDeleteAndEdit.setLayoutParams(layoutDeleteAndEditParams);
                layoutDeleteAndEdit.setOrientation(LinearLayout.HORIZONTAL);
                layoutTitleAndEdit.addView(layoutDeleteAndEdit);

                ImageView pbDelete = new ImageView(this);
                LinearLayout.LayoutParams pbDeleteParams = new LinearLayout.LayoutParams(35, 35);
                pbDelete.setLayoutParams(pbDeleteParams);
                pbDelete.setImageResource(R.drawable.trash);
                pbDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        btnCampaignRemoveYes = campaignRemoveDialog.findViewById(R.id.btnRemoveYes);
                        btnCampaignRemoveNo = campaignRemoveDialog.findViewById(R.id.btnRemoveNo);

                        btnCampaignRemoveYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                databaseHelper.deleteCampaign(campaign.getId());
                                Toast.makeText(getApplicationContext(), "Kampanya başarılı bir şekilde silindi", Toast.LENGTH_SHORT).show();
                                refreshActivity();
                                campaignRemoveDialog.dismiss();
                            }
                        });

                        btnCampaignRemoveNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                campaignRemoveDialog.dismiss();
                            }
                        });

                        campaignRemoveDialog.show();
                    }
                });
                layoutDeleteAndEdit.addView(pbDelete);

                ImageView pbUpdate = new ImageView(this);
                LinearLayout.LayoutParams pbUpdateParams = new LinearLayout.LayoutParams(35, 35);
                pbUpdate.setLayoutParams(pbUpdateParams);
                pbUpdateParams.setMargins(35,0,0,0);
                pbUpdate.setImageResource(R.drawable.editing);
                pbUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        btnUpdateCampaignYes = campaignUpdateDialog.findViewById(R.id.btnUpdateCampaignCampaignAdmin);
                        btnUpdateCampaignCancel = campaignUpdateDialog.findViewById(R.id.btnUpdateCancelCampaignCampaignsAdmin);

                        tbCampaignUpdateTitle = campaignUpdateDialog.findViewById(R.id.tbCampaignTitleCampaignUpdateAdmin);
                        tbCampaignUpdateContent = campaignUpdateDialog.findViewById(R.id.tbCampaignContentCampaignUpdateAdmin);
                        tbCampaignUpdateStart = campaignUpdateDialog.findViewById(R.id.tbCampaignStartCampaignUpdateAdmin);
                        tbCampaignUpdateDeadline= campaignUpdateDialog.findViewById(R.id.tbCampaignDeadlineCampaignUpdateAdmin);

                        tbCampaignUpdateTitle.setText(campaign.getTitle());
                        tbCampaignUpdateContent.setText(campaign.getContent());
                        tbCampaignUpdateStart.setText(campaign.getStartingDate());
                        tbCampaignUpdateDeadline.setText(campaign.getDeadline());

                        loadDateTimerPickerForUpdateCampaign();

                        btnUpdateCampaignYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String title = tbCampaignUpdateTitle.getText().toString();
                                String content = tbCampaignUpdateContent.getText().toString();
                                String startDate = tbCampaignUpdateStart.getText().toString();
                                String deadLine = tbCampaignUpdateDeadline.getText().toString();

                                if (title.isEmpty() || title.length() <= 0){
                                    tbCampaignTitle.setError("Kampanya başlığı boş bırakılamaz");
                                }
                                else if (content.isEmpty() || content.length() <= 0){
                                    tbCampaignTitle.setError("Kampanya içeriği boş bırakılamaz");
                                }
                                else if (startDate.isEmpty() || startDate.length() <= 0){
                                    tbCampaignTitle.setError("Kampanya başlangıç tarihi boş bırakılamaz");
                                }
                                else if (deadLine.isEmpty() || deadLine.length() <= 0){
                                    tbCampaignTitle.setError("Kampanya bitiş tarihi boş bırakılamaz");
                                }
                                else{
                                    Campaign campaignUpdate = campaign;
                                    campaignUpdate.setTitle(title);
                                    campaignUpdate.setContent(content);
                                    campaignUpdate.setStartingDate(startDate);
                                    campaignUpdate.setDeadline(deadLine);
                                    databaseHelper.updateCampaign(campaignUpdate);
                                    Toast.makeText(getApplicationContext(), "Kampanya başarılı bir şekilde güncellendi", Toast.LENGTH_SHORT).show();
                                    refreshActivity();
                                    campaignUpdateDialog.dismiss();
                                }
                            }
                        });

                        btnUpdateCampaignCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                campaignUpdateDialog.dismiss();
                            }
                        });

                        campaignUpdateDialog.show();
                    }
                });
                layoutDeleteAndEdit.addView(pbUpdate);

                TextView lblContent = new TextView(this);
                LinearLayout.LayoutParams lblContentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblContent.setLayoutParams(lblContentParams);
                lblContent.setTextSize(14);
                lblContentParams.setMargins(30,15,30,0);
                lblContent.setText(campaign.getContent());
                lblContent.setTextColor(getResources().getColor(R.color.gray_dark2));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    lblContent.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
                }
                linearLayout.addView(lblContent);

                LinearLayout layoutCampaignStartAndDeadline = new LinearLayout(this);
                LinearLayout.LayoutParams layoutCompanyParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutCampaignStartAndDeadline.setLayoutParams(layoutCompanyParams);
                layoutCompanyParams.setMargins(30,20,0,30);
                layoutCampaignStartAndDeadline.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.addView(layoutCampaignStartAndDeadline);

                TextView lblStartingDate = new TextView(this);
                LinearLayout.LayoutParams lblStartingDateParams = new LinearLayout.LayoutParams(300, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblStartingDate.setLayoutParams(lblStartingDateParams);
                lblStartingDate.setTextSize(12);
                lblStartingDate.setTypeface(null, Typeface.BOLD);
                lblStartingDateParams.setMargins(0,15,0,0);
                lblStartingDate.setText("Başlangıç: " + campaign.getStartingDate());
                lblStartingDate.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutCampaignStartAndDeadline.addView(lblStartingDate);

                TextView lblDeadline = new TextView(this);
                LinearLayout.LayoutParams lblDeadlineParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblDeadline.setLayoutParams(lblDeadlineParams);
                lblDeadline.setTextSize(12);
                lblDeadline.setTypeface(null, Typeface.BOLD);
                lblDeadlineParams.setMargins(0,15,30,0);
                lblDeadline.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                lblDeadline.setText("Bitiş: " + campaign.getDeadline());
                lblDeadline.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutCampaignStartAndDeadline.addView(lblDeadline);

                campaignsLayout.addView(linearLayout);

            }
        }
        else{
            ImageView pbWarning = new ImageView(this);
            LinearLayout.LayoutParams pbWarningParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            pbWarning.setLayoutParams(pbWarningParams);
            pbWarningParams.gravity= Gravity.CENTER;
            pbWarning.setImageResource(R.drawable.warning);
            pbWarning.setPadding(0,80,0,0);
            campaignsLayout.addView(pbWarning);

            TextView lblCampaignsNotFound = new TextView(this);
            LinearLayout.LayoutParams lblCampaignsNotFoundParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblCampaignsNotFound.setLayoutParams(lblCampaignsNotFoundParams);
            lblCampaignsNotFound.setTextSize(15);
            lblCampaignsNotFound.setTypeface(null, Typeface.BOLD);
            lblCampaignsNotFound.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            lblCampaignsNotFoundParams.setMargins(30,30,30,10);
            lblCampaignsNotFound.setText("Sistemde kayıtlı kampanya bulunamadı !");
            lblCampaignsNotFound.setTextColor(getResources().getColor(R.color.gray_dark2));
            campaignsLayout.addView(lblCampaignsNotFound);
        }

    }

    private void loadDateTimerPickerForAddCampaign(){
        /* Campaign Start Date Picker Dialog */
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                month += 1;
                writeDateOfBirth(tbCampaignStart);
            }
        };

        tbCampaignStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminCampaignsActivity.this, date, myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        /* Campaign End Date Picker Dialog */

        /* Campaign Deadline Picker Dialog */
        DatePickerDialog.OnDateSetListener date2 =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                month += 1;
                writeDateOfBirth(tbCampaignDeadline);
            }
        };

        tbCampaignDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminCampaignsActivity.this, date2, myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        /* Campaign Deadline Picker Dialog */
    }

    private void loadDateTimerPickerForUpdateCampaign(){
        /* Campaign Start Date Picker Dialog */
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                month += 1;
                writeDateOfBirth(tbCampaignUpdateStart);
            }
        };

        tbCampaignUpdateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminCampaignsActivity.this, date, myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        /* Campaign End Date Picker Dialog */

        /* Campaign Deadline Picker Dialog */
        DatePickerDialog.OnDateSetListener date2 =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                month += 1;
                writeDateOfBirth(tbCampaignUpdateDeadline);
            }
        };

        tbCampaignUpdateDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminCampaignsActivity.this, date2, myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        /* Campaign Deadline Picker Dialog */
    }

    private void refreshActivity(){
        campaignsLayout.removeAllViews();
        listCampaigns();
    }

    private void clearAddNewCampaign(){
        tbCampaignTitle.setText("");
        tbCampaignContent.setText("");
        tbCampaignStart.setText("");
        tbCampaignDeadline.setText("");
    }

    private void writeDateOfBirth(EditText view){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        view.setText(dateFormat.format(myCalendar.getTime()));
    }
}