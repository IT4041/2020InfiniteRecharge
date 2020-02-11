/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {
  
  private CANSparkMax sparkMax1;
  private CANSparkMax sparkMax2;
  private CANPIDController pidController;
  private CANEncoder encoder;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    sparkMax1 = new CANSparkMax(RobotMap.ShooterSparkMax1, MotorType.kBrushless);//31
    sparkMax2 = new CANSparkMax(RobotMap.ShooterSparkMax2, MotorType.kBrushless);

    sparkMax1.restoreFactoryDefaults();
    sparkMax2.restoreFactoryDefaults();

    sparkMax2.follow(sparkMax1, true);

    pidController = sparkMax1.getPIDController();
    encoder = sparkMax1.getEncoder();

    // PID coefficients
    kP = 0.0065; 
    kI = 0;
    kD = 0; 
    kIz = 0; 
    kFF = 0; 
    kMaxOutput = 1; 
    kMinOutput = -1;

    // set PID coefficients
    pidController.setP(kP);
    pidController.setI(kI);
    pidController.setD(kD);
    pidController.setIZone(kIz);
    pidController.setFF(kFF);
    pidController.setOutputRange(kMinOutput, kMaxOutput);

    // display PID coefficients on SmartDashboard
    SmartDashboard.putNumber("shooter P Gain", kP);
    SmartDashboard.putNumber("shooter I Gain", kI);
    SmartDashboard.putNumber("shooter D Gain", kD);
    SmartDashboard.putNumber("shooter I Zone", kIz);
    SmartDashboard.putNumber("shooter Feed Forward", kFF);
    SmartDashboard.putNumber("shooter Max Output", kMaxOutput);
    SmartDashboard.putNumber("shooter Min Output", kMinOutput);
    SmartDashboard.putNumber("shooter Set Rotations", 0);

  }

  @Override
  public void periodic() {
      // This method will be called once per scheduler run
     // read PID coefficients from SmartDashboard
     double p = SmartDashboard.getNumber("shooter P Gain", 0);
     double i = SmartDashboard.getNumber("shooter I Gain", 0);
     double d = SmartDashboard.getNumber("shooter D Gain", 0);
     double iz = SmartDashboard.getNumber("shooter I Zone", 0);
     double ff = SmartDashboard.getNumber("shooter Feed Forward", 0);
     double max = SmartDashboard.getNumber("shooter Max Output", 0);
     double min = SmartDashboard.getNumber("shooter Min Output", 0);
     double rotations = SmartDashboard.getNumber("shooter Set Rotations", 0);
 
     // if PID coefficients on SmartDashboard have changed, write new values to controller
     if((p != kP)) { pidController.setP(p); kP = p; }
     if((i != kI)) { pidController.setI(i); kI = i; }
     if((d != kD)) { pidController.setD(d); kD = d; }
     if((iz != kIz)) { pidController.setIZone(iz); kIz = iz; }
     if((ff != kFF)) { pidController.setFF(ff); kFF = ff; }
     if((max != kMaxOutput) || (min != kMinOutput)) { 
      pidController.setOutputRange(min, max); 
       kMinOutput = min; kMaxOutput = max; 
     }
 
     /**
      * PIDController objects are commanded to a set point using the 
      * SetReference() method.
      * 
      * The first parameter is the value of the set point, whose units vary
      * depending on the control type set in the second parameter.
      * 
      * The second parameter is the control type can be set to one of four 
      * parameters:
      *  com.revrobotics.ControlType.kDutyCycle
      *  com.revrobotics.ControlType.kPosition
      *  com.revrobotics.ControlType.kVelocity
      *  com.revrobotics.ControlType.kVoltage
      */
    pidController.setReference(rotations, ControlType.kPosition);
     
     SmartDashboard.putNumber("shooter SetPoint", rotations);
     SmartDashboard.putNumber("shooter ProcessVariable", encoder.getPosition());
  }
}
