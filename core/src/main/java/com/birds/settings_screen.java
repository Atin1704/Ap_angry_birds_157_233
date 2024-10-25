package com.birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class settings_screen implements Screen {
    private final AssetManager assetManager;
    private Texture background_image;
    private Texture exit_icon;
    private Texture angry_bird;
    private Texture Volume_icon;
    private Texture Credits;
    private Texture Username;
    private Texture Notification_off;
    private Texture Notification_on;
    private static boolean  Notif_status=false;
    private Texture volume_bar;
    private Texture volume_bar_green;
    private Texture volume_bar_wood;
    private Main game_runner;
    private final SpriteBatch spriteBatch;
    StretchViewport viewport;
    Vector2 touchPos;
    boolean checker;

    private boolean isDragging = false; // Track if the user is dragging the volume bar
    private float volumePercentage = 0.5f; // Volume percentage (initially 50%)

    public settings_screen(Main main, AssetManager assetManager,boolean checker) {
        this.game_runner = main;
        this.assetManager = assetManager;
        this.spriteBatch = main.batch;
        viewport = new StretchViewport(100, 100);
        touchPos = new Vector2();
        this.checker = checker;
    }

    @Override
    public void show() {
        background_image = assetManager.get("Settings_bg.png", Texture.class);
        exit_icon = assetManager.get("Exit_icon.png", Texture.class);
        angry_bird = assetManager.get("Angry_Birds.png", Texture.class);
        Volume_icon = assetManager.get("Volume_icon.png", Texture.class);
        Credits = assetManager.get("Credits.png", Texture.class);
        Username = assetManager.get("Username.png", Texture.class);
        Notification_off = assetManager.get("Notification_off.png", Texture.class);
        Notification_on = assetManager.get("Notification_on.png", Texture.class);

        volume_bar = assetManager.get("volume_bar.png", Texture.class);
        volume_bar_green = assetManager.get("volume_bar_green.png", Texture.class);
        volume_bar_wood = assetManager.get("vertical_wooden_bar.png", Texture.class);
    }

    private float notificationAlpha = 1f; // Starts fully opaque
    private boolean fadeIn = false;       // Direction of the fade effect
    private boolean fadeTransition = false; // Only activate fade upon touch

    @Override
    public void render(float v) {
        input(); // Update the input every frame
        ScreenUtils.clear(Color.BLACK);
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(background_image, 0, 0, worldWidth, worldHeight);
        spriteBatch.draw(exit_icon, 80, 3, worldWidth / 8, worldHeight / 12);
        spriteBatch.draw(angry_bird, 15, 72, 65, worldHeight / 5);
        spriteBatch.draw(Credits, 33, 38, worldWidth / 3, worldHeight / 9);

        // Draw Notification icon with fade effect only if transition is triggered
        spriteBatch.setColor(1, 1, 1, notificationAlpha); // Apply alpha transparency
        if (!Notif_status) {
            spriteBatch.draw(Notification_off, 33, 20, worldWidth / 3, worldHeight / 9);
        } else {
            spriteBatch.draw(Notification_on, 33, 20, worldWidth / 3, worldHeight / 9);
        }
        spriteBatch.setColor(1, 1, 1, 1); // Reset color to opaque

        spriteBatch.draw(Username, 33, 56, worldWidth / 3, worldHeight / 9);

        // Volume bar rendering remains unchanged
        float volumeBarX = 20;
        float volumeBarY = 25;
        float volumeBarWidth = worldWidth / 22;
        float volumeBarHeight = 40;
        spriteBatch.draw(volume_bar_wood, volumeBarX, volumeBarY, volumeBarWidth, volumeBarHeight);

        // Draw green volume bar
        float volumeGreenHeight = volumePercentage * volumeBarHeight;
        spriteBatch.draw(volume_bar_green, volumeBarX, volumeBarY, volumeBarWidth, volumeGreenHeight);

        spriteBatch.draw(Volume_icon, 19, 23, 6, worldHeight / 14);

        spriteBatch.end();

        // Adjust notificationAlpha for a fade effect only when transition is active
        if (fadeTransition) {
            if (fadeIn) {
                notificationAlpha += 0.05f; // Increase alpha to make it fade in
                if (notificationAlpha >= 1f) {
                    fadeIn = false;
                    fadeTransition = false; // Stop transition after reaching full opacity
                }
            } else {
                notificationAlpha -= 0.05f; // Decrease alpha for fade out
                if (notificationAlpha <= 0f) {
                    fadeIn = true;
                }
            }
        }
    }

    private long lastNotificationToggleTime = 0; // Track the last toggle time as long
    private static final float NOTIFICATION_TOGGLE_COOLDOWN = 0.3f; // 0.3 seconds cooldown

    private void input() {
        if (Gdx.input.isTouched()) {
            // Get the touch position and convert it to world coordinates
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);

            // Check if the user is touching the exit icon
            float exitIconX = 80f;
            float exitIconY = 3f;
            float exitIconWidth = viewport.getWorldWidth() / 8;
            float exitIconHeight = viewport.getWorldHeight() / 12;

            if (touchPos.x >= exitIconX && touchPos.x <= (exitIconX + exitIconWidth)
                && touchPos.y >= exitIconY && touchPos.y <= (exitIconY + exitIconHeight)) {

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        if (checker) {
                            game_runner.setScreen(new pause_screen(game_runner, assetManager, 1));
                        } else {
                            game_runner.setScreen(new main_screen(game_runner, assetManager));
                        }
                    }
                }, 0.25f);  // Delay of 0.25 seconds for smoother transition
            }

            // Check if the user touched the notification icon with cooldown
            float notifX = 33;
            float notifY = 20;
            float notifWidth = viewport.getWorldWidth() / 3;
            float notifHeight = viewport.getWorldHeight() / 9;

            if (touchPos.x >= notifX && touchPos.x <= (notifX + notifWidth)
                && touchPos.y >= notifY && touchPos.y <= (notifY + notifHeight)) {

                // Only toggle if enough time has passed since the last toggle
                if (TimeUtils.timeSinceMillis(lastNotificationToggleTime) > NOTIFICATION_TOGGLE_COOLDOWN * 1000) {
                    lastNotificationToggleTime = TimeUtils.millis(); // Update the last toggle time
                    Notif_status = !Notif_status;
                    fadeTransition = true; // Trigger fade effect on touch
                    fadeIn = !Notif_status; // Set fade direction based on new status
                }
            }

            // Check if the user is touching the volume bar
            float volumeBarX = 20;
            float volumeBarY = 25;  // Adjusted to the top of the bar
            float volumeBarWidth = viewport.getWorldWidth() / 22;
            float volumeBarHeight = 40;

            if (touchPos.x >= volumeBarX && touchPos.x <= (volumeBarX + volumeBarWidth)
                && touchPos.y >= volumeBarY && touchPos.y <= (volumeBarY + volumeBarHeight)) {
                isDragging = true;
            }
        } else {
            isDragging = false;
        }

        // Smooth volume dragging adjustment
        if (isDragging) {
            float volumeBarY = 25;
            float volumeBarHeight = 40;
            float mouseY = touchPos.y - volumeBarY;
            float newVolumePercentage = Math.max(0, Math.min(1, mouseY / volumeBarHeight));

            // Gradually approach the new value for smoothness
            volumePercentage += (newVolumePercentage - volumePercentage) * 0.2f;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
