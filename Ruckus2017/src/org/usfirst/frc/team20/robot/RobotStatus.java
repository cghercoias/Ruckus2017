package org.usfirst.frc.team20.robot;

import edu.wpi.first.wpilibj.Timer;

public class RobotStatus implements Loggable{
	Climber climb;
	DriveTrain drive;
	FlyWheel fly;
	FuelTank fuelTank;
	GroundCollector collector;
	double gyroAngle, gyroSetpoint;
	String mode;
	double batteryVoltage, currentDraw;
	public RobotStatus(Climber c, DriveTrain d, FlyWheel f, FuelTank ft, GroundCollector gc){
		climb = c;
		drive = d;
		fly = f;
		fuelTank = ft;
		collector = gc;
		gyroAngle = 0;
		gyroSetpoint = 0;
		batteryVoltage = 0;
		currentDraw = 0;
		mode = "";
	}
	
	@Override
	public String log() {
		//title line	TODO tell Carl there is no way to pull the battery voltage from the rio
		String log = "Mode\t\tMatch Time\t\tCurrent Draw\t\tGyro Setpoint\t\tGyro Angle\t\tDrive Straight Throttle\t\tTurn Throttle\t\t"
				+ "\t\tLeft Drive Voltage\t\tRight Drive Voltage\t\tLeft Drive 1 Current\t\tLeft Drive 2 Current\t\tLeft Drive 3 Current\t\tRight Drive 1 Current"
				+ "\t\tRight Drive 2 Current\t\tRight Drive 3 Current\t\tDrive Encoder Set Position\t\tLeft Encoder Speed\t\tRight Encoder Speed"
				+ "\t\tLeft Encoder Position\t\tRight Encoder Position\t\tHigh Gear?\t\tShooter Motor Voltage\t\tShooter Motor Current\t\tShooter Set Speed"
				+ "\t\tShooter Encoder Speed\t\tShooting?\t\tClimber Motor Voltage\t\tClimber Motor 1 Current\t\tClimber Motor 2 Current"
				+ "\t\tCollector Motor Voltage\t\tCollector Motor Current\t\tIndexer Motor Voltage\t\tIndexer Motor Current\t\tAgitator Up?\t\tGear Flaps Up?\n";
		//adds total robot components
		log += mode + "\t\t" + Timer.getMatchTime() + "\t\t" + getTotalCurrent() + "\t\t" + gyroSetpoint + "\t\t" + gyroAngle + "\t\t";
		return log;
	}
	public void setGyroAngle(double aGyroAngle){
		gyroAngle = aGyroAngle;
	}
	public void setGyroSetpoint(double aGyroSetpoint){
		gyroSetpoint = aGyroSetpoint;
	}
	public void setMode(String aMode){
		mode = aMode;
	}
	public double getTotalCurrent(){
//		return drive.masterLeft.getOutputCurrent() + drive.followerLeftOne.getOutputCurrent() + drive.followerLeftTwo.getOutputCurrent() + 
//				drive.masterRight.getOutputCurrent() + drive.followerRightOne.getOutputCurrent() + drive.followerRightTwo.getOutputCurrent() +
//		        climb.climberFollower.getOutputCurrent() + climb.climberMaster.getOutputCurrent() + fly.flywheelMaster.getOutputCurrent() +
//				fuelTank.tankMotor.getOutputCurrent() + collector.collector.getOutputCurrent();
		return 0.0;
	}
}
