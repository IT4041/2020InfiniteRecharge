/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.components;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import com.kauailabs.navx.frc.AHRS;

public class NavX extends SubsystemBase {
  /**
   * Creates a new NavX.
   */

   private AHRS navX;

  public NavX() {
    
    try {
        navX = new AHRS(SerialPort.Port.kMXP);
      } catch (RuntimeException ex ) {
        DriverStation.reportError("Error instantiating navX MXP: " + ex.getMessage(), true);
      }

      navX.reset();
      

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("NavX YawAngle", navX.getYaw());
  }
}
