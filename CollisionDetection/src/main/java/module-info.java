import dk.sdu.mmmi.cbse.collisionsystem.CollisionDetector;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;

    provides IPostEntityProcessingService with CollisionDetector;
}