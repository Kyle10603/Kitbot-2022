// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.DefaultDrive;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  //instantiates a new drive joystick with the XboxController class
  private XboxController driveStick;

  //Instantiates two button, which will represent the left and right bumpers on the drive stick
  private JoystickButton driveLeftBumper;
  private JoystickButton driveRightBumper;

  //Instantiates all subsystems
  private Drive drive;
  private Climber climber;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    //declares the drive joystick as an XboxController in port 0 on the Driver Station
    driveStick = new XboxController(0);

    //declares all subsystems
    drive = new Drive();
    climber = new Climber();

    //declares the button created before as JoystickButtons on the driveStick with a buttonID equal to its corresponding value
    //in the XboxController class. Whenever you are putting in button ids, use the values provided in the XboxController class
    //as shown. All buttons and axis are describes in their names, just make sure to add the .value after accessing the button
    //or axis to get its id.
    driveLeftBumper = new JoystickButton(driveStick, XboxController.Button.kLeftBumper.value);
    driveRightBumper = new JoystickButton(driveStick, XboxController.Button.kRightBumper.value);

    /*
      Note the, () ->, expression here. This is called a lambda expression, or an unnamed function. It is connected with the
      DoubleSupplier class used in the Drive subsystem as well as the more general Supplier class. It allows you to pass in a
      block of code (method) , such as the driveStick.getLeftY(), as an argument for the function that is run whenever the 
      getAsDouble() method is called, essentially "supplying" a double value. We use this so that we only have to create one 
      DefaultDrive command that can get updated values by running the getLeftY() or getRightX() methods each time the command 
      iterates. To make it easier to understand the syntax, think of the () as representing the parenthesis on a normal method, 
      where your arguments are passed in. The -> and code afterwards is all of the code that is in the curly braces in the method.

      Example:

        public void arcadeDrive(double speed, double rotation) {
          drive.arcadeDrive(speed, rotation);
        }
      
      would be the same as the lamba expression
        (speed, rotation) -> drive.arcadeDrive(speed, rotation);

      and a multiline function like
        public void myFunction() {
          int n = 0;
          n++;
        }

      can be represented as this lambda expression, noting the curly braces
        () -> {
          int n = 0;
          n++;
        }
    */
    
    drive.setDefaultCommand(new DefaultDrive(
      () -> driveStick.getLeftY(), 
      () -> driveStick.getRightX(), 
      drive));
    // Configure the buttons to start new commands when they are pressed or released
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    /*
      These are called button bindings, describing which commands should be run when the robot program recognizes a button has
      been pressed or released. Any command can be passed in here, including commands that are created within the argument, like
      the "new DefaultDrive" statement above, or commands that are created as variables in the RobotContainer program. In this
      case, the command InstantCommand() is used, which is a special type of command that is created in one line. Any code that
      normally goes in the execute() function inside a command class is passed into the InstantCommand using a lamba expression
      as the first argument, with any subsystems that are used by the command that normally go into the addRequirements() method
      as each subsequent argument. 

      Here, notice the new syntax: "climber::winchDown". This is a shorthand syntax for a lamba expression that can be used when
      only one method is being passed into the expression and that method has no arguments. In the first InstantCommand, 
      "climber::winchDown" is the same as "() -> climber.winchDown()". In the shorthand statement, the class variable name comes
      first, followed by two colons and then the method name to be used without the parenthesis. In our case, the "climber" class
      name is used, followed by two colons, and then the winchDown method name, since the winchDown() method has no arguments.

      Look at this link for more information about the InstantCommand class:
      https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj2/command/InstantCommand.html
    */
    driveLeftBumper.whenPressed(new InstantCommand(climber::winchDown, climber));
    /*
      Here, we are adding a button binding that senses when the button is released, running the stop() method for the climber.
      We have to do this because the robot program will not automatically reverse any command that is run through the whenPressed
      binding. If this statement wasn't included, the climber would continually winch down endlessly, since we never stop the
      climber.
    */
    driveLeftBumper.whenReleased(new InstantCommand(climber::stop, climber));

    driveRightBumper.whenPressed(new InstantCommand(climber::winchUp, climber));
    driveRightBumper.whenReleased(new InstantCommand(climber::stop, climber));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new InstantCommand(() -> {});
  }
}
