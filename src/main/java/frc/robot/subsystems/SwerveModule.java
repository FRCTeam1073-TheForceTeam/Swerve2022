// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

/** Add your docs here. */
public class SwerveModule 
{
    private SwerveModuleConfig cfg;
    private WPI_TalonFX steerMotor, driveMotor;
    private SwerveModuleIDConfig ids;
    private CANCoder steerEncoder;

    public SwerveModule(SwerveModuleConfig cfg, SwerveModuleIDConfig ids)
    {
        this.cfg = cfg;
        this.ids = ids;
        steerMotor = new WPI_TalonFX(ids.steerMotorID);
        driveMotor = new WPI_TalonFX(ids.driveMotorID);
        steerEncoder = new CANCoder(ids.steerEncoderID);
        setUpMotors();
    }

    public double getSteeringAngle()
    {
        return 0;
    }

    public double getVelocity(){
        return 0;
    }

    public void setCommand(double steeringAngle, double Velocity){

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
}