// level_1_screen.java
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
import com.badlogic.gdx.physics.box2d.*;
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

    private Main game_runner;
    private final SpriteBatch spriteBatch;

    StretchViewport viewport;
    Vector2 touchPos;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private ShapeRenderer shapeRenderer;
    private Bird redBird, yellowBird, blackBird;
    private Slingshot slingshot;

    public level_1_screen(Main main, AssetManager assetManager) {
        logger.info("Initializing level_1_screen");
        this.game_runner = main;
        this.assetManager = assetManager;
        this.spriteBatch = main.batch;
        viewport = new StretchViewport(1200, 1000);
        touchPos = new Vector2();

        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();
        shapeRenderer = new ShapeRenderer();

        // Load textures
        background_image = new Texture("Level2_bg.png");
        pause_button = new Texture("Pause_icon.png");

        createGround();
        createBirds();
        createSlingshot();
    }

    private void createGround() {
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(600, 217));
        Body groundBody = world.createBody(groundBodyDef);

        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vector2(-600, 0), new Vector2(600, 0));
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundShape;
        fixtureDef.friction = 0.5f;
        groundBody.createFixture(fixtureDef);
        groundShape.dispose();
    }

    private void createBirds() {
        redBird = new Red_bird(world, 255, 417, 50, 50); // Adjusted yPos to place the red bird on the slingshot
        yellowBird = new Yellow_bird(world, 130, 217, 50, 50);
        blackBird = new Black_bird(world, 70, 217, 50, 50);
    }

    private void createSlingshot() {
        slingshot = new Slingshot(world, 230, 217, 50, 200);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        input();
        world.step(1/60f, 6, 2);
        redBird.update();
        yellowBird.update();
        blackBird.update();
        slingshot.update();

        ScreenUtils.clear(Color.BLACK);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(background_image, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        redBird.draw(spriteBatch);
        yellowBird.draw(spriteBatch);
        blackBird.draw(spriteBatch);
        slingshot.draw(spriteBatch); // Draw the slingshot sprite
        spriteBatch.end();

        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BROWN);
        shapeRenderer.line(slingshot.getX(), slingshot.getY() + slingshot.getHeight(), redBird.getX(), redBird.getY());
        shapeRenderer.line(slingshot.getX() + slingshot.getWidth(), slingshot.getY() + slingshot.getHeight(), redBird.getX(), redBird.getY());
        shapeRenderer.end();

        debugRenderer.render(world, viewport.getCamera().combined);
    }

    private boolean isDragging = false;

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

        // Handle sling mechanism
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);

            if (touchPos.dst(redBird.getX(), redBird.getY()) < 50) {
                redBird.setPosition(touchPos.x, touchPos.y);
                isDragging = true;
            }
        } else if (isDragging) {
            isDragging = false;
            float dx = slingshot.getX() - redBird.getX();
            float dy = slingshot.getY() - redBird.getY();
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            float forceMagnitude = 10 * distance * (float) redBird.speedMultiplier;
            Vector2 slingForce = new Vector2(forceMagnitude * (dx / distance), forceMagnitude * (dy / distance));
            redBird.launch(slingForce);
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
        world.dispose();
        debugRenderer.dispose();
        shapeRenderer.dispose();
        background_image.dispose();
        pause_button.dispose();
    }
}
