/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.subsystems.components.*;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  //The robot's subsystems and commands are defined here...
  //components
  private final Camera camera = new Camera();
  private final ColorSensor colorSensor = new ColorSensor();
  private final RangeSensors rangeSensors = new RangeSensors();

  public final DriveTrain driveTrain = new DriveTrain();
  private final Elevator elevator = new Elevator();
  private final ElevatorArm elevatorArm = new ElevatorArm();
  public final Indexer indexer = new Indexer(rangeSensors);
  private final IntakeElbow intakeElbow = new IntakeElbow();
  private final IntakeWheels intakeWheels = new IntakeWheels();
  private final Turret turret = new Turret();
  private final Shooter shooter = new Shooter(turret, indexer);


  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    XboxController driver = new XboxController(0);
    XboxController assist = new XboxController(1);
    JoystickButton buttonA_dr = new JoystickButton(assist,RobotMap.buttonA);
    JoystickButton buttonY_dr = new JoystickButton(assist,RobotMap.buttonY);
    JoystickButton buttonX_dr = new JoystickButton(assist,RobotMap.buttonX);
    JoystickButton buttonB_dr = new JoystickButton(assist,RobotMap.buttonB);

    JoystickButton buttonA_as = new JoystickButton(driver,RobotMap.buttonA);

    //JoystickButton buttonBumperLeft = new JoystickButton(driver,RobotMap.buttonBumperLeft);
    JoystickButton buttonBumperRight = new JoystickButton(driver,RobotMap.buttonBumperRight);

    buttonBumperRight.whenPressed((edu.wpi.first.wpilibj2.command.Command)new TurretStartTargeting(turret,shooter));
    buttonBumperRight.whenReleased((edu.wpi.first.wpilibj2.command.Command)new TurretEndTargeting(turret,shooter));

    buttonX_dr.whenPressed((edu.wpi.first.wpilibj2.command.Command)new IntakeWheelsOn(intakeWheels));
    buttonB_dr.whenPressed((edu.wpi.first.wpilibj2.command.Command)new IntakeWheelsOff(intakeWheels));

    buttonA_dr.whenPressed((edu.wpi.first.wpilibj2.command.Command)new IntakeDown(intakeElbow));
    buttonY_dr.whenPressed((edu.wpi.first.wpilibj2.command.Command)new IntakeHome(intakeElbow));

    buttonA_as.whenReleased((edu.wpi.first.wpilibj2.command.Command)new LiftBallsStop(indexer));
    buttonA_as.whenPressed((edu.wpi.first.wpilibj2.command.Command)new LiftBalls(indexer));

  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    // return m_autoCommand;
    return new WeekZeroAuto(turret, shooter, driveTrain, indexer);
  }
}
