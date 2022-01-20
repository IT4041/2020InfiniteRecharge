/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import frc.robot.RobotMap;

public class Turret extends SubsystemBase {

  private final TalonSRX talon = new TalonSRX(RobotMap.TurretTalon); 
  private Boolean autoHead = false;
  private int targetPosition = 4500;
  private Boolean start = true;

  // value that store the current position of turret
  //private double current = 0;
  //private boolean aquireTarget = false;

  // private final double gr = 208/36; // gear ration 208/36
  // private final double ppmr = 4096; //pulses per motor revolution (ctre mag encoder ppr 4096)
  // private final double pptr = gr * ppmr; // pulses per turret revolution
  // private final double dptp = pptr/360; // degrees per turret pulse
  //private double m_xOffset = 0.0; // x offset reported by limelight
  private final int maxOffset = -6000;// Maximum x offset allow
  // private final int tolerance = 65;

  public Turret() {
    //pulse per revolution = 4096
    talon.configFactoryDefault();
    talon.set(ControlMode.Position, 0);
    talon.setNeutralMode(NeutralMode.Brake);

    //this is a very important line of code
    talon.setSensorPhase(false);
    talon.setInverted(false);
    talon.configVoltageCompSaturation(12);
    talon.enableVoltageCompensation(true);

    talon.configForwardSoftLimitEnable(true);
    talon.configReverseSoftLimitEnable(true);

    talon.configForwardSoftLimitThreshold(-maxOffset);
    talon.configReverseSoftLimitThreshold(maxOffset);

    talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 30);

    talon.configNominalOutputForward(0,30);
    talon.configNominalOutputReverse(0,30);
    talon.configPeakOutputForward(.25, 30);
    talon.configPeakOutputReverse(-.25, 30);

    talon.configAllowableClosedloopError(0,40,30);

		talon.config_kF(0, 0.0, 30);
		talon.config_kP(0, 1.5, 30);
		talon.config_kI(0, 0.0, 30);
    talon.config_kD(0, 0.0, 30);

    talon.configPeakCurrentLimit(40 , 30);
    talon.configPeakCurrentDuration(120, 30);
    talon.configContinuousCurrentLimit(15, 30);
    talon.enableCurrentLimit(true);

    //pre-flight checklist to make sure turrret is face directly backwards
    talon.setSelectedSensorPosition(0,0,30);
    talon.set(ControlMode.Position, 0);
  
  }

  @Override
  public void periodic() {

    if(autoHead){

      if(start){
        autoHead_X();
        start = false;
      }
      
      int currPostion = talon.getSelectedSensorPosition();
      //currPostion/targetPosition > .9
      if(Math.abs(currPostion)/Math.abs(targetPosition) >= .9){
        //turn the other way
        if(targetPosition == -4500){
          autoHead_X();
        }
        else{
          autoHead_B();
        }
      }
    }

  }

  private void autoHead_X(){
    talon.set(ControlMode.Position, 4500);
    targetPosition = 4500;

  }
  private void autoHead_B(){
    talon.set(ControlMode.Position, -4500);
    targetPosition = -4500;
  }

  public void teleHead_X(){
    talon.set(ControlMode.PercentOutput, .25);
    autoHead = false;

  }
  public void teleHead_B(){
    talon.set(ControlMode.PercentOutput, -.25);
    autoHead = false;
  }

  public void teleHead_stop(){
    talon.set(ControlMode.PercentOutput, 0.0);
    autoHead = false;
  }

  public void autoToggle(){
    autoHead = !autoHead;
  }

}
