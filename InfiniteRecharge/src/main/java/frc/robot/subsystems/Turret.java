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
import edu.wpi.first.networktables.*;

public class Turret extends PIDSubsystem {

  private static final TalonSRX talon = new TalonSRX(6); 
  private static final Encoder encoder = new Encoder(0, 1, true, Encoder.EncodingType.k4X);

  private double current = 0;
  private double increment = 100;
  private boolean aquireTarget = false;

  public Turret() {
    super(
        // The PIDController used by the subsystem
        new PIDController(0.085, 0, 0.0001)
    );

        this.enable();
        talon.configFactoryDefault();
        encoder.reset();
        
  }

  @Override
  public void periodic() {

    super.periodic(); 
    SmartDashboard.putNumber("Turret setpoint", current);
    SmartDashboard.putBoolean("Turret PID", this.isEnabled());
    SmartDashboard.putBoolean("Running periodic", true);
    if(aquireTarget){
      SmartDashboard.putBoolean("Aquire Target", true);
      current = current + Update_Limelight_Tracking();
      SmartDashboard.putNumber("Turret setpoint", current);
      this.setSetpoint(current);
      Timer.delay(0.001);

      current = this.getMeasurement();
    }
    else{
      SmartDashboard.putBoolean("Aquire Target", false);
    }

  }

  @Override
  public void useOutput(double output, double setpoint) {
    // Use the output here
    talon.set(ControlMode.PercentOutput, output);
    SmartDashboard.putNumber("Turret PID Ouput", output);
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here
    int  value = encoder.get();
    SmartDashboard.putNumber("Turret PID process variable", value);
    return value;
  }

  public void changeSetPoint(){
    current = current + increment;
    SmartDashboard.putNumber("Turret setpoint", current);
    this.setSetpoint(current);

  }

  public void changeSetPointBack(){
    current = current - increment;
    SmartDashboard.putNumber("Turret setpoint", current);
    this.setSetpoint(current);

  }

  public void startTargeting(){
    this.aquireTarget = true;
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);

  }

  public void endTargeting(){
    this.aquireTarget = false;
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    current = 0;
    this.setSetpoint(current);
  }

  public double Update_Limelight_Tracking()
  {

        final double gr = 208/24; // gear ration 208/24
        final double ppmr = 44.4; //pulses per motor revolution
        double pptr = gr * ppmr; // pulses per turret revolution
        double dptp = 360/pptr; // degrees per turret pulse
        double steer_cmd = 0.0;

        double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);

        if (tv < 1.0){
          steer_cmd = 0.0;
        }else{
          // Start with proportional turning
          steer_cmd = tx * dptp;
        }
        SmartDashboard.putNumber("Turret Change", steer_cmd);
        return steer_cmd;
  }
}
