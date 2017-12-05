package org.usfirst.frc.team20.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class GearPickup implements Loggable{
	DoubleSolenoid carlosPiston;
	boolean up = false;
	
	public GearPickup() {
		carlosPiston = new DoubleSolenoid(Constants.FLOOR_EXTEND_PORT, Constants.FLOOR_RETRACT_PORT);
	}
	
	public void retract(){
		carlosPiston.set(DoubleSolenoid.Value.kReverse);
		up = true;
	}
	
	public void extend(){
		carlosPiston.set(DoubleSolenoid.Value.kForward);
		up = false;
	}

	@Override
	public String log() {
		return "/nCarlos Piston/t" + carlosPiston.get();
	}
}