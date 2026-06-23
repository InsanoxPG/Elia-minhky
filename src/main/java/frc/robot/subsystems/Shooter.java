// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.BooleanSupplier;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.ControlRequest;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import dev.doglog.DogLog;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    TalonFX shooterMotor;
    VoltageOut voltageController;

    public Shooter() {
        this.voltageController = new VoltageOut(0); // rotations per second
        this.shooterMotor = new TalonFX(62); // id might be wrong

        this.shooterMotor.getConfigurator().apply(new TalonFXConfiguration());
    }

    public void shoot(double v) {
        // shooterMotor.setControl(voltageController.withOutput(v));
        shooterMotor.setVoltage(v);
        // shooterMotor.set(1);
    }

    public Command basicShoot(BooleanSupplier a_pressed) {
        DogLog.log("a button pressed?", a_pressed.getAsBoolean());
        return Commands.run(() -> {
            shoot(1);
        }, this);
    }

    public Command stop() {
        return Commands.runOnce(() -> {shooterMotor.setVoltage(0);}, this);
    }

    @Override
    public void periodic() {
        DogLog.log("APplied volts", this.shooterMotor.getMotorVoltage().getValueAsDouble());
        // DogLog.log("APplied volts", this.shooterMotor.);
    }
}
