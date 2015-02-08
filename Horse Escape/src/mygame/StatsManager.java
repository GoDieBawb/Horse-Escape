/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.state.AppStateManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author bob
 */
public class StatsManager {
    
    private int    highScore;
    
    public StatsManager(AppStateManager stateManager) {
    
        loadInfo(stateManager);
        
    }
    
    public final void loadInfo(AppStateManager stateManager) {
  
        Yaml yaml         = new Yaml();
        LinkedHashMap map;
        String filePath;
      
        try {
      
            filePath = stateManager.getState(AndroidManager.class).filePath;
      
        }
      
        catch (Exception e) {
      
            filePath = System.getProperty("user.home")+ "/";
      
        }
      
        try {
          
            File file            = new File(filePath + "Save" + ".yml");
            FileInputStream fi   = new FileInputStream(file);
            Object obj           = yaml.load(fi);
            map                  = (LinkedHashMap) obj;
            fi.close();
          
        }
      
        catch (Exception e) {
          
            highScore      = 0;
            saveInfo(stateManager);
            loadInfo(stateManager);
            return;
      
        }

        highScore  = (Integer)  map.get("HighScore");
      
    }
  
    public void saveInfo(AppStateManager stateManager) {
      
      HashMap contents = new HashMap();
      contents.put("HighScore", highScore);
      
      DumperOptions options = new DumperOptions();
      options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
      options.setAllowUnicode(true);
      Yaml yaml = new Yaml(options);      
      
      
      String filePath;
      
      try {
      
          filePath = stateManager.getState(AndroidManager.class).filePath;
      
      }
      
      catch (Exception e) {
      
          filePath = System.getProperty("user.home") + "/";
      
      }
      
      File file = new File(filePath + "Save" + ".yml");
      
      try {
          
          FileWriter fw  = new FileWriter(file);
          yaml.dump(contents, fw);
          fw.close();
          
      }
      
      catch(Exception e) {
      
      }
  
    }
    
    public int getHighscore() {
        return highScore;
    }
    
    public void saveNewHigh(int newHighScore, AppStateManager stateManager) {
        
        highScore = newHighScore;
        saveInfo(stateManager);
        
    }
    
}
