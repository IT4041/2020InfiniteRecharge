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
import frc.robot.subsystems.Indexer;

public class TurretStartTargetingAuto extends CommandBase {

  private final Turret m_Turret;
  private final Shooter m_shooter;
  private final Indexer m_Indexer;
  private boolean done = false;
  private int ballCount = 1;
  private boolean frontOfBall = false;
  private boolean backOfBall = false;
  private int delay = 0;

  public TurretStartTargetingAuto(Turret in_turret, Shooter in_shooter,Indexer in_Indexer) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_Turret = in_turret;
    m_shooter = in_shooter;
    m_Indexer = in_Indexer;
    addRequirements(m_Turret);
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    m_Indexer.setInAutonomous(true);
  }

  // Called every time the schedMuler runs while the command is scheduled.
  @Override
  public void execute() {
    m_Turret.startTargeting();
    m_shooter.startShooting();

    if(m_Indexer.m_sensors.iSeeABall()){
      frontOfBall = true;
    }
    else{
      if(frontOfBall){
        backOfBall = true;
      }
    }

    if(backOfBall && frontOfBall){
      ballCount++;
      frontOfBall = false;
      backOfBall = false;
    }

    if(ballCount > 2){
      if(delay > 150){
        done = true;
      }
      delay++;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_Indexer.setInAutonomous(false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return done;
  }
}
