package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        setShowSettings(false);
        setDisplayStatView(false);
        setDisplayFps(false);
        flyCam.setEnabled(false);
        //flyCam.setMoveSpeed(50);
        cam.setLocation(new Vector3f(-64*3,6,0));
        cam.lookAt(new Vector3f(0,3,0), new Vector3f(0,1,0));
        stateManager.attach(new PlayerManager());
        stateManager.attach(new SceneManager());
        stateManager.attach(new BuildingManager());
        stateManager.attach(new BaleManager());
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
}
