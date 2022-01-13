// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {
  private Victor rightMaster;
  private Victor rightFollower;
  private Victor leftMaster;
  private Victor leftFollower;

  private MotorControllerGroup leftGroup;
  private MotorControllerGroup rightGroup;

  private DifferentialDrive differentialDrive;
  /** Creates a new Drive. */
  public Drive() {
    rightMaster = new Victor(0);
    rightFollower = new Victor(1);
    leftMaster = new Victor(2);
    leftFollower = new Victor(3);

    leftGroup = new MotorControllerGroup(leftMaster, leftFollower);
    rightGroup = new MotorControllerGroup(rightMaster, rightFollower);

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
