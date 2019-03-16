/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.util.ArrayList;
import java.util.List;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        turtle.forward(sideLength);
        // The inside angle of a square are equal 90 
        turtle.turn(90);
        turtle.forward(sideLength);
        turtle.turn(90);
        turtle.forward(sideLength);
        turtle.turn(90);
        turtle.forward(sideLength);
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
    	if(sides > 2) {
    		return (double)360 / sides;
    	}
		return 0;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        if(angle > 0 && angle < 180)
        	return (int)Math.round(360 / (180 - angle)); 
        return 0;
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        double angle = calculateRegularPolygonAngle(sides);
        turtle.forward(sideLength);
        for(int i=1 ; i < sides ; i++) {
        	turtle.turn(angle);
        	turtle.forward(sideLength);
        }
        
    }

    /**
     * Given the current direction, current location, and a target location, calculate the heading
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentHeading. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentHeading current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to heading (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateHeadingToPoint(double currentHeading, int currentX, int currentY,
                                                 int targetX, int targetY) {
    	double angle =  Math.toDegrees(Math.atan2(targetX-currentX , targetY-currentY))-currentHeading;
    	if (angle < 0) {
    		angle += 360;
    	}
    	return angle;
    }

    /**
     * Given a sequence of points, calculate the heading adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateHeadingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of heading adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateHeadings(List<Integer> xCoords, List<Integer> yCoords) {
        List<Double> list = new ArrayList<Double>();
        if(xCoords!=null && xCoords.size() == yCoords.size()) {
        	list.add(calculateHeadingToPoint(0.0,xCoords.get(0), yCoords.get(0), xCoords.get(1), yCoords.get(1)));
        	for(int i=1 ; i < xCoords.size()-1 ; i++ ) {
        		list.add( calculateHeadingToPoint(list.get(i-1),xCoords.get(i), yCoords.get(i), xCoords.get(i+1), yCoords.get(i+1)));
        	}
        }
        
        return list;
    }

    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
    	List<Integer> xCoords=new ArrayList<>();
    	List<Integer> yCoords=new ArrayList<>();
    	
    	xCoords.add(0); xCoords.add(1); xCoords.add(3); xCoords.add(1);
    	xCoords.add(0); xCoords.add(-1); xCoords.add(-3); xCoords.add(-1); xCoords.add(0);
    	yCoords.add(3); yCoords.add(1); yCoords.add(0); yCoords.add(-1);
    	yCoords.add(-3); yCoords.add(-1); yCoords.add(0); yCoords.add(1); yCoords.add(3);
    	turtle.color(PenColor.BLUE);
    	List<Double> angles = calculateHeadings(yCoords, yCoords);
    	int[] sideLenght = {20, 30 ,40, 50};
    	turtle.color(PenColor.BLUE);
    	boucler(turtle,sideLenght,angles);
    	
    	List<Double> newangles =new ArrayList<>();
    	for(int j=angles.size()-1 ; j >=0  ; j--) {
    		newangles.add(angles.get(j));
    	}
    	turtle.turn(0.0);
    	boucler(turtle,sideLenght,newangles);
    	turtle.turn(90.0);
    	turtle.forward(80);
    }
    
    private static void boucler(Turtle turtle,int[] sideLenght,List<Double> angles) {
    	for(int i=0; i<sideLenght.length; i++) {
    		turtle.forward(sideLenght[i]);
        	for(int j=0 ; j < angles.size() ; j++) {
        		turtle.turn(angles.get(j));
        		turtle.forward(sideLenght[i]);
        	}
    	}
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();
        //drawSquare(turtle, 100);
        //drawRegularPolygon(turtle, 3, 90);
        // draw the window
        
        drawPersonalArt(turtle);
        turtle.draw();
    }

}
