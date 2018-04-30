package com.example.biro.footballsocer.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biro.footballsocer.models.Team;
import com.example.biro.footballsocer.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.clubImage)
    ImageView clubImage;
    @BindView(R.id.clubName)
    TextView clubName;
    @BindView(R.id.clubCountry)
    TextView clubCountry;
    @BindView(R.id.clubLeague)
    TextView clubLeague;
    @BindView(R.id.clubStadium)
    TextView clubStadium;
    @BindView(R.id.clubMatches)
    TextView clubMatches;
    @BindView(R.id.clubPoints)
    TextView clubPoints;
    @BindView(R.id.clubPosition)
    TextView clubPosition;
    @BindView(R.id.clubWins)
    TextView clubWins;
    @BindView(R.id.clubLoses)
    TextView clubLoses;
    @BindView(R.id.clubDraws)
    TextView clubDraws;
    @BindView(R.id.clubGoals)
    TextView clubGoals;
    @BindView(R.id.clubGoalsAg)
    TextView clubGoalsAg;
    @BindView(R.id.adView)
    AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Team team = getIntent().getParcelableExtra(getString(R.string.teamkey));

        clubLoses.setText(team.getLoses());

        if (team != null) {
            Picasso.get()
                    .load(team.getImage()).resize(500,500).placeholder(R.drawable.progress_animation).into(clubImage);
            clubName.setText(team.getName());
            clubCountry.setText(team.getCountry());
            int roundId = team.getLeague();
            if (roundId == Integer.valueOf(getString(R.string.premierLeague)))
                clubLeague.setText(getString(R.string.drawer_item_1));
            else if (roundId == Integer.valueOf(getString(R.string.bundesliga)))
                clubLeague.setText(getString(R.string.drawer_item_2));
            else if (roundId == Integer.valueOf(getString(R.string.serieA)))
                clubLeague.setText(getString(R.string.drawer_item_3));
            else
                clubLeague.setText(getString(R.string.drawer_item_4));
            clubMatches.setText(String.valueOf(team.getGames()));
            clubPoints.setText(String.valueOf(team.getPoints()));
            clubPosition.setText(String.valueOf(team.getPosition()));
            clubStadium.setText(String.valueOf(team.getStadium()));
            clubWins.setText(String.valueOf(team.getWins()));
            clubLoses.setText(String.valueOf(team.getLoses()));
            clubDraws.setText(String.valueOf(team.getDraws()));
            clubGoals.setText(String.valueOf(team.getGoals()));
            clubGoalsAg.setText(String.valueOf(team.getGoalsAg()));
        }

//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .build();
//        mAdView.loadAd(adRequest);
//
//
//        final InterstitialAd mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(getString(R.string.ad_mob_interstitialAd));
//        mInterstitialAd.loadAd(adRequest);
//
//        mInterstitialAd.setAdListener(new AdListener()
//        {
//            @Override
//            public void onAdLoaded() {
//                // Code to be executed when an ad finishes loading.
//                mInterstitialAd.show();
//            }
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                // Code to be executed when an ad request fails.
//                Log.d("Ads", "onAdFailedToLoad");
//            }
//
//        });

    }
}
