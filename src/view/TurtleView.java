package view;

import java.text.DecimalFormat;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TurtleView extends ImageView {

	private double myHeading;
	private boolean penUp = false;
	private int myID;
	private int myImageID;

	public TurtleView(int id, Image img) {
		super(img);
		setFitWidth(30);
		setFitHeight(30);
		penUp = false;
		myHeading = 0;
		myID = id;
		myImageID = 1;
	}
	

	// relative movement
	public void move(double moveX, double moveY) {
		setTranslateX(moveX);
		setTranslateY(moveY);
	}

	// absolute movement
	public double setXY(double x, double y) {
		double newX = getTranslateX();
		double newY = getTranslateY();
		setTranslateX(x);
		setTranslateY(-y);
		return Math.sqrt(Math.pow(newX - x, 2) + Math.pow(newY - y, 2));
	}

	public double setRelativeHeading(double angle) {
		setRotate(adjustAngle(getRotate() + angle));
		myHeading = getRotate();
		return myHeading;
	}

	public double setAbsoluteHeading(double angle) {
		double old = getRotate();
		setRotate(adjustAngle(angle));
		myHeading = angle;
		return getRotate() - old;
	}

	public void setPenUp(boolean isUp) {
		penUp = isUp;
	}

	public double getHeading() {
		return myHeading;
	}

	public boolean getPenUp() {
		return penUp;
	}

	public String getPenPosition() {
		if (penUp == true){
			return "Up";
		}
		return "Down";
	}

	public String isShowing() {
		if (isVisible()){
			return "Visible";
		}
		return "Hidden";
	}

	public void showTurtle(boolean show) {
		setVisible(show);
	}

	public String getYCoord() {
	    DecimalFormat decimalFormat = new DecimalFormat("#.#");
		double yCoord = getTranslateY();
		if (yCoord == -0){
			return "0";
		}
		return decimalFormat.format(-1 * yCoord);
	}
	
	/**
	 * Normalizes a double parameter to an angle between 0-360
	 * @param angle
	 * @return normalized angle between 0-360
	 */
	private double adjustAngle(double angle) {
		angle = angle % 360;
		if (angle < 0) {
			angle = 360 - (-1) * angle;
		}
		return angle;
	}

	public int getID() {
		return myID;
	}
	
	public void setImageID(int id){
		myImageID = id;
	}
	
	public int getImageID(){
		return myImageID;
	}
}