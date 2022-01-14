// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {
  private WPI_VictorSPX rightMaster;
  private WPI_VictorSPX rightFollower;
  private WPI_VictorSPX leftMaster;
  private WPI_VictorSPX leftFollower;

  private MotorControllerGroup leftGroup;
  private MotorControllerGroup rightGroup;

  private DifferentialDrive differentialDrive;
  /** Creates a new Drive. */
  public Drive() {
    rightMaster = new WPI_VictorSPX(0);
    rightFollower = new WPI_VictorSPX(1);
    leftMaster = new WPI_VictorSPX(2);
    leftFollower = new WPI_VictorSPX(3);

    leftGroup = new MotorControllerGroup(rightMaster, rightFollower);
    rightGroup = new MotorControllerGroup(leftMaster, leftFollower);

    //Note this change; implement this into your own code to ensure that the robot doesn't turn when it's supposed to drive forward
    rightGroup.setInverted(true);

    differentialDrive = new DifferentialDrive(leftGroup, rightGroup);
  }

  public void arcadeDrive(double speed, double rotation) {
    differentialDrive.arcadeDrive(speed, rotation);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
