package com.team5115.auto;

import com.team5115.Constants;
import com.team5115.PID;
import com.team5115.robot.Robot;
import com.team5115.statemachines.StateMachineBase;

public class Turn extends StateMachineBase {

    public static final int DRIVING = 1;
    public static final int FINISHED = 2;

    double angle;
    double maxSpeed;

    double targetAngle;	// encoder reading when the command started

    PID pidController;

    public Turn(double angle, double maxSpeed) {
        this.angle = angle;
        this.maxSpeed = maxSpeed;
    }

    public void setState(int s) {
        switch (s) {
            case DRIVING:

                // run once when entering the DRIVING state
                targetAngle = Robot.drivetrain.getYaw() + angle;

                // construct the PID controller every time it's run in case constants have changed
                pidController = new PID(Constants.AUTO_TURN_KP, Constants.AUTO_TURN_KI, Constants.AUTO_TURN_KD, maxSpeed);

        }

        state = s;
    }

    public void update() {
        switch (state) {
            case DRIVING:

                // run every Constants.DELAY seconds while driving
                double clearYaw = clearSteer(Robot.drivetrain.getYaw(), targetAngle);
                Robot.drivetrain.drive(0, pidController.getPID(targetAngle, clearYaw, Robot.drivetrain.getForwardVelocity()));

                if (pidController.isFinished(Constants.TURN_TOLERANCE, Constants.TURN_DTOLERANCE)) {
                    Robot.drivetrain.drive(0, 0);
                    setState(FINISHED);
                }

        }
    }

    private double clearSteer(double yaw, double target) {
        if (Math.abs(target - yaw) > Math.PI) {
            if (target < Math.PI) {	// yaw must be too high for target
                yaw -= Math.PI * 2;
            } else {	// yaw must be too low for target
                yaw += Math.PI * 2;
            }
        }

        return yaw;
    }

}
