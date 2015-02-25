package view;

import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import resources.Strings;

public class Workspace extends StackPane{
	
	//private StackPane myDisplay;
	private Rectangle myBackground;


	public static final int GRID_WIDTH = 800;
	public static final int GRID_HEIGHT = 550;
	//adjusts anchorpane coordinates to set 0,0 as the center of the gridsets center point at the
	public static final double X_ADJUSTMENT = GRID_WIDTH / 2;  
	public static final double Y_ADJUSTMENT = GRID_HEIGHT / 2;  
	
	
	public Workspace(Map<Integer, TurtleView> turtleList, Group lines){
	     setPadding(new Insets(15));
	     
	     //is there a way to dynamically set grid size
	     myBackground = new Rectangle(GRID_WIDTH,GRID_HEIGHT);
	     myBackground.setFill(Color.WHITE);
	     TurtleView turtle = turtleList.get(0);
	     turtleList.put(0,turtle);
	     getChildren().addAll(myBackground,lines,turtle);
         setAlignment(Pos.CENTER);
	     
	}
	
	/*public Map<Integer, TurtleView> getTurtles(){
		return myTurtles;
	}*/
	
	public void addTurtle(int id, TurtleView turtle){
		//myTurtles.put(id, turtle);
		//add turtle into the workspace
	}
	
	public void setBackground(Color color){
		myBackground.setFill(color);
	}

}