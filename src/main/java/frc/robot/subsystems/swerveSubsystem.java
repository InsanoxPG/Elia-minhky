package frc.robot.subsystems;

import java.io.File;
import java.util.function.DoubleSupplier;

import dev.doglog.DogLog;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import swervelib.parser.SwerveParser;
import swervelib.SwerveDrive;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;

public class swerveSubsystem extends SubsystemBase {
    double maxSpeed;
    File swerveJSON;
    SwerveDrive swerveDrive;

    public swerveSubsystem() {
        double maxSpeed = 1; // meters (per second probably)
        File swerveJSON = new File(Filesystem.getDeployDirectory(), "swerve");
        try {

            swerveDrive = new SwerveParser(swerveJSON).createSwerveDrive(maxSpeed);
            swerveDrive.resetOdometry(new Pose2d(5, 5, Rotation2d.kZero));

        } catch (Exception e) { // catch an IOException if swerveJSON does not exist
            e.printStackTrace();
        }

    }

    public Command drive(DoubleSupplier tX, DoubleSupplier tY, DoubleSupplier angle) {

        return Commands.run(() -> {
            DogLog.log("translational X value", tX.getAsDouble());
            DogLog.log("translational y value", tY.getAsDouble());
            DogLog.log("angle value", angle.getAsDouble());
            double tXV = MathUtil.applyDeadband(tX.getAsDouble() * swerveDrive.getMaximumChassisVelocity(), 0.15);
            double tYV = MathUtil.applyDeadband(-tY.getAsDouble() * swerveDrive.getMaximumChassisVelocity(), 0.15);
            double angleV = MathUtil.applyDeadband(angle.getAsDouble(), 0.2) * swerveDrive.getMaximumChassisAngularVelocity();
            swerveDrive.drive(new Translation2d(tXV,
                    tYV),
                    angleV, // radians per second
                    true, // field-relative?
                    false); // closed loop velocity control?
                    
        }, this);
    }
}
