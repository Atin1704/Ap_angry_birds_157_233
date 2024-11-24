package com.birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.data.*;

import java.util.logging.Logger;

public class level_1_screen implements Screen {
    private static final Logger logger = Logger.getLogger(level_1_screen.class.getName());
    private final AssetManager assetManager;
    private Texture background_image;
    private Texture pause_button;
    private Slingshot slingshot;
    private Black_bird bb_1;
    private Red_bird rb_1;
    private Yellow_bird yb_1;
    private Main game_runner;
    private final SpriteBatch spriteBatch;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private ShapeRenderer shapeRenderer;
    StretchViewport viewport;
    Vector2 touchPos;
    private boolean isDragging;
    private Vector2 slingStart;
    private Vector2 slingEnd;
    private boolean isBirdLaunched;

    public level_1_screen(Main main, AssetManager assetManager) {
        logger.info("Initializing level_1_screen");
        this.game_runner = main;
        this.assetManager = assetManager;
        this.spriteBatch = main.batch;
        viewport = new StretchViewport(1200, 1000);
        touchPos = new Vector2();
        world = new World(new Vector2(0, -35.0f), true);
        debugRenderer = new Box2DDebugRenderer();
        shapeRenderer = new ShapeRenderer();

        rb_1 = new Red_bird(world, 230, 217); // Place red bird on slingshot
        bb_1 = new Black_bird(world, 170, 217);
        yb_1 = new Yellow_bird(world, 105, 217);
        slingshot = new Slingshot(world, main.batch, assetManager, 230, 217);
        slingStart = new Vector2(230, 217);
        slingEnd = new Vector2(230, 217);
        isBirdLaunched = false;

        createGround();
    }

    private void createGround() {
        logger.info("Creating ground");
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody; // Ensure ground is a static body
        groundBodyDef.position.set(new Vector2(0, 217));
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vector2(0, 0), new Vector2(viewport.getWorldWidth(), 0));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundShape;
        world.createBody(groundBodyDef).createFixture(fixtureDef);
        groundShape.dispose();
    }

    @Override
    public void show() {
        logger.info("Showing level_1_screen");
        background_image = assetManager.get("Level2_bg.png", Texture.class);
        pause_button = assetManager.get("Pause_icon.png", Texture.class);
    }

    @Override
    public void render(float v) {
        logger.info("Rendering level_1_screen");
        input();
        world.step(1/60f, 6, 2);
        rb_1.update();
        bb_1.update();
        yb_1.update();

        ScreenUtils.clear(Color.BLACK);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(background_image, 0, 0, worldWidth, worldHeight);
        slingshot.render();
        rb_1.render(spriteBatch);
        yb_1.render(spriteBatch);
        bb_1.render(spriteBatch);
        spriteBatch.draw(pause_button, 1080, 910, 65, 65);

        spriteBatch.end();

        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BROWN);
        shapeRenderer.line(slingStart.x, slingStart.y, slingEnd.x, slingEnd.y);
        shapeRenderer.end();

        debugRenderer.render(world, viewport.getCamera().combined);
    }

    private void input() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            logger.info("ESCAPE key pressed, exiting");
            Gdx.app.exit();
        }
        // Handle touch input for pause button
        if (Gdx.input.justTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);

            float pauseButtonX = 1100f;
            float pauseButtonY = 920f;
            float pauseButtonWidth = 65f;
            float pauseButtonHeight = 65f;

            if (touchPos.x >= pauseButtonX && touchPos.x <= (pauseButtonX + pauseButtonWidth)
                && touchPos.y >= pauseButtonY && touchPos.y <= (pauseButtonY + pauseButtonHeight)) {
                logger.info("Pause button clicked");
                game_runner.click.play();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game_runner.setScreen(new pause_screen(game_runner, assetManager, 1));
                    }
                }, 0.25f);  // Delay of 0.25 seconds
            }
        }

        // Handle "Enter" key press for victory screen
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            logger.info("ENTER key pressed, switching to Victory_Screen");
            Victory_Screen v1 = new Victory_Screen(game_runner, assetManager, 1);
            game_runner.setScreen(v1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DEL)) {
            logger.info("DEL key pressed, switching to Defeat_Screen");
            game_runner.setScreen(new Defeat_Screen(game_runner, assetManager, 1));
        }

        // Handle slingshot dragging
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);

            if (isDragging) {
                slingEnd.set(touchPos);
            } else if (touchPos.dst(slingStart) < 50) {
                isDragging = true;
                slingEnd.set(touchPos);
            }
        } else if (isDragging) {
            isDragging = false;
            // Launch the bird
            Vector2 launchVector = slingStart.cpy().sub(slingEnd).scl(5);
            rb_1.getBody().applyLinearImpulse(launchVector, rb_1.getBody().getWorldCenter(), true);
            slingEnd.set(slingStart);
            isBirdLaunched = true;
        }

        // Prevent bird from moving until launched
        if (!isBirdLaunched) {
            rb_1.getBody().setTransform(slingStart, 0);
            rb_1.getBody().setLinearVelocity(0, 0);
        }
    }

    @Override
    public void resize(int width, int height) {
        logger.info("Resizing level_1_screen");
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        logger.info("Pausing level_1_screen");
    }

    @Override
    public void resume() {
        logger.info("Resuming level_1_screen");
    }

    @Override
    public void hide() {
        logger.info("Hiding level_1_screen");
    }

    @Override
    public void dispose() {
        logger.info("Disposing level_1_screen");
    }
}
