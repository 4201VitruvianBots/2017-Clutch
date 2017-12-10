package org.usfirst.frc.team4201.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoRightTurn extends CommandGroup {

    public AutoRightTurn() {
        addSequential(new DriveStraightWithGyro(3, .5));
        addSequential(new DriveTurnWithGyro(0, 90)); // wait time, speed, angle
        addSequential(new DriveStraightWithGyro(1, .5));
    }
}
