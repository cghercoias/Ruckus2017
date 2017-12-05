package org.usfirst.frc.team20.robot;

public class TsarControls {
	Controller tsarJoy;
	DriveTrain drive;
	Climber climb;
	FuelTank tank;
	GearMechanism gear;
	FlyWheel flywheel;
	GroundCollector collector;
	boolean shooting;
	boolean tankToFlywheel;
	double speedStraight;
	double speedLeft;
	double speedRight;

	public TsarControls(DriveTrain d, Climber c, FuelTank t, GearMechanism g, FlyWheel f, GroundCollector co){
		tsarJoy = new Controller(0);
		drive = d;
		climb = c;
		tank = t;
		gear = g;
		flywheel = f;
		collector = co;
		speedStraight = 0;
		speedLeft = 0;
		speedRight = 0;
		flywheel.setPID(Constants.FLYWHEEL_P, Constants.FLYWHEEL_I, Constants.FLYWHEEL_D, Constants.FLYWHEEL_F);
	}
	
	public void tsarControls(){	
		//flywheel.flywheelSpeed();
		//gear.moveFlaps();
 		//Driver Code
		System.out.println(speedStraight + " Speed Straight");
		System.out.println(speedRight + " Speed Right");
		System.out.println(speedLeft + " Speed Left");
		if(tsarJoy.getRightTriggerAxis() > 0.1){
			speedStraight = tsarJoy.getRightTriggerAxis();
		}else{
 			speedStraight = 0.0;
 		}
		if(tsarJoy.getLeftXAxis() < -0.1){
			speedLeft = -tsarJoy.getLeftXAxis();			
		}else{
			speedLeft = 0.0;
		}
		if(tsarJoy.getLeftXAxis() > 0.1){
			speedRight = tsarJoy.getLeftXAxis();
		}else{
			speedRight = 0.0;
		}
		if (speedStraight > 0 || speedStraight < 0 || speedLeft < 0 || speedRight > 0) {
			drive.drive(speedStraight, speedRight, speedLeft);
		}else{
			drive.stopDrive();
		}
		if (tsarJoy.getButtonDRight()) {
			drive.shiftHigh();
		}
		if (tsarJoy.getButtonDLeft()) {
			drive.shiftLow();
		}
		if(tsarJoy.getButtonDUp()){
			climb.climb(1);
		}
		if(tsarJoy.getButtonDDown()){
			climb.stopClimbing();
		}

		
		//Operator Code
		if (tsarJoy.getButtonY()) {
			collector.intake(1);
			tank.tankMotorIntoTank(1);
		}
		if (tsarJoy.getButtonA()) {
			collector.outtake(1);
			tank.tankMotorIntoFlywheel(1);
			tankToFlywheel = true;
		}
		if(tsarJoy.getButtonX()){
			collector.stopCollector();
			tank.stopTank();
			tankToFlywheel = false;
		}
		if(tsarJoy.getButtonB()){
			if (flywheel.flywheelReady(Constants.FLYWHEEL_SPEED)) {
				collector.intake(1);
				tank.tankMotorIntoFlywheel(1);
			}
			shooting = true;
		}
		if (tsarJoy.getButtonStart()) {
			flywheel.shootWithEncoders(Constants.FLYWHEEL_SPEED);
		}
		if (tankToFlywheel || shooting) {
			tank.runAgitator();
		}
		if (tsarJoy.getButtonBack()) {
			flywheel.stopFlywheel();
			tank.stopTank();
			collector.stopCollector();
			shooting = false;
			tankToFlywheel = false;
		}		
	}
}