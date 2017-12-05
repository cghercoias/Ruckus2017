//Author: Rahul Shah and Ronak Parida
package org.usfirst.frc.team20.robot;

public class OperatorControls {
	Controller operatorJoy;
	FuelTank tank;
	GearMechanism gear;
	FlyWheel flywheel;
	GroundCollector collector;
	GearPickup carlos;
	boolean shooting;
	boolean tankToFlywheel;
	boolean getStartTime = false;
	boolean collectingGear = false;
	double startTime;

	public OperatorControls(FuelTank h, GearMechanism g, FlyWheel f, GroundCollector c, GearPickup b) {
		operatorJoy = new Controller(1);
		tank = h;
		gear = g;
		flywheel = f;
		collector = c;
		carlos = b;
		flywheel.setPID(Constants.FLYWHEEL_P, Constants.FLYWHEEL_I, Constants.FLYWHEEL_D, Constants.FLYWHEEL_F);
		shooting = false;
		tankToFlywheel = false;
	}

	public void operatorControls() {
		if (operatorJoy.getButtonY()) {
			tank.retractAgitator();
			collector.intake(0.85);
			tank.tankMotorIntoTank(0.95);
		}
		if (operatorJoy.getButtonA()) {
			collector.outtake(1.0);
		}
		if (operatorJoy.getButtonDRight()) {
			carlos.extend();
		}
		if (operatorJoy.getButtonDLeft()) {
			carlos.retract();
		}
		if (operatorJoy.getButtonDDown()) { // gear collector collect
			carlos.extend();
			collector.outtake(1.0);
		}
		if (operatorJoy.getButtonDUp()) { // gear collector hold for placement
			carlos.retract();
		}
		if (Math.sqrt((operatorJoy.getRightXAxis() * operatorJoy.getRightXAxis())
				+ (operatorJoy.getRightYAxis() * operatorJoy.getRightYAxis())) > 0.5) {
			gear.gearFlapIn();
		} else {
			gear.gearFlapOut();
		}
		if (operatorJoy.getButtonX() || operatorJoy.getButtonB()) {
			collector.stopCollector();
			tank.stopTank();
			tankToFlywheel = false;
			collectingGear = false;
			getStartTime = false;
		}
		if (operatorJoy.getButtonStart()) {
			flywheel.shootWithEncoders(Constants.FLYWHEEL_SPEED);
		}
		if (operatorJoy.getRightTriggerAxis() > 0) {
			if (flywheel.flywheelReady(Constants.FLYWHEEL_SPEED)) {
				collector.intake(1.0);
				tank.tankMotorIntoFlywheel(0.3);	//0.4
			}
			shooting = true;
		}
		if (tankToFlywheel || shooting) {
			tank.runAgitator();
		}
		if (operatorJoy.getButtonBack()) {
			flywheel.stopFlywheel();
			tank.stopTank();
			collector.stopCollector();
			shooting = false;
			tankToFlywheel = false;
			getStartTime = false;
			collectingGear = false;
			tank.retractAgitator();
		}
	}
}
