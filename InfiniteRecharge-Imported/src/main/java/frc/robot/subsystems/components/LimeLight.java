/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.components;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimeLight extends SubsystemBase {
  /**
   * Creates a new limeLight. class handles everything relate to the limelight and vision
   */

  // LimeLight table values **********************************
  // tv	Whether the limelight has any valid targets (0 or 1)
  // tx	Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
  // ty	Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
  // ta	Target Area (0% of image to 100% of image)
  private final NetworkTable limelightNT = NetworkTableInstance.getDefault().getTable("limelight");

  private double tv,tx,ta,ty;

  public LimeLight() {
    //make sure led is off when lime light is initialized
    limelightNT.getEntry("ledMode").setNumber(1);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    tv = limelightNT.getEntry("tv").getDouble(0);
    tx = limelightNT.getEntry("tx").getDouble(0);
    ta = limelightNT.getEntry("ta").getDouble(0);
    //ty = limelightNT.getEntry("ty").getDouble(0);

    SmartDashboard.putBoolean("HasValidTarget", hasValidTarget());
  }

  public void ledOn(){
    limelightNT.getEntry("ledMode").setNumber(3);
  }

  public void ledOff(){
    limelightNT.getEntry("ledMode").setNumber(1);
  }

  public boolean hasValidTarget(){
    return tv >= 1.0;
  }

  public double getXOffset(){
    return tx;
  }

  public double getDistanceToTarget(){

    double o = 2.875; // measured ta value at 120 inches 
    double k = 203.4; // k = 120*Math.sqrt(ta)

    double delta = o - ta;
    delta = Math.cbrt(delta);
    delta = delta/12;
    delta = ta - delta;
    delta = Math.sqrt(delta);
    delta = k/delta;

    SmartDashboard.putNumber("distance", Math.round(delta));
    return  Math.round(delta);
  }
  
}
