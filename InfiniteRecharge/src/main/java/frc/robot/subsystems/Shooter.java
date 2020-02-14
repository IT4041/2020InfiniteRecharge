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
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Indexer;

public class Shooter extends SubsystemBase {
  
  private CANSparkMax sparkMax1;
  private CANSparkMax sparkMax2;
  private CANPIDController pidController;
  private CANEncoder encoder;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, minRPM;
  private Turret m_Turret;
  private Indexer m_Indexer;

  //values for rpm calculation
  private double multiplier = 26.5; //rpm multiplier

  private double compensator = 1.15;
  private double velocity = 0.0;
  private double rpm_tolerance = 45.0;

  /**
   * Creates a new Shooter.
   */
  public Shooter(Turret in_Turret, Indexer in_Indexer) {
    m_Turret = in_Turret;
    m_Indexer = in_Indexer;

    sparkMax1 = new CANSparkMax(RobotMap.ShooterSparkMax1, MotorType.kBrushless);//31
    sparkMax2 = new CANSparkMax(RobotMap.ShooterSparkMax2, MotorType.kBrushless);

    sparkMax1.restoreFactoryDefaults();
    sparkMax2.restoreFactoryDefaults();

    sparkMax2.follow(sparkMax1, true);

    pidController = sparkMax1.getPIDController();
    encoder = sparkMax1.getEncoder();

    // PID coefficients
    kP = 0.001; 
    kI = 0;
    kD = 0.00002; 
    kIz = 0; 
    kFF = 0.00002; 
    kMaxOutput = 1; 
    kMinOutput = -1;
    maxRPM = 6550;
    minRPM = 3300;

    // set PID coefficients
    pidController.setP(kP);
    pidController.setI(kI);
    pidController.setD(kD);
    pidController.setIZone(kIz);
    pidController.setFF(kFF);
    pidController.setOutputRange(kMinOutput, kMaxOutput);

    // SmartDashboard.putNumber("shooter multiplier", multiplier);
    // SmartDashboard.putNumber("shooter compensator", compensator);
    SmartDashboard.putBoolean("shooter at rpm", false);
    SmartDashboard.putNumber("shooter Desired velocity", 0);
    SmartDashboard.putNumber("shooter Actual velocity", encoder.getVelocity());
    SmartDashboard.putNumber("shooter distance", 0);
    SmartDashboard.putNumber("shooter compensated rpms",  0);
    SmartDashboard.putNumber("shooter expected rpms",  0);

  }

  @Override
  public void periodic() {

    // velocity is 115% of actual  
    if(m_Turret.OnTarget()){
      velocity = calculateRPMs();
      pidController.setReference(velocity, ControlType.kVelocity);
      SmartDashboard.putNumber("shooter Desired velocity", velocity );
      SmartDashboard.putNumber("shooter Actual velocity", encoder.getVelocity());
      
      if(atRPM()){
        m_Indexer.shooting();
      }
    }

    SmartDashboard.putBoolean("shooter at rpm", atRPM());
  }

  private double calculateRPMs(){

    double finalRPMS;
    double distance = m_Turret.distanceToTarget();
    double calcRPMs = distance * multiplier * compensator;

    finalRPMS = (calcRPMs < maxRPM ? calcRPMs : maxRPM);
    finalRPMS = (calcRPMs < minRPM ? minRPM : calcRPMs);

    SmartDashboard.putNumber("shooter compensated rpms", finalRPMS);
    SmartDashboard.putNumber("shooter expected rpms",  finalRPMS * 0.85);
    SmartDashboard.putNumber("shooter distance", distance);

    return finalRPMS;
  }

  private boolean atRPM(){

    double convertBack = 1.1764705885;

    boolean atSpeed = false;
    double measuredVelo = encoder.getVelocity();
    double compMeasured = measuredVelo * compensator;
    compMeasured = (measuredVelo * compensator < minRPM ? minRPM * 0.85 : (measuredVelo * compensator));

    if( compMeasured < (velocity + rpm_tolerance) && compMeasured > (velocity - rpm_tolerance)){
      atSpeed = true;
    }
    return atSpeed;
  }

  public void endShooting(){
    velocity = 0.0;
    pidController.setReference(velocity, ControlType.kVelocity);
    pidController.setP(0.0);
    pidController.setI(0.0);
    pidController.setD(0.0);
    pidController.setIZone(0.0);
    pidController.setFF(0.0);
    m_Indexer.endShooting();
  }

  public void startShooting(){
    pidController.setP(kP);
    pidController.setI(kI);
    pidController.setD(kD);
    pidController.setIZone(kIz);
    pidController.setFF(kFF);
  }

}
