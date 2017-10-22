package com.team5115.auto;

import com.team5115.Constants;
import com.team5115.PID;
import com.team5115.robot.Robot;
import com.team5115.statemachines.StateMachineBase;

public class Line extends StateMachineBase {
	
	public static final int DRIVING = 1;
	public static final int FINISHED = 2;
	
	double dist;
	double maxSpeed;
	
	double targetDist;	// target encoder reading including what has already been travelled
	
	PID pidController;
	
	public Line(double dist, double maxSpeed) {
		this.dist = dist;
		this.maxSpeed = maxSpeed;
	}
	
	public void setState(int s) {
		switch (s) {
		case DRIVING:
			
			// run once when entering the DRIVING state
			targetDist = Robot.drivetrain.getDist() + dist;
			
			// construct the PID controller every time it's run in case constants have changed
			pidController = new PID(Constants.AUTO_LINE_KP, Constants.AUTO_LINE_KI, Constants.AUTO_LINE_KD, maxSpeed);
			
		}
		
		state = s;
	}
	
	public void update() {
		switch (state) {
		case DRIVING:
			
			// run every Constants.DELAY seconds while driving
			Robot.drivetrain.drive(pidController.getPID(targetDist, Robot.drivetrain.getDist(), Robot.drivetrain.getForwardVelocity()), 0);
			
			if (pidController.isFinished(Constants.LINE_TOLERANCE, Constants.LINE_DTOLERANCE)) {
				Robot.drivetrain.drive(0, 0);
				setState(FINISHED);
			}
			
		}
	}

}
