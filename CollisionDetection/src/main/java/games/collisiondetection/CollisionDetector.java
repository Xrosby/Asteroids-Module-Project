/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package games.collisiondetection;

import services.IGamePluginService;
import data.*;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author MadsNorby
 */

@ServiceProvider(service = IGamePluginService.class)

public class CollisionDetector implements IGamePluginService{

    @Override
    public void start(GameData gameData, World world) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stop(GameData gameData, World world) {
     //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
