/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.input.event.TouchEvent;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import tonegod.gui.controls.text.TextElement;
import tonegod.gui.core.Screen;

/**
 *
 * @author Bawb
 */
public class GuiManager {
    
    private Screen            screen;
    private TextElement       scoreDisplay;
    private TextElement       restartDisplay;
    private TextElement       highDisplay;
    private SimpleApplication app;
    
    public GuiManager(SimpleApplication app) {
        this.app = app;
        createScreen();
        createScoreDisplay();
        createRestartDisplay();
        createHighDisplay();
        app.getViewPort().setBackgroundColor(ColorRGBA.Cyan);
    }
    
    private void createScreen() {
        
        screen = new Screen(app, "tonegod/gui/style/atlasdef/style_map.gui.xml") {
        
        @Override
        public void onTouchEvent(TouchEvent evt) {
            
            InteractionManager a = app.getStateManager().getState(PlayerManager.class).getInteractionManager();
            float xSpot          = evt.getX();
            
            
            if (evt.getType() == evt.getType().DOWN) {
                
                if (xSpot < screen.getWidth()/2)
                a.setLeft(true);
                else
                a.setRight(true);
                
            }
            
            else if (evt.getType() == evt.getType().UP) {
        
                if (app.getStateManager().getState(PlayerManager.class).getIsDead()) {
                    app.getStateManager().getState(PlayerManager.class).restart();
                }
                a.setLeft(false);
                a.setRight(false);
         
            }

            super.onTouchEvent(evt);

        }
        
        };
        screen.setUseTextureAtlas(true,"tonegod/gui/style/atlasdef/atlas.png");
        app.getGuiNode().addControl(screen);
        screen.setUseMultiTouch(true);
    }
    
    public Screen getScreen() {
        return screen;
    }
    
    private void createScoreDisplay() {
        
        BitmapFont font = app.getAssetManager().loadFont("Interface/Fonts/UnrealGold.fnt");
        
        scoreDisplay  = new TextElement(screen, "Score Display", Vector2f.ZERO, new Vector2f(300,50), font) { 
            
            @Override
            public void onUpdate(float tpf) {}
            @Override
            public void onEffectStart() {}
            @Override
            public void onEffectStop() {}
            
        };
       
        screen.addElement(scoreDisplay);
        scoreDisplay.setFontSize(screen.getWidth()/18);
        scoreDisplay.setDimensions(screen.getWidth()/5, screen.getHeight()/6);
        placeScoreDisplay();
        
    }
    
    public void placeScoreDisplay() {
        scoreDisplay.setPosition(screen.getWidth() / 1.1f - scoreDisplay.getWidth()*1.25f, screen.getHeight() / 1.1f - scoreDisplay.getHeight()*.9f);
    }
    
    private void createRestartDisplay() {
    
        BitmapFont font = app.getAssetManager().loadFont("Interface/Fonts/UnrealGold.fnt");
        
        restartDisplay  = new TextElement(screen, "Restart Display", Vector2f.ZERO, new Vector2f(300,50), font) { 
            
            @Override
            public void onUpdate(float tpf) {}
            @Override
            public void onEffectStart() {}
            @Override
            public void onEffectStop() {}
            
        };
       
        screen.addElement(restartDisplay);
        restartDisplay.setFontSize(screen.getWidth()/18);
        restartDisplay.setDimensions(screen.getWidth() / 6, screen.getHeight() / 9);
        restartDisplay.setText("Touch to R estart");
        restartDisplay.setPosition(screen.getWidth() / 2f - restartDisplay.getWidth() * 1.5f, screen.getHeight() / 1.5f - restartDisplay.getHeight() / 2);
        restartDisplay.hide();         
        
    }
    
    private void createHighDisplay() {
    
        BitmapFont font = app.getAssetManager().loadFont("Interface/Fonts/UnrealGold.fnt");
        
        highDisplay  = new TextElement(screen, "High Display", Vector2f.ZERO, new Vector2f(300,50), font) { 
            
            @Override
            public void onUpdate(float tpf) {}
            @Override
            public void onEffectStart() {}
            @Override
            public void onEffectStop() {}
            
        };
       
        screen.addElement(highDisplay);
        highDisplay.setFontSize(screen.getWidth()/18);
        highDisplay.setDimensions(screen.getWidth() / 6, screen.getHeight() / 9);
        highDisplay.setText("         ");
        highDisplay.setPosition(restartDisplay.getPosition().x, restartDisplay.getPosition().y - scoreDisplay.getHeight()*1.25f);
        highDisplay.hide(); 
        
    }
    
    public TextElement getHighDisplay() {
        return highDisplay;
    }
    
    public TextElement getRestartDisplay() {
        return restartDisplay;
    }
    
    public void setScoreDisplay(int score) {
        scoreDisplay.setText("Score: " + score);
    } 
    
    public void showEndGame(boolean newHigh, int score, int currentHighScore) {
        
        if (newHigh) {
            scoreDisplay.setText("New H igh Score: " + score);
            highDisplay.setText("Old H ighScore: " + currentHighScore);
        }
        
        else {
            scoreDisplay.setText("  Final Score: " + score +  System.getProperty("line.separator"));
            highDisplay.setText("    H ighScore: " + currentHighScore);
        }
        
        highDisplay.show();
        scoreDisplay.setPosition(restartDisplay.getPosition().x, restartDisplay.getPosition().y - screen.getHeight()/10);
        
    }
    
}
