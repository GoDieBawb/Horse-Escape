/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector2f;
import tonegod.gui.core.Screen;

/**
 *
 * @author Bawb
 */
public class InteractionManager implements ActionListener {
    
    private SimpleApplication app;
    private InputManager      inputManager;
    private boolean           left = false, right = false;
    
    public InteractionManager(SimpleApplication app) {
        this.app     = app;
        inputManager = app.getInputManager();
        inputManager.setSimulateMouse(true);
        setUpKeys();
    }
    
    public boolean isLeft() {
        return left;
    }
    
    public boolean isRight() {
        return right;
    }
    
    public void setLeft(boolean b) {
        left = b;
    }
    
    public void setRight(boolean b) {
        right = b;
    }
    
    private void setUpKeys() {
        
        inputManager.addMapping("Click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addListener(this, "Click");
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        
    }
    
    @Override
    public void onAction(String binding, boolean isPressed, float tpf) {
        
        boolean isDead       = app.getStateManager().getState(PlayerManager.class).getIsDead();
        
        if (!isDead) {
            
            if (binding.equals("Left")) {
                left = isPressed;
            }
        
            else if (binding.equals("Right")) {
                right = isPressed;
            }
    
            else if (binding.equals("Click")) {
            
                Vector2f clickSpot = inputManager.getCursorPosition();
                float xSpot = clickSpot.getX();
                Screen screen = app.getStateManager().getState(PlayerManager.class).getGuiManager().getScreen();
            
                if (xSpot < screen.getWidth()/2)
                left = isPressed;
                else
                right = isPressed;
            
            }
            
        }
        
        else {
            if (isPressed)  
            app.getStateManager().getState(PlayerManager.class).restart();
        }
        
    }
    
}
