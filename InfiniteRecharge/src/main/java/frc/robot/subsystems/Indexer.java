/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.subsystems.components.RangeSensors;

public class Indexer extends SubsystemBase {

  private final TalonSRX talon = new TalonSRX(RobotMap.IndexerTalon);
  private RangeSensors m_RangeSensors;
  private int ballCount = 0;
  private int maxCount = 2;
  private boolean m_automate = false;
  private boolean bumped = false;
  private boolean intaking = false;
  /**
   * Creates a new indexer.
   */
  public Indexer(RangeSensors in_RangeSensors) {
    m_RangeSensors = in_RangeSensors;
    talon.configFactoryDefault();
    talon.configContinuousCurrentLimit(18);
    talon.configPeakCurrentLimit(30);
    talon.configPeakCurrentDuration(200);
    talon.enableCurrentLimit(true);
  }

  @Override
  public void periodic() {
  // This method will be called once per scheduler run

    if(doAutoIndexing()){// if returns true let the indexer do it's thing
      if(this.safeToIndex()){// returns true if we don't have a full lift
      // index and count balls  
      this.index(false);
      }
      else{
        //lift is full
        this.bumpBack();
      }
    }
    else{
      //count balls only
      this.index(true);
    }
  }

  public void on(){
    //lift balls
    talon.set(ControlMode.PercentOutput, 0.85);
  }

  public void off(){
    //stop lift motor
    talon.set(ControlMode.PercentOutput, 0.0);
  }

  public void enabledAutoIndexing(boolean in_automate){
    //if true indexer will auto index and count balls
    //prepping for shooting 
    m_automate = in_automate;
  }

  public void reset(){
    //reset indexer, funcitons is used after shooting
    ballCount = 0;
    bumped = false;
    intaking = false;
  }

  public void setBallCount(int in_ballCount){
    //allow ball count to be modified. used by shoot preloaded
    //autonomous command
    ballCount = in_ballCount;
  }

  public int getBallCount(){
    //return how many balls have been processed
    return ballCount;
  }

  // internal processing functions *************************************************

  private void index(boolean countOnly){
    //this functions moves and counts the balls as they pass threw the robot

    if(!m_RangeSensors.externalTriggered() && !m_RangeSensors.internalTriggered()){
      //nothing is triggering either sensor
      if(!countOnly){
        this.off(); //stage 0
      }
      intaking = false;
    }
    else if(m_RangeSensors.externalTriggered() && !m_RangeSensors.internalTriggered()){
      //the external sensor is trigger only, so were intaking a ball
      if(!countOnly){
        this.on(); // stage 1
      }
      intaking = true;
    }
    else if(!m_RangeSensors.externalTriggered() && m_RangeSensors.internalTriggered()){
      //only the internal sensor is triggered, we must have moved a ball into the lift
      //or one ball is just rattling around. That is why we check to see if intaking is true
      //before incrementing the add counter
      if(!countOnly){
        this.off(); //stage 2 & 4
      }
      if(intaking){
        this.addBall();
      }
      intaking = false;
    }
    else{
      //both sensor are triggered, we have a ball and we're getting another
      if(!countOnly){
        this.on(); // stage 3
      }
      intaking = true;
    }
  }

  private boolean safeToIndex(){
    //boolean function that return true 
    //if more balls can be lifted
    return ballCount < maxCount;
  }
  private boolean doAutoIndexing(){
    // true if indexer should automatically be shuffling  
    // and counting balls as they are picked up
    return m_automate;
  }

  private void addBall(){
    //increment internal ball count
    ballCount++;
  }

  private void bumpBack(){
    //this is a function to allow for moving the 
    //balls slightly down the indexer to prevent 
    //jamming of shooter head
    if(!bumped){
      talon.set(ControlMode.PercentOutput, -0.4);
      Timer.delay(0.1);
      talon.set(ControlMode.PercentOutput, 0.0);
      bumped = true;
    }
  }

}
