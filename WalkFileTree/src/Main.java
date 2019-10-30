/**
 *
 *  @author Bondarenko Maksym S16748
 *
 */


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{
  public static void main(String[] args) {
    Application.launch(args);
  }

@Override
public void start(Stage stage) throws Exception {
	Label label = new Label("Input directory name:\n(no whitespaces)");
	TextField txtfield = new TextField();
	Button btn = new Button("Ok");
	BorderPane.setAlignment(label, Pos.CENTER);
	txtfield.setMaxWidth(400);
	BorderPane.setAlignment(btn, Pos.CENTER);
	BorderPane root = new BorderPane();
	root.setTop(label);
	root.setCenter(txtfield);
	root.setBottom(btn);
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setTitle("First Application");
    stage.setWidth(500);
    stage.setHeight(300);
    stage.show();
    btn.setOnAction(new EventHandler<ActionEvent>() {
    	 
        @Override
        public void handle(ActionEvent event) {
        	walkFileTree(txtfield.getText());
        }
    });
}
	private void walkFileTree(String dirName) {
	    String resultFileName = "Result.txt";
	    Futil.processDir(dirName, resultFileName);
	}
}
