/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;
import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {

   
        
        //https://opengameart.org/content/spaceship-set-4-pixel-art-space-ships#
        
        
        LwjglApplicationConfiguration cfg
                = new LwjglApplicationConfiguration();
        cfg.title = "Asteroids";
        cfg.width = 1400;
        cfg.height = 900;
        cfg.useGL30 = false;
        cfg.resizable = false;

        new LwjglApplication(new Game(), cfg);

    }

}
