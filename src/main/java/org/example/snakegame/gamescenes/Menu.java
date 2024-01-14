package org.example.snakegame.gamescenes;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static org.example.snakegame.globals.GameParameters.HEIGHT;
import static org.example.snakegame.globals.GameParameters.WIDTH;

public class Menu extends GameScene {
    Stage stage;
    VBox root;
    public static Menu create(Stage stage) {
        VBox root = new VBox();
        return new Menu(stage, root);
    }
    private Menu(Stage stage, VBox root) {
        super(root);
        this.root = root;
        this.stage = stage;
        // Create buttons
        Button singlePlayerButton = new Button("Single Player");
        Button singlePlayerMouseButton = new Button("Single Player with mouse");
        Button twoPlayersButton = new Button("Two Players");
        Button networkPlayButton = new Button("Network Play");
        Button exitButton = new Button("Exit");
        // Set actions for the buttons (you can customize these actions)
        singlePlayerButton.setOnAction(event -> SceneManager.goToSinglePlayer(stage));
        twoPlayersButton.setOnAction(event -> SceneManager.goToTwoPlayers(stage));
        networkPlayButton.setOnAction(event -> SceneManager.goToNetworkPlay(stage));
        exitButton.setOnAction(event -> stage.close());
        singlePlayerMouseButton.setOnAction(event-> SceneManager.goToSinglePlayerMouse(stage));
        // Create layout
        root.setPadding(new Insets(20, 20, 20, 20));
        singlePlayerButton.setStyle("margin-bottom: 20px;");
        twoPlayersButton.setStyle("margin-bottom: 20px;");
        networkPlayButton.setStyle("margin-bottom: 20px;");
        exitButton.setStyle("margin-bottom: 20px;");
        root.getChildren().addAll(singlePlayerButton,singlePlayerMouseButton, twoPlayersButton, networkPlayButton, exitButton);
    }
    public void startScene(){
        stage.setScene(this);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stopTimer() {

    }
}
