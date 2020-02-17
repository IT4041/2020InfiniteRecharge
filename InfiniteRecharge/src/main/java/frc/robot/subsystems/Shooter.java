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
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {
  
  private final CANSparkMax sparkMax1 = new CANSparkMax(RobotMap.ShooterSparkMax1, MotorType.kBrushless);
  private final CANSparkMax sparkMax2 = new CANSparkMax(RobotMap.ShooterSparkMax2, MotorType.kBrushless);
  private final CANPIDController pidController = sparkMax1.getPIDController();
  private final CANEncoder encoder = sparkMax1.getEncoder();
  private double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, minRPM;
  private int accumulator = 0;
  private double velocity = 0.0;

  /**
   * Creates a new Shooter. This subsystem controls the shooter head
   */
  public Shooter() {

    sparkMax1.restoreFactoryDefaults();
    sparkMax2.restoreFactoryDefaults();

    sparkMax2.follow(sparkMax1, true);

    // PID coefficients
    kP = 0.001; 
    kI = 0;
    kD = 0.00002; 
    kIz = 0; 
    kFF = 0.00002; //kFF = 0.0001754385964912281; possible value for voltage pid
    kMaxOutput = 1; 
    kMinOutput = -1;
    maxRPM = 6550;
    minRPM = 4150;

    // set PID coefficients
    pidController.setP(kP);
    pidController.setI(kI);
    pidController.setD(kD);
    pidController.setIZone(kIz);
    pidController.setFF(kFF);
    pidController.setOutputRange(kMinOutput, kMaxOutput);

  }

  @Override
  public void periodic() {
  }

  // todo: change pid to voltage
  //"I’ve generally found that good tuning of a velocity PID starts with feed-forward
  //- as mentioned, set it to (max motor command)/(max output speed), and play with it 
  //to see how close you can get to your setpoint without any feedback. After that, 
  //bring in the rest of the PID to help reject disturbances and 
  //get you even closer to the setpoint." gerthworm
  private double calculateRPMs(double distance){

    double finalRPMS;
    double origin = 4150;

    //calculate rpms 
    finalRPMS =  origin + (((distance - 120) / 12)*70);

    //use min rpms if calculated value is below min threshold
    finalRPMS = (finalRPMS < minRPM) ? minRPM : finalRPMS;

    return finalRPMS;
  }

  public boolean readyToShoot(){
    boolean atSpeed = false;
    double measuredVelo = encoder.getVelocity();
    double compensatedVelo = velocity * 0.87;

    if(measuredVelo < (compensatedVelo + 25) && measuredVelo > (compensatedVelo - 25) && measuredVelo > 3000){
      atSpeed = true;
      accumulator++;
    }
    return atSpeed && accumulator > 50;
  }

  private void disablePID(){
    velocity = 0.0;
    pidController.setReference(velocity, ControlType.kVelocity);
    pidController.setP(0.0);
    pidController.setI(0.0);
    pidController.setD(0.0);
    pidController.setIZone(0.0);
    pidController.setFF(0.0);
  }

  private void enablePID(){
    pidController.setP(kP);
    pidController.setI(kI);
    pidController.setD(kD);
    pidController.setIZone(kIz);
    pidController.setFF(kFF);
  }

  public void on(double distance){
    this.enablePID();
    velocity = this.calculateRPMs(distance);
    pidController.setReference(velocity, ControlType.kVelocity);
  }
  
  public void off(){
    this.disablePID();
    accumulator = 0;
  }
}
