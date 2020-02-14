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

public class IntakeWheels extends SubsystemBase {

  private static final TalonSRX talon = new TalonSRX(RobotMap.IntakeWheelsTalon); 
  /**
   * Creates a new IntakeWheels.
   */
  public IntakeWheels() {
    talon.configFactoryDefault();
    talon.configContinuousCurrentLimit(18);
    talon.configPeakCurrentLimit(40);
    talon.configPeakCurrentDuration(1000);
    talon.enableCurrentLimit(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void on(){
    talon.set(ControlMode.PercentOutput, -0.65);
  }

  public void off(){
    talon.set(ControlMode.PercentOutput, 0.0); 
  }
}
