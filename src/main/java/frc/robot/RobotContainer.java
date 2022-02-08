/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019-2020 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

// import Xbox Controller and related buttons and axes
import edu.wpi.first.wpilibj.XboxController;
import static edu.wpi.first.wpilibj.XboxController.Axis.*;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import static edu.wpi.first.wpilibj.XboxController.Button.*;

// import commands
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Climb;

// import RevDrivetrain subsystem
import frc.robot.subsystems.RevDrivetrain;

// import constants
import static frc.robot.Constants.*;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */

public class RobotContainer {

  // Drive Controller
  private XboxController xbox = new XboxController(kXboxPort);

  // Drive Subsystem
  private final RevDrivetrain rDrive = new RevDrivetrain();

  // ClimAuto subsystem
  private final Climb climb = new Climb();

  // Drive with Controller 
  private Command manualDrive = new RunCommand(
    
    () -> rDrive.getDifferentialDrive().
    tankDrive(rDrive.deadband(xbox.getRawAxis(kLeftY.value), percentDeadband), 
    rDrive.deadband(xbox.getRawAxis(kRightY.value), percentDeadband),
    false
    ),
    rDrive
    );

    private Command moveArm = new RunCommand(
  
  // move the lift up and down with right and left triggers, respectively
    () -> climb.move(xbox.getRawAxis(kRightTrigger.value) - xbox.getRawAxis(kLeftTrigger.value)), climb);

  public RobotContainer() {
    rDrive.setDefaultCommand(manualDrive);

    // configure the button bindings
    configureButtonBindings();

    // run climbTeleop as the default command
    climb.setDefaultCommand(moveArm);
  }

  private void configureButtonBindings() {
    
    // checking for whether hook is engaged when A is pressed
    // if it is: run the climb sequential command group
    // if it is not: run an empty instant command group (does nothing)

    new JoystickButton(xbox, kA.value)
    .whenPressed(new InstantCommand (() -> climb.reaching(false)));

    new JoystickButton(xbox, kY.value)
    .whenPressed(new InstantCommand (() -> climb.reaching(true)));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
}
