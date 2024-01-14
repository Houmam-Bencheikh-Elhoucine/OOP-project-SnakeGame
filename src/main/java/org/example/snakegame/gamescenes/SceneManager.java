package org.example.snakegame.gamescenes;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    private static GameScene mainScene;
    public static void goToMenu(Stage stage){
        if(mainScene != null) {
            mainScene.stopTimer();
        }
        mainScene = Menu.create(stage);
        start(stage);
    }
    public static void goToSinglePlayer(Stage stage) {
        if(mainScene != null) {
            mainScene.stopTimer();
        }
        mainScene = SinglePlayerScene.create(stage);
        start(stage);
    }

    public static void goToTwoPlayers(Stage stage) {
        if(mainScene != null) {
            mainScene.stopTimer();
        }
        mainScene = TwoPlayerScene.create(stage);
        start(stage);
    }

    public static void goToNetworkPlay(Stage stage) {
    }
    private static void start(Stage stage){
        stage.hide();
        mainScene.startScene();
    }
}
