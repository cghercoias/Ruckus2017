package org.usfirst.frc.team20.robot;

public class Constants {
	// CANTalons
	public static final int DRIVETRAIN_MASTER_RIGHT_MOTOR_PORT = 4; // Right Top
	public static final int DRIVETRAIN_FOLLOWER_RIGHT_MOTOR_PORT_ONE = 2; // Right Front
	public static final int DRIVETRAIN_FOLLOWER_RIGHT_MOTOR_PORT_TWO = 3; // Right Back
	public static final int DRIVETRAIN_MASTER_LEFT_MOTOR_PORT = 10; // Left Top
	public static final int DRIVETRAIN_FOLLOWER_LEFT_MOTOR_PORT_ONE = 9; // Left Front
	public static final int DRIVETRAIN_FOLLOWER_LEFT_MOTOR_PORT_TWO = 8; // Left Back
	public static final int CLIMBER_MASTER_PORT = 12; // Climb Right, 1 on practice robot
	public static final int CLIMBER_FOLLOWER_PORT = 11; // Climb Left
	public static final int GROUND_COLLECTOR_MOTOR_PORT = 5; // Collector
	public static final int FLYWHEEL_MASTER_PORT = 6; // Fly Wheel
	public static final int FUEL_TANK_COLLECTOR_MOTOR_PORT = 7; // Fuel Tank

	// Solenoids
	public static final int DRIVETRAIN_EXTEND_PORT = 0;
	public static final int DRIVERTRAIN_RETRACT_PORT = 1;
	public static final int TANK_EXTEND_PORT = 2;
	public static final int TANK_RETRACT_PORT = 3;	
	public static final int GEAR_EXTEND_PORT = 4;
	public static final int GEAR_RETRACT_PORT = 5;
	public static final int FLOOR_EXTEND_PORT = 6;
	public static final int FLOOR_RETRACT_PORT = 7;

	// Sockets
	public static final int PI_SOCKET_PORT_NUMBER = 0;
	public static final int COMPUTER_PORT = 50000;
	public static final String COMPUTER_IP = "";	//TODO find IP with RoboRio

	// FlyWheel
	public static final double FLYWHEEL_P = 0.15; // 0.003
	public static final double FLYWHEEL_I = 0.0; // 0.0
	public static final double FLYWHEEL_D = 0.0; // 0.0
	public static  double FLYWHEEL_F = 0.027; // 0.032, 0.034 after champs, 0.027 for hopper shot
	public static  double FLYWHEEL_SPEED = 3800.0;	//3800.0 champs, 4400 new hood, 7000 basketball hoop
	public static final double FLYWHEEL_DEADBAND = 200.0;

	// Bump Switch
	public static final int GEAR_BUMP_SWITCH_PORT_ONE = 0;

	// Turning Constants
	public static final double DRIVING_P = 0.010;  // TODO 0.020 practice, 0.010 competition
	public static final double TURN_P_LEFT = 0.020;	//0.020 practice
	public static final double TURN_P_RIGHT = 0.020;//0.030 practice
	public static final double NOMINAL_VOLTAGE = 0.30;	//0.20 practice, 0.30 competition
	
	//Misc.
	public static final double TURNING_DEADBAND = 2.0;
	public static final double KPA_DEADBAND = 5.0;
	public static final double CLIMBER_MAX_VOLTAGE = 70.0;
}