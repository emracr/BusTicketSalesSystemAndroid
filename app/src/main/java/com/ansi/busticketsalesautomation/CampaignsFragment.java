package com.ansi.busticketsalesautomation;

import android.graphics.Typeface;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CampaignsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CampaignsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CampaignsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CampaignsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CampaignsFragment newInstance(String param1, String param2) {
        CampaignsFragment fragment = new CampaignsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    DatabaseHelper databaseHelper;

    LinearLayout campaignsLayout;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_campaigns, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        campaignsLayout = view.findViewById(R.id.campaignsLayoutFragmentCampaigns);


        List<Campaign> campaigns = databaseHelper.getCampaigns();

        if (campaigns.size() > 0){

            for (Campaign campaign : campaigns) {

                LinearLayout linearLayout = new LinearLayout(view.getContext());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(40,40,40,15);
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setBackgroundResource(R.drawable.campaign_box_background);
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                TextView lblTitle = new TextView(view.getContext());
                LinearLayout.LayoutParams lblTitleParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblTitle.setLayoutParams(lblTitleParams);
                lblTitle.setTextSize(17);
                lblTitle.setTypeface(null, Typeface.BOLD);
                lblTitleParams.setMargins(30,25,0,0);
                lblTitle.setText(campaign.getTitle());
                lblTitle.setTextColor(getResources().getColor(R.color.gray_dark));
                linearLayout.addView(lblTitle);

                TextView lblContent = new TextView(view.getContext());
                LinearLayout.LayoutParams lblContentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblContent.setLayoutParams(lblContentParams);
                lblContent.setTextSize(14);
                lblContentParams.setMargins(30,15,30,0);
                lblContent.setText(campaign.getContent());
                lblContent.setTextColor(getResources().getColor(R.color.gray_dark2));
                lblContent.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
                linearLayout.addView(lblContent);

                LinearLayout layoutCampaignStartAndDeadline = new LinearLayout(view.getContext());
                LinearLayout.LayoutParams layoutCompanyParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutCampaignStartAndDeadline.setLayoutParams(layoutCompanyParams);
                layoutCompanyParams.setMargins(30,20,0,30);
                layoutCampaignStartAndDeadline.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.addView(layoutCampaignStartAndDeadline);

                TextView lblStartingDate = new TextView(view.getContext());
                LinearLayout.LayoutParams lblStartingDateParams = new LinearLayout.LayoutParams(300, LinearLayout.LayoutParams.WRAP_CONTENT);
                lblStartingDate.setLayoutParams(lblStartingDateParams);
                lblStartingDate.setTextSize(12);
                lblStartingDate.setTypeface(null, Typeface.BOLD);
                lblStartingDateParams.setMargins(0,15,0,0);
                lblStartingDate.setText("Başlangıç: " + campaign.getStartingDate());
                lblStartingDate.setTextColor(getResources().getColor(R.color.gray_dark2));
                layoutCampaignStartAndDeadline.addView(lblStartingDate);

                TextView lblDeadline = new TextView(view.getContext());
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

            ImageView pbWarning = new ImageView(view.getContext());
            LinearLayout.LayoutParams pbWarningParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            pbWarning.setLayoutParams(pbWarningParams);
            pbWarningParams.gravity= Gravity.CENTER;
            pbWarning.setImageResource(R.drawable.warning);
            pbWarning.setPadding(0,80,0,0);
            campaignsLayout.addView(pbWarning);

            TextView lblCampaignNotFound = new TextView(view.getContext());
            LinearLayout.LayoutParams lblCampaignNotFoundParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lblCampaignNotFound.setLayoutParams(lblCampaignNotFoundParams);
            lblCampaignNotFound.setTextSize(15);
            lblCampaignNotFound.setTypeface(null, Typeface.BOLD);
            lblCampaignNotFound.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            lblCampaignNotFoundParams.setMargins(50,30,20,10);
            lblCampaignNotFound.setText("Şu an herhangi bir kampanya bulunmamaktadır !");
            lblCampaignNotFound.setTextColor(getResources().getColor(R.color.gray_dark2));
            campaignsLayout.addView(lblCampaignNotFound);
        }

        return view;
    }
}