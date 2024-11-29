



//    private void createObstacles() {
//        obstacles = new ArrayList<>();
//        obstacles.add(new Wood_block(world, bodyRemovalManager, 700, 270, 110, 110));
//        obstacles.add(new Stone_block(world, bodyRemovalManager, 810, 270, 110, 110));
//        obstacles.add(new Glass_block(world, bodyRemovalManager, 920, 270, 110, 110));
//        obstacles.add(new Wood_block(world, bodyRemovalManager, 755, 380, 110, 110));
//        obstacles.add(new Stone_block(world, bodyRemovalManager, 865, 380, 110, 110));
//    }
//
//    private void createPigs() {
//        pigs = new ArrayList<>();
//        pigs.add(new King_pig(world, bodyRemovalManager, 780, 480, 70,70));
//        pigs.add(new Normal_pig(world,bodyRemovalManager, 850, 480, 70, 70));
//    }


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
    private transient static final Logger logger = Logger.getLogger(level_1_screen.class.getName());
    private transient final AssetManager assetManager;
    private transient Texture background_image;
    private transient Texture pause_button;
    private transient Texture arrow_texture;
    private transient Sprite arrow_sprite;
    private transient BodyRemovalManager bodyRemovalManager;

    private transient Main game_runner;
    private transient final SpriteBatch spriteBatch;

    public transient StretchViewport viewport;
    public transient Vector2 touchPos;

    public transient World world;
    private transient Box2DDebugRenderer debugRenderer;
    private transient ShapeRenderer shapeRenderer;
    private transient Slingshot slingshot;
    public ArrayList<Bird> birds;
    private transient Bird currentBird;
    public ArrayList<Obstacle> obstacles;
    public ArrayList<Pig> pigs;
    public transient Body groundBody;

    public boolean isDragging = false;
    private transient Vector2 dragStart = new Vector2();

    // level_1_screen.java
    public boolean allBirdsLaunchedFlag = false;
    public boolean allBirdsLaunchedTimerStarted = false;
    private transient Timer.Task allBirdsLaunchedTask;
    public transient CollisionHandler collisionHandler;

    private transient level_1_screen currentScreen;

    public level_1_screen(Main main, AssetManager assetManager) {
        logger.info("Initializing level_1_screen");
        this.game_runner = main;
        this.assetManager = assetManager;
        this.spriteBatch = main.batch;
        viewport = new StretchViewport(1200, 1000);
        touchPos = new Vector2();
        this.currentScreen = this;

        world = new World(new Vector2(0, -15.0f), true);
        debugRenderer = new Box2DDebugRenderer();
        shapeRenderer = new ShapeRenderer();

        createGround();

        collisionHandler = new CollisionHandler(groundBody);
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
        createPigs();
        createObstacles();

    }

    public level_1_screen(Main main, AssetManager assetManager, level_1_screen level) {
        logger.info("Initializing level_1_screen");
        this.game_runner = main;
        this.assetManager = assetManager;
        this.spriteBatch = main.batch;
        viewport = new StretchViewport(1200, 1000);
        touchPos = new Vector2();
        this.currentScreen = this;

        world = new World(new Vector2(0, -15.0f), true);
        debugRenderer = new Box2DDebugRenderer();
        shapeRenderer = new ShapeRenderer();

        createGround();

        collisionHandler = new CollisionHandler(groundBody);
        world.setContactListener(collisionHandler);
        bodyRemovalManager = new BodyRemovalManager(world);

        // Load textures
        background_image = new Texture("Level2_bg.png");
        pause_button = new Texture("Pause_icon.png");
        arrow_texture = new Texture("arrow.png");
        arrow_sprite = new Sprite(arrow_texture);
        arrow_sprite.setOriginCenter();

        createBirds(level);
        createSlingshot();

        createObstacles(level);
        createPigs(level);

        this.allBirdsLaunchedFlag = level.allBirdsLaunchedFlag;
        this.allBirdsLaunchedTimerStarted = level.allBirdsLaunchedTimerStarted;
    }

    public level_1_screen(){

        spriteBatch=null;
        assetManager=null;




        createBirds(1);
        createObstacles(1);
        createPigs(1);

        this.allBirdsLaunchedFlag =false;
        this.allBirdsLaunchedTimerStarted = false;



    }

    private void createBirds(int i) {
        birds = new ArrayList<>();
        birds.add(new Black_bird( 200, 217, 50, 50,false));
        birds.add(new Black_bird( 200, 217, 50, 50,false));
        birds.add(new Black_bird( 200, 217, 50, 50,false));

    }
    private void createObstacles(int i) {
        obstacles = new ArrayList<>();
        obstacles.add(new Glass_block( 920, 270, 110, 110,true));
        obstacles.add(new Glass_block( 920, 270, 110, 110,true));
        obstacles.add(new Glass_block( 920, 270, 110, 110,true));
        obstacles.add(new Glass_block( 920, 270, 110, 110,true));
        obstacles.add(new Glass_block( 920, 270, 110, 110,true));



    }
    private void createPigs(int i) {
        pigs = new ArrayList<>();
        pigs.add(new Normal_pig( 760, 480, 70,70,false));
        pigs.add(new Normal_pig( 850, 480, 70, 70,false));

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

    private void createBirds(level_1_screen level) {
        birds = new ArrayList<>();
        System.out.println("Birds added");
        birds.add(new Red_bird(world, (Red_bird)level.birds.get(0)));
        System.out.println("Added Red_bird");
        birds.add(new Red_bird(world, (Red_bird)level.birds.get(1)));
        System.out.println("Added Yellow_bird");
        birds.add(new Black_bird(world, (Black_bird)level.birds.get(2)));
        System.out.println("Added Black_bird");
    }

    private void createBirds() {
        birds = new ArrayList<>();
        birds.add(new Red_bird(world, 200, 217, 50, 50));
        birds.add(new Red_bird(world, 140, 217, 50, 50));
        birds.add(new Black_bird(world, 80, 217, 50, 50));
    }

    private void createSlingshot() {
        slingshot = new Slingshot(world, 230, 217, 50, 200);
    }
    private void createPigs() {
        pigs = new ArrayList<>();
        pigs.add(new King_pig(world, bodyRemovalManager, 760, 480, 70,70));
        pigs.add(new Normal_pig(world,bodyRemovalManager, 850, 480, 70, 70));
    }

    private void createObstacles() {
        obstacles = new ArrayList<>();
        obstacles.add(new Wood_block(world, bodyRemovalManager, 700, 270, 110, 110));
        obstacles.add(new Stone_block(world, bodyRemovalManager, 810, 270, 110, 110));
        obstacles.add(new Glass_block(world, bodyRemovalManager, 920, 270, 110, 110));
        obstacles.add(new Wood_block(world, bodyRemovalManager, 755, 380, 110, 110));
        obstacles.add(new Stone_block(world, bodyRemovalManager, 865, 380, 110, 110));
    }


    private void createPigs(level_1_screen level) {
        pigs = new ArrayList<>();
        System.out.println("Size of pigs in level: " + level.pigs.size());
        for (Pig pig : level.pigs) {
            System.out.println(pig.isSpriteNull);
            if (pig instanceof Old_pig && !pig.isSpriteNull) {
                System.out.println("Old Pig added");
                pigs.add(new Old_pig(world, bodyRemovalManager, (Old_pig) pig));
            } else if (pig instanceof Normal_pig && !pig.isSpriteNull) {
                System.out.println("Normal Pig added");
                pigs.add(new Normal_pig(world, bodyRemovalManager, (Normal_pig) pig));
            } else if (pig instanceof King_pig && !pig.isSpriteNull) {
                pigs.add(new King_pig(world, bodyRemovalManager, (King_pig) pig));
            }
        }
    }

    private void createObstacles(level_1_screen level) {
        obstacles = new ArrayList<>();
        for (Obstacle obstacle : level.obstacles) {
            if (obstacle instanceof Wood_block && !obstacle.isSpriteNull) {
                System.out.println("Wood Block Added");
                obstacles.add(new Wood_block(world, bodyRemovalManager, (Wood_block) obstacle));
            } else if (obstacle instanceof Stone_block && !obstacle.isSpriteNull) {
                obstacles.add(new Stone_block(world, bodyRemovalManager, (Stone_block) obstacle));
            } else if (obstacle instanceof Glass_block && !obstacle.isSpriteNull) {
                obstacles.add(new Glass_block(world, bodyRemovalManager, (Glass_block) obstacle));
            } else if (obstacle instanceof Wood_stick_hor && !obstacle.isSpriteNull) {
                obstacles.add(new Wood_stick_hor(world, bodyRemovalManager, (Wood_stick_hor) obstacle));
            } else if (obstacle instanceof Wood_stick_ver && !obstacle.isSpriteNull) {
                obstacles.add(new Wood_stick_ver(world, bodyRemovalManager, (Wood_stick_ver) obstacle));
            } else if (obstacle instanceof Stone_stick_hor && !obstacle.isSpriteNull) {
                obstacles.add(new Stone_stick_hor(world, bodyRemovalManager, (Stone_stick_hor) obstacle));
            } else if (obstacle instanceof Stone_stick_vert && !obstacle.isSpriteNull) {
                obstacles.add(new Stone_stick_vert(world, bodyRemovalManager, (Stone_stick_vert) obstacle));
            }
        }
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
            if (bird.get_is_special()) {
                bird.special_ability(obstacles, pigs, collisionHandler);
            }
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
            Vector2 velocity = new Vector2((dragStart.x - dragEnd.x), dragStart.y - dragEnd.y).scl(5);
            currentBird.setVelocity(velocity);
            currentBird.setAwake(true);
            currentBird.setLaunched(true);
            currentBird = null; // Allow selecting a new bird
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            for (Bird bird : birds) {
                if (bird.isLaunched()) {
                    bird.set_is_special(true);
                }
            }
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
