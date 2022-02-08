// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

// import information on CANSparkMAx motor controller
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// import information from other files
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;

public class Climb extends SubsystemBase {
  /** Creates a new Climb subsystem */

  // add a compressor
  private Compressor airow = new Compressor(0, PneumaticsModuleType.CTREPCM);

  // add a piston for moving arm
  private Solenoid piston = new Solenoid(compressorModule, armMoverPort);
  
  // add a motor for lift
  private CANSparkMax liftMotor = new CANSparkMax(liftPort, MotorType.kBrushless);

  public Climb () {
  }

  // add a method that moves lift up
  public void move(double speed) {
    liftMotor.set(speed);
  }

  // check if arm is fully reached backwards (if piston value = true)
  public boolean isReaching() {
    return piston.get();
  }

  // method makes piston extend (makes the arm reach)
  public void reaching(boolean pistonReach) {
    piston.set(pistonReach);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
