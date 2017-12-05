package org.usfirst.frc.team20.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class GearMechanism implements Loggable{
	DoubleSolenoid gearFlap;
	FlyWheel flywheel;
	OperatorControls operator;

	public GearMechanism(FlyWheel f, OperatorControls o) {
		gearFlap = new DoubleSolenoid(Constants.GEAR_EXTEND_PORT, Constants.GEAR_RETRACT_PORT);
		flywheel = f;
		operator = o;
	}

	public void gearFlapOut() {
		gearFlap.set(DoubleSolenoid.Value.kReverse);
	}

	public void gearFlapIn() {
		gearFlap.set(DoubleSolenoid.Value.kForward);
	}

	@Override
	public String log() {
		return "/nGear Flaps/t" + gearFlap.get();
	}
}