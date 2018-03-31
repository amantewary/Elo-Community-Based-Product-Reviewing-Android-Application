package group.hashtag.projectelo.Activities;

/**
 * Created by deeks on 3/31/2018.
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;

import com.chyrta.onboarder.OnboarderActivity;
import com.chyrta.onboarder.OnboarderPage;


import java.util.ArrayList;
import java.util.List;

import group.hashtag.projectelo.R;

public class OnboardingAvtivity extends OnboarderActivity
{
    List<OnboarderPage> onboarderPages;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        onboarderPages = new ArrayList<OnboarderPage>();
//        buttonSetTextColor(Color.BLACK);

        ImageButton ibnext = findViewById(R.id.ib_next);

//        ibnext.setImageResource(R.drawable.ic_navigate_next_black_24dp);
//        ibnext.setBackgroundResource(R.drawable.ic_navigate_next_black_24dp);

        setActiveIndicatorColor(android.R.color.black);
        setInactiveIndicatorColor(android.R.color.white);

        shouldDarkenButtonsLayout(true);
        setDividerColor(Color.BLACK);
        setDividerHeight(5);

        OnboarderPage onboarderPage1 = new OnboarderPage("Elo", "Thinking of buying a gadget? Would you like to get personal review from people who have used the same gadgets? Elo is here to help. Elo is a community based application which allows people to give reviews about the gadgets they have used.", R.drawable.screen_1);
        onboarderPage1.setTitleColor(R.color.black);
        onboarderPage1.setTitleTextSize(25);
        onboarderPage1.setDescriptionColor(R.color.black);
        onboarderPage1.setDescriptionTextSize(15);
        onboarderPage1.setBackgroundColor(R.color.white);
        onboarderPages.add(onboarderPage1);

        OnboarderPage onboarderPage2 = new OnboarderPage("Elo is easy to use.","Click a picture, post a review and rate the reviews. Elo has a great interactive features and is easy to navigate.",R.drawable.screen_2);
        onboarderPage2.setTitleColor(R.color.black);
        onboarderPage2.setTitleTextSize(25);
        onboarderPage2.setDescriptionColor(R.color.black);
        onboarderPage2.setDescriptionTextSize(15);
        onboarderPage2.setBackgroundColor(R.color.white);
        onboarderPages.add(onboarderPage2);

        setOnboardPagesReady(onboarderPages);
    }

    @Override
    protected void onSkipButtonPressed()
    {
        super.onSkipButtonPressed();

        //Go to Home Screen. Currently kept it as onboarding screen.

        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    @Override
    public void onFinishButtonPressed()
    {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }


}
