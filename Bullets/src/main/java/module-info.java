import dk.sdu.mmmi.cbse.bulletsystem.BulletControlSystem;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.bulletsystem.BulletPlugin;

module Bullet {
    exports dk.sdu.mmmi.cbse.bulletsystem;
    requires Common;
    requires CommonBullet;

    provides IEntityProcessingService with BulletControlSystem;
    provides IGamePluginService with BulletPlugin;
    provides BulletSPI with BulletControlSystem;
}