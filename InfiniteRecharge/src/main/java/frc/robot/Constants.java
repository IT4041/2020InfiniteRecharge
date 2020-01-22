/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.constants;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;

/**
 * Add your docs here.
 */
public class Constants {
    public static final class DriveConstants {
        public static final double kTrackwidthFoot = 3.9245892079521147;
        public static final DifferentialDriveKinematics = 
            new DifferentialDriveKinematics(kTrackwidthFoot);

    //found in the characterization tool suite
    public static final double ksVolts = 1.5;
    public static final double kvVoltSecondsPerFoot = 0.323;
    public static final double ksVoltsSecondsSquarePerFoot = 0.0291;

    public static final double kPDriveVel = 1.3;
    }

    public static final class AutoConstants {
        public static final double kMaxSpeedFeetPerSecond = 16;
        public static final double kMaxAccelerationFeetPerSecondSquared = 7;

        // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;
    }

    
}
