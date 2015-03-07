package view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;
import model.Polar;
import model.TurtleCommand;

public class Drawer {
	private Color myColor;
	private double[] myXBounds = new double[2];
	private double[] myYBounds = new double[2];
	private double[] myHalf = new double[2];
	private Workspace myWorkspace;
	private double STROKE_SIZE = 1;
	public void setStroke(Double size){
		STROKE_SIZE = size;
	}
	public Drawer(Workspace workspace){//double xMax, double yMax) {
		myWorkspace = workspace;
		double xMax = myWorkspace.getGridWidth();
		double yMax = myWorkspace.getGridHeight();
		myXBounds[0] = 0;
		myXBounds[1] = xMax+5;
		myYBounds[0] = 0;
		myYBounds[1] = yMax;
		myHalf[0] = 0.5 * (myXBounds[1] - myXBounds[0]);
		myHalf[1] = 0.5 * (myYBounds[1] - myYBounds[0]);
		myColor = Color.BLACK;
	}

	// may have to remove list from turtlecommand
	// TODO: remove list if it is no longer a list being used
	public List<Polyline> draw(Map<Integer, TurtleView> turtles,
			List<TurtleCommand> instructions, SideBar sidebar, Group linesGroup) {
		List<Polyline> lines = new ArrayList<Polyline>();
		Iterator<TurtleCommand> it = instructions.iterator();
		while (it.hasNext()) {
			TurtleCommand command = it.next();
			System.out.println(command.getTurtleId());
			TurtleView turtle = turtles.get(command.getTurtleId());
			System.out.println(turtle);
			Polar polar = command.getPolar();
			// move turtle and draw line
			if (polar.getDistance() != 0) {
				double angle = turtle.getRotate();
				double turtleX = turtle.getLayoutX();
				double turtleY = turtle.getLayoutY();
				double moveX = Math.sin(Math.toRadians(polar.getAngle() + 180 - angle))
						* polar.getDistance();
				double moveY = Math.cos(Math.toRadians(polar.getAngle() + 180 - angle))
						* polar.getDistance();
				
				double startX = turtleX + turtle.getTranslateX() + turtle.getFitWidth()
						/ 2;
				double startY = turtleY + turtle.getTranslateY() + turtle.getFitHeight()
						/ 2;
				double newX = turtleX + moveX + turtle.getTranslateX() + 15;
				double newY = turtleY + moveY + turtle.getTranslateY() + 15;
				if (newY < myYBounds[0]) {
					wrapY(1, turtle, polar, lines, 0, newY, startX, moveX, moveY,
							turtleY, startY);
					linesGroup.getChildren().addAll(lines);
				} else if (newY > myYBounds[1]) {
					wrapY(1, turtle, polar, lines, 1, newY, startX, moveX, moveY,
							turtleY, startY);
					linesGroup.getChildren().addAll(lines);
				} else if (newX < myXBounds[0]) {
					wrapX(0, turtle, polar, lines, 0, newX, startY, moveY, moveX,
							turtleX, startX);
					linesGroup.getChildren().addAll(lines);
				} else if (newX > myXBounds[1]) {
					wrapX(0, turtle, polar, lines, 1, newX, startY, moveY, moveX,
							turtleX, startX);
					linesGroup.getChildren().addAll(lines);
				} else {
					double endX = startX + moveX;
					double endY = startY + moveY;
					if (!turtle.getPenUp()) {
						lines.addAll(animator(startX, startY, endX, endY));
						animate(turtle,lines, linesGroup);
					}
					
				}
			} else {
				if (command.isRelative()) {
					turtle.setRelativeHeading(polar.getAngle());
				} else {
					turtle.setAbsoluteHeading(polar.getAngle());
				}
			}
			sidebar.updateTurtleProperties(command.getTurtleId(), myWorkspace);
		}

		return lines;
	}
	int index;
	private void animate(TurtleView turtle,List<Polyline> lines, Group target) {
		index = 0;
        Timeline animation = new Timeline();
		KeyFrame animate = new KeyFrame(Duration.millis(30),
				e -> updateKeyFrame(turtle,lines, target));
		animation.getKeyFrames().add(animate);
		animation.setCycleCount(lines.size());
		animation.play();
	}
	private void updateKeyFrame(TurtleView turtle,List<Polyline> lines, Group target) {
		System.out.println("turtle was at "+ turtle.getTranslateX()+" "+ turtle.getTranslateY());
		target.getChildren().add(lines.get(index));
		turtle.move(turtle.getTranslateX()+(lines.get(1).getPoints().get(2)-lines.get(0).getPoints().get(2)), turtle.getTranslateY()+(lines.get(1).getPoints().get(3)-lines.get(0).getPoints().get(3)));
		index++;
	}
	private List<Polyline> animator(double startX,
			double startY, double endX, double endY) {
		List<Polyline> myLines = new ArrayList();
		for	(int i=0; i<10; i++){
			Polyline pLine = new Polyline();
			pLine.setStroke(myColor);
			pLine.setStrokeWidth(STROKE_SIZE);
			pLine.getPoints().addAll(
					new Double[] { startX+(double)i/10*(endX-startX), startY+(double)i/10*(endY-startY), startX+(double)(i+1)/10*(endX-startX), startY+(double)(i+1)/10*(endY-startY)});
			System.out.println("adding line from "+ startX+(double)i/10*(endX-startX)+" "+startY+(double)i/10*(endY-startY)+ " to "+startX+(double)(i+1)/10*(endX-startX)+ " "+ startY+(double)(i+1)/10*(endY-startY));
			myLines.add(pLine);
		}
		return myLines;
	}
	private void wrapY(int dir, TurtleView turtle, Polar polar,
			List<Polyline> lines, int i, double newY, double startX, double moveX,
			double moveY, double turtleY, double startY) {
		double endX1 = startX + moveX
				* Math.abs((Math.pow(-1, i) * (myYBounds[i] - newY + moveY)) / moveY);
		double endX2 = startX + moveX;
		System.out.println(Math.pow(-1, i) * myHalf[dir] - myYBounds[i] + newY);
		turtle.move(turtle.getTranslateX() + moveX, Math.pow(-1, i) * myHalf[dir]
				- myYBounds[i] + newY);
		if (!turtle.getPenUp()) {
			double endY = turtle.getTranslateY() + turtleY + turtle.getFitHeight() / 2;
			lines.add(drawLine(startX, startY, endX1, myYBounds[i]));
			lines.add(drawLine(endX1, myYBounds[1 - i], endX2, endY));
		}
	}

	private void wrapX(int dir, TurtleView turtle, Polar polar,
			List<Polyline> lines, int i, double newX, double startY, double moveY,
			double moveX, double turtleX, double startX) {
		double endY1 = startY + moveY
				* Math.abs(Math.pow(-1, i) * (newX - moveX - myXBounds[i]) / moveX);
		double endY2 = startY + moveY;
		turtle.move(Math.pow(-1, i) * myHalf[dir] - myXBounds[i] + newX,
				turtle.getTranslateY() + moveY);
		if (!turtle.getPenUp()) {
			double endX = turtle.getTranslateX() + turtleX + turtle.getFitWidth() / 2;
			lines.add(drawLine(startX, startY, myXBounds[i], endY1));
			lines.add(drawLine(myXBounds[1 - i], endY1, endX, endY2));
		}
	}

	private Polyline drawLine(double startX, double startY, double endX, double endY) {
		Polyline polyline = new Polyline();
		polyline.setStroke(myColor);
		polyline.getPoints().addAll(new Double[] { startX, startY, endX, endY });
		return polyline;
	}

	/*
	 * private getEditedTurtleID(List<TurtleCommand> instructions){
	 * Iterator<TurtleCommand> it = instructions.iterator(); while
	 * (it.hasNext()) { TurtleCommand command = it.next(); return
	 * command.getTurtleId(); } }
	 */
	// It may be better to remove this method above and directly pass the
	// updates to the sidebar

	public void changeColor(Color c) {
		myColor = c;
	}

	/*
	 * public void setPenUp(boolean isUp){ penUp = isUp; }
	 */
}
