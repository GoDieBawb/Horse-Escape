/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigbawb.horseescape;

import android.app.Activity;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import mygame.PlayerManager;

/**
 *
 * @author Bawb
 */
public class AdManager extends AbstractAppState {
    
    private SimpleApplication app;
    private AppStateManager   stateManager;
    private AdActivity        adActivity;
    private Activity          mainActivity;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.app          = (SimpleApplication) app;
        this.stateManager = stateManager;
    }
    
    public void setMainActivity(Activity mainActivity) {
        this.mainActivity = mainActivity;
    }
    
    public void createAdActivity() {        
        adActivity = new AdActivity();
    }
    
    @Override
    public void update(float tpf) {
    
        boolean isDead = stateManager.getState(PlayerManager.class).getIsDead();
        
        if (isDead) {
            
        }
        
    }
    
}
