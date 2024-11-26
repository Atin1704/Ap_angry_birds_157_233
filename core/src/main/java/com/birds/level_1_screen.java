// level_1_screen.java
package com.birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.data.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;

public class level_1_screen extends Level implements Screen, Serializable {
    private static final Logger logger = Logger.getLogger(level_1_screen.class.getName());
    private final AssetManager assetManager;
    private Texture background_image;
    private Texture pause_button;
    private Texture arrow_texture;
    private Sprite arrow_sprite;
    private BodyRemovalManager bodyRemovalManager;

    private Main game_runner;
    private final  SpriteBatch spriteBatch;

    StretchViewport viewport;
    Vector2 touchPos;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private ShapeRenderer shapeRenderer;
    private Slingshot slingshot;
    private ArrayList<Bird> birds;
    private Bird currentBird;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Pig> pigs;
    private Body groundBody;

    private boolean isDragging = false;
    private Vector2 dragStart = new Vector2();

    // level_1_screen.java
    private boolean allBirdsLaunchedFlag = false;
    private boolean allBirdsLaunchedTimerStarted = false;
    private Timer.Task allBirdsLaunchedTask;

    private transient level_1_screen currentScreen;

    public level_1_screen(Main main, AssetManager assetManager) {
        logger.info("Initializing level_1_screen");
        this.game_runner = main;
        this.assetManager = assetManager;
        this.spriteBatch = main.batch;
        viewport = new StretchViewport(1200, 1000);
        touchPos = new Vector2();
        this.currentScreen = this;

        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();
        shapeRenderer = new ShapeRenderer();

        createGround();

        CollisionHandler collisionHandler = new CollisionHandler(groundBody);
        world.setContactListener(collisionHandler);
        bodyRemovalManager = new BodyRemovalManager(world);

        // Load textures
        background_image = new Texture("Level2_bg.png");
        pause_button = new Texture("Pause_icon.png");
        arrow_texture = new Texture("arrow.png");
        arrow_sprite = new Sprite(arrow_texture);
        arrow_sprite.setOriginCenter();

        createBirds();
        createSlingshot();
        createObstacles();
        createPigs();
    }

    private void createGround() {
        // Create ground
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(new Vector2(600, 217));
        groundBody = world.createBody(groundBodyDef);

        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vector2(-600, 0), new Vector2(600, 0));
        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.friction = 0.5f;
        groundFixtureDef.restitution = 0.0f; // No bouncing
        groundBody.createFixture(groundFixtureDef);
        groundShape.dispose();

        // Create left boundary
        BodyDef leftBoundaryDef = new BodyDef();
        leftBoundaryDef.type = BodyDef.BodyType.StaticBody;
        leftBoundaryDef.position.set(new Vector2(0, 500));
        Body leftBoundary = world.createBody(leftBoundaryDef);

        EdgeShape leftShape = new EdgeShape();
        leftShape.set(new Vector2(0, -500), new Vector2(0, 500));
        FixtureDef leftFixtureDef = new FixtureDef();
        leftFixtureDef.shape = leftShape;
        leftFixtureDef.friction = 0.5f;
        leftFixtureDef.restitution = 0.0f; // No bouncing
        leftBoundary.createFixture(leftFixtureDef);
        leftShape.dispose();

        // Create right boundary
        BodyDef rightBoundaryDef = new BodyDef();
        rightBoundaryDef.type = BodyDef.BodyType.StaticBody;
        rightBoundaryDef.position.set(new Vector2(1200, 500));
        Body rightBoundary = world.createBody(rightBoundaryDef);

        EdgeShape rightShape = new EdgeShape();
        rightShape.set(new Vector2(0, -500), new Vector2(0, 500));
        FixtureDef rightFixtureDef = new FixtureDef();
        rightFixtureDef.shape = rightShape;
        rightFixtureDef.friction = 0.5f;
        rightFixtureDef.restitution = 0.0f; // No bouncing
        rightBoundary.createFixture(rightFixtureDef);
        rightShape.dispose();

        // Create top boundary
        BodyDef topBoundaryDef = new BodyDef();
        topBoundaryDef.type = BodyDef.BodyType.StaticBody;
        topBoundaryDef.position.set(new Vector2(600, 1000));
        Body topBoundary = world.createBody(topBoundaryDef);

        EdgeShape topShape = new EdgeShape();
        topShape.set(new Vector2(-600, 0), new Vector2(600, 0));
        FixtureDef topFixtureDef = new FixtureDef();
        topFixtureDef.shape = topShape;
        topFixtureDef.friction = 0.5f;
        topFixtureDef.restitution = 0.0f; // No bouncing
        topBoundary.createFixture(topFixtureDef);
        topShape.dispose();
    }

    private void createBirds() {
        birds = new ArrayList<>();
        birds.add(new Red_bird(world, 80, 217, 50, 50));
        birds.add(new Yellow_bird(world, 140, 217, 50, 50));
        birds.add(new Black_bird(world, 200, 217, 50, 50));
    }

    private void createSlingshot() {
        slingshot = new Slingshot(world, 230, 217, 50, 200);
    }

    private void createObstacles() {
        obstacles = new ArrayList<>();
        obstacles.add(new Wood_block(world, bodyRemovalManager, 700, 270, 110, 110));
        obstacles.add(new Stone_block(world, bodyRemovalManager, 810, 270, 110, 110));
        obstacles.add(new Glass_block(world, bodyRemovalManager, 920, 270, 110, 110));
        obstacles.add(new Wood_block(world, bodyRemovalManager, 755, 380, 110, 110));
        obstacles.add(new Stone_block(world, bodyRemovalManager, 865, 380, 110, 110));
    }

    private void createPigs() {
        pigs = new ArrayList<>();
        pigs.add(new King_pig(world, bodyRemovalManager, 780, 480, 70,70));
        pigs.add(new Normal_pig(world,bodyRemovalManager, 850, 480, 70, 70));
    }

    private void placeBirdOnSlingshot(Bird bird) {
    bird.setAwake(false);
    bird.setPosition(slingshot.getX(), slingshot.getY() + slingshot.getHeight());
     // Ensure the bird is not awake until launched
}

    @Override
    public void show() {

    }


    // Add this method to check if all birds are launched
    private boolean allBirdsLaunched() {
        if (allBirdsLaunchedFlag) {
            return true;
        }

        for (Bird bird : birds) {
            if (!bird.isLaunched()) {
                return false;
            }
        }

        if (!allBirdsLaunchedTimerStarted) {
            allBirdsLaunchedTimerStarted = true;
            allBirdsLaunchedTask = new Timer.Task() {
                @Override
                public void run() {
                    allBirdsLaunchedFlag = true;
                }
            };
            Timer.schedule(allBirdsLaunchedTask, 25);
        }

        return false;
    }

    // Add this method to check if all pig bodies are null
    private boolean allPigBodiesNull() {
        for (Pig pig : pigs) {
            if (pig.getBody() != null) {
                return false;
            }
        }
        return true;
    }

    // Update the render method
    @Override
    public void render(float delta) {
        input();

        world.step(1 / 60f, 6, 2);
        for (Bird bird : birds) {
            bird.update();
        }
        for (Obstacle obstacle : obstacles) {
            obstacle.update();
        }
        for (Pig pig : pigs) {
            pig.update();
        }
        slingshot.update();

        bodyRemovalManager.removeMarkedBodies();

        // Check conditions for victory or defeat
        if (allPigBodiesNull() && allBirdsLaunched()) {
            game_runner.setScreen(new Victory_Screen(game_runner, assetManager, 1));
        } else if (!allPigBodiesNull() && allBirdsLaunched()) {
            game_runner.setScreen(new Defeat_Screen(game_runner, assetManager, 1));
        } else if (allPigBodiesNull() && !allBirdsLaunched()) {
            game_runner.setScreen(new Victory_Screen(game_runner, assetManager, 1));
        }

        ScreenUtils.clear(Color.BLACK);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(background_image, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        spriteBatch.draw(pause_button, 1100, 920, 65, 65);
        for (Bird bird : birds) {
            bird.draw(spriteBatch);
        }
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(spriteBatch);
            if (obstacle.getHealth() > 0) {
                game_runner.font.draw(spriteBatch, "Health: " + obstacle.getHealth(), obstacle.getXPos(), obstacle.getYPos() + obstacle.getHeight() + 10);
            }
        }
        for (Pig pig : pigs) {
            pig.draw(spriteBatch);
            if (pig.getHealth() > 0) {
                game_runner.font.draw(spriteBatch, "Health: " + pig.getHealth(), pig.getXPos(), pig.getYPos() + pig.getHeight() + 10);
            }
        }
        slingshot.draw(spriteBatch);

        // Draw the arrow if dragging
        if (isDragging) {
            float arrowX = slingshot.getX() + slingshot.getWidth();
            float arrowY = slingshot.getY() + slingshot.getHeight() / 2;
            arrow_sprite.setPosition(arrowX, arrowY);
            Vector2 direction = new Vector2(dragStart.x - touchPos.x, dragStart.y - touchPos.y);
            arrow_sprite.setRotation(direction.angleDeg());
            arrow_sprite.setSize(direction.len() / 2, arrow_sprite.getHeight());
            arrow_sprite.draw(spriteBatch);
        }

        spriteBatch.end();

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
                    game_runner.setScreen(new pause_screen(game_runner, assetManager, 1,currentScreen));
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

    // Handle bird selection
    if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) && birds.size() > 0 && !birds.get(0).isLaunched()) {
        if (currentBird != null) {
            currentBird.setPosition(currentBird.getX(), 235); // Reset current bird's position
        }
        currentBird = birds.get(0);
        placeBirdOnSlingshot(currentBird);
    } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) && birds.size() > 1 && !birds.get(1).isLaunched()) {
        if (currentBird != null) {
            currentBird.setPosition(currentBird.getX(), 235); // Reset current bird's position
        }
        currentBird = birds.get(1);
        placeBirdOnSlingshot(currentBird);
    } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3) && birds.size() > 2 && !birds.get(2).isLaunched()) {
        if (currentBird != null) {
            currentBird.setPosition(currentBird.getX(), 235); // Reset current bird's position
        }
        currentBird = birds.get(2);
        placeBirdOnSlingshot(currentBird);
    }

    // Handle dragging input for slingshot
    if (Gdx.input.isTouched() && currentBird != null && !currentBird.isLaunched()) {
        if (!isDragging) {
            isDragging = true;
            dragStart.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(dragStart);
        }
        touchPos.set(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(touchPos);
        slingshot.drag(touchPos.x, touchPos.y);
    } else if (isDragging) {
        isDragging = false;
        Vector2 dragEnd = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(dragEnd);
        Vector2 velocity = new Vector2(dragStart.x - dragEnd.x, dragStart.y - dragEnd.y).scl(5);
        currentBird.setVelocity(velocity);
        currentBird.setAwake(true);
        currentBird.setLaunched(true);
        currentBird = null; // Allow selecting a new bird
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
        arrow_texture.dispose();
    }
}
