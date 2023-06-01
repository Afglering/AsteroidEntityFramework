import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.playersystem.PlayerPlugin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

public class PlayerShipTest {

    @Mock
    private GameData gameData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(gameData.getDisplayWidth()).thenReturn((int) 800f);
        when(gameData.getDisplayHeight()).thenReturn((int) 600f);
    }

    @Test
    void testCreatePlayerShip() {
        Entity playerShip = PlayerPlugin.createPlayerShip(gameData);

        // Verify that the player ship has the correct components
        Assertions.assertNotNull(playerShip.getPart(MovingPart.class));
        Assertions.assertNotNull(playerShip.getPart(PositionPart.class));
        Assertions.assertNotNull(playerShip.getPart(LifePart.class));

        // Verify that the components have the correct values
        MovingPart movingPart = playerShip.getPart(MovingPart.class);
        Assertions.assertEquals(10f, movingPart.getDeceleration());
        Assertions.assertEquals(200f, movingPart.getAcceleration());
        Assertions.assertEquals(300f, movingPart.getMaxSpeed());
        Assertions.assertEquals(5f, movingPart.getRotationSpeed());

        PositionPart positionPart = playerShip.getPart(PositionPart.class);
        Assertions.assertEquals(400f, positionPart.getX());
        Assertions.assertEquals(300f, positionPart.getY());
        Assertions.assertEquals(3.1415f / 2, positionPart.getRadians());

        LifePart lifePart = playerShip.getPart(LifePart.class);
        Assertions.assertEquals(1, lifePart.getLife());
    }
}
