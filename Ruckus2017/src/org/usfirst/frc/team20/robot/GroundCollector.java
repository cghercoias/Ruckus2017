//Author: Saathvik Narra
package org.usfirst.frc.team20.robot;

import com.ctre.CANTalon;

public class GroundCollector implements Loggable{
	CANTalon collector;
	Constants constants;
	
	public GroundCollector(){
		collector = new CANTalon(Constants.GROUND_COLLECTOR_MOTOR_PORT);
		collector.reverseOutput(false);
	}
	
	public void intake(double speed) {
		collector.set(speed);
	}
	public void outtake(double speed) {
		collector.set(-speed);
	}
	public void stopCollector(){
		collector.set(0);
	}
	public boolean collectorJam(){
		if(collector.getOutputCurrent() > 50){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public String log() {
		return "/nCollector Motor/t" + collector.getOutputCurrent() + "/t" + collector.getBusVoltage() + "/t" + collector.getOutputVoltage();
	}
}
