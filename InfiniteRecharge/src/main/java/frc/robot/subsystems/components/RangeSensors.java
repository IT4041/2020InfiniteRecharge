/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.components;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RangeSensors extends SubsystemBase {

   static double ballThreshold = 60;//tbd


  // Create instance of Time-Of_Flight driver for device 1 & 2
  //private final TimeOfFlight rangeSensor1 = new TimeOfFlight(RobotMap.TimeOfFlight1);
  private static final TimeOfFlight rangeSensor2 = new TimeOfFlight(RobotMap.TimeOfFlight2);
  /**
   * Creates a new RangeSensors.
   */
  public RangeSensors() {
    // Configure time of flight sensor for short ranging mode and sample
    // distance every 5 ms
    //rangeSensor1.setRangingMode(RangingMode.Short, 5);
    rangeSensor2.setRangingMode(RangingMode.Short, 5);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // SmartDashboard.putNumber("1. Distance(mm)", (int)rangeSensor1.getRange());
    // SmartDashboard.putNumber("1. Std Dev(mm)", (int)rangeSensor1.getRangeSigma());
    // SmartDashboard.putString("1. Status", rangeSensor1.getStatus().toString());

    SmartDashboard.putNumber("2. Distance(mm)", (int)rangeSensor2.getRange());
    SmartDashboard.putNumber("2. Std Dev(mm)", (int)rangeSensor2.getRangeSigma());
    SmartDashboard.putString("2. Status", rangeSensor2.getStatus().toString());
  }


  public static boolean iSeeABall(){

    return rangeSensor2.getRange() < ballThreshold;
  }
}
