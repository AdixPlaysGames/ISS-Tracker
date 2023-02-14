package projekt.ISSTracker.tutorial;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.io.FileNotFoundException;


public class AlertBox {

    public static void display(String title, String message) throws FileNotFoundException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(400);
        window.setMinHeight(300);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Już rozumiem!");
        closeButton.setOnAction(e -> window.close());

        // Graphics
        Image image = new Image("TUTORIAL.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setX(10);
        imageView.setY(10);
        imageView.setFitHeight(600);
        imageView.setPreserveRatio(true);

        Image icon = new Image("ISS_icon.png");
        window.getIcons().add(icon);

        VBox layout = new VBox(30);
        Group root = new Group(imageView);
        layout.getChildren().addAll(root, label, closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);
        layout.setMinHeight(500);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
