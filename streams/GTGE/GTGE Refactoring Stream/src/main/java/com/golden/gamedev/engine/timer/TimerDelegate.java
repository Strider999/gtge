/**
 * 
 */
package com.golden.gamedev.engine.timer;

import java.io.Serializable;

import com.golden.gamedev.ActiveHolder;
import com.golden.gamedev.Resettable;

/**
 * The {@link TimerDelegate} class encapulates the {@link Serializable} state of
 * all {@link Timer} instances, so that it is possible to create both
 * {@link Serializable} and non-{@link Serializable} timers. Note that the
 * {@link TimerDelegate} class does not implement {@link Timer}, as its
 * {@link #action(long)} method is incorrect for a proper {@link Timer} instance
 * (it does not execute a proper action before returning true).
 * 
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see Timer
 * @see Serializable
 * 
 */
final class TimerDelegate implements ActiveHolder, Resettable, Serializable {
	
	/**
	 * Serializable ID generated by Eclipse.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Whether or not this {@link TimerDelegate} instance {@link #isActive() is
	 * active}. Defaults to true.
	 */
	private boolean active = true;
	
	/**
	 * The {@link #getDelay() delay} of this {@link Timer} instance, in
	 * milliseconds.
	 */
	private long delay;
	
	/**
	 * The {@link #getCurrentTick() current tick} of this {@link TimerDelegate}
	 * instance.
	 */
	private long currentTick;
	
	/**
	 * Creates a new {@link TimerDelegate} instance with the specified
	 * {@link #getDelay() delay} in milliseconds.
	 * @param delay The delay of this {@link TimerDelegate} instance, in
	 *        milliseconds.
	 */
	public TimerDelegate(final long delay) {
		super();
		this.delay = delay;
	}
	
	/**
	 * Provides the action method for this {@link TimerDelegate} instance, but
	 * doesn't properly implement {@link Timer#action(long)} as it executes no
	 * behavior when true is returned. Proper {@link Timer} implementations read
	 * the value of this result, execute an action if appropriate, and then pass
	 * this result back to the caller.
	 * @param elapsedTime The long value representing the last time this method
	 *        was <i>virtually invoked</i> (in milliseconds)
	 * @return True if a proper {@link Timer} implementation should take action
	 *         and return true, false otherwise.
	 * @see Timer#action(long)
	 */
	public boolean action(long elapsedTime) {
		if (this.active) {
			this.currentTick += elapsedTime;
			if (this.currentTick >= this.delay) {
				// The time is up. Synchronize the current tick to make the next
				// tick accurate
				this.currentTick -= this.delay;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the value of the current tick.
	 * @param currentTick The value of the current tick.
	 * @see Timer#getCurrentTick()
	 */
	public long getCurrentTick() {
		return currentTick;
	}
	
	/**
	 * Sets the {@link #getCurrentTick() current tick} to the specified value.
	 * @param currentTick The {@link #getCurrentTick() current tick} to set.
	 */
	public void setCurrentTick(final long currentTick) {
		this.currentTick = currentTick;
	}
	
	/**
	 * Gets the delay of this {@link TimerDelegate} instance, in milliseconds.
	 * @return The delay of this {@link TimerDelegate} instance, in
	 *         milliseconds.
	 * @see Timer#getDelay()
	 */
	public long getDelay() {
		return this.delay;
	}
	
	public boolean isActive() {
		return this.active;
	}
	
	/**
	 * Resets the {@link #getCurrentTick() current tick} to 0.
	 * @see Timer#reset()
	 */
	public void reset() {
		this.currentTick = 0;
	}
	
	public void setActive(boolean active) {
		this.active = active;
		this.reset();
	}
	
	/**
	 * Sets the delay of this {@link TimerDelegate} instance, in milliseconds.
	 * @param delay The delay of this {@link TimerDelegate} instance, in
	 *        milliseconds.
	 * @see Timer#getDelay()
	 */
	public void setDelay(long delay) {
		this.delay = delay;
		this.reset();
	}
}
