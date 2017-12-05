//Author: Daniel Pickett
package org.usfirst.frc.team20.robot;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class FuelTank implements Loggable{
	CANTalon tankMotor;
	private DoubleSolenoid agitatorPistons;
	double initialMatchTime;
	boolean tankToFlywheel;
	int counter = 0;
	boolean out = false;
	
	public FuelTank(){
		tankMotor = new CANTalon(Constants.FUEL_TANK_COLLECTOR_MOTOR_PORT);
		tankMotor.reverseOutput(false);
		agitatorPistons = new DoubleSolenoid(Constants.TANK_EXTEND_PORT, Constants.TANK_RETRACT_PORT);
		tankToFlywheel = false;
		tankMotor.reverseOutput(false);
	}
	
	public void tankMotorIntoFlywheel(double speed){
		tankMotor.set(speed);
	}	
	public void tankMotorIntoTank(double speed){
		tankMotor.set(-speed);
	}
	public void stopTank(){
		tankMotor.set(0);
	}
	public void retractAgitator() {
		agitatorPistons.set(DoubleSolenoid.Value.kReverse);
	}
	public void actuateAgitator() {
		agitatorPistons.set(DoubleSolenoid.Value.kForward);
	}
	public void runAgitator(){
		counter++;
		if(counter==14){
			actuateAgitator();
		}else if(counter >= 42){
			retractAgitator();
			counter = 0;
		}
		
	}

	@Override
	public String log() {
		return "/nTankMotor/t" + tankMotor.getOutputCurrent() + "/t" + tankMotor.getBusVoltage() + "/t" + tankMotor.getOutputVoltage()
		+ "/n***Pneumatic/tCommanded Dog Position"
		+ "/nAgitator/t" + agitatorPistons.get();
	}
}