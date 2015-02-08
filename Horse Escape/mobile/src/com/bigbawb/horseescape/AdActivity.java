/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigbawb.horseescape;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.gms.ads.*;

/**
 *
 * @author Bawb
 */
public class AdActivity extends Activity {
    
    private InterstitialAd interstitial;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("CREATING THIS BITCH");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Create the interstitial.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId("ca-app-pub-9434547190848397/1026349065");

        // Create ad request.
        AdRequest adRequest = new AdRequest.Builder().build();

        // Begin loading your interstitial.
        interstitial.loadAd(adRequest);

    }

    public InterstitialAd getAd() {
        return interstitial;
    }
    
    // Invoke displayInterstitial() when you are ready to display an interstitial.
    public void displayInterstitial() {
        this.runOnUiThread(new Runnable() {
        
            @Override
            public void run() {
                
                if (interstitial.isLoaded()) {
                    interstitial.show();
                }
                
            }
            
        });
        
    }
    
}
