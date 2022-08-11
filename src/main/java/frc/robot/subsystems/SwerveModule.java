// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.math.geometry.Translation2d;

/** Add your docs here. */
public class SwerveModule 
{
    private SwerveModuleConfig cfg;
    private WPI_TalonFX steerMotor, driveMotor;
    private SwerveModuleIDConfig ids;
    private CANCoder steerEncoder;
    public Translation2d position;

    public SwerveModule(SwerveModuleConfig cfg, SwerveModuleIDConfig ids)
    {
        this.position = cfg.position;
        this.cfg = cfg;
        this.ids = ids;
        steerMotor = new WPI_TalonFX(ids.steerMotorID);
        driveMotor = new WPI_TalonFX(ids.driveMotorID);
        steerEncoder = new CANCoder(ids.steerEncoderID);
        setUpMotors();
    }

    public double getSteeringAngle()
    {
        return steerEncoder.getAbsolutePosition()-cfg.steerAngleOffset;
    }

    public double getVelocity(){
        return driveMotor.getSelectedSensorVelocity()/cfg.tickPerMeter*10.0;
    }
//*Wrapping code from sds example swerve library
    public void setCommand(double steeringAngle, double driveVelocity){
        steeringAngle %= (2.0 * Math.PI);
        if (steeringAngle < 0.0) {
            steeringAngle += 2.0 * Math.PI;
        }

        double difference = steeringAngle - getSteeringAngle();
        // Change the target angle so the difference is in the range [-pi, pi) instead of [0, 2pi)
        if (difference >= Math.PI) {
            steeringAngle -= 2.0 * Math.PI;
        } else if (difference < -Math.PI) {
            steeringAngle += 2.0 * Math.PI;
        }
        difference = steeringAngle - getSteeringAngle(); // Recalculate difference

        // If the difference is greater than 90 deg or less than -90 deg the drive can be inverted so the total
        // movement of the module is less than 90 deg
        if (difference > Math.PI / 2.0 || difference < -Math.PI / 2.0) {
            // Only need to add 180 deg here because the target angle will be put back into the range [0, 2pi)
            steeringAngle += Math.PI;
            driveVelocity *= -1.0;
        }

        // Put the target angle back into the range [0, 2pi)
        steeringAngle %= (2.0 * Math.PI);
        if (steeringAngle < 0.0) {
            steeringAngle += 2.0 * Math.PI;
        }

        setDriveVelocity(driveVelocity);
        setSteerAngle(steeringAngle);
    }
    public void setDriveVelocity(double driveVelocity){
        driveMotor.set(ControlMode.Velocity,driveVelocity*cfg.tickPerMeter*0.1);
    }
    public void setSteerAngle(double steeringAngle){
        steerMotor.set(ControlMode.Position,steeringAngle*cfg.tickPerRadian);
     }

    public void setUpMotors()
    {
        steerMotor.configFactoryDefault();
        driveMotor.configFactoryDefault();

        steerMotor.setInverted(false);
        driveMotor.setInverted(false);

        steerMotor.setSafetyEnabled(false);
        driveMotor.setSafetyEnabled(false);

        steerMotor.setNeutralMode(NeutralMode.Brake);
        driveMotor.setNeutralMode(NeutralMode.Brake);

        steerMotor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, cfg.steerCurrentLimit, cfg.steerCurrentThreshold, cfg.steerCurrentThresholdTime));
        driveMotor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, cfg.driveCurrentLimit, cfg.driveCurrentThreshold, cfg.driveCurrentThresholdTime));

        //TODO: Fix the thing
        //steerMotor.configSelectedFeedbackSensor(steerEncoder, 0, 0);
        driveMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);



        steerMotor.config_kP(0, cfg.steerP);
        steerMotor.config_kI(0, cfg.steerI);
        steerMotor.config_kD(0, cfg.steerD);
        steerMotor.config_kF(0, cfg.steerF);
        steerMotor.setIntegralAccumulator(0);

        driveMotor.config_kP(0, cfg.driveP);
        driveMotor.config_kI(0, cfg.driveI);
        driveMotor.config_kD(0, cfg.driveD);
        driveMotor.config_kF(0, cfg.driveF);
        driveMotor.setIntegralAccumulator(0);
    }

    public void setDebugTranslate(double power)
    {
        driveMotor.set(ControlMode.PercentOutput, power);
    }

    public void setDebugRotate(double power)
    {
        steerMotor.set(ControlMode.PercentOutput, power);
    }
}