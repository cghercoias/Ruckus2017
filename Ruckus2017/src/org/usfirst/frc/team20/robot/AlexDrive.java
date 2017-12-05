//Author: Roland Rao
package org.usfirst.frc.team20.robot;

import edu.wpi.first.wpilibj.Timer;

public class AlexDrive implements Loggable{
	Controller alexJoy;
	double speedStraight;
	double speedRight;
	double speedLeft;
	DriveTrain drive;
	Climber climb;

	public AlexDrive(DriveTrain d, Climber c) {
		alexJoy = new Controller(0);
		drive = d;
		climb = c;
	}

	public void AlexControls() {
		// Driver Code
		if(alexJoy.getLeftYAxis()>.1 || alexJoy.getLeftYAxis() < -.1){
			speedStraight = -alexJoy.getLeftYAxis();
		}else{
			speedStraight = 0;
		}
		if (alexJoy.getLeftTriggerAxis() > 0.1) {
			speedLeft = alexJoy.getLeftTriggerAxis();
		} else {
			speedLeft = 0.0;
		}
		if (alexJoy.getRightTriggerAxis() > 0.1) {
			speedRight = alexJoy.getRightTriggerAxis();
		} else {
			speedRight = 0.0;
		}
		if (speedStraight > 0 || speedStraight < 0 || speedLeft > 0 || speedRight > 0) {
			drive.drive(speedStraight, speedRight, speedLeft);
		} else {
			drive.stopDrive();
		}
		if(alexJoy.getButtonY()){
			drive.shiftHigh();
		}
		if(alexJoy.getButtonB()){
			drive.shiftLow();
			Timer.delay(0.01);
		}
		if(alexJoy.getButtonX()){
			climb.climb(1);
		}		
		if (climb.climberMaster.getOutputCurrent() > 50 || climb.climberFollower.getOutputCurrent() > 50) {
			climb.stopClimbing();
		}
		if(alexJoy.getButtonA()){
			climb.stopClimbing(); 
		}
	}

	@Override
	public String log() {
		String log = Double.toString(alexJoy.getLeftYAxis());
		if(alexJoy.getRightTriggerAxis() > 0){
			log += Double.toString(alexJoy.getRightTriggerAxis());
		} else if (alexJoy.getLeftTriggerAxis() > 0){
			log += Double.toString(alexJoy.getLeftTriggerAxis());
		}
		return log;
	}

}
