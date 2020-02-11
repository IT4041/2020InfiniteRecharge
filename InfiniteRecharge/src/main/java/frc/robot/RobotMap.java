

package frc.robot;

public class RobotMap {

    //This is where we store all locations of buttons and subsystems

    //DriveTrain
    public static final int FrontRightTalon = 20;
	public static final int FrontLeftTalon = 23;
	public static final int BackRightTalon = 22;
	public static final int BackLeftTalon = 21;
	public static final int TopRightTalon = 24;
	public static final int TopLeftTalon = 25;
    //end DriveTrain

	//Turret
	public static final int TurretTalon = 6;
	public static final int TurretChannelA = 0;
	public static final int TurretChannelB = 1;
	//end Turret

	//Shooter
	public static final int ShooterSparkMax1 = 31;
	public static final int ShooterSparkMax2 = 32;
	//end Shooter

	//Intake Elbow
	public static final int IntakeElbowSparkMax = 30;
	//end Intake Elbow

	//Intake Wheels
	public static final int IntakeWheelsTalon = 7;
	//end Intake Wheels

	//Ranger Sensor
	public static final int TimeOfFlight1 = 1;
	public static final int TimeOfFlight2 = 2;
	//end Ranger Sensor

	//Indexer
	public static final int IndexerTalon = 2;
	//end Indexer

	//Elevator Arm
	public static final int ElevatorArmTalon = 5;
	//end Elevator Arm

	//Elevator
	public static final int ElevatorTalon1 = 1;
	public static final int ElevatorTalon2 = 2;
	//end Elevator
	
    //start xbox *****************************************
	public static final int xboxControllerDriver = 0;
	public static final int xboxControllerAssist = 1;
	
	public static final int buttonA = 1;
	public static final int buttonB = 2;
	public static final int buttonX = 3;
	public static final int buttonY = 4;
	
	public static final int buttonBumperLeft = 5;
	public static final int buttonBumperRight = 6;
	
	public static final int buttonSelect = 7;
	public static final int buttonStart = 8;
	
	public static final int rightStickY = 5;
	public static final int leftStickY = 1;
	
	public static final int rightStickX = 4;
	public static final int leftStickX = 0;
	
	public static final int rightTrigger = 3;
	public static final int leftTrigger = 3;

	public static final int rightJoystickPush = 12;
	public static final int leftJoystickPush = 11;

  //end xbox *****************************************

}