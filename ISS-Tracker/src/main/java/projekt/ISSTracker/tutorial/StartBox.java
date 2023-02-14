package projekt.ISSTracker.tutorial;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.io.FileNotFoundException;


public class StartBox {

    public static void display() throws FileNotFoundException {
        Stage window = new Stage();
        window.setTitle("Informacje wstępne");
        window.setMinWidth(400);
        window.setMinHeight(400);

        Label label = new Label();

        VBox layout = new VBox(30);
        Button buttonNo = new Button("Poradzę sobie");
        buttonNo.setOnAction(e -> window.close());
        buttonNo.setDefaultButton(true);
        Button buttonNext = new Button("Tak, z chęcią!");
        buttonNext.setOnAction(e -> {
            try {
                AlertBox.display("Tutorial", "Czy wszystko jasne kamracie?");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        // Graphics
        Image image = new Image("ISS-tracker.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setX(10);
        imageView.setY(10);
        imageView.setFitHeight(400);
        imageView.setPreserveRatio(true);

        Image icon = new Image("ISS_icon.png");
        window.getIcons().add(icon);

        Group root = new Group(imageView);
        layout.getChildren().addAll(root, label, buttonNext, buttonNo);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(3);
        layout.setMinHeight(500);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }
}