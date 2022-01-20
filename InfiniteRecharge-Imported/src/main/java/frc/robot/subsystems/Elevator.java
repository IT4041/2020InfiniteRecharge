/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class Elevator extends SubsystemBase {

  private static final TalonSRX elevatorTalon = new TalonSRX(RobotMap.ElevatorTalon); 

  /**
   * Creates a new Elevator.
   */
  public Elevator() {
    elevatorTalon.configFactoryDefault();
    elevatorTalon.set(ControlMode.Position,0);
    elevatorTalon.setNeutralMode(NeutralMode.Brake);

    //this is a very important line of code
    elevatorTalon.setSensorPhase(true);
    elevatorTalon.setInverted(true);

    elevatorTalon.configClosedloopRamp(0.25, 30);
    elevatorTalon.configForwardSoftLimitEnable(true);
    elevatorTalon.configReverseSoftLimitEnable(true);

    elevatorTalon.configForwardSoftLimitThreshold(Constants.ElevatorPositions.Top);
    elevatorTalon.configReverseSoftLimitThreshold(Constants.ElevatorPositions.Home);
    elevatorTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);
    
    elevatorTalon.configNominalOutputForward(0,30);
    elevatorTalon.configNominalOutputReverse(0,30);
    elevatorTalon.configPeakOutputForward(1.0, 30);
    elevatorTalon.configPeakOutputReverse(-0.7, 30);

    elevatorTalon.configPeakCurrentLimit(68, 30);
    elevatorTalon.configPeakCurrentDuration(120, 30);
    elevatorTalon.configContinuousCurrentLimit(15, 30);
    elevatorTalon.enableCurrentLimit(true);

    elevatorTalon.configAllowableClosedloopError(0, 0, 30);

		elevatorTalon.config_kF(0, 0.0, 30);
		elevatorTalon.config_kP(0, 0.0, 30);
    elevatorTalon.config_kI(0, 0.0, 30);
    elevatorTalon.config_kD(0, 0.0, 30);

    //pre-flight checklist to make sure elevator is all the way @ bottom
    elevatorTalon.setSelectedSensorPosition(Constants.ElevatorPositions.Home, 0, 30);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void up(){
    setPosition(Constants.ElevatorPositions.Top);
  }

  public void down(){
    setPosition(Constants.ElevatorPositions.Home);
  }

  private void setPosition(int position){
    elevatorTalon.set(ControlMode.Position, position, DemandType.ArbitraryFeedForward,0.2);
  }

  public void stop(){
    elevatorTalon.set(ControlMode.PercentOutput, 0.0);
  }

}
