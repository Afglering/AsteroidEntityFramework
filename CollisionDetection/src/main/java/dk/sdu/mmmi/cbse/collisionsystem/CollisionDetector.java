package dk.sdu.mmmi.cbse.collisionsystem;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class CollisionDetector implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        // two for loops for all entities in the world
        for (Entity entity : world.getEntities()) {
            for (Entity collisionDetection : world.getEntities()) {
                // get life parts on all entities
                LifePart entityLife = entity.getPart(LifePart.class);

                // if the two entities are identical, skip the iteration
                if (entity.getID().equals(collisionDetection.getID())) {
                    continue;

                    // remove entities with zero in expiration
                }

                // CollisionDetection
                if (this.collides(entity, collisionDetection)) {
                    // if entity has been hit, and should have its life reduced
                    if (entityLife.getLife() > 0) {
                        entityLife.setLife(entityLife.getLife() - 1);
                        entityLife.setIsHit(true);
                        // if entity is out of life - remove
                        if (entityLife.getLife() <= 0) {
                            world.removeEntity(entity);
                        }
                    }
                }
            }
        }
    }

    public boolean collides(Entity entity, Entity entity2) {
        if (entity == entity2) {
            return false; // same entity
        }

        PositionPart entPos = entity.getPart(PositionPart.class);
        PositionPart ent2Pos = entity2.getPart(PositionPart.class);
        float dx = entPos.getX() - ent2Pos.getX();
        float dy = entPos.getY() - ent2Pos.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        return distance < (entity.getRadius() + entity2.getRadius());
    }
}

