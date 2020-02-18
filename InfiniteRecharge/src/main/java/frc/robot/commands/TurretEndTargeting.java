/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Shooter;

public class TurretEndTargeting extends CommandBase {

  private final Turret m_Turret;
  private final Shooter m_shooter;

  public TurretEndTargeting(Turret in_turret, Shooter in_shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_Turret = in_turret;
    m_shooter = in_shooter;
    addRequirements(m_Turret);
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_Turret.endTargeting();
    m_shooter.endShooting();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
