/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package games.bullet;

import services.IGamePluginService;
import data.GameData;
import data.World;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author MadsNorby
 */

@ServiceProvider(service = IGamePluginService.class)
public class BulletPlugin implements IGamePluginService {
    

    @Override
    public void start(GameData gd, World world) {
         //no implementation
    }


    @Override
    public void stop(GameData gd, World world) {
   //no implementation
    }

}
