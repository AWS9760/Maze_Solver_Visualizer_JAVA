package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class StatusBar {
    private HBox container;
    private Label algorithmLabel;
    private Label visitedLabel;
    private Label pathLengthLabel;
    private Label pathCostLabel;
    private Label timeLabel;
    private Label statusLabel;
    
    public StatusBar() {
        container = new HBox(15);
        container.setPadding(new Insets(10));
        container.setStyle("-fx-background-color: #2d2d2d;");
        container.setAlignment(Pos.CENTER_LEFT);
        
        algorithmLabel = new Label("Algorithm: None");
        algorithmLabel.setStyle("-fx-text-fill: #e0e0e0;");
        visitedLabel = new Label("Nodes Visited: 0");
        visitedLabel.setStyle("-fx-text-fill: #e0e0e0;");
        pathLengthLabel = new Label("Path Length: 0");
        pathLengthLabel.setStyle("-fx-text-fill: #e0e0e0;");
        pathCostLabel = new Label("Path Cost: 0");
        pathCostLabel.setStyle("-fx-text-fill: #e0e0e0;");
        timeLabel = new Label("Time: 0ms");
        timeLabel.setStyle("-fx-text-fill: #e0e0e0;");
        statusLabel = new Label("Status: Ready");
        statusLabel.setStyle("-fx-text-fill: #e0e0e0;");
        
        container.getChildren().addAll(algorithmLabel, visitedLabel, pathLengthLabel, 
            pathCostLabel, timeLabel, statusLabel);
    }
    
    public HBox getContainer() {
        return container;
    }
    
    public void updateAlgorithm(String algorithm) {
        algorithmLabel.setText("Algorithm: " + algorithm);
    }
    
    public void updateVisited(int count) {
        visitedLabel.setText("Nodes Visited: " + count);
    }
    
    public void updatePathLength(int length) {
        pathLengthLabel.setText("Path Length: " + length);
    }
    
    public void updatePathCost(int cost) {
        pathCostLabel.setText("Path Cost: " + cost);
    }
    
    public void updateTime(long timeMs) {
        timeLabel.setText("Time: " + timeMs + "ms");
    }
    
    public void updateStatus(String status) {
        statusLabel.setText("Status: " + status);
    }
    
    public void reset() {
        algorithmLabel.setText("Algorithm: None");
        visitedLabel.setText("Nodes Visited: 0");
        pathLengthLabel.setText("Path Length: 0");
        pathCostLabel.setText("Path Cost: 0");
        timeLabel.setText("Time: 0ms");
        statusLabel.setText("Status: Ready");
    }
}

