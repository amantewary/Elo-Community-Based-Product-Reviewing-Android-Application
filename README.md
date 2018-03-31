# Elo - Community based product reviewing application


Elo is an Android application which allows people to share and contribute their experiences of using gadgets they own. This application allows people who intend to make purchases to get personal reviews and gain the buying power from different people’s experience. Elo provides a platform for the user to come together to discover the most relevant product based on user comparative analysis of their reviews.

## Libraries

- **CircleImageView :**  Copyright 2014 - 2017 Henning Dodenhof

	This is a library which customizes the image view to make it look rounded.


    Licensed under the Apache License, Version 2.0 (the "License");you may not use this file except in compliance with the License.You may obtain a copy of the License at [Apache](http://www.apache.org/licenses/LICENSE-2.0)

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
    
  
- **CircularReveal** : The MIT License (MIT) Copyright (c) 2016 Abdullaev Ozodrukh

    Circular reveal creates animations in apps and guides the users and gives an understanding about the current state of the application without recall or recognition of the previous steps.
      

    Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
    
    " THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR THERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. "
    
   
   Copyright 2015 Miguel Catalan Bañuls
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
    	http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



## Code Examples

Currently, the user credentials and activities are not being stored and tracked in the database when the user opts for the  Gmail login due to time constraints. These changes are will be incorporated in the future.

**Problem 1: Method for handling the user signin**

A short description.
```
googleSignIn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
});

private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(LoginActivity.class.getCanonicalName(), "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }


```

## Feature Section

- Splash screen 
- Registration Screen 
- Google Login
- User Home Screen
- Email Login
- User Info Screen
- User Logout
- User Interface
- User Interface
- User Profile
- Search
- Users
- Navigation
- Search
- Feedback

## Final Project Status
The Minimum functionalities of the project have been completed and tested and we will be focusing on building the deliverables for the expected and bonus functionalities.

#### Minimum Functionality
- Sign-up page and Sign-in using Google account for the new user. (Completed)
- Sign-in page for the existing user. (Completed)
- The user interface may follow Nielsen’s Heuristics Principle[1]. (Completed)

#### Expected Functionality

- The app follows material design guidelines (Completed)	
- The app allows the user to edit their information on their profile page. (Completed)
- The app allows the user to choose their profile picture from existing list of profile avatar,  the photo gallery or camera. (In Progress)
- The app allows the user to search the device they are looking for. (In Progress)
- The app allows the user to view the profile of another user. (In Progress)
- The app allows the user to write a review about a product they have on their profile. (In Progress)
- The app allows the user to add a picture of the product they are reviewing. (In Progress)
- The app displays user’s contribution points on their profile page based on the ratings they received on their review. (In Progress)
- The app allows the user to add products to a wishlist. (In Progress)


#### Bonus Functionality
- The app allows the user to rate the reviews of another user. (In Progress)
- The app allows the user to send messages to another user. (In Progress)
- The app allows the user to search other users using their nickname. (In Progress)
- The app allows the user to navigate using gestures within the app. (In Progress)
- The app allows the user to comment on reviews of another user. (In Progress)

## Sources

[1] J. Nielsen and R. Molich, “Heuristic evaluation of user interfaces,” Proceedings of the SIGCHI conference on Human factors in computing systems Empowering people - CHI 90, 1990.

[2] "Introduction - Material Design", Material Design, 2018. [Online]. Available: https://material.io/guidelines/material-design/introduction.html. [Accessed: 31- Mar- 2018].

[3] "hdodenhof/CircleImageView", GitHub, 2018. [Online]. Available: https://github.com/hdodenhof/CircleImageView. [Accessed: 31- Mar- 2018].

[4] "MaterialSearchView - Miguel Catalan", Miguelcatalan.info, 2018. [Online]. Available: http://miguelcatalan.info/2015/09/23/MaterialSearchView/. [Accessed: 31- Mar- 2018].

[5] "varunest/SparkButton", GitHub, 2018. [Online]. Available: https://github.com/varunest/SparkButton. [Accessed: 31- Mar- 2018].

[6] "Recent Support Library Revisions | Android Developers", Developer.android.com, 2018. [Online]. Available: https://developer.android.com/topic/libraries/support-library/revisions.html. [Accessed: 31- Mar- 2018].

[7] "square/picasso", GitHub, 2018. [Online]. Available: https://github.com/square/picasso. [Accessed: 31- Mar- 2018].

[8] "whalemare/sheetmenu", GitHub, 2018. [Online]. Available: https://github.com/whalemare/sheetmenu. [Accessed: 31- Mar- 2018].

[9] V. Bauer, "android-crop | Android-Arsenal.com", Android Arsenal, 2018. [Online]. Available: https://android-arsenal.com/details/1/205. [Accessed: 31- Mar- 2018].

[10] "umano/AndroidSlidingUpPanel", GitHub, 2018. [Online]. Available: https://github.com/umano/AndroidSlidingUpPanel. [Accessed: 31- Mar- 2018].

[11] "ozodrukh/CircularReveal", GitHub, 2018. [Online]. Available: https://github.com/ozodrukh/CircularReveal. [Accessed: 31- Mar- 2018].