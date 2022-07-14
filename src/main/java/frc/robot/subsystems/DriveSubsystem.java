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

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    kinematics = new SwerveDriveKinematics(
      new Translation2d(0,0),
      new Translation2d(0,0),
      new Translation2d(0,0),
      new Translation2d(0,0)
    );

    modules = new SwerveModule[4];
    modules[0] = new SwerveModule(new SwerveModuleConfig(),new SwerveModuleIDConfig());
    modules[1] = new SwerveModule(new SwerveModuleConfig(),new SwerveModuleIDConfig());
    modules[2] = new SwerveModule(new SwerveModuleConfig(),new SwerveModuleIDConfig());
    modules[3] = new SwerveModule(new SwerveModuleConfig(),new SwerveModuleIDConfig());

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
    // This method will be called once per scheduler run
    SwerveModuleState[] states = kinematics.toSwerveModuleStates(chassisSpeeds);
    SwerveDriveKinematics.desaturateWheelSpeeds(states, 3.0);

    modules[0].setCommand(states[0].angle.getRadians(), states[0].speedMetersPerSecond);
    modules[1].setCommand(states[1].angle.getRadians(), states[1].speedMetersPerSecond);
    modules[2].setCommand(states[2].angle.getRadians(), states[2].speedMetersPerSecond);
    modules[3].setCommand(states[3].angle.getRadians(), states[3].speedMetersPerSecond);

    SmartDashboard.putNumber("Module 0 Angle", modules[0].getSteeringAngle());
    SmartDashboard.putNumber("Module 1 Angle", modules[1].getSteeringAngle());
    SmartDashboard.putNumber("Module 2 Angle", modules[2].getSteeringAngle());
    SmartDashboard.putNumber("Module 3 Angle", modules[3].getSteeringAngle());
    SmartDashboard.putNumber("Module 0 Velocity", modules[0].getVelocity());
    SmartDashboard.putNumber("Module 1 Velocity", modules[1].getVelocity());
    SmartDashboard.putNumber("Module 2 Velocity", modules[2].getVelocity());
    SmartDashboard.putNumber("Module 3 Velocity", modules[3].getVelocity());
  }
}