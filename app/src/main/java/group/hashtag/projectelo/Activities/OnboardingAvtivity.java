package group.hashtag.projectelo.Activities;

/**
 * Created by deeks on 3/31/2018.
 */


//        MIT License
//
//        Copyright (c) 2017 Dzmitry Chyrta, Daniel Morales
//
//        Permission is hereby granted, free of charge, to any person obtaining a copy
//        of this software and associated documentation files (the "Software"), to deal
//        in the Software without restriction, including without limitation the rights
//        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//        copies of the Software, and to permit persons to whom the Software is
//        furnished to do so, subject to the following conditions:
//
//        The above copyright notice and this permission notice shall be included in all
//        copies or substantial portions of the Software.
//
//        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//        SOFTWARE.

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
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


        buttonSetTextColor(Color.BLACK);

        ImageButton ibnext = findViewById(R.id.ib_next);
        ibnext.setImageResource(R.drawable.ic_navigate_next_black_24dp);
        ibnext.setBackgroundResource(R.drawable.ic_navigate_next_black_24dp);


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

        OnboarderPage onboarderPage3 = new OnboarderPage("What's next", "What are you waiting for? Go ahead and fill in your details and begin using Elo.",R.drawable.screen_3);
        onboarderPage3.setTitleColor(R.color.black);
        onboarderPage3.setTitleTextSize(25);
        onboarderPage3.setDescriptionColor(R.color.black);
        onboarderPage3.setDescriptionTextSize(15);
        onboarderPage3.setBackgroundColor(R.color.white);
        onboarderPages.add(onboarderPage3);


        setOnboardPagesReady(onboarderPages);
    }

    @Override
    protected void onSkipButtonPressed()
    {
        super.onSkipButtonPressed();

        //Go to Home Screen. Currently kept it as onboarding screen.

        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }



    private void buttonSetTextColor(int clr)
    {
        Button skip = findViewById(R.id.btn_skip);
        skip.setTextColor(clr);

        Button finish = findViewById(R.id.btn_finish);
        finish.setTextColor(clr);
    }

    @Override
    public void onFinishButtonPressed()
    {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed()
    {
//        super.onBackPressed();
    }
}
