/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.Node;
import java.util.Random;

/**
 *
 * @author Bawb
 */
public class BaleManager extends AbstractAppState {
    
    private SimpleApplication app;
    private Node              baleNode;
    private PlayerManager     playerManager;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.app          = (SimpleApplication) app;
        setPlayerManager();
        createBaleNode();
    }
    
    private void setPlayerManager() {
        playerManager = app.getStateManager().getState(PlayerManager.class);
    }
    
    private void createBaleNode() {
        baleNode =  new Node();
        baleNode.setLocalTranslation(0, 3, 0);
        baleNode.scale(3);
        app.getRootNode().attachChild(baleNode);
    }
    
    public Node getBaleNode() {
        return baleNode;
    }
    
    private void createBale() {
        Node bale = (Node) app.getAssetManager().loadModel("Models/Hay.j3o");
        baleNode.attachChild(bale);
        bale.scale(.5f);
        placeBale(bale);
    }
    
    private void placeBale(Node bale) {
    
        int xSpot     = randInt(16,96);
        int zSpot     = randInt(1,64);
        int negChance = randInt(1,2);
        int negMult   = 1;
        
        if (negChance == 1)
        negMult = -1;
        
        bale.setLocalTranslation(xSpot, -.25f , zSpot*negMult);
        bale.rotate(0, -randInt(1,3), 0);
        
    }
    
    private int randInt(int min, int max) {
        
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
        
    }
    
    public void checkCollision() {
    
        CollisionResults results = new CollisionResults();
        baleNode.collideWith(playerManager.getPlayer().getModel().getWorldBound(), results);
        
        if (results.size() > 2) {
            results.getCollision(0).getGeometry().getParent().removeFromParent();
            playerManager.setScore(playerManager.getScore()+1);
        }
        
    }
    
    @Override
    public void update(float tpf) {
        
        checkCollision();
        
        if (baleNode.getQuantity() < 10) {
            createBale();
        }
        
        for (int i = 0; i < baleNode.getQuantity(); i++) {
        
            Node currentBale = (Node) baleNode.getChild(i);
            int  ms          = playerManager.getDifficulty();
            
            currentBale.move(-ms*tpf,0, playerManager.getMoveDir()*tpf);
            currentBale.rotate(5*tpf,0,5*tpf);
            
            if (currentBale.getLocalTranslation().x <= -128) {
                
                currentBale.removeFromParent();
                
            }
            
        }
        
    }
    
}
