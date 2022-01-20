/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.*;
import frc.robot.commands.groups.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.components.*;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
//import edu.wpi.first.wpilibj2.command.RunCommand;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  //The robot's subsystems and commands are defined here...
  //components
  public final XboxController driver = new XboxController(RobotMap.xboxControllerDriver);
  private final NavX navX = new NavX();
  public final DriveTrain driveTrain = new DriveTrain(navX);
  private final Turret turret = new Turret();


  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  private void configureButtonBindings() {

    JoystickButton buttonRB_dr = new JoystickButton(driver,RobotMap.buttonBumperRight);
    JoystickButton buttonLB_dr = new JoystickButton(driver,RobotMap.buttonBumperLeft);
    JoystickButton buttonStart_dr = new JoystickButton(driver,RobotMap.buttonStart);
    JoystickButton buttonSelect_dr = new JoystickButton(driver,RobotMap.buttonSelect);

    buttonStart_dr.whenPressed(new autoHeadToggle(turret));
    buttonSelect_dr.whenPressed(new headStop(turret));

    buttonRB_dr.whileHeld(new headright(turret));
    buttonLB_dr.whileHeld(new headleft(turret));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    // return m_autoCommand;
    return null;
    //return new RightTrench(bombardier, driveTrain, intakeWheels, intakeElbow);
  }

  public void disabledLEDS(){
    //limeLight.ledOff();
  }

  public void enableAutoIndexing(){
    //indexer.enabledAutoIndexing(true);
  }
}
