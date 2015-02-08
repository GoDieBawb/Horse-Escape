/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.Node;

/**
 *
 * @author Bawb
 */
public class PlayerManager extends AbstractAppState {
    
    private SimpleApplication  app;
    private Player             player;
    private int                score;
    private int                difficulty;
    private int                moveDir;
    private InteractionManager interactionManager;
    private GuiManager         guiManager;
    private Node               buildingNode;
    private boolean            isDead;
    private StatsManager       statsManager;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.app          = (SimpleApplication) app;
        createPlayer();
        setDifficulty();
        createInteractionManager();
        createGuiManager();
        createStatsManager();
    }
    
    public boolean getIsDead() {
        return isDead;
    }
    
    public void restart() {
        app.getStateManager().getState(BuildingManager.class).getBuildingNode().detachAllChildren();
        isDead = false;
        player.revive(app.getStateManager());
        setScore(0);
        guiManager.getRestartDisplay().hide();
        guiManager.getHighDisplay().hide();
        guiManager.placeScoreDisplay();
    }
    
    private void createStatsManager() {
        statsManager = new StatsManager(app.getStateManager());
    }
    
    public void setBuildingNode(Node buildingNode) {
        this.buildingNode = buildingNode;
    }
    
    private void createGuiManager() {
        guiManager = new GuiManager(app);
    }
    
    public GuiManager getGuiManager() {
        return guiManager;
    }
    
    private void createInteractionManager() {
        interactionManager = new InteractionManager(app);
    }
    
    public InteractionManager getInteractionManager() {
        return interactionManager;
    }
    
    private void setDifficulty() {
    
        difficulty = difficulty + 1;
        AnimChannel am = player.getAnimChannel();
        am.setSpeed(am.getSpeed() + .05f);
        
        if (am.getSpeed() < 1)
        am.setSpeed(1);
        
        if (am.getSpeed() > 3)
        am.setSpeed(3);
        
        if (difficulty < 30)
        difficulty = 30;
        
        if (difficulty > 100) 
        difficulty = 100;
        
    }
    
    public int getDifficulty() {
        return difficulty;
    }
    
    public void setScore(int score) {
        this.score = score;
        setDifficulty();
    }
    
    public int getScore() {
        return score;
    }
    
    private void createPlayer() {
    
        player = new Player(app.getStateManager());
        app.getRootNode().attachChild(player);
        
    }
    
    public Player getPlayer() {
        return player;
    }
    
    private void setMoveDir() {
        
        if (interactionManager.isLeft())
        moveDir = 10 + difficulty/5;
        
        else if (interactionManager.isRight())
        moveDir = -10 - difficulty/5;
        
        else
        moveDir = 0;
        
    }
    
    public int getMoveDir() {
        return moveDir;
    }
    
    @Override
    public void update(float tpf) {
    
        if (!isDead) {
            
            setMoveDir();
            guiManager.setScoreDisplay(score);
        
            CollisionResults results = new CollisionResults();
            buildingNode.collideWith(player.getModel().getWorldBound(), results);
        
            if (results.size() > 2) {
                
                int currentHighScore = statsManager.getHighscore();
                boolean newHigh      = false;
                
                if (currentHighScore < score) {
                    
                    statsManager.saveNewHigh(score, app.getStateManager());
                    newHigh = true;
                    
                }
                
                isDead     = true;
                difficulty = 0;
                player.die();
                guiManager.getRestartDisplay().show();
                guiManager.showEndGame(newHigh, score, currentHighScore);
                moveDir = 0;
                
            }
        
        } 
        
    }
    
}
