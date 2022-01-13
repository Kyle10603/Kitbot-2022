// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {

  private Victor winch;

  /** Creates a new Climber. */
  public Climber() {
    winch = new Victor(4);
  }

  //This is a helper method that clarifies what winching up means in the context of the set method; 
  //might need to be inverted depending on motor orientation
  public void winchUp() {
    set(0.8);
  }

  //Might need to be inverted depending on motor orientation
  public void winchDown() {
    set(-0.8);
  }

  //Helper method to stop the climber
  public void stop() {
    set(0);
  }

  //Main set method that can be called externally
  public void set(double speed) {
    winch.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
