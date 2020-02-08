/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Indexer extends SubsystemBase {

  // Create instance of Time-Of_Flight driver for device 1
  private final TimeOfFlight m_rangeSensor1 = new TimeOfFlight(1);
  private final TimeOfFlight m_rangeSensor2 = new TimeOfFlight(2);
  /**
   * Creates a new indexer.
   */
  public Indexer() {

      
    // Configure time of flight sensor for short ranging mode and sample
    // distance every 40 ms
    m_rangeSensor1.setRangingMode(RangingMode.Short, 40);
    m_rangeSensor2.setRangingMode(RangingMode.Short, 40);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("1. Distance(mm)", (int)m_rangeSensor1.getRange());
    SmartDashboard.putNumber("1. Std Dev(mm)", (int)m_rangeSensor1.getRangeSigma());
    SmartDashboard.putString("1. Status", m_rangeSensor1.getStatus().toString());

    SmartDashboard.putNumber("2. Distance(mm)", (int)m_rangeSensor2.getRange());
    SmartDashboard.putNumber("2. Std Dev(mm)", (int)m_rangeSensor2.getRangeSigma());
    SmartDashboard.putString("2. Status", m_rangeSensor2.getStatus().toString());
    
  }
}
