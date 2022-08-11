// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
  private SwerveDriveKinematics kinematics;
  private SwerveModule[] modules;
  private ChassisSpeeds chassisSpeeds;
  private boolean debug = true;

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {

    modules = new SwerveModule[4];
    //front left
    SwerveModuleConfig moduleConfig = new SwerveModuleConfig();
    SwerveModuleIDConfig moduleIDConfig = new SwerveModuleIDConfig();
    moduleIDConfig.driveMotorID = 1;
    moduleIDConfig.steerMotorID = 2;
    moduleIDConfig.steerEncoderID = 3;
    moduleConfig.position = new Translation2d(0.217,0.217);
    modules[0] = new SwerveModule(moduleConfig, moduleIDConfig);

    //front right
    moduleConfig = new SwerveModuleConfig();
    moduleIDConfig = new SwerveModuleIDConfig();
    moduleIDConfig.driveMotorID = 4;
    moduleIDConfig.steerMotorID = 5;
    moduleIDConfig.steerEncoderID = 6;
    moduleConfig.position = new Translation2d(0.217,-0.217);
    modules[1] = new SwerveModule(moduleConfig, moduleIDConfig);

    //back left
    moduleConfig = new SwerveModuleConfig();
    moduleIDConfig = new SwerveModuleIDConfig();
    moduleIDConfig.driveMotorID = 7;
    moduleIDConfig.steerMotorID = 8;
    moduleIDConfig.steerEncoderID = 9;
    moduleConfig.position = new Translation2d(-0.217,0.217);
    modules[2] = new SwerveModule(moduleConfig, moduleIDConfig);

    //back right
    moduleConfig = new SwerveModuleConfig();
    moduleIDConfig = new SwerveModuleIDConfig();
    moduleIDConfig.driveMotorID = 10;
    moduleIDConfig.steerMotorID = 11;
    moduleIDConfig.steerEncoderID = 12;
    moduleConfig.position = new Translation2d(-0.217,-0.217);
    modules[3] = new SwerveModule(moduleConfig, moduleIDConfig);

    kinematics = new SwerveDriveKinematics(
      modules[0].position,
      modules[1].position,
      modules[2].position,
      modules[3].position
    );
    chassisSpeeds = new ChassisSpeeds(0,0,0);
  }

  public void setChassisSpeeds(ChassisSpeeds speeds){
    chassisSpeeds = speeds;
  }

  public ChassisSpeeds getChassisSpeeds(){
    SwerveModuleState[] wheelStates = new SwerveModuleState[4];
    wheelStates[0] = new SwerveModuleState();
    wheelStates[1] = new SwerveModuleState();
    wheelStates[2] = new SwerveModuleState();
    wheelStates[3] = new SwerveModuleState();

    wheelStates[0].speedMetersPerSecond = modules[0].getVelocity();
    wheelStates[1].speedMetersPerSecond = modules[1].getVelocity();
    wheelStates[2].speedMetersPerSecond = modules[2].getVelocity();
    wheelStates[3].speedMetersPerSecond = modules[3].getVelocity();

    wheelStates[0].angle = new Rotation2d(modules[0].getSteeringAngle());
    wheelStates[1].angle = new Rotation2d(modules[1].getSteeringAngle());
    wheelStates[2].angle = new Rotation2d(modules[2].getSteeringAngle());
    wheelStates[3].angle = new Rotation2d(modules[3].getSteeringAngle());

    return kinematics.toChassisSpeeds(wheelStates);
  }

  @Override
  public void periodic() {
    if (! debug){
      // This method will be called once per scheduler run
      SwerveModuleState[] states = kinematics.toSwerveModuleStates(chassisSpeeds);
      SwerveDriveKinematics.desaturateWheelSpeeds(states, 3.0);

      modules[0].setCommand(states[0].angle.getRadians(), states[0].speedMetersPerSecond);
      modules[1].setCommand(states[1].angle.getRadians(), states[1].speedMetersPerSecond);
      modules[2].setCommand(states[2].angle.getRadians(), states[2].speedMetersPerSecond);
      modules[3].setCommand(states[3].angle.getRadians(), states[3].speedMetersPerSecond);
    }
    SmartDashboard.putNumber("Module 0 Angle", modules[0].getSteeringAngle());
    SmartDashboard.putNumber("Module 1 Angle", modules[1].getSteeringAngle());
    SmartDashboard.putNumber("Module 2 Angle", modules[2].getSteeringAngle());
    SmartDashboard.putNumber("Module 3 Angle", modules[3].getSteeringAngle());
    SmartDashboard.putNumber("Module 0 Velocity", modules[0].getVelocity());
    SmartDashboard.putNumber("Module 1 Velocity", modules[1].getVelocity());
    SmartDashboard.putNumber("Module 2 Velocity", modules[2].getVelocity());
    SmartDashboard.putNumber("Module 3 Velocity", modules[3].getVelocity());
  }
  public void setDebugSpeed(double speed){
    modules[0].setDebugTranslate(speed);
    modules[1].setDebugTranslate(speed);
    modules[2].setDebugTranslate(speed);
    modules[3].setDebugTranslate(speed);
  }
  public void setDebugAngle(double angle){
    modules[0].setDebugRotate(angle);
    modules[1].setDebugRotate(angle);
    modules[2].setDebugRotate(angle);
    modules[3].setDebugRotate(angle);
  }
}