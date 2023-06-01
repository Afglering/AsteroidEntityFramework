package dk.sdu.mmmi.cbse.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;

public class  AsteroidPlugin implements IGamePluginService {

    public AsteroidPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        for (int i = 0; i < 5; i++) {
            // Add entities to the world
            Entity asteroid = createAsteroid(gameData);
            world.addEntity(asteroid);
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove all asteroids
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Asteroid) {
                world.removeEntity(entity);
            }
        }
    }

    private Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        float speed = (float) Math.random() * 10f + 20f;
        float radians = (float) Math.random() * 2 * 3.1415f;

        Random random = new Random();
        float x = random.nextFloat() * gameData.getDisplayWidth();
        float y = random.nextFloat() * gameData.getDisplayHeight();

        asteroid.setRadius(20);
        asteroid.add(new MovingPart(0, speed, speed, 0));
        asteroid.add(new PositionPart(x, y, radians));
        asteroid.add(new LifePart(3));

        return asteroid;
    }
}
