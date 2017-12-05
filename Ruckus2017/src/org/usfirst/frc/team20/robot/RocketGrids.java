package org.usfirst.frc.team20.robot;

public class RocketGrids {
	public RobotGrid getGrid(String autoMode){
		if(Integer.parseInt(autoMode) == RobotModes.SPLINE_LEFT_SIDE){
			return sideLeft();
		} else if (Integer.parseInt(autoMode) == RobotModes.SPLINE_RIGHT_SIDE){
			return sideRight();
		} else if (Integer.parseInt(autoMode) == RobotModes.SPLINE_MIDDLE_BOILER){
			return middleBoiler();
		} else if (Integer.parseInt(autoMode) == RobotModes.TEST){
			return test();
		}
		return null;
	}

	public RobotGrid sideLeft(){
		System.out.println("LEFT GRID IS BEING MADE");
		RobotGrid grid = new RobotGrid(0, 0, 0, 3);
		return grid;
	}
	
	public RobotGrid sideRight(){
		RobotGrid grid = new RobotGrid(28, 277.549, 0, 3);
		grid.addPoint(124.870, 204.725, 300);
		System.out.println("RIGHT GRID WOOOO");
		return grid;
	}
	
	public RobotGrid middleBoiler(){
		RobotGrid grid = new RobotGrid(0,0,0,3);
		return grid;
	}

	public RobotGrid test(){
		System.out.println("");
		RobotGrid grid = new RobotGrid(124.87, 204.725, 300, 3);
		grid.addPoint(28, 277.549, 0);
		return grid;
	}
}
