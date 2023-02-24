package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Reflection;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultimediaProgramming extends Application {

    private ImageView imageView;

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);

        // create a button to open a file chooser dialog
        Button openButton = new Button("Open Image");
        openButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image File");
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
            }
        });

        // create a button to apply a filter to the image
        Button filterButton = new Button("Apply Filter");
        filterButton.setOnAction(event -> {
            // TODO: add filter code here
        	
        	 // create a list of filters
            List<Effect> filters = new ArrayList<>();
            filters.add(new SepiaTone(0.8));
            filters.add(new GaussianBlur(10));
            filters.add(new BoxBlur(10, 10, 3));
            filters.add(new ColorAdjust(0, -0.5, -0.5, 0));
            filters.add(new Bloom());
            
            filters.add(new Reflection());

            // select a random filter from the list
            Random random = new Random();
            Effect filter = filters.get(random.nextInt(filters.size()));

            // apply the selected filter to the image
            imageView.setEffect(filter);
        });

        // create a button to download the image
        Button downloadButton = new Button("Download");
        downloadButton.setOnAction(event -> {
            if (imageView.getImage() != null) {
                try {
                    Image image = imageView.getImage();
                    Path path = Paths.get("image.png");
                    Files.write(path, getImageBytes(image.getUrl()));
                    System.out.println("Image downloaded to " + path.toAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // create a horizontal box to hold the buttons
        HBox buttonBox = new HBox(10, openButton, filterButton, downloadButton);
        root.setTop(buttonBox);

        // create an image view to display the image
        imageView = new ImageView();
        imageView.setFitWidth(400);
        imageView.setFitHeight(400);
        root.setCenter(imageView);

        primaryStage.setTitle("Multimedia Programming");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private byte[] getImageBytes(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        return url.openConnection().getInputStream().readAllBytes();
    }
}
