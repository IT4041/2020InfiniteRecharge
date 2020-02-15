/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.subsystems.components.RangeSensors;

public class Indexer extends SubsystemBase {

  private int ballCount = 0;
  public RangeSensors m_sensors;
  private boolean frontOfBall = false;
  private boolean backOfBall = false;
  private boolean shooting = false;
  private boolean backedOff = false;
  private boolean m_inAutonomous = false;

  private static final TalonSRX talon = new TalonSRX(RobotMap.IndexerTalon);
  /**
   * Creates a new indexer.
   */
  public Indexer(RangeSensors rangeSensors) {
    talon.configFactoryDefault();
    talon.configFactoryDefault();
    talon.configContinuousCurrentLimit(18);
    talon.configPeakCurrentLimit(40);
    talon.configPeakCurrentDuration(1000);
    talon.enableCurrentLimit(true);
    m_sensors = rangeSensors;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    if(!m_inAutonomous){
      if(!shooting){
        if(ballCount < 2){

          if(m_sensors.iSeeABall()){
            talon.set(ControlMode.PercentOutput, -0.85);
            frontOfBall = true;
          }
          else{
            talon.set(ControlMode.PercentOutput, 0.0);
            if(frontOfBall){
              backOfBall = true;
            }
          }

          if(backOfBall && frontOfBall){
            addBall();
            frontOfBall = false;
            backOfBall = false;
          }
        }else if (ballCount == 2 && !backedOff){
          backOff();
        }
      }
    }

    SmartDashboard.putBoolean("m_inAutonomous", m_inAutonomous);
    SmartDashboard.putBoolean("fb", frontOfBall);
    SmartDashboard.putBoolean("bb", backOfBall);
    SmartDashboard.putNumber("ballCount", ballCount);

  }

  public void setInAutonomous(boolean in_Autonomous){
    m_inAutonomous = in_Autonomous;
  }

  private void addBall(){
    ballCount ++;
  }

  private void emptyTheTank(){
    //function get called when we shoot
    ballCount = 0;
  }
  
  private void backOff(){
    talon.set(ControlMode.PercentOutput, 0.4);
    Timer.delay(0.1);
    talon.set(ControlMode.PercentOutput, 0.0);
    backedOff = true;
  }

  private void On(){
    talon.set(ControlMode.PercentOutput, -0.85);
  }

  public void shooting(){
    emptyTheTank();
    On();
    shooting = true;
    backedOff = false;
  }

  public void endShooting(){
    shooting = false;
    talon.set(ControlMode.PercentOutput, 0.0);
  }
  
}
