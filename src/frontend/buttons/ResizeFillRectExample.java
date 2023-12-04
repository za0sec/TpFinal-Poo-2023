package frontend.buttons;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ResizeFillRectExample extends Application {

    @Override
    public void start(Stage stage) {
        Rectangle fillRect = new Rectangle(100, 100, Color.RED);
        fillRect.setWidth(200);
        fillRect.setHeight(150);

        Group root = new Group(fillRect);
        Scene scene = new Scene(root, 500, 400);
        stage.setScene(scene);
        stage.show();
        //resize the rectangle following mouse path
        scene.setOnMouseMoved(e -> {
            fillRect.setWidth(e.getX());
            fillRect.setHeight(e.getY());
        });
    }
}
