/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class WeekZeroAuto extends SequentialCommandGroup {
  /**
   * Creates a new WeekZeroAuto.
   */
  
  public WeekZeroAuto(Turret in_turret, Shooter in_shooter, DriveTrain in_drive, Indexer in_Indexer) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand())
    super(new TurretStartTargetingAuto(in_turret, in_shooter, in_Indexer), new TurretEndTargeting(in_turret, in_shooter), new driveFoward(in_drive));
  }
}
