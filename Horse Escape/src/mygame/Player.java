/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;

/**
 *
 * @author Bawb
 */
public class Player extends Node {
    
    private Node        model;
    private AnimChannel animChannel;
    private AnimControl animControl;
    
    public Player(AppStateManager stateManager) {
    
        setModel(stateManager);
        attachChild(model);
        createAnimations();
        run();
        
    }
    
    private void setModel(AppStateManager stateManager) {
    
        model               = (Node) stateManager.getApplication().getAssetManager().loadModel("Models/horse.j3o");
        ShadeChanger shader = new ShadeChanger(stateManager.getApplication().getAssetManager());
        shader.makeUnshaded(model);
        attachChild(model);
        model.rotate(0,1.6f,0);
        model.setLocalTranslation(0,2f,0);
        setLocalTranslation(-59*3,0,0);
        scale(2);
        
    }
    
    public Node getModel() {
        return model;
    }
    
    private void createAnimations() {
        animControl = model.getControl(AnimControl.class);
        animChannel = animControl.createChannel();    
    }
    
    public AnimChannel getAnimChannel() {
        return animChannel;
    }
    
    private void run() {
    
        animChannel.setLoopMode(LoopMode.Loop);
        animChannel.addAllBones();
        animChannel.setAnim("Gallop");
        
    }
    
    public void die() {
        animChannel.setAnim("Rear");
        animChannel.setLoopMode(LoopMode.DontLoop);
        model.rotate(0,0,1.6f);
        model.setLocalTranslation(0, 0, 0);
        animChannel.setSpeed(1);
    }
    
    public void revive(AppStateManager stateManager) {
        model.rotate(0, 0, -1.6f);
        model.setLocalTranslation(0,2f,0);
        run();
    }
    
}
