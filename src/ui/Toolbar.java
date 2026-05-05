package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Toolbar {
    private VBox container;
    private ComboBox<String> algorithmComboBox;
    private ComboBox<String> mazeGeneratorComboBox;
    private Button startButton;
    private Button clearButton;
    private Button resetPathButton;
    private Button randomMazeButton;
    private Slider speedSlider;
    private Slider gridSizeSlider;
    private Label speedLabel;
    private Label gridSizeLabel;
    
    public Toolbar() {
        container = new VBox(10);
        container.setPadding(new Insets(10));
        container.setStyle("-fx-background-color: #2d2d2d;");
        

        HBox algorithmBox = new HBox(10);
        algorithmBox.setAlignment(Pos.CENTER_LEFT);
        Label algorithmLabel = new Label("Algorithm:");
        algorithmLabel.setStyle("-fx-text-fill: #e0e0e0;");
        algorithmComboBox = new ComboBox<>();
        algorithmComboBox.getItems().addAll("BFS", "DFS", "A*", "Dijkstra");
        algorithmComboBox.setValue("BFS");
        algorithmComboBox.setPrefWidth(180);
        algorithmBox.getChildren().addAll(algorithmLabel, algorithmComboBox);
        

        HBox mazeGenBox = new HBox(10);
        mazeGenBox.setAlignment(Pos.CENTER_LEFT);
        Label mazeGenLabel = new Label("Maze Generator:");
        mazeGenLabel.setStyle("-fx-text-fill: #e0e0e0;");
        mazeGeneratorComboBox = new ComboBox<>();
        mazeGeneratorComboBox.getItems().addAll("Random", "Wilson's Algorithm");
        mazeGeneratorComboBox.setValue("Random");
        mazeGeneratorComboBox.setPrefWidth(200);
        mazeGenBox.getChildren().addAll(mazeGenLabel, mazeGeneratorComboBox);
        

        HBox buttonBox1 = new HBox(10);
        buttonBox1.setAlignment(Pos.CENTER_LEFT);
        startButton = new Button("Start Visualization");
        startButton.setPrefWidth(150);
        startButton.getStyleClass().add("start-button");
        clearButton = new Button("Clear Grid");
        clearButton.setPrefWidth(120);
        clearButton.getStyleClass().add("clear-button");
        resetPathButton = new Button("Reset Path");
        resetPathButton.setPrefWidth(120);
        resetPathButton.getStyleClass().add("reset-button");
        buttonBox1.getChildren().addAll(startButton, clearButton, resetPathButton);
        

        HBox buttonBox2 = new HBox(10);
        buttonBox2.setAlignment(Pos.CENTER_LEFT);
        randomMazeButton = new Button("Generate Maze");
        randomMazeButton.setPrefWidth(150);
        randomMazeButton.getStyleClass().add("random-button");
        buttonBox2.getChildren().addAll(randomMazeButton);
        

        HBox speedBox = new HBox(10);
        speedBox.setAlignment(Pos.CENTER_LEFT);
        speedLabel = new Label("Speed: 50");
        speedLabel.setStyle("-fx-text-fill: #e0e0e0;");
        speedSlider = new Slider(1, 100, 50);
        speedSlider.setPrefWidth(200);
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);
        speedSlider.setMajorTickUnit(25);
        speedSlider.setMinorTickCount(5);
        speedBox.getChildren().addAll(speedLabel, speedSlider);
        

        HBox gridSizeBox = new HBox(10);
        gridSizeBox.setAlignment(Pos.CENTER_LEFT);
        gridSizeLabel = new Label("Grid Size: 25x25");
        gridSizeLabel.setStyle("-fx-text-fill: #e0e0e0;");
        gridSizeSlider = new Slider(10, 50, 25);
        gridSizeSlider.setPrefWidth(200);
        gridSizeSlider.setShowTickLabels(true);
        gridSizeSlider.setShowTickMarks(true);
        gridSizeSlider.setMajorTickUnit(10);
        gridSizeSlider.setMinorTickCount(5);
        gridSizeSlider.setSnapToTicks(true);
        gridSizeBox.getChildren().addAll(gridSizeLabel, gridSizeSlider);
        
        container.getChildren().addAll(algorithmBox, mazeGenBox, buttonBox1, buttonBox2, speedBox, gridSizeBox);
    }
    
    public VBox getContainer() {
        return container;
    }
    
    public ComboBox<String> getAlgorithmComboBox() {
        return algorithmComboBox;
    }
    
    public Button getStartButton() {
        return startButton;
    }
    
    public Button getClearButton() {
        return clearButton;
    }
    
    public Button getResetPathButton() {
        return resetPathButton;
    }
    
    public Button getRandomMazeButton() {
        return randomMazeButton;
    }
    
    public Slider getSpeedSlider() {
        return speedSlider;
    }
    
    public Slider getGridSizeSlider() {
        return gridSizeSlider;
    }
    
    public void updateGridSizeLabel(int size) {
        gridSizeLabel.setText("Grid Size: " + size + "x" + size);
    }
    
    public ComboBox<String> getMazeGeneratorComboBox() {
        return mazeGeneratorComboBox;
    }

    
    public void updateSpeedLabel(double speed) {
        speedLabel.setText("Speed: " + (int)speed);
    }
}

