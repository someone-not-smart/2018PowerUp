package com.team5115.auto;

import com.team5115.Constants;
import com.team5115.PID;
import com.team5115.robot.Robot;
import com.team5115.statemachines.StateMachineBase;

public class AutoDrive extends StateMachineBase {

    public static final int DRIVING = 1;
    public static final int FINISHED = 2;

    // deltas
    double dist;
    double angle;

    // absolute readings when finished
    double targetDist;
    double targetAngle;

    PID forwardController;
    PID turnController;

    public void startLine(double dist, double maxSpeed) {
        this.dist = dist;
        this.angle = 0;

        forwardController = new PID(Constants.AUTO_FORWARD_KP, Constants.AUTO_FORWARD_KI, Constants.AUTO_FORWARD_KD, maxSpeed);
        turnController = new PID(Constants.AUTO_TURN_KP, Constants.AUTO_TURN_KI, Constants.AUTO_TURN_KD);

        setState(DRIVING);
    }

    public void startTurn(double angle, double maxSpeed) {
        this.dist = 0;
        this.angle = angle;

        forwardController = new PID(Constants.AUTO_FORWARD_KP, Constants.AUTO_FORWARD_KI, Constants.AUTO_FORWARD_KD);
        turnController = new PID(Constants.AUTO_TURN_KP, Constants.AUTO_TURN_KI, Constants.AUTO_TURN_KD, maxSpeed);

        setState(DRIVING);
    }

    public void update() {
        switch (state) {
            case DRIVING:

                // run every Constants.DELAY seconds while driving
                double vForward = forwardController.getPID(targetDist, Robot.drivetrain.getDist(), Robot.drivetrain.getForwardVelocity());

                double clearYaw = clearSteer(Robot.drivetrain.getYaw(), targetAngle);
                double vTurn = turnController.getPID(targetAngle, clearYaw, Robot.drivetrain.getTurnVelocity());

                Robot.drivetrain.drive(vForward, vTurn);

                if (forwardController.isFinished(Constants.FORWARD_TOLERANCE, Constants.FORWARD_DTOLERANCE) && turnController.isFinished(Constants.TURN_TOLERANCE, Constants.TURN_DTOLERANCE)) {
                    Robot.drivetrain.drive(0, 0);
                    setState(FINISHED);
                }

                break;

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
