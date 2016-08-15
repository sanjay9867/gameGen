package com.fancytank.gamegen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import org.greenrobot.eventbus.EventBus;

public class ScreenManager {
    private static ScreenManager instance;
    private static Game game;
    public static ScreenEnum currentScreen;

    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public void initialize(Game game) {
        this.game = game;
    }

    public void showScreen(ScreenEnum screenEnum) {
        Screen lastScreen = game.getScreen();
        currentScreen = screenEnum;
        AbstractScreen newScreen = screenEnum.getScreen();
        newScreen.buildStage();
        EventBus.getDefault().post(MainGdx.AppStatus.SETUP_FINISHED);
        game.setScreen(newScreen);

        if (lastScreen != null) {
            lastScreen.dispose();
        }
    }
}
