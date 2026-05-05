import controller.MazeController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Grid;
import ui.GridRenderer;
import ui.StatusBar;
import ui.Toolbar;

public class Main extends Application {
    private static final int DEFAULT_GRID_SIZE = 25;
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 800;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Maze Solver Visualizer");


        Grid grid = new Grid(DEFAULT_GRID_SIZE, DEFAULT_GRID_SIZE);


        Toolbar toolbar = new Toolbar();
        StatusBar statusBar = new StatusBar();


        BorderPane root = new BorderPane();
        root.setTop(toolbar.getContainer());
        root.setBottom(statusBar.getContainer());


        Canvas canvas = new Canvas();
        canvas.widthProperty().bind(root.widthProperty().subtract(20));
        canvas.heightProperty().bind(root.heightProperty().subtract(toolbar.getContainer().heightProperty())
            .subtract(statusBar.getContainer().heightProperty()).subtract(20));
        
        root.setCenter(canvas);
        
        GridRenderer renderer = new GridRenderer(canvas, grid);

        MazeController controller = new MazeController(grid, renderer, statusBar, canvas, primaryStage);


        toolbar.getAlgorithmComboBox().setOnAction(e -> {
            String algorithm = toolbar.getAlgorithmComboBox().getValue();
            controller.setAlgorithm(algorithm);
        });


        toolbar.getMazeGeneratorComboBox().setOnAction(e -> {
            String generator = toolbar.getMazeGeneratorComboBox().getValue();
            controller.generateMaze(generator);
        });


        toolbar.getStartButton().setOnAction(e -> controller.startVisualization());
        toolbar.getClearButton().setOnAction(e -> controller.clearGrid());
        toolbar.getResetPathButton().setOnAction(e -> controller.resetPath());
        toolbar.getRandomMazeButton().setOnAction(e -> {
            String generator = toolbar.getMazeGeneratorComboBox().getValue();
            controller.generateMaze(generator);
        });


        toolbar.getSpeedSlider().valueProperty().addListener((obs, oldVal, newVal) -> {
            double speed = newVal.doubleValue();
            toolbar.updateSpeedLabel(speed);
            controller.setSpeed(speed);
        });


        toolbar.getGridSizeSlider().valueProperty().addListener((obs, oldVal, newVal) -> {
            int size = newVal.intValue();
            toolbar.updateGridSizeLabel(size);
            controller.resizeGrid(size);
        });


        toolbar.updateGridSizeLabel(DEFAULT_GRID_SIZE);
        toolbar.updateSpeedLabel(50);


        canvas.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 0 && canvas.getHeight() > 0) {
                renderer.updateCellSize();
                renderer.render();
            }
        });
        canvas.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 0 && canvas.getWidth() > 0) {
                renderer.updateCellSize();
                renderer.render();
            }
        });


        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        root.setStyle("-fx-background-color: #1e1e1e;");


        try {
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("CSS file not found, using default styling");
        }
        
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();

        renderer.render();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

