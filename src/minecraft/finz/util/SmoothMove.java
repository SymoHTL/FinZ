package finz.util;

import finz.FinZ;

import java.util.Timer;
import java.util.TimerTask;

public class SmoothMove {

    public static void moveSmooth(float desiredPitch, float desiredYaw) {
        moveSmooth(desiredPitch, desiredYaw, 25);
    }

    public static void moveSmooth(float desiredPitch, float desiredYaw, int steps) {
        float currentPitch = FinZ.INSTANCE.mc.thePlayer.rotationPitch;
        float currentYaw = FinZ.INSTANCE.mc.thePlayer.rotationYaw;
        Timer timer = new Timer();
        // make the transition linear and smooth

        float pitchStep;
        float yawStep;

        if (currentPitch > desiredPitch)
            pitchStep = (currentPitch - desiredPitch);
        else
            pitchStep = (desiredPitch - currentPitch);
        pitchStep /= steps;
        if (currentYaw > desiredYaw)
            yawStep = (currentYaw - desiredYaw);
        else
            yawStep = (desiredYaw - currentYaw);
        yawStep /= steps;

        for (int i = 0; i < steps; i++) {
            float finalPitchStep = pitchStep;
            float finalYawStep = yawStep;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    FinZ.INSTANCE.mc.thePlayer.rotationPitch += finalPitchStep;
                    FinZ.INSTANCE.mc.thePlayer.rotationYaw += finalYawStep;
                }
            }, 1);
        }

    }




}
