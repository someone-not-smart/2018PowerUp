package com.team5115.systems;

import com.ctre.CANTalon;
//import com.kauailabs.navx.frc.AHRS;
import com.team5115.Constants;

public class DriveTrain {

    public boolean inuse;

    CANTalon frontleft;
    CANTalon frontright;
    CANTalon backleft;
    CANTalon backright;

    public double lastLeftSpeed = 0;
    public double lastRightSpeed = 0;

    public int direction;

    public DriveTrain(){

        frontleft = new CANTalon(Constants.FRONT_LEFT_MOTOR_ID);
        frontright = new CANTalon(Constants.FRONT_RIGHT_MOTOR_ID);
        backleft = new CANTalon(Constants.BACK_LEFT_MOTOR_ID);
        backright = new CANTalon(Constants.BACK_RIGHT_MOTOR_ID);

        backleft.changeControlMode(CANTalon.TalonControlMode.Follower);
        backright.changeControlMode(CANTalon.TalonControlMode.Follower);
        backleft.set(frontleft.getDeviceID());
        backright.set(frontright.getDeviceID());
        direction = 1;

    }

    /**
     * Drive the robot with given forward and angular speeds, as a % of vBus
     * @param speed
     * @param turn
     */
    public void drive(double speed, double turn){
        double leftspeed = speed + turn;
        double rightspeed = speed - turn;

        if(Math.abs(leftspeed) > 1){
            leftspeed = 1;
        }
        if(Math.abs(rightspeed) > 1){
            rightspeed = 1;
        }

        frontleft.set(leftspeed);
        frontright.set(rightspeed);
    }

    public double getDist() {
        // TODO: average of left and right encoder distances
        double quadEncoderPosL = frontleft.getEncPosition();
        double quadEncoderPosR = frontright.getEncPosition();
        return quadEncoderPosL + quadEncoderPosR / 2;
    }

    public double getForwardVelocity() {
        // TODO: average of encoder velocities from talons
        double quadEncoderVelocityL = frontleft.getEncVelocity();
        double quadEncoderVelocityR = frontright.getEncVelocity();
        return quadEncoderVelocityL + quadEncoderVelocityR / 2;
    }

    public double getYaw() {
        // TODO: yaw from gyro
        return navx.getYaw();
    }

    public double getTurnVelocity() {
        // TODO: yaw velocity from gyro
        return navx.getRate();
    }

    // This method resets the values given by the encoders to a default of 0
    public void resetEncoders() {
        frontleft.setPosition(0);
        frontright.setPosition(0);
    }

}
