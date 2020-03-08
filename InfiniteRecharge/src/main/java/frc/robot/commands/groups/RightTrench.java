/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.groups;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;
import frc.robot.commands.auto.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class RightTrench extends SequentialCommandGroup {
  /**
   * Creates a new RightTrench.
   */
  public RightTrench(Bombardier in_Bombardier, DriveTrain in_drive, IntakeWheels in_IntakeWheels, IntakeElbow in_Elbow) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand())
    super(new ShootPreloaded(in_Bombardier),
          new TurnToAngle(in_drive, -35, 0.325), 
          new DriveStraight(in_drive, 72, 0.25), 
          new TurnToAngle(in_drive, 0, 0.325),
          new AutoIntake(in_Elbow, in_IntakeWheels), 
          new DriveStraight(in_drive, 95, 0.2));
    //super(new ShootPreloaded(in_Bombardier), new TurnToAngle(in_drive, -45), new DriveStraight(in_drive, 3, 0.4));

  }
}
