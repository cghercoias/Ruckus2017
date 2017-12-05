//Author: Austin Knox
package org.usfirst.frc.team20.robot;

public class CircleArcs {
    private double rightArc;
    private double leftArc;
    private double totalAngle;
    private boolean direction;
    private static double width;
    double percentLeft, percentRight;
    
    public CircleArcs(){//r is radius, degrees is the angle you want to end at, direction is the way you want to turn true turns right
    	setWidth(32.0);
    }

    public void createArc(double radius, double degrees, boolean direction){
    	totalAngle = degrees;
        double radians =degrees*(Math.PI/180);
        this.direction = direction;
        double smallR = radius-(width/2);
        double largeR = radius+(width/2);
        if (direction){
            rightArc = (2*Math.PI*smallR)/4;//radians not 2PI/4
            leftArc = (2*Math.PI*largeR)/4;
        }else{
            leftArc = radians*smallR;
            rightArc = radians*largeR;
        }    	
    }
    public static void setWidth(double width){
        CircleArcs.width = width;
    }
    public static double getWidth(){
        return CircleArcs.width/2;
    }
//    public CircleArcs(double r, boolean direction){//asumes 90 degree arc
//        double smallR = r-getWidth();
//        double largeR = r+getWidth();
//        if (direction){
//            rightArc = .5*Math.PI*smallR;
//            leftArc = .5*Math.PI*largeR;
//        }else{
//            leftArc = .5*Math.PI*smallR;
//            rightArc = .5*Math.PI*largeR;
//        }
//    }
    public double getLeftArc(){
        return leftArc;
    }
    public double getRightArc(){
        return rightArc;
    }
    public double getRightAngle(double travel){
        percentRight = travel/rightArc;
        if (!direction)
            return -percentRight*totalAngle;
        return percentRight*totalAngle;
    }
    public double getLeftAngle(double travel){
        percentLeft = travel/leftArc;
        System.out.println(percentLeft);
        if (!direction)
            return -percentLeft*totalAngle;
        return percentLeft*totalAngle;
    }
    public boolean rightDone(double travel){
    	if(getRightAngle(travel)==getRightAngle(travel));
    	if(percentRight < 0.98){
    		return false;
    	}else{
    		return true;
    	}
    }
    public boolean leftDone(double travel){
    	if(getLeftAngle(travel)==getLeftAngle(travel));
    	if(percentRight < 0.98){
    		return false;
    	}else{
    		return true;
    	}
    }

}