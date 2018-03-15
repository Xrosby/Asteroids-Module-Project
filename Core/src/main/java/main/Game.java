package main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import data.GameData;
import services.IEntityProcessingService;
import services.IGamePluginService;
import data.World;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javafx.scene.control.Alert;
import managers.GameInputProcessor;
import events.Event;
import events.EventType;
import data.Entity;
import entityparts.ShapePart;
import org.openide.util.Lookup;

public class Game
        implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;

    private final GameData gameData = new GameData();
    private List<IEntityProcessingService> entityProcessors = new ArrayList<>();
    private World world = new World();
    private Lookup lookup = Lookup.getDefault();

    @Override
    public void create() {

        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );
        
        
        // TODO: Her skal være en lookup listener
        // TODO: Lookup listener skal oprettes i en metode i Game klassen
       
        

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getPluginServices()) {
            iGamePlugin.start(gameData, world);
        }
    }

    @Override
    public void render() {

        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        update();

        draw();

        gameData.getKeys().update();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }

        for (Event e : gameData.getEvents()) {
            if (e.getEventType() == EventType.GAMEOVER) {
     
                
                for(Entity entity : world.getEntities()) {
                    world.removeEntity(entity);
                }
                
                create();
                gameData.removeEvent(e);
            }
        }
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {
            if (entity.getPart(ShapePart.class) != null) {
                ShapePart shapePart = entity.getPart(ShapePart.class);

                sr.setColor(1, 1, 1, 1);

                sr.begin(ShapeRenderer.ShapeType.Line);

                float[] shapex = shapePart.getShapex();
                float[] shapey = shapePart.getShapey();

                for (int i = 0, j = shapex.length - 1;
                        i < shapex.length;
                        j = i++) {

                    sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
                }

                sr.end();

            } else {
                System.out.println(entity.getType() + " does not have a shapepart");
            }

        }

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    private Collection<? extends IGamePluginService> getPluginServices() {
        return lookup.lookupAll(IGamePluginService.class);
       // return SPILocator.locateAll(IGamePluginService.class);
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return lookup.lookupAll(IEntityProcessingService.class);
      //  return SPILocator.locateAll(IEntityProcessingService.class);
    }
}
