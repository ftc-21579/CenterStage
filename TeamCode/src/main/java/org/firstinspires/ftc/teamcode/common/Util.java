package org.firstinspires.ftc.teamcode.common;

public class Util {

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Woah fancy math thing
     * velocity = Vi + at
     * position = Di + Vit + 0.5at
     *
     * should subtract the systems current position from the return value and feed into some type of controller
     * @param max_acceleration max accel of the system
     * @param max_velocity max velo of the system
     * @param distance distance to travel
     * @param elapsed_time time elapsed since start of profile
     * @return target position at the given time
     */
    public static double motion_profile(double max_acceleration, double max_velocity, double distance, double elapsed_time) {
        // time to accelerate to max velocity
        double acceleration_dt = max_velocity / max_acceleration;

        // if cant accel to max velo by distance, accel as much as possible
        double half_distance = distance / 2;
        double acceleration_distance = 0.5 * max_acceleration * acceleration_dt * acceleration_dt;

        if (acceleration_distance > half_distance) {
            acceleration_dt = Math.sqrt(half_distance / (0.5 * max_acceleration));
        }

        acceleration_distance = 0.5 * max_acceleration * acceleration_dt * acceleration_dt;

        // recalculate max velo based on time to accel and deccel
        max_velocity = max_acceleration * acceleration_dt;

        // trapezoidal, so deccel time is same as accel time
        double deceleration_dt = acceleration_dt;

        // calculate time at max velo
        double cruise_distance = distance - 2 * acceleration_distance;
        double cruise_dt = cruise_distance / max_velocity;
        double deceleration_time = acceleration_dt + cruise_dt;

        // are we actually in the motion profile?
        double entire_dt = 2 * acceleration_dt + cruise_dt + deceleration_dt;
        if (elapsed_time > entire_dt) {
            return distance;
        }

        // if accel
        if (elapsed_time < acceleration_dt) {
            return 0.5 * max_acceleration * elapsed_time * elapsed_time;
        }
        // if cruising/max velo
        else if (elapsed_time < deceleration_time) {
            acceleration_distance = 0.5 * max_acceleration * acceleration_dt * acceleration_dt;
            double cruise_current_dt = elapsed_time - acceleration_dt;

            return acceleration_distance + max_velocity * cruise_current_dt;
        }
        // gots to be decelerating
        else {
            acceleration_distance = 0.5 * max_acceleration * acceleration_dt * acceleration_dt;
            cruise_distance = max_velocity * cruise_dt;
            deceleration_time = elapsed_time - deceleration_dt;

            return acceleration_distance + cruise_distance + max_velocity * deceleration_time - 0.5 * max_acceleration * deceleration_time * deceleration_time;
        }
    }
}