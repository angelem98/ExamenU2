package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        BorderPane bp = new BorderPane();

        bp.setLeft(insertar());
        bp.setStyle("-fx-background-color: #F0E68C;");


        primaryStage.setTitle("Programa");
        primaryStage.setScene(new Scene(bp, 300, 275));
        primaryStage.show();
    }

    public AnchorPane insertar(){
        final int[] contador = {0};
        final int[] resultado = {0};
        final boolean[] verdadero = {false};
        AnchorPane anch = new AnchorPane();

        //cabecerade texto y localizacion
        Label titulo = new Label("Ingrese el resultado ");
        titulo.setFont(Font.font("Century Gothic", FontWeight.BLACK, 18));
        titulo.setLayoutX(85);
        titulo.setLayoutY(10);


        //label donde dara los numeros aleatorios
        Label label1 = new Label();
        final int[] numero1 = {(int) (Math.random() * 20) + 1};
        label1.setFont(Font.font("Century Gothic", FontWeight.BLACK, 18));
        label1.setText(String.valueOf(numero1[0]) + " por ");
        label1.setLayoutX(100);
        label1.setLayoutY(60);
        final int[] numero2 = {(int) (Math.random() * 20) + 1};
        Label label2 = new Label();
        label2.setFont(Font.font("Century Gothic", FontWeight.BLACK, 18));
        label2.setText(String.valueOf(numero2[0]));
        label2.setLayoutX(150);
        label2.setLayoutY(60);

        resultado[0] = numero1[0] * numero2[0];

        Label label4 = new Label("Tiempo");
        label4.setFont(Font.font("Century Gothic", FontWeight.BLACK, 18));
        label4.setLayoutX(100);
        label4.setLayoutY(90);

        Label label5 = new Label();
        label5.setFont(Font.font("Century Gothic", FontWeight.BLACK, 18));
        label5.setLayoutX(180);
        label5.setLayoutY(95);

        TextField textField = new TextField();
        textField.setLayoutX(100);
        textField.setLayoutY(115);
        textField.setPrefSize(60,20);

        anch.getChildren().add(titulo);
        anch.getChildren().add(label1);
        anch.getChildren().add(label2);
        anch.getChildren().add(textField);
        anch.getChildren().add(label4);
        anch.getChildren().add(label5);


       Task tarea = new Task(){
           @Override
           protected Object call() throws Exception{
               while (!verdadero[0]){
                   Platform.runLater(new Runnable() {
                       @Override
                       public void run() {
                           label5.setText(String.valueOf(contador[0]));
                           //System.out.println(contador[0]);

                       }
                   });
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   contador[0]++;
               }
               return null;
           }
       };

       //creamos el thread
        Thread hilo = new Thread(tarea);
        hilo.setDaemon(true);
        hilo.start();

        //boton
        Button btn = new Button("Comprobar");
        btn.setStyle("-fx-background-color: #87CEEB;");
        AnchorPane.setTopAnchor(btn,180.0);
        AnchorPane.setLeftAnchor(btn,120.0);
        AnchorPane.setRightAnchor(btn,120.0);
        AnchorPane.setBottomAnchor(btn,60.0);
        anch.getChildren().add(btn);

        final int[]result = {resultado[0]};
        btn.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(textField.getText().length() != 0){
                    if(result[0] == Integer.parseInt(textField.getText())){
                        verdadero[0]=true;
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Respuesta Correcta");
                        alert.setContentText("Tiempo que tomo responder: "+contador[0]);
                        alert.show();

                        numero1[0]= (int)(Math.random()*100)+1;
                        label1.setText(String.valueOf(numero1[0]));
                        numero2[0] = (int)(Math.random()*100)+1;
                        label2.setText(String.valueOf(numero1[0]));
                        label5.setText("");
                        textField.setText("");
                        result[0] = numero1[0]*numero2[0];
                        contador[0]=0;
                        verdadero[0]=false;
                        hilo.run();
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Respuesta Incorrecta");
                        alert.show();
                    }


                }
            }
        });


        return anch;
    }




    public static void main(String[] args) {
        launch(args);
    }
}
