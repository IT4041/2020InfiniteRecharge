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

public class RangeSensors extends SubsystemBase {

   static double ballThreshold = 350;//tbd

  // Create instance of Time-Of_Flight driver for device 1 & 2
  private final TimeOfFlight rangeSensorInternal = new TimeOfFlight(RobotMap.TimeOfFlightInternal);
  private final TimeOfFlight rangeSensorExternal = new TimeOfFlight(RobotMap.TimeOfFlightExternal);
  /**
   * Creates a new RangeSensors.
   */
  public RangeSensors() {
    // Configure time of flight sensor for short ranging mode and sample
    // distance every 5 ms
    rangeSensorInternal.setRangingMode(RangingMode.Short, 5);
    rangeSensorExternal.setRangingMode(RangingMode.Short, 5);

  }

  @Override
  public void periodic() {
  }

  public boolean externalTriggered(){
    return rangeSensorExternal.getRange() < ballThreshold;
  }

  public boolean internalTriggered(){
    return rangeSensorInternal.getRange() < ballThreshold;
  }
}
