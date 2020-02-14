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

public class Elevator extends SubsystemBase {

  // private static final TalonSRX talon1 = new TalonSRX(RobotMap.ElevatorTalon1); 
  // private static final TalonSRX talon2 = new TalonSRX(RobotMap.ElevatorTalon2); 
  /**
   * Creates a new Elevator.
   */
  public Elevator() {
    // talon1.configFactoryDefault();
    // talon2.configFactoryDefault();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
