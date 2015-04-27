package eu.matejkormuth.game.client.al.effects;

import static org.lwjgl.openal.EFX10.*;

public class EchoEffect extends Effect {

    public EchoEffect() {
        super(AL_EFFECT_ECHO);
    }

    /**
     * Sets the amount of high frequency damping to apply to the echo effect.
     * 
     * @param passedValue
     *            The new damping value to apply to the echo effect
     */
    public void setDamping(float passedValue) {
        if (passedValue < AL_ECHO_MIN_DAMPING)
            setPropertyf(AL_ECHO_DAMPING, AL_ECHO_MIN_DAMPING);
        else if (passedValue > AL_ECHO_MAX_DAMPING)
            setPropertyf(AL_ECHO_DAMPING, AL_ECHO_MAX_DAMPING);
        else
            setPropertyf(AL_ECHO_DAMPING, passedValue);
    }

    /**
     * Sets the echo effect's delay time.
     * 
     * @param passedValue
     *            The echo effect's new delay time
     */
    public void setDelay(float passedValue) {
        if (passedValue < AL_ECHO_MIN_DELAY)
            setPropertyf(AL_ECHO_DELAY, AL_ECHO_MIN_DELAY);
        else if (passedValue > AL_ECHO_MAX_DELAY)
            setPropertyf(AL_ECHO_DELAY, AL_ECHO_MAX_DELAY);
        else
            setPropertyf(AL_ECHO_DELAY, passedValue);
    }

    /**
     * Sets the amount of feedback to echo back.
     * 
     * @param passedValue
     *            The new feedback volume of the echo effect
     */
    public void setFeedback(float passedValue) {
        if (passedValue < AL_ECHO_MIN_FEEDBACK)
            setPropertyf(AL_ECHO_FEEDBACK, AL_ECHO_MIN_FEEDBACK);
        else if (passedValue > AL_ECHO_MAX_FEEDBACK)
            setPropertyf(AL_ECHO_FEEDBACK, AL_ECHO_MAX_FEEDBACK);
        else
            setPropertyf(AL_ECHO_FEEDBACK, passedValue);
    }

    /**
     * Sets the delay between each echo "tap".
     * 
     * @param passedValue
     *            The new delay time between each echo "tap"
     */
    public void setLRDelay(float passedValue) {
        if (passedValue < AL_ECHO_MIN_LRDELAY)
            setPropertyf(AL_ECHO_LRDELAY, AL_ECHO_MIN_LRDELAY);
        else if (passedValue > AL_ECHO_MAX_LRDELAY)
            setPropertyf(AL_ECHO_LRDELAY, AL_ECHO_MAX_LRDELAY);
        else
            setPropertyf(AL_ECHO_LRDELAY, passedValue);
    }

    /**
     * Sets the amount of hard panning allowed for each echo.
     * 
     * @param passedValue
     *            The new level of flexibility for the echo effect's panning
     */
    public void setSpread(float passedValue) {
        if (passedValue < AL_ECHO_MIN_SPREAD)
            setPropertyf(AL_ECHO_SPREAD, AL_ECHO_MIN_SPREAD);
        else if (passedValue > AL_ECHO_MAX_SPREAD)
            setPropertyf(AL_ECHO_SPREAD, AL_ECHO_MAX_SPREAD);
        else
            setPropertyf(AL_ECHO_SPREAD, passedValue);
    }

    /**
     * Returns the high frequency damping of the echo effect.
     * 
     * @return The float value of the high-frequency damping
     */
    public float getDamping() {
        return getPropertyf(AL_ECHO_DAMPING);
    }

    /**
     * Returns the delay time of the echo effect.
     * 
     * @return The float value of the delay time
     */
    public float getDelay() {
        return getPropertyf(AL_ECHO_DELAY);
    }

    /**
     * Returns the feedback of the echo effect.
     * 
     * @return The float value of the echo feedback
     */
    public float getFeedback() {
        return getPropertyf(AL_ECHO_FEEDBACK);
    }

    /**
     * Returns the delay between each echo "tap".
     * 
     * @return The float value of the echo "tap" delay
     */
    public float getLRDelay() {
        return getPropertyf(AL_ECHO_LRDELAY);
    }

    /**
     * Returns the amount of hard panning allowed for each echo.
     * 
     * @return The float value of the panning flexibility
     */
    public float getSpread() {
        return getPropertyf(AL_ECHO_SPREAD);
    }
}
