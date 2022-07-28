package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class OI 
{
    public static final boolean debug = true;


    public static Joystick driverController;



    public static void init() 
    {
        driverController = new Joystick(0);
        LEFT_X_ZERO = 0;
        LEFT_Y_ZERO = 0;
        RIGHT_X_ZERO = 0;
        RIGHT_Y_ZERO = 0;
         zeroDriverController();
    }

    public static void onEnable() {
        zeroDriverController();
    }

    public static void update() {}



    public static void zeroDriverController() {
        //Sets all the offsets to zero, then uses whatever value it returns as the new offset.
        LEFT_X_ZERO = 0;
        LEFT_Y_ZERO = 0;
        RIGHT_X_ZERO = 0;
        RIGHT_Y_ZERO = 0;
        LEFT_X_ZERO = getDriverLeftX();
        LEFT_Y_ZERO = getDriverLeftY();
        RIGHT_X_ZERO = getDriverRightX();
        RIGHT_Y_ZERO = getDriverRightY();
    }

    private static final double LEFT_X_MIN = -1;
    private static final double LEFT_X_MAX = 1;
    private static double LEFT_X_ZERO = 0;
    public static double getDriverLeftX() {
        return MathUtil.clamp(2.0 * (driverController.getRawAxis(0) - (LEFT_X_MAX + LEFT_X_MIN) * 0.5) / (LEFT_X_MAX - LEFT_X_MIN) - LEFT_X_ZERO, -1, 1);
    }

    private static final double LEFT_Y_MIN = -1;
    private static final double LEFT_Y_MAX = 1;
    private static double LEFT_Y_ZERO = 0;
    public static double getDriverLeftY() {
        return MathUtil.clamp(2.0 * (driverController.getRawAxis(1) - (LEFT_Y_MAX + LEFT_Y_MIN) * 0.5) / (LEFT_Y_MAX - LEFT_Y_MIN) - LEFT_Y_ZERO, -1, 1);
    }

    private static final double RIGHT_X_MIN=-1;
    private static final double RIGHT_X_MAX = 1;
    private static double RIGHT_X_ZERO = 0;
    public static double getDriverRightX() {
        return MathUtil.clamp(2.0 * (driverController.getRawAxis(3) - (RIGHT_X_MAX + RIGHT_X_MIN) * 0.5) / (RIGHT_X_MAX - RIGHT_X_MIN) - RIGHT_X_ZERO, -1, 1);
    }

    private static final double RIGHT_Y_MIN = -1;
    private static final double RIGHT_Y_MAX = 1;
    private static double RIGHT_Y_ZERO = 0;
    public static double getDriverRightY() {
        return MathUtil.clamp(2.0 * (driverController.getRawAxis(4) - (RIGHT_Y_MAX + RIGHT_Y_MIN) * 0.5) / (RIGHT_Y_MAX - RIGHT_Y_MIN) - RIGHT_Y_ZERO, -1, 1);
    }
    
    
    
    public static void setRumble(double val){
        //operatorController.setRumble(RumbleType.kLeftRumble, val);
       // operatorController.setRumble(RumbleType.kRightRumble, val);
    }
    
   
       
}