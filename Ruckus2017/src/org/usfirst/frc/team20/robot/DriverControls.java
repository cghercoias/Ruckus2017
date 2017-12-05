//Author: Atharva Gawde and Roland Rao
package org.usfirst.frc.team20.robot;

import edu.wpi.first.wpilibj.Timer;

public class DriverControls {
	Controller driverJoy;
	DriveTrain drive;
	Climber climb;
	double speedStraight, speedLeft, speedRight;
	boolean driveShift;

	public DriverControls(DriveTrain d, Climber c) {
		driverJoy = new Controller(0);
		drive = d;
		climb = c;
		speedStraight = 0;
		speedLeft = 0;
		speedRight = 0;
		driveShift = false;
	}

	public void driverControls() {
		if (driverJoy.getRightTriggerAxis() > 0.1) {
			speedStraight = driverJoy.getRightTriggerAxis()*0.75;
		} else if (driverJoy.getLeftTriggerAxis() > 0.1) {
			speedStraight = -driverJoy.getLeftTriggerAxis()*0.75;
		} else {
			speedStraight = 0.0;
		}
		if (driverJoy.getLeftXAxis() < -0.1) {
			speedLeft = -driverJoy.getLeftXAxis()*0.75;
		} else {
			speedLeft = 0.0;
		}
		if (driverJoy.getLeftXAxis() > 0.1) {
			speedRight = driverJoy.getLeftXAxis()*0.75;
		} else {
			speedRight = 0.0;
		}
		if (speedStraight > 0 || speedStraight < 0 || speedLeft > 0 || speedRight > 0) {
			drive.drive(speedStraight, speedRight, speedLeft);
		} else {
			drive.stopDrive();
		}
		if (driverJoy.getButtonY()) {
			drive.shiftHigh();
		}
		if (driverJoy.getButtonB()) {
			drive.shiftLow();
			Timer.delay(0.01);
		}
		if (driverJoy.getButtonA()) {
			climb.climb(1);
		}
//		if (driverJoy.getButtonDUp()){
//			climb.safe = false;
//		}
		if (climb.climberMaster.getOutputCurrent() > 50 || climb.climberFollower.getOutputCurrent() > 50) {
			climb.stopClimbing();
		}
		if (driverJoy.getButtonX()) {
			climb.stopClimbing();
		}
	}
}