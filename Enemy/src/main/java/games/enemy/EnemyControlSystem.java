/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package games.enemy;

import data.*;
import entityparts.*;
import services.*;
import events.*;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author MadsNorby
 */
@ServiceProvider(service = IEntityProcessingService.class)
public class EnemyControlSystem implements IEntityProcessingService {

    private Random random = new Random();
    private boolean direction_right;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);

            if (random.nextDouble() > 0.8) {
                direction_right = (direction_right == false) ? true : false;
            }

            gameData.addEvent(new Event(enemy, EventType.ENEMYSHOT));

            movingPart.setLeft(!direction_right);
            movingPart.setRight(direction_right);
            movingPart.setUp(true);

            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);

            updateShape(enemy);
        }
    }

    private void updateShape(Entity entity) {

        //float[] shapex = entity.getShapeX();
        //float[] shapey = entity.getShapeY();
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
