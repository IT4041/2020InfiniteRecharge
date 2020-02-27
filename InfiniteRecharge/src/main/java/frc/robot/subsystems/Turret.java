/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotMap;

public class Turret extends PIDSubsystem {

  private final TalonSRX talon = new TalonSRX(RobotMap.TurretTalon); 
  private final Encoder encoder = new Encoder(RobotMap.TurretChannelA, RobotMap.TurretChannelB, true, Encoder.EncodingType.k4X);

  // value that store the current position of turret
  private double current = 0;
  private boolean aquireTarget = false;

  private final double gr = 208/36; // gear ration 208/36
  private final double ppmr = 44.4; //pulses per motor revolution
  private final double pptr = gr * ppmr; // pulses per turret revolution
  private final double dptp = 360/pptr; // degrees per turret pulse
  private double m_xOffset = 0.0; // x offset reported by limelight
  private final double maxOffset = 90;// Maximum x offset allow

  public Turret() {
    super(
        // The PIDController used by the subsystem
        new PIDController(0.0275, 0, 0.0003, 0.01)
    );

    // turn on pid controller
    this.enable();
    talon.configFactoryDefault();

    encoder.reset();

    // set the tolerance of pid controller to allow turret to be slightly off target
    // 2 degrees in either direction
    super.m_controller.setTolerance(2);

    SmartDashboard.putBoolean("onTarget", false);
    SmartDashboard.putBoolean("Aquire Target", false);
  }

  @Override
  public void periodic() {

    SmartDashboard.putBoolean("onTarget", this.onTarget());
    SmartDashboard.putBoolean("Aquire Target", this.aquireTarget);

    //Make sure the super's pid is running
    super.periodic(); 

    if(aquireTarget){//Bombardier notified turrent to target
      // TrackTarget returns the offset to the target in degrees
      // if limelight has a valid target, if no valid target is found
      // TrackTarget returns no offset(0.0)
      // 1.)add the offset to current position
      current = current + trackTarget();

      //Only apply changes that are less than 90 degrees off starting position
      //if target positions is greater than 90 return 90 with the proper sign(+/-)
      //current = Math.abs(current) <= maxOffset ? current : (Math.signum(current) * maxOffset);
      // 2.) update pid setpoint to new position
      this.setSetpoint(current);
      // 3.) delay thread so adjustment can happen
      Timer.delay(0.0005);
      
      // 4.) update current positoin to position after adjustment and delay
      current = this.getMeasurement();
    }
  }

  // PID functions *******************************************************************
  @Override
  public void useOutput(double output, double setpoint) {
    // Use the output here
    talon.set(ControlMode.PercentOutput, output);
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here
    int  value = encoder.get();
    return value;
  }
  //end PID ************************************************************************

  // OI function *******************************************************************
  public void targetingEnabled(double in_XOffset){
    //Turret will auto-aim towards target
    this.aquireTarget = true;
    //get x offset from limelight
    m_xOffset = in_XOffset;
  }

  public void targetingDisabled(){
    //Turret will stop auto-aiming towards target
    // and return to center back position
    this.aquireTarget = false;
    
    //reset x offset
    m_xOffset = 0.0;

    //recenter turret on back of robot
    current = 0;
    this.setSetpoint(current);
  }
  // end OI functions *******************************************************************


  // vision functions *******************************************************************
  private double trackTarget()
  {
    // TrackTarget returns the offset to the target in turret pulses (+/-)  
    return m_xOffset * dptp;
  }

  public boolean onTarget(){
    // is the pid report that on the setpoint within the tolerance
    return super.m_controller.atSetpoint();
  }

  // end vision functions *******************************************************************

}
