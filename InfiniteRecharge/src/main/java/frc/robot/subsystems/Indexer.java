/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.subsystems.components.RangeSensors;

public class Indexer extends SubsystemBase {


  private int ballCount = 0;

  private static final TalonSRX talon = new TalonSRX(RobotMap.IndexerTalon);
  /**
   * Creates a new indexer.
   */
  public Indexer() {
    talon.configFactoryDefault();
    talon.set(ControlMode.PercentOutput, -0.4);// negative is up
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if(RangeSensors.iSeeABall()){
      addBall();
    }
  }

  public void addBall(){
    ballCount ++;
  }

public void emptyTheTank(){
  //function get called when we shoot
  ballCount = 0;
}

public void intake(){
  talon.set(ControlMode.PercentOutput, 0.4);

}

public void backOff(){
  talon.set(ControlMode.PercentOutput, -0.4);
}
  
}
