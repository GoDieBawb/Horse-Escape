/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import java.util.Random;

/**
 *
 * @author Bawb
 */
public class BuildingManager extends AbstractAppState {
    
    private Node              buildingNode;
    private SimpleApplication app;
    private ShadeChanger      shader;
    private Long              delay;
    private PlayerManager     playerManager;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
    
        this.app = (SimpleApplication) app;
         createPlayerManager();
        createShadeChanger();
        createBuildingNode();
    }
    
    private void createPlayerManager() {
        playerManager = app.getStateManager().getState(PlayerManager.class);
    }
    
    private void createShadeChanger() {
        shader = new ShadeChanger(app.getAssetManager());
    }
    
    private void createBuildingNode() {
        delay        = System.currentTimeMillis() / 1000;
        buildingNode = new Node();
        buildingNode.scale(3);
        app.getRootNode().attachChild(buildingNode);
        playerManager.setBuildingNode(buildingNode);
    }
    
    public Node getBuildingNode() {
        return buildingNode;
    }
    
    private void createBuilding() {
        
        Node building      = new Node();
        Node buildingModel = (Node) app.getAssetManager().loadModel("Models/Structures/House" + String.valueOf(randInt(1,4)) + ".j3o");
        building.attachChild(buildingModel);
        shader.makeUnshaded(building);
        buildingNode.attachChild(building);
        placeBuilding(building);
        delay = System.currentTimeMillis() / 1000;
        
    }
    
    private void placeBuilding(Node building) {
    
        int xSpot     = randInt(16,96);
        int zSpot     = randInt(0,56);
        int negChance = randInt(1,2);
        int negMult   = 1;
        
        if (negChance == 1)
        negMult = -1;
        
        building.setLocalTranslation(xSpot, -.25f , zSpot*negMult);
        building.rotate(0, -randInt(1,3), 0);
        
    }
    
    private int randInt(int min, int max) {
        
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
        
    }
    
    @Override
    public void update(float tpf) {
        
        if (buildingNode.getQuantity() < 12) {
            
            Long d = (System.currentTimeMillis() / 1000 - delay);
            
            //if (d > .0001f)
            createBuilding();
        }
        
        int  ms = playerManager.getDifficulty();
        
        for (int i = 0; i < buildingNode.getQuantity(); i++) {
        
            Node currentBuilding = (Node) buildingNode.getChild(i);
            
            currentBuilding.move(-ms*tpf,0, playerManager.getMoveDir()*tpf);
            
            if (currentBuilding.getLocalTranslation().x <= -128) {
            
                currentBuilding.removeFromParent();
                
            }
            
        }
        
    }
    
}
