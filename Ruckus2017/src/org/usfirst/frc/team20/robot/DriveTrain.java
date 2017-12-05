
 //Author: Roland Rao, Ben Hogan, and Sydney Walker
package org.usfirst.frc.team20.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;

public class DriveTrain implements Loggable{
	DriverStation d = DriverStation.getInstance();
	DoubleSolenoid shifter = new DoubleSolenoid(Constants.DRIVETRAIN_EXTEND_PORT, Constants.DRIVERTRAIN_RETRACT_PORT);
	CANTalon masterRight, followerRightOne, followerRightTwo;
	CANTalon masterLeft, followerLeftOne, followerLeftTwo;
	int state;
	double turnAngle = 0.0, startingAngle = 0.0;
	double adjustment, rotateToAngleRate, currentRotationRate;
	double multiplier;
	boolean kArcadeStandard_Reported = false;
	boolean setSetpoint;
	boolean highGear;
	public DriveTrain() {
		// Setting the Motor Port Numbers
		masterRight = new CANTalon(Constants.DRIVETRAIN_MASTER_RIGHT_MOTOR_PORT);
		masterRight.reverseOutput(false);
		followerRightOne = new CANTalon(Constants.DRIVETRAIN_FOLLOWER_RIGHT_MOTOR_PORT_ONE);
		followerRightTwo = new CANTalon(Constants.DRIVETRAIN_FOLLOWER_RIGHT_MOTOR_PORT_TWO);
		masterLeft = new CANTalon(Constants.DRIVETRAIN_MASTER_LEFT_MOTOR_PORT);
		masterLeft.reverseOutput(false);
		followerLeftOne = new CANTalon(Constants.DRIVETRAIN_FOLLOWER_LEFT_MOTOR_PORT_ONE);
		followerLeftTwo = new CANTalon(Constants.DRIVETRAIN_FOLLOWER_LEFT_MOTOR_PORT_TWO);
		// Setting the Follower Motors to Their Respective Masters
		followerRightOne.changeControlMode(CANTalon.TalonControlMode.Follower);
		followerRightOne.set(masterRight.getDeviceID());
		followerRightOne.reverseOutput(true);
		followerRightTwo.changeControlMode(CANTalon.TalonControlMode.Follower);
		followerRightTwo.set(masterRight.getDeviceID());
		followerRightTwo.reverseOutput(true);
		followerLeftOne.changeControlMode(CANTalon.TalonControlMode.Follower);
		followerLeftOne.set(masterLeft.getDeviceID());
		followerLeftOne.reverseOutput(true);
		followerLeftTwo.changeControlMode(CANTalon.TalonControlMode.Follower);
		followerLeftTwo.set(masterLeft.getDeviceID());
		followerLeftTwo.reverseOutput(true);
		// Setting the Encoders on the Master Motors
		masterLeft.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		masterRight.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		masterLeft.setEncPosition(0);
		masterRight.setEncPosition(0);
		multiplier = 6.5;
//		test = new CANTalon(12);
//		test.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
//		test.setEncPosition(0);
	}

	public void resetTalons() {
		masterLeft.reset();
		masterRight.reset();
	}

	public void drive(double speed, double rightTurn, double leftTurn) { // drives
		if (shifter.get() == DoubleSolenoid.Value.kReverse) {
			rightTurn *= .95;
			leftTurn *= .95;
			if (speed != 0) { // forward
				adjustment = 0;
			}
			masterRight.set(speed - rightTurn + leftTurn + adjustment);
			masterLeft.set(-speed + leftTurn - rightTurn);
		} else {
			if (speed != 0) { // forward
				adjustment = 0;
			}
			masterRight.set(speed - rightTurn + leftTurn + adjustment);
			masterLeft.set(-speed + leftTurn - rightTurn);
		}
	}
	
	public void stopDrive() {
		masterRight.set(0);
		masterLeft.set(0);
	}
	
	public void turnLeft(double speed){
		masterRight.set(-speed);
		masterLeft.set(-speed);
	}

	public void turnRight(double speed){
		masterRight.set(speed);
		masterLeft.set(speed);
	}

	public void shiftHigh() { // shifts into high gear ratio
		highGear = true;
		shifter.set(DoubleSolenoid.Value.kReverse);
	}

	public void shiftLow() { // shifts into low gear ratio
		shifter.set(DoubleSolenoid.Value.kForward);
		highGear = false;
	}

	@Override
	public String log() {
		return "***\nDrivetrain Talon\tCurrent Output\tBus Voltage\tOutput Voltage"
		+ "\nMasterRight\t" + masterRight.getOutputCurrent() + "\t" + masterRight.getBusVoltage() + "\t" + masterRight.getOutputVoltage()
		+ "\nFollowerRightOne\t" + followerRightOne.getOutputCurrent() + "\t" + followerRightOne.getBusVoltage() + "\t" + followerRightOne.getOutputVoltage()
		+ "\nFollowerRightTwo\t" + followerRightTwo.getOutputCurrent() + "\t" + followerRightTwo.getBusVoltage() + "\t" + followerRightTwo.getOutputVoltage()
		+ "\nMasterLeft\t" + masterLeft.getOutputCurrent() + "\t" + masterLeft.getBusVoltage() + "\t" + masterLeft.getOutputVoltage()
		+ "\nFollowerLeftOne\t" + followerLeftOne.getOutputCurrent() + "\t" + followerLeftOne.getBusVoltage() + "\t" + followerLeftOne.getOutputVoltage()
		+ "\nFollowerLeftTwo\t" + followerLeftTwo.getOutputCurrent() + "\t" + followerLeftTwo.getBusVoltage() + "\t" + followerLeftTwo.getOutputVoltage()
		+ "\n***Pneumatics\tCommanded Dog Position"
		+ "\nDrivetrain Shifting\t" + shifter.get();
	}
}