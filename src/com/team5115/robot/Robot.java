package com.team5115.robot;

import com.team5115.Constants;
import com.team5115.systems.DriveTrain;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends IterativeRobot {

	// Define subsystems
	public static DriveTrain drivetrain;
	
	// Initialization phase of the robot class
	// This method runs once when the robot turns on and does not run again until the robot reboots
	public void robotInit() {
		// Initialize subsystems
		drivetrain = new DriveTrain();
	}

	// Runs once when the autonomous phase of the game starts
	public void autonomousInit() {}

	//Runs periodically while the game is in the autonomous phase
	public void autonomousPeriodic() {
		Timer.delay(Constants.DELAY);
	}

	// Runs once when the game enters the driver operated stage
	public void teleopInit() {}

	// Runs periodically when the game is in the driver operated stage
	public void teleopPeriodic() {
		Timer.delay(Constants.DELAY);
	}
	
	// Runs when the robot is disabled
	public void disabledInit() {}

	// Runs periodically while the robot is disabled
	public void disabledPeriodic() {
		Timer.delay(Constants.DELAY);
	}

}
                                                                                                                         