package games.player;

import data.Entity;
import events.Event;
import events.EventType;
import data.GameKeys;
import data.GameData;
import services.IEntityProcessingService;
import data.World;
import entityparts.PositionPart;
import entityparts.MovingPart;
import entityparts.ShapePart;
import org.openide.util.lookup.ServiceProviders;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author jcs
 */
@ServiceProvider(service = IEntityProcessingService.class)

public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);

            movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));

            if (gameData.getKeys().isPressed(GameKeys.SPACE)) {
                Event bulletEvent = new Event(player, EventType.BULLET);

                gameData.addEvent(bulletEvent);
            }

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);

            updateShape(player);
        }
    }

    private void updateShape(Entity entity) {

        PositionPart positionPart = entity.getPart(PositionPart.class);
        ShapePart shapePart = entity.getPart(ShapePart.class);

        float[] shapex = shapePart.getShapex();
        float[] shapey = shapePart.getShapey();

        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 8);
        shapey[0] = (float) (y + Math.sin(radians) * 8);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 8);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8);

        shapePart.setShapex(shapex);
        shapePart.setShapey(shapey);

        // entity.setShapeX(shapex);
        // entity.setShapeY(shapey);
    }

}
