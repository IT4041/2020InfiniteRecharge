/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.RobotMap;

public class DriveTrain extends SubsystemBase {
  /**
   * Creates a new DriveTrain.
   */

   //declare the motor crontrollers
   private final WPI_TalonFX frontRightTalon = new WPI_TalonFX(RobotMap.FrontRightTalon);
   private final WPI_TalonFX frontLeftTalon = new WPI_TalonFX(RobotMap.FrontLeftTalon);
   private final WPI_TalonFX backRightTalon = new WPI_TalonFX(RobotMap.BackRightTalon);
   private final WPI_TalonFX backLeftTalon = new WPI_TalonFX(RobotMap.BackLeftTalon);
   private final WPI_TalonFX topRightTalon = new WPI_TalonFX(RobotMap.TopRightTalon);
   private final WPI_TalonFX topLeftTalon = new WPI_TalonFX(RobotMap.TopLeftTalon);
  

   //groups the motor controllers into left and right groups
   private final SpeedControllerGroup rightSCG = new SpeedControllerGroup(frontRightTalon, backRightTalon, topRightTalon);
   private final SpeedControllerGroup leftSCG = new SpeedControllerGroup(frontLeftTalon, backLeftTalon, topLeftTalon);

   //declares the drive train (which consists of each motor controller)
   public final DifferentialDrive robotDrive = new DifferentialDrive(leftSCG, rightSCG);


   private static DriveTrain instance;

   private DriveTrain(){
    init();
  }

   public static DriveTrain getInstance(){
     if (instance == null){
       instance = new DriveTrain();
     }

     return instance;
   }

   private void init(){
     
    frontRightTalon.setNeutralMode(NeutralMode.Coast);
    frontLeftTalon.setNeutralMode(NeutralMode.Coast);
    backRightTalon.setNeutralMode(NeutralMode.Coast);
    backLeftTalon.setNeutralMode(NeutralMode.Coast);
    topRightTalon.setNeutralMode(NeutralMode.Brake);
    topLeftTalon.setNeutralMode(NeutralMode.Brake);
    //todo:current limits

    robotDrive.setExpiration(1);
    robotDrive.setSafetyEnabled(false);
    
   }

   public void arcade(Joystick driver){

    double speed = -driver.getRawAxis(RobotMap.leftStickY);
    double turn = driver.getRawAxis(RobotMap.rightStickX);
    robotDrive.arcadeDrive(speed, turn, true);

    SmartDashboard.putNumber("drivetrain speed", speed);
    SmartDashboard.putNumber("drivetrain turn", turn);
  }


}
