
package org.usfirst.frc.team20.robot;

import java.io.IOException;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	RobotDrive myDrive;
	DriveTrain drive;
	FlyWheel flywheel;
	GroundCollector collector;
	GearMechanism gear;
	GearPickup carlos;
	FuelTank tank;
	Climber climb;
	DriverControls driver;
	OperatorControls operator;
	AlexDrive alex;
	// TsarControls tsar;
	Compressor compressor;
	// DriverVision cam;
	CircleArcs arc;
	AHRS gyro = new AHRS(SerialPort.Port.kMXP); // DO NOT PUT IN ROBOT INIT
	RocketScript getNewScript;
	RocketGrids grid;
	String[] rocketScriptData;
	int rocketScriptCurrentCount, rocketScriptLength = 0, startingENCClicks, autoModeSubStep = 0, startingENCClicksLeft = 0, startingENCClicksRight = 0;
	double rotateToAngleRate, currentRotationRate, startTime;
	double nominalVoltage = Constants.NOMINAL_VOLTAGE;
	boolean shooting = false, resetGyro = false, setStartTime = false, waitStartTime = false, setStartTimeFlywheel = false, 
			gotStartingENCClicks = false, setStartTimeShoot = false, resetGyroTurn = false;
	RobotStatus robot = new RobotStatus(climb, drive, flywheel, tank, collector);
//	Logger logger = new Logger();
	boolean beenEnabled = false, socket = false;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */

	@Override
	public void robotInit() {
		// Initilization
		flywheel = new FlyWheel();
		collector = new GroundCollector();
		tank = new FuelTank();
		gear = new GearMechanism(flywheel, operator);
		climb = new Climber();
		drive = new DriveTrain();
		carlos = new GearPickup();
		//driver = new DriverControls(drive, climb);
		operator = new OperatorControls(tank, gear, flywheel, collector, carlos);
		arc = new CircleArcs();
		getNewScript = new RocketScript();
		grid = new RocketGrids();
		alex = new AlexDrive(drive, climb);
		// tsar = new TsarControls(drive, climb, tank, gear, flywheel, collector);
		
		// cam = new DriverVision("cam0", 0);
		// cam.startUSBCamera();

		compressor = new Compressor();
		compressor.setClosedLoopControl(true);
		
		drive.shiftHigh();
		flywheel.setPID(Constants.FLYWHEEL_P, Constants.FLYWHEEL_I, Constants.FLYWHEEL_D, Constants.FLYWHEEL_F);

		gyro.reset();

		myDrive = new RobotDrive(drive.masterRight, drive.masterLeft);
		myDrive.setExpiration(1.0);
		
		//register to log
//		logger.register(robot);
//		logger.register(alex);
//		logger.register(drive);
//		logger.register(flywheel);
//		logger.register(climb);
//		logger.register(collector);
//		logger.register(tank);
//		logger.register(gear);
//		logger.startSocket(); 
		socket = true;
	}

	public void disabledInit(){
//		if(beenEnabled){
//			try {
//				logger.closeSocket(); socket = false;
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		beenEnabled = false;
	}

	public void autonomousInit() {
		drive.masterLeft.setVoltageRampRate(60);
		drive.masterRight.setVoltageRampRate(60);
		gyro.reset();
		drive.masterLeft.setEncPosition(0);
		drive.masterLeft.enable();
		drive.masterRight.setEncPosition(0);
		drive.masterRight.enable();
		autoModeSubStep = 0;
		rocketScriptCurrentCount = 0;
		gear.gearFlapOut();
		robot.setMode("Auto");
		// rocketScriptData = getNewScript.testTurningRight60();
		// rocketScriptData = getNewScript.testTurningRight90();
		// rocketScriptData = getNewScript.testTurningRight105();
		// rocketScriptData = getNewScript.testTurningRight170();
		// rocketScriptData = getNewScript.testTurningLeft60();
		// rocketScriptData = getNewScript.testTurningLeft90();
		// rocketScriptData = getNewScript.testTurningLeft105();
		// rocketScriptData = getNewScript.testTurningLeft170();
		// rocketScriptLength = rocketScriptData.length;
		
//		rocketScriptData = getNewScript.splineSideRight();
//		rocketScriptLength = rocketScriptData.length;

		boolean button1 = SmartDashboard.getBoolean("DB/Button 0", false);
		boolean button2 = SmartDashboard.getBoolean("DB/Button 1", false);
		boolean button3 = SmartDashboard.getBoolean("DB/Button 2", false);
		boolean button4 = SmartDashboard.getBoolean("DB/Button 3", false);
		double neutralZone = SmartDashboard.getNumber("DB/Slider 0", 0.0);
		double spline = SmartDashboard.getNumber("DB/Slider 1", 0.0);
		if (button1) {
			if (button2 && !button3 && !button4 && neutralZone == 0.0) { // middle																			// peg
				rocketScriptData = getNewScript.middleGear();
				rocketScriptLength = rocketScriptData.length;
				gear.gearFlapOut();
			} else if (button2 && !button3 && !button4 && neutralZone != 0.0) {
				rocketScriptData = getNewScript.middleGearNeutralZoneRed();
				rocketScriptLength = rocketScriptData.length;
				gear.gearFlapOut();
			} else if (!button2 && button3 && !button4 && neutralZone == 0.0) { // right
				if(spline != 0.0){
					rocketScriptData = getNewScript.splineSideRight();
					System.out.println("RUNNING SPLINE WOOO");
				} else {
					rocketScriptData = getNewScript.rightGear();					
				}
				rocketScriptLength = rocketScriptData.length;	
				gear.gearFlapOut();
			} else if (!button2 && button3 && !button4 && neutralZone != 0.0) {
				rocketScriptData = getNewScript.rightGearNeutralZone();
				rocketScriptLength = rocketScriptData.length;
				gear.gearFlapOut();
			} else if (!button2 && !button3 && button4 && neutralZone == 0.0) { // left
				if(spline != 0.0){
					rocketScriptData = getNewScript.splineSideLeft();
				} else {
					rocketScriptData = getNewScript.leftGear();
				}
				rocketScriptLength = rocketScriptData.length;					
				gear.gearFlapOut();
			} else if (!button2 && !button3 && button4 && neutralZone != 0.0) {
				rocketScriptData = getNewScript.leftGearNeutralZone();
				rocketScriptLength = rocketScriptData.length;
				gear.gearFlapOut();
			} else if (button2 && button3 && !button4) { // side peg to boiler
				rocketScriptData = getNewScript.rightGearToBoilerRed();
				rocketScriptLength = rocketScriptData.length;
				gear.gearFlapOut();
			} else if (!button2 && button3 && button4) { // 40kPa
				rocketScriptData = getNewScript.hopperToBoilerRed();
				rocketScriptLength = rocketScriptData.length;
				gear.gearFlapIn();
			} else if (button2 && !button3 && button4) { // 10kPa
				rocketScriptData = getNewScript.stayAtBoilerAndShoot();
				rocketScriptLength = rocketScriptData.length;
				gear.gearFlapIn();
			} else if (button2 && button3 && button4) {
				rocketScriptData = getNewScript.middleGearToBoilerRed();
				rocketScriptLength = rocketScriptData.length;
				gear.gearFlapOut();
			}
		} else {
			if (button2 && !button3 && !button4 && neutralZone == 0.0) { // middle
				rocketScriptData = getNewScript.middleGear();
				rocketScriptLength = rocketScriptData.length;
				gear.gearFlapOut();
			} else if (button2 && !button3 && !button4 && neutralZone != 0.0) {
				rocketScriptData = getNewScript.middleGearNeutralZoneBlue();
				rocketScriptLength = rocketScriptData.length;
				gear.gearFlapOut();
			} else if (!button2 && button3 && !button4 && neutralZone == 0.0) { // right
//				if(spline != 0.0){
					rocketScriptData = getNewScript.splineSideRight();
//				} else {
//					rocketScriptData = getNewScript.rightGear();					
//				}
				rocketScriptLength = rocketScriptData.length;
				gear.gearFlapOut();
			} else if (!button2 && button3 && !button4 && neutralZone != 0.0) {
				rocketScriptData = getNewScript.rightGearNeutralZone();
				rocketScriptLength = rocketScriptData.length;
				gear.gearFlapOut();
			} else if (!button2 && !button3 && button4 && neutralZone == 0.0) { // left
				if(spline != 0.0){
					rocketScriptData = getNewScript.splineSideLeft();
				} else {
					rocketScriptData = getNewScript.leftGear();
				}
				rocketScriptLength = rocketScriptData.length;					
				gear.gearFlapOut();
			} else if (!button2 && !button3 && button4 && neutralZone != 0.0) {
				rocketScriptData = getNewScript.leftGearNeutralZone();
				rocketScriptLength = rocketScriptData.length;
				gear.gearFlapOut();
			} else if (button2 && !button3 && button4) { // 10kPa
				rocketScriptData = getNewScript.stayAtBoilerAndShoot();
				rocketScriptLength = rocketScriptData.length;
				gear.gearFlapIn();
			} else if (button2 && button3 && !button4) { // side peg to boiler
				rocketScriptData = getNewScript.leftGearToBoilerBlue();
				rocketScriptLength = rocketScriptData.length;
				gear.gearFlapOut();
			} else if (!button2 && button3 && button4) { // 40kPa
				rocketScriptData = getNewScript.hopperToBoilerBlue();
				rocketScriptLength = rocketScriptData.length;
				gear.gearFlapIn();
			} else if (button2 && button3 && button4) {
				rocketScriptData = getNewScript.middleGearToBoilerBlue();
				rocketScriptLength = rocketScriptData.length;
				gear.gearFlapOut();
			}
		}
		
//		if(!socket){
//			logger.startSocket(); socket = true;
//		}
//		beenEnabled = true;
	}

	/**
	 * This function is called periodically during autonomous
	 */
	boolean gyroReset = false;
	@Override
	public void autonomousPeriodic() {
		System.out.println("NavX: " + gyro.getYaw());
//		logger.log();
		robot.setGyroAngle(gyro.getYaw());
		if(!gyroReset){
			gyro.reset();
			gyroReset = true;
		}
		if (rocketScriptCurrentCount < rocketScriptLength) {
			String[] values = rocketScriptData[rocketScriptCurrentCount].split(";");
			if (Integer.parseInt(values[0]) == RobotModes.FAST_DRIVE_STRAIGHT) {
				if (fastDriveStraight(Double.parseDouble(values[1]),
						Double.parseDouble(values[2]) - AutoConstants.STOPPING_INCHES, Double.parseDouble(values[3]))) {
					gotStartingENCClicks = false;
					gyro.reset();
					rocketScriptCurrentCount++;
				}
			}
			if (Integer.parseInt(values[0]) == RobotModes.SHOOTING) {
				flywheel.shootWithEncoders(Constants.FLYWHEEL_SPEED);
				System.out.println("Flywheel RPMS " + flywheel.flywheelSpeed());
				collector.intake(1);
				tank.tankMotorIntoFlywheel(0.4);
				shooting = true;
				// }
				if (shooting) {
					tank.runAgitator();
					System.out.println("***************Running Agitator");
					if (!setStartTimeFlywheel) {
						startTime = Timer.getFPGATimestamp();
						setStartTimeFlywheel = true;
					}
					if (Timer.getFPGATimestamp() - startTime > Double.parseDouble(values[1])) {
						System.out.println("******************************DONE SHOOTING");
						rocketScriptCurrentCount++;
					}
				}
			}
			if (Integer.parseInt(values[0]) == RobotModes.STOP_SHOOTING) {
				System.out.println("****************Stopping Shooting");
				flywheel.stopFlywheel();
				collector.stopCollector();
				tank.stopTank();
				shooting = false;
				setStartTimeFlywheel = false;
				rocketScriptCurrentCount++;
			}
			if (Integer.parseInt(values[0]) == RobotModes.WAIT) {
				System.out.println("WAITING");
				if (!waitStartTime) {
					startTime = Timer.getFPGATimestamp();
					waitStartTime = true;
				}
				if (Timer.getFPGATimestamp() - startTime > Double.parseDouble(values[1])) {
					System.out.println("******************************DONE WAITING");
					waitStartTime = false;
					rocketScriptCurrentCount++;
				}
			}
			if (Integer.parseInt(values[0]) == RobotModes.SPIN_UP) {
				flywheel.shootWithEncoders(Constants.FLYWHEEL_SPEED);
				rocketScriptCurrentCount++;
			}
			if (Integer.parseInt(values[0]) == RobotModes.TIME_DRIVE) {
				if (fastDriveStraightTimer(Double.parseDouble(values[1]), Double.parseDouble(values[2]), true)) {
					rocketScriptCurrentCount++;
				}
			}
			if (Integer.parseInt(values[0]) == RobotModes.LOW_GEAR) {
				drive.shiftLow();
				rocketScriptCurrentCount++;
			}
			if (Integer.parseInt(values[0]) == RobotModes.HIGH_GEAR) {
				drive.shiftHigh();
				rocketScriptCurrentCount++;
			}
			if (Integer.parseInt(values[0]) == RobotModes.ARC_TURN) {
//				System.out.println(grid.getGrid(values[2]).toString());
				if(spline(Double.parseDouble(values[1]), grid.getGrid(values[2]))){
					rocketScriptCurrentCount++;
				}
			}
			if (Integer.parseInt(values[0]) == RobotModes.TURN) {
				System.out.println("******************************** " + gyro.getAngle());
				if (turn(Double.parseDouble(values[1]))) {
					setStartTime = false;
					resetGyroTurn = false;
					rocketScriptCurrentCount++;
				}
			}
		}
	}

	@Override
	public void teleopInit() {
		gear.gearFlapOut();
		flywheel.stopFlywheel();
		collector.stopCollector();
		shooting = false;
		tank.stopTank();
		flywheel.flywheelMaster.configPeakOutputVoltage(12.0f, 0.0f);
		tank.retractAgitator();
		drive.shiftHigh();
		drive.masterRight.setVoltageRampRate(120);
		drive.masterLeft.setVoltageRampRate(120);
		robot.setMode("Tele");
//		if(!socket){
//			logger.startSocket(); socket = true;
//		}
//		beenEnabled = true;
	}

	/**
	 * This function is called periodically during operator control
	 */

	@Override
	public void teleopPeriodic() {
//		logger.log();
		// driver.driverControls();
		operator.operatorControls();
		// tsar.tsarControls();
		alex.AlexControls();
		SmartDashboard.putString("DB/String 0", "Flywheel Speed");
		SmartDashboard.putString("DB/String 1", "Flywheel Ready?");
		SmartDashboard.putString("DB/String 2", "High Gear?");
		SmartDashboard.putString("DB/String 3", "Collector Jam?");
		SmartDashboard.putString("DB/String 4", "Carlos Up?");
		SmartDashboard.putString("DB/String 5", Double.toString(flywheel.flywheelSpeed()));
		SmartDashboard.putString("DB/String 6", Boolean.toString(flywheel.flywheelReady(Constants.FLYWHEEL_SPEED)));
		SmartDashboard.putString("DB/String 7", Boolean.toString(drive.highGear));
		SmartDashboard.putString("DB/String 8", Boolean.toString(collector.collectorJam()));
		SmartDashboard.putString("DB/String 9", Boolean.toString(carlos.up));
	}

	/**
	 * This function is called periodically during test mode
	 */

	boolean reset = false;
	@Override
	public void testPeriodic() {
		if(!reset){
			System.out.println("Before Reset: " + gyro.getYaw());
			gyro.resetDisplacement();
			reset = true;
		}
		System.out.println("After Reset: " + gyro.getYaw());
//		System.out.println("NavX: " + gyro.getYaw());
//		System.out.println("Left: " + drive.masterLeft.getEncPosition());
//		System.out.println(" Right: " + drive.followerRightOne.getEncPosition());
//		System.out.println("0: " + port0.getEncPosition()); 
//		System.out.println("1: " + port1.getEncPosition()); 
//		System.out.println("2: " + port2.getEncPosition()); 
//		System.out.println("3: " + port3.getEncPosition()); 
//		System.out.println("4: " + port4.getEncPosition()); 
//		System.out.println("5: " + port5.getEncPosition()); 
//		System.out.println("6: " + port6.getEncPosition()); 
//		System.out.println("7: " + port7.getEncPosition()); 
//		System.out.println("8: " + port8.getEncPosition()); 
//		System.out.println("9: " + port9.getEncPosition()); 
//		System.out.println("10: " + port10.getEncPosition()); 
//		System.out.println("11: " + port11.getEncPosition()); 
//		System.out.println("12: " + port12.getEncPosition()); 
//		System.out.println("13: " + port13.getEncPosition()); 
	}

	// Auto Methods
	public boolean turn(double angleToDrive) {
		double pValue = 0;
		if (gyro.getAngle() < angleToDrive) {
			pValue = Constants.TURN_P_RIGHT;
		} else {
			pValue = Constants.TURN_P_LEFT;
		}
		if (!resetGyroTurn) {
			gyro.reset();
			resetGyroTurn = true;
			nominalVoltage = Constants.NOMINAL_VOLTAGE;
		}
		if (Math.abs(gyro.getAngle() - angleToDrive) < Constants.TURNING_DEADBAND) {
			System.out.println("*************************************HIT TURNING DEADBAND");
			if (!setStartTime) {
				startTime = Timer.getFPGATimestamp();
				setStartTime = true;
				nominalVoltage = Constants.NOMINAL_VOLTAGE;
			}
			if (Timer.getFPGATimestamp() - startTime > 0.5) {
				System.out.println("#######################################################HIT TUNE TIME");
				nominalVoltage = 0;
				if (Math.abs(gyro.getAngle() - angleToDrive) < Constants.TURNING_DEADBAND) {
					System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&done turning");
					drive.stopDrive();
					return true;
				} else {
					setStartTime = false;
				}
			}
		}
		System.out.println("*****************************TURNINGGGGGGGGGGGGGGGGGGGGGG");
		arcadeTurn(-((gyro.getAngle() - angleToDrive) * pValue));
		return false;
	}

	public void arcadeTurn(double rotateValue) {
		double leftMotorSpeed, rightMotorSpeed;
		if (rotateValue > 1.0) {
			rotateValue = 1.0;
		} else if (rotateValue < -1.0) {
			rotateValue = -1.0;
		}
		if (rotateValue >= 0.0) {
			rotateValue = rotateValue * rotateValue;
		} else {
			rotateValue = -(rotateValue * rotateValue);
		}
		if (rotateValue > 0.0) {
			leftMotorSpeed = 0.0;
			rightMotorSpeed = rotateValue;
		} else {
			leftMotorSpeed = -rotateValue;
			rightMotorSpeed = 0.0;
		}
		if (leftMotorSpeed > 1.0) {
			leftMotorSpeed = 1.0;
		} else if (leftMotorSpeed < -1.0) {
			leftMotorSpeed = -1.0;
		} else if (Math.abs(leftMotorSpeed) < Constants.NOMINAL_VOLTAGE) {
			if (leftMotorSpeed > 0.0) {
				leftMotorSpeed = nominalVoltage;
			} else {
				leftMotorSpeed = -nominalVoltage;
			}
		}
		if (rightMotorSpeed > 1.0) {
			rightMotorSpeed = 1.0;
		} else if (rightMotorSpeed < -1.0) {
			rightMotorSpeed = -1.0;
		} else if (Math.abs(rightMotorSpeed) < Constants.NOMINAL_VOLTAGE) {
			if (rightMotorSpeed > 0.0) {
				rightMotorSpeed = nominalVoltage;
			} else {
				rightMotorSpeed = -nominalVoltage;
			}
		}
		System.out.println("*********************** " + leftMotorSpeed);
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& " + rightMotorSpeed);
		drive.masterLeft.set(leftMotorSpeed);
		drive.masterRight.set(-rightMotorSpeed);
	}

	public boolean fastDriveStraightTimer(double speed, double howMuchTime, boolean withGyro) {
		if (!setStartTime) {
			startTime = Timer.getFPGATimestamp();
			setStartTime = true;
			gyro.reset();
		}
		System.out.println("Time is: " + (Timer.getFPGATimestamp() - startTime));
		if (Timer.getFPGATimestamp() - startTime < howMuchTime) {
			if (Math.abs(speed) > 0.00) {
				if (withGyro)
					myDrive.arcadeDrive(speed, -(gyro.getAngle() * Constants.DRIVING_P));
				else
					myDrive.arcadeDrive(speed, 0);
			}
		} else {
			myDrive.arcadeDrive(0.0, 0);
			setStartTime = false;
			return true;
		}
		return false;
	}

	public boolean fastDriveStraight(double speed, double inches, double angleToDrive) {
		if (gotStartingENCClicks == false) {
			gyro.reset();
			gotStartingENCClicks = true;
			startingENCClicks = drive.masterLeft.getEncPosition();
			System.out.println("Start ENC click value = " + startingENCClicks);
		}
		if (Math.abs((double) (drive.masterLeft.getEncPosition() - startingENCClicks)) > Math
				.abs(inches * AutoConstants.TICKS_PER_INCH)) {
			drive.masterLeft.set(0.00);
			drive.masterRight.set(0.00);
			System.out.println("Final NavX Angle: " + gyro.getAngle());
			System.out.println("Enc value after speed 0 " + drive.masterLeft.getEncPosition());
			return true;
		} else {
			if (inches > 0) {
				if (Math.abs((double) (drive.masterLeft.getEncPosition() - startingENCClicks)) < Math
						.abs(inches * AutoConstants.TICKS_PER_INCH))
					myDrive.arcadeDrive(speed, -((gyro.getAngle() - angleToDrive) * Constants.DRIVING_P)); // .07
			} else {
				myDrive.arcadeDrive(-speed, -((gyro.getAngle() - angleToDrive) * Constants.DRIVING_P)); // .07
			}
		}
		return false;
	}

	public boolean spline(double speed, RobotGrid spline) {
		if (gotStartingENCClicks == false) {
			gotStartingENCClicks = true;
			startingENCClicksLeft = drive.masterLeft.getEncPosition();
			startingENCClicksRight = -drive.followerRightOne.getEncPosition();
		}
		if (spline.getDistance() <= (((drive.masterLeft.getEncPosition() - startingENCClicksLeft)/ AutoConstants.TICKS_PER_INCH + (-drive.followerRightOne.getEncPosition() - startingENCClicksRight)/ AutoConstants.TICKS_PER_INCH))/2 || ((drive.masterRight.getOutputCurrent() > 30) && ((drive.masterLeft.getEncPosition() - startingENCClicksLeft)/ AutoConstants.TICKS_PER_INCH + (-drive.followerRightOne.getEncPosition() - startingENCClicksRight)/ AutoConstants.TICKS_PER_INCH)/2 > .5*spline.getDistance())) {
			drive.masterLeft.set(0.00);
			drive.masterRight.set(0.00);
			System.out.println("Final NavX Angle: " + gyro.getYaw());
			System.out.println("Enc value after speed 0 " + drive.masterLeft.getEncPosition());
			//System.out.println(spline.toString());
			gotStartingENCClicks = false;
			return true;
		} else {
			double angleToDrive;
			if (spline.getDistance()-(((drive.masterLeft.getEncPosition() - startingENCClicksLeft)/ AutoConstants.TICKS_PER_INCH + (-drive.followerRightOne.getEncPosition() - startingENCClicksRight)/ AutoConstants.TICKS_PER_INCH)/2) <(20*speed))
				speed *= spline.getDistance()-(((drive.masterLeft.getEncPosition() - startingENCClicksLeft)/ AutoConstants.TICKS_PER_INCH + (-drive.followerRightOne.getEncPosition() - startingENCClicksRight)/ AutoConstants.TICKS_PER_INCH)/2)/(20*speed);
			if (speed > 0)
				angleToDrive = (spline.getAngle(Math.abs(((drive.masterLeft.getEncPosition() - startingENCClicksLeft)/ AutoConstants.TICKS_PER_INCH + (-drive.followerRightOne.getEncPosition() - startingENCClicksRight)/ AutoConstants.TICKS_PER_INCH))/2));
			else
				angleToDrive = (spline.getReverseAngle(Math.abs(((drive.masterLeft.getEncPosition() - startingENCClicksLeft)/ AutoConstants.TICKS_PER_INCH + (-drive.followerRightOne.getEncPosition() - startingENCClicksRight)/ AutoConstants.TICKS_PER_INCH))/2));
			if (spline.getDistance() > 0) {
				if (spline.getDistance() > Math.abs(((drive.masterLeft.getEncPosition() - startingENCClicksLeft)/ AutoConstants.TICKS_PER_INCH + (-drive.followerRightOne.getEncPosition() - startingENCClicksRight)/ AutoConstants.TICKS_PER_INCH))/2)
				{
				System.out.println("speed = " + speed);
				System.out.println("angle = " + gyro.getYaw());
					if(angleToDrive < -90 && gyro.getYaw() > 90){
						double temp = -180 - angleToDrive;
						temp += -(180 - gyro.getYaw());
						myDrive.arcadeDrive(speed, -temp /360*8,true);
					} else if (angleToDrive > 90 && gyro.getYaw() < -90){
						double temp = 180 - angleToDrive;
						temp += (180 + gyro.getYaw());
						myDrive.arcadeDrive(speed, -temp /360*8,true);					
					} else {
						myDrive.arcadeDrive(speed, -((gyro.getYaw() - angleToDrive) /360*8),true);
					}
					//System.out.println(gyro.getYaw() - angleToDrive);
				}
			} else {
				myDrive.arcadeDrive(-speed, -((gyro.getYaw() - angleToDrive) /360*8),true);
			}
		}
		return false;
	}
}
