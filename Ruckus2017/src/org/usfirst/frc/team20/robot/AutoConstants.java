package org.usfirst.frc.team20.robot;

public class AutoConstants {
	public static final double TICKS_PER_INCH = 650.0;
	public static final double STOPPING_INCHES = 5.0;
	
	//Speeds
	public static final String DRIVE_STRAIGHT_SPEED = "0.60";	//0.35
	public static final String DRIVE_BOILER_SPEED = "0.85";
	public static final String SLAM_SPEED = "1.0";
	public static final String SPLINE_SPEED = "0.6";
	
	//Flywheel Stuff
	public static final double FLYWHEEL_SPEED = 3000.0;
	public static final String FLYWHEEL_DELAY_START_BOILER = "2";

	//Peg Autos
	public static final String MIDDLE_GEAR_TIME = "3.0";
	public static final String AROUND_AIRSHIP = "130.0";
	public static final String GEAR_WAIT_TIME_LONG = "3.0";
	public static final String SIDE_PEG_DISTANCE_ONE = "78.0";
	public static final String SIDE_PEG_DISTANCE_TWO = "50.0";
	public static final String PEG_BACK_AWAY_PEG = "-30.0";
	public static final String NEUTRAL_ZONE_DISTANCE = "200.0";
	public static final String RIGHT_PEG_ANGLE = "-80.0";
	public static final String LEFT_PEG_ANGLE = "80.0";
		
	//Side Peg to Boiler Autos
	public static final String BOILER_BACK_AWAY_PEG = "-20.0";
	public static final String SIDE_PEG_TO_BOILER_DISTANCE = "90.0";
	public static final String SIDE_PEG_TO_BOILER_ANGLE_RED = "180.0";
	public static final String SIDE_PEG_TO_BOILER_ANGLE_BLUE = "-180.0";
	public static final String SIDE_BOILER_TIME = "3.0";
	public static final String GEAR_WAIT_TIME_SHORT = "2.5";
	
	//Middle Peg to Boiler Autos
	public static final String MIDDLE_BOILER_FAST_SPEED = "1.0";
	public static final String MIDDLE_BOILER_SLOW_SPEED = "0.4";
	public static final String MIDDLE_BOILER_FAST_TIME = "0.55";
	public static final String MIDDLE_BOILER_SLOW_TIME = "2.35";
	public static final String MIDDLE_BOILER_GEAR_WAIT = "0.0";	//1d.5, that's a double, right?
	public static final String MIDDLE_BOILER_BACK_UP = "-12.0";
	public static final String MIDDLE_BOILER_ANGLE_RED = "105.0";
	public static final String MIDDLE_BOILER_ANGLE_BLUE = "-105.0";
	public static final String MIDDLE_BOILER_FINAL_DRIVE = "2.0";

	//Slam Slam Autos
	public static final String SLAM_DRIVE_ONE = "1.0";
	public static final String SLAM_DRIVE_TWO = "1.0";
	public static final String SLAM_HOPPER_WAIT = "1.0";
	public static final String SLAM_BACK_UP = "0.25";
	public static final String SLAM_ANGLE_RED = "80.0";
	public static final String SLAM_ANGLE_BLUE = "-80.0";	
	public static final String SLAM_DRIVE_THREE = "0.75";
	public static final String SLAM_DRIVE_FOUR = "1.0";
}