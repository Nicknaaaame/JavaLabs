package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;

public class Controller {
    @FXML
    private ToggleGroup rbtGroup;
    @FXML
    private TextField brushSize;
    @FXML
    private Canvas canvas;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private StackPane stackPane;
    private TextField textField;
    private GraphicsContext g;

    private double x1, x2, y1, y2;
    private boolean isDragged = false;
    private Stage primaryStage;

    public void initialize(){
        primaryStage = Main.getPrimaryStage();
        g = canvas.getGraphicsContext2D();
        onClear();
        primaryStage.heightProperty().addListener(e->{
            canvas.setHeight(primaryStage.getHeight());
            onClear();
        });
        primaryStage.widthProperty().addListener(e->{
            canvas.setWidth(primaryStage.getWidth());
            onClear();
        });
    }

    public void onClear(){
        g.setFill(Color.WHITE);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void onBrush(){
        canvas.setOnMousePressed(this::drawBrush);
        canvas.setOnMouseDragged(this::drawBrush);
        canvas.setOnMouseReleased(e->{}); //лямбда не удаляется поэтому так
    }

    public void onDraw(){
        RadioButton rbt = (RadioButton) rbtGroup.getSelectedToggle();
        String nameRbt = rbt.getText();
        if(nameRbt.equals("Brush")){
            onBrush();
        }
        else {
            canvas.setOnMousePressed(this::createFigurePressed);
            canvas.setOnMouseDragged(this::createFigureDrag);
            switch (nameRbt) {
                case "Rectangle":
                    canvas.setOnMouseReleased(e->{
                        if(isDragged) {
                            g.setFill(colorPicker.getValue());
                            g.fillPolygon(new double[]{x1, x2, x2, x1}, new double[]{y1, y1, y2, y2}, 4);
                            isDragged = false;
                            x1 = x2 = y1 = y2 = 0;
                        }
                    });
                    break;
                case "Oval":
                    canvas.setOnMouseReleased(this::onOvalReleased);
                    break;
                case "Triangle":
                    canvas.setOnMouseReleased(e->{
                        if(isDragged){
                            g.setFill(colorPicker.getValue());
                            g.fillPolygon(new double[]{(x1+x2)/2, x1, x2}, new double[]{y1, y2, y2}, 3);
                            isDragged = false;
                            x1 = x2 = y1 = y2 = 0;
                        }
                    });
                    break;
                case "Line":
                    canvas.setOnMouseReleased(e->{
                        if(isDragged) {
                            g.setStroke(colorPicker.getValue());
                            g.setLineWidth(Double.parseDouble(brushSize.getText()));
                            g.strokeLine(x1, y1, x2, y2);
                            isDragged = false;
                            x1 = x2 = y1 = y2 = 0;
                        }
                    });
                    break;
                case "Text":
                    canvas.setOnMouseReleased(mouseEvent->{
                        if(isDragged) {
                            textField = new TextField();
                            stackPane.setAlignment(Pos.TOP_LEFT);
                            textField.setTranslateX(x1);
                            textField.setTranslateY(y1);
                            textField.setPrefWidth(x2-x1);
                            textField.setPrefHeight(y2-y1);
                            textField.setMaxWidth(textField.getPrefWidth());
                            textField.setMaxHeight(textField.getPrefHeight());
                            textField.setAlignment(Pos.TOP_LEFT);
                            textField.setOnKeyReleased(keyEvent -> {
                                        if (KeyCode.getKeyCode("Enter") == keyEvent.getCode()) {
                                            g.setStroke(colorPicker.getValue());
                                            g.strokeText(textField.getText(), textField.getTranslateX()+8, textField.getTranslateY()+16);
                                            stackPane.getChildren().remove(textField);
                                        }
                                    });
                            stackPane.getChildren().add(textField);
                            isDragged = false;
                        }
                    });
                    break;
            }
        }
    }

    private void createFigurePressed(MouseEvent e){
        x1 = e.getX();
        y1 = e.getY();
    }

    private void createFigureDrag(MouseEvent e){
        x2 = e.getX();
        y2 = e.getY();
        isDragged = true;
    }

    private void onOvalReleased(MouseEvent e){
        if(isDragged) {
            g.setFill(colorPicker.getValue());
            double delX = x2 - x1, delY = y2 - y1;
            if (delX >= 0 && delY >= 0) {
                g.fillOval(x1, y1, +delX, +delY);
            } else
            if (delX >= 0 && delY <= 0) {
                g.fillOval(x1, y2, +delX, -delY);
            } else
            if (delX <= 0 && delY >= 0) {
                g.fillOval(x2, y1, -delX, +delY);
            } else
            if (delX <= 0 && delY <= 0) {
                g.fillOval(x2, y2, -delX, -delY);
            }
            isDragged = false;
            x1 = x2 = y1 = y2 = 0;
        }
    }

    private void drawBrush(MouseEvent e){
        double size = Double.parseDouble(brushSize.getText());
        double x = e.getX() - size / 2;
        double y = e.getY() - size / 2;
        g.setFill(colorPicker.getValue());
        g.fillOval(x, y, size, size);
    }

    public void onSave() {
        FileChooser fileChooser = new FileChooser();
        try {
            Image snapshot = canvas.snapshot(null, null);
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File(fileChooser.showSaveDialog(Main.getPrimaryStage()).getPath()+".png"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void onOpen(){
        Image img = new Image(new FileChooser().showOpenDialog(Main.getPrimaryStage()).toURI().toString());
        g.drawImage(img, 0, 0);
    }
}
