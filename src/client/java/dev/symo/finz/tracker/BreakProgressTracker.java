package dev.symo.finz.tracker;

public interface BreakProgressTracker {
    float getBreakProgress(float tickDelta);

    default int getBreakProgressPercent(float tickDelta) {
        return (int) (this.getBreakProgress(tickDelta) * 100);
    }
}