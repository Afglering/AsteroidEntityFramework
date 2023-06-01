package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.components.IProcessor;
import dk.sdu.mmmi.cbse.components.PluginInjection;
import dk.sdu.mmmi.cbse.managers.GameInputProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component("game")
public class Game implements ApplicationListener {
    private static OrthographicCamera cam;
    private ShapeRenderer sr;

    private final GameData gameData = new GameData();

    private World world = new World();

    private AnnotationConfigApplicationContext component;

    public Game() {
        this.component = new AnnotationConfigApplicationContext();
        this.component.scan("dk.sdu.mmmi.cbse.components");
        this.component.refresh();
    }

    private void updateCam(int width, int height) {
        gameData.setDisplayWidth(width);
        gameData.setDisplayHeight(height);

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate((float) gameData.getDisplayWidth() / 2, (float) gameData.getDisplayHeight() / 2);
        cam.update();
    }
    @Override
    public void create() {

        sr = new ShapeRenderer();

        if (
                gameData.getDisplayWidth() != Gdx.graphics.getWidth()
                        || gameData.getDisplayHeight() != Gdx.graphics.getHeight()
        ) {
            this.updateCam(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );

        ((PluginInjection) component.getBean("pluginInjector")).startPlugins(gameData, world);
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
        ((IProcessor) component.getBean("processInjector")).process(gameData, world);
        ((IProcessor) component.getBean("postProcessInjector")).process(gameData, world);
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {
                sr.setColor(1, 1, 1, 1);

                sr.begin(ShapeRenderer.ShapeType.Line);

            float[] shapex = entity.getShapeX();
            float[] shapey = entity.getShapeY();

            for (int i = 0, j = shapex.length - 1;
                    i < shapex.length;
                    j = i++) {

                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }

            sr.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        this.updateCam(width, height);
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
}
