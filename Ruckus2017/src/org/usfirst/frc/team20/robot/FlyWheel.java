//Author: Roland Rao
package org.usfirst.frc.team20.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.FeedbackDeviceStatus;
import com.ctre.CANTalon.TalonControlMode;

public class FlyWheel implements Loggable{
	CANTalon flywheelMaster;
	CANTalon flywheelFollower;
	boolean flywheelEncoder;
	
	public FlyWheel(){
		flywheelMaster = new CANTalon(Constants.FLYWHEEL_MASTER_PORT);
		flywheelMaster.reverseOutput(false);
		flywheelEncoder = false;
		flywheelMaster.configPeakOutputVoltage(12.0f, 0.0f);
	}	

	public boolean flywheelEncoder(){
		FeedbackDeviceStatus sensorStatusFlywheel = flywheelMaster.isSensorPresent(FeedbackDevice.QuadEncoder);
		flywheelEncoder = (FeedbackDeviceStatus.FeedbackStatusPresent == sensorStatusFlywheel);
		return flywheelEncoder;
	}
	public void shoot(double speed){
		flywheelMaster.changeControlMode(TalonControlMode.Voltage);
		flywheelMaster.set(-speed);
	}
	public void setPID(double p, double i, double d, double f){
		flywheelMaster.setP(p);
		flywheelMaster.setI(i);
		flywheelMaster.setD(d);
		flywheelMaster.setF(f);
	}
	public void shootWithEncoders(double RPMS){
		flywheelMaster.changeControlMode(TalonControlMode.Speed);
		double cps = RPMS/600*4096;
		flywheelMaster.set(cps);
	}
	public boolean flywheelReady(double RPMS){
		if(RPMS - Constants.FLYWHEEL_DEADBAND < flywheelSpeed() && flywheelSpeed() < RPMS + Constants.FLYWHEEL_DEADBAND){
			return true;
		}else{
			return false;
		}
	}
	public double flywheelSpeed(){
		return flywheelMaster.getSpeed()/4096*600;
	}
	public void stopFlywheel(){
		flywheelMaster.set(0);
	}

	@Override
	public String log() {
		return "";
//		return "/nFlywheelMaster/t" + flywheelMaster.getOutputCurrent() + "/t" + flywheelMaster.getBusVoltage() + "/t" + flywheelMaster.getOutputVoltage()
//		+ "/nFlywheelFollower/t" + flywheelFollower.getOutputCurrent() + "/t" + flywheelFollower.getBusVoltage() + "/t" + flywheelFollower.getOutputVoltage();
	}
}