package org.firstinspires.ftc.teamcode.trajectorysequence;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.path.PathContinuityViolationException;
import com.acmerobotics.roadrunner.profile.MotionProfile;
import com.acmerobotics.roadrunner.profile.MotionProfileGenerator;
import com.acmerobotics.roadrunner.profile.MotionState;
import com.acmerobotics.roadrunner.trajectory.DisplacementMarker;
import com.acmerobotics.roadrunner.trajectory.DisplacementProducer;
import com.acmerobotics.roadrunner.trajectory.MarkerCallback;
import com.acmerobotics.roadrunner.trajectory.SpatialMarker;
import com.acmerobotics.roadrunner.trajectory.TemporalMarker;
import com.acmerobotics.roadrunner.trajectory.TimeProducer;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.acmerobotics.roadrunner.trajectory.TrajectoryMarker;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.acmerobotics.roadrunner.util.Angle;

import org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.sequencesegment.SequenceSegment;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.sequencesegment.TrajectorySegment;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.sequencesegment.TurnSegment;
import org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.sequencesegment.WaitSegment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TrajectorySequenceBuilder {
    private final double resolution = 0.25;

    private final TrajectoryVelocityConstraint baseVelConstraint;
    private final TrajectoryAccelerationConstraint baseAccelConstraint;

    private TrajectoryVelocityConstraint currentVelConstraint;
    private TrajectoryAccelerationConstraint currentAccelConstraint;

    private final double baseTurnConstraintMaxAngVel;
    private final double baseTurnConstraintMaxAngAccel;

    private double currentTurnConstraintMaxAngVel;
    private double currentTurnConstraintMaxAngAccel;

    private final List<SequenceSegment> sequenceSegments;

    private final List<TemporalMarker> temporalMarkers;
    private final List<DisplacementMarker> displacementMarkers;
    private final List<SpatialMarker> spatialMarkers;

    private Pose2d lastPose;

    private double tangentOffset;

    private boolean setAbsoluteTangent;
    private double absoluteTangent;

    private TrajectoryBuilder currentTrajectoryBuilder;

    private double currentDuration;
    private double currentDisplacement;

    private double lastDurationTraj;
    private double lastDisplacementTraj;

    public TrajectorySequenceBuilder(
            Pose2d startPose,
            Double startTangent,
            TrajectoryVelocityConstraint baseVelConstraint,
            TrajectoryAccelerationConstraint baseAccelConstraint,
            double baseTurnConstraintMaxAngVel,
            double baseTurnConstraintMaxAngAccel
    ) {
        this.baseVelConstraint = baseVelConstraint;
        this.baseAccelConstraint = baseAccelConstraint;

        this.currentVelConstraint = baseVelConstraint;
        this.currentAccelConstraint = baseAccelConstraint;

        this.baseTurnConstraintMaxAngVel = baseTurnConstraintMaxAngVel;
        this.baseTurnConstraintMaxAngAccel = baseTurnConstraintMaxAngAccel;

        this.currentTurnConstraintMaxAngVel = baseTurnConstraintMaxAngVel;
        this.currentTurnConstraintMaxAngAccel = baseTurnConstraintMaxAngAccel;

        sequenceSegments = new ArrayList<>();

        temporalMarkers = new ArrayList<>();
        displacementMarkers = new ArrayList<>();
        spatialMarkers = new ArrayList<>();

        lastPose = startPose;

        tangentOffset = 0.0;

        setAbsoluteTangent = (startTangent != null);
        absoluteTangent = startTangent != null ? startTangent : 0.0;

        currentTrajectoryBuilder = null;

        currentDuration = 0.0;
        currentDisplacement = 0.0;

        lastDurationTraj = 0.0;
        lastDisplacementTraj = 0.0;
    }

    public TrajectorySequenceBuilder(
            Pose2d startPose,
            TrajectoryVelocityConstraint baseVelConstraint,
            TrajectoryAccelerationConstraint baseAccelConstraint,
            double baseTurnConstraintMaxAngVel,
            double baseTurnConstraintMaxAngAccel
    ) {
        this(
                startPose, null,
                baseVelConstraint, baseAccelConstraint,
                baseTurnConstraintMaxAngVel, baseTurnConstraintMaxAngAccel
        );
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder lineTo(Vector2d endPosition) {
        return addPath(() -> currentTrajectoryBuilder.lineTo(endPosition, currentVelConstraint, currentAccelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder lineTo(
            Vector2d endPosition,
            TrajectoryVelocityConstraint velConstraint,
            TrajectoryAccelerationConstraint accelConstraint
    ) {
        return addPath(() -> currentTrajectoryBuilder.lineTo(endPosition, velConstraint, accelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder lineToConstantHeading(Vector2d endPosition) {
        return addPath(() -> currentTrajectoryBuilder.lineToConstantHeading(endPosition, currentVelConstraint, currentAccelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder lineToConstantHeading(
            Vector2d endPosition,
            TrajectoryVelocityConstraint velConstraint,
            TrajectoryAccelerationConstraint accelConstraint
    ) {
        return addPath(() -> currentTrajectoryBuilder.lineToConstantHeading(endPosition, velConstraint, accelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder lineToLinearHeading(Pose2d endPose) {
        return addPath(() -> currentTrajectoryBuilder.lineToLinearHeading(endPose, currentVelConstraint, currentAccelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder lineToLinearHeading(
            Pose2d endPose,
            TrajectoryVelocityConstraint velConstraint,
            TrajectoryAccelerationConstraint accelConstraint
    ) {
        return addPath(() -> currentTrajectoryBuilder.lineToLinearHeading(endPose, velConstraint, accelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder lineToSplineHeading(Pose2d endPose) {
        return addPath(() -> currentTrajectoryBuilder.lineToSplineHeading(endPose, currentVelConstraint, currentAccelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder lineToSplineHeading(
            Pose2d endPose,
            TrajectoryVelocityConstraint velConstraint,
            TrajectoryAccelerationConstraint accelConstraint
    ) {
        return addPath(() -> currentTrajectoryBuilder.lineToSplineHeading(endPose, velConstraint, accelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder strafeTo(Vector2d endPosition) {
        return addPath(() -> currentTrajectoryBuilder.strafeTo(endPosition, currentVelConstraint, currentAccelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder strafeTo(
            Vector2d endPosition,
            TrajectoryVelocityConstraint velConstraint,
            TrajectoryAccelerationConstraint accelConstraint
    ) {
        return addPath(() -> currentTrajectoryBuilder.strafeTo(endPosition, velConstraint, accelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder forward(double distance) {
        return addPath(() -> currentTrajectoryBuilder.forward(distance, currentVelConstraint, currentAccelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder forward(
            double distance,
            TrajectoryVelocityConstraint velConstraint,
            TrajectoryAccelerationConstraint accelConstraint
    ) {
        return addPath(() -> currentTrajectoryBuilder.forward(distance, velConstraint, accelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder back(double distance) {
        return addPath(() -> currentTrajectoryBuilder.back(distance, currentVelConstraint, currentAccelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder back(
            double distance,
            TrajectoryVelocityConstraint velConstraint,
            TrajectoryAccelerationConstraint accelConstraint
    ) {
        return addPath(() -> currentTrajectoryBuilder.back(distance, velConstraint, accelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder strafeLeft(double distance) {
        return addPath(() -> currentTrajectoryBuilder.strafeLeft(distance, currentVelConstraint, currentAccelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder strafeLeft(
            double distance,
            TrajectoryVelocityConstraint velConstraint,
            TrajectoryAccelerationConstraint accelConstraint
    ) {
        return addPath(() -> currentTrajectoryBuilder.strafeLeft(distance, velConstraint, accelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder strafeRight(double distance) {
        return addPath(() -> currentTrajectoryBuilder.strafeRight(distance, currentVelConstraint, currentAccelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder strafeRight(
            double distance,
            TrajectoryVelocityConstraint velConstraint,
            TrajectoryAccelerationConstraint accelConstraint
    ) {
        return addPath(() -> currentTrajectoryBuilder.strafeRight(distance, velConstraint, accelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder splineTo(Vector2d endPosition, double endHeading) {
        return addPath(() -> currentTrajectoryBuilder.splineTo(endPosition, endHeading, currentVelConstraint, currentAccelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder splineTo(
            Vector2d endPosition,
            double endHeading,
            TrajectoryVelocityConstraint velConstraint,
            TrajectoryAccelerationConstraint accelConstraint
    ) {
        return addPath(() -> currentTrajectoryBuilder.splineTo(endPosition, endHeading, velConstraint, accelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder splineToConstantHeading(Vector2d endPosition, double endHeading) {
        return addPath(() -> currentTrajectoryBuilder.splineToConstantHeading(endPosition, endHeading, currentVelConstraint, currentAccelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder splineToConstantHeading(
            Vector2d endPosition,
            double endHeading,
            TrajectoryVelocityConstraint velConstraint,
            TrajectoryAccelerationConstraint accelConstraint
    ) {
        return addPath(() -> currentTrajectoryBuilder.splineToConstantHeading(endPosition, endHeading, velConstraint, accelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder splineToLinearHeading(Pose2d endPose, double endHeading) {
        return addPath(() -> currentTrajectoryBuilder.splineToLinearHeading(endPose, endHeading, currentVelConstraint, currentAccelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder splineToLinearHeading(
            Pose2d endPose,
            double endHeading,
            TrajectoryVelocityConstraint velConstraint,
            TrajectoryAccelerationConstraint accelConstraint
    ) {
        return addPath(() -> currentTrajectoryBuilder.splineToLinearHeading(endPose, endHeading, velConstraint, accelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder splineToSplineHeading(Pose2d endPose, double endHeading) {
        return addPath(() -> currentTrajectoryBuilder.splineToSplineHeading(endPose, endHeading, currentVelConstraint, currentAccelConstraint));
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder splineToSplineHeading(
            Pose2d endPose,
            double endHeading,
            TrajectoryVelocityConstraint velConstraint,
            TrajectoryAccelerationConstraint accelConstraint
    ) {
        return addPath(() -> currentTrajectoryBuilder.splineToSplineHeading(endPose, endHeading, velConstraint, accelConstraint));
    }

    private org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder addPath(AddPathCallback callback) {
        if (currentTrajectoryBuilder == null) newPath();

        try {
            callback.run();
        } catch (PathContinuityViolationException e) {
            newPath();
            callback.run();
        }

        Trajectory builtTraj = currentTrajectoryBuilder.build();

        double durationDifference = builtTraj.duration() - lastDurationTraj;
        double displacementDifference = builtTraj.getPath().length() - lastDisplacementTraj;

        lastPose = builtTraj.end();
        currentDuration += durationDifference;
        currentDisplacement += displacementDifference;

        lastDurationTraj = builtTraj.duration();
        lastDisplacementTraj = builtTraj.getPath().length();

        return this;
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder setTangent(double tangent) {
        setAbsoluteTangent = true;
        absoluteTangent = tangent;

        pushPath();

        return this;
    }

    private org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder setTangentOffset(double offset) {
        setAbsoluteTangent = false;

        this.tangentOffset = offset;
        this.pushPath();

        return this;
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder setReversed(boolean reversed) {
        return reversed ? this.setTangentOffset(Math.toRadians(180.0)) : this.setTangentOffset(0.0);
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder setConstraints(
            TrajectoryVelocityConstraint velConstraint,
            TrajectoryAccelerationConstraint accelConstraint
    ) {
        this.currentVelConstraint = velConstraint;
        this.currentAccelConstraint = accelConstraint;

        return this;
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder resetConstraints() {
        this.currentVelConstraint = this.baseVelConstraint;
        this.currentAccelConstraint = this.baseAccelConstraint;

        return this;
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder setVelConstraint(TrajectoryVelocityConstraint velConstraint) {
        this.currentVelConstraint = velConstraint;

        return this;
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder resetVelConstraint() {
        this.currentVelConstraint = this.baseVelConstraint;

        return this;
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder setAccelConstraint(TrajectoryAccelerationConstraint accelConstraint) {
        this.currentAccelConstraint = accelConstraint;

        return this;
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder resetAccelConstraint() {
        this.currentAccelConstraint = this.baseAccelConstraint;

        return this;
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder setTurnConstraint(double maxAngVel, double maxAngAccel) {
        this.currentTurnConstraintMaxAngVel = maxAngVel;
        this.currentTurnConstraintMaxAngAccel = maxAngAccel;

        return this;
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder resetTurnConstraint() {
        this.currentTurnConstraintMaxAngVel = baseTurnConstraintMaxAngVel;
        this.currentTurnConstraintMaxAngAccel = baseTurnConstraintMaxAngAccel;

        return this;
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder addTemporalMarker(MarkerCallback callback) {
        return this.addTemporalMarker(currentDuration, callback);
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder UNSTABLE_addTemporalMarkerOffset(double offset, MarkerCallback callback) {
        return this.addTemporalMarker(currentDuration + offset, callback);
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder addTemporalMarker(double time, MarkerCallback callback) {
        return this.addTemporalMarker(0.0, time, callback);
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder addTemporalMarker(double scale, double offset, MarkerCallback callback) {
        return this.addTemporalMarker(time -> scale * time + offset, callback);
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder addTemporalMarker(TimeProducer time, MarkerCallback callback) {
        this.temporalMarkers.add(new TemporalMarker(time, callback));
        return this;
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder addSpatialMarker(Vector2d point, MarkerCallback callback) {
        this.spatialMarkers.add(new SpatialMarker(point, callback));
        return this;
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder addDisplacementMarker(MarkerCallback callback) {
        return this.addDisplacementMarker(currentDisplacement, callback);
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder UNSTABLE_addDisplacementMarkerOffset(double offset, MarkerCallback callback) {
        return this.addDisplacementMarker(currentDisplacement + offset, callback);
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder addDisplacementMarker(double displacement, MarkerCallback callback) {
        return this.addDisplacementMarker(0.0, displacement, callback);
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder addDisplacementMarker(double scale, double offset, MarkerCallback callback) {
        return addDisplacementMarker((displacement -> scale * displacement + offset), callback);
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder addDisplacementMarker(DisplacementProducer displacement, MarkerCallback callback) {
        displacementMarkers.add(new DisplacementMarker(displacement, callback));

        return this;
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder turn(double angle) {
        return turn(angle, currentTurnConstraintMaxAngVel, currentTurnConstraintMaxAngAccel);
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder turn(double angle, double maxAngVel, double maxAngAccel) {
        pushPath();

        MotionProfile turnProfile = MotionProfileGenerator.generateSimpleMotionProfile(
                new MotionState(lastPose.getHeading(), 0.0, 0.0, 0.0),
                new MotionState(lastPose.getHeading() + angle, 0.0, 0.0, 0.0),
                maxAngVel,
                maxAngAccel
        );

        sequenceSegments.add(new TurnSegment(lastPose, angle, turnProfile, Collections.emptyList()));

        lastPose = new Pose2d(
                lastPose.getX(), lastPose.getY(),
                Angle.norm(lastPose.getHeading() + angle)
        );

        currentDuration += turnProfile.duration();

        return this;
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder waitSeconds(double seconds) {
        pushPath();
        sequenceSegments.add(new WaitSegment(lastPose, seconds, Collections.emptyList()));

        currentDuration += seconds;
        return this;
    }

    public org.firstinspires.ftc.teamcode.common.drive.roadrunner.trajectorysequence.TrajectorySequenceBuilder addTrajectory(Trajectory trajectory) {
        pushPath();

        sequenceSegments.add(new TrajectorySegment(trajectory));
        return this;
    }

    private void pushPath() {
        if (currentTrajectoryBuilder != null) {
            Trajectory builtTraj = currentTrajectoryBuilder.build();
            sequenceSegments.add(new TrajectorySegment(builtTraj));
        }

        currentTrajectoryBuilder = null;
    }

    private void newPath() {
        if (currentTrajectoryBuilder != null)
            pushPath();

        lastDurationTraj = 0.0;
        lastDisplacementTraj = 0.0;

        double tangent = setAbsoluteTangent ? absoluteTangent : Angle.norm(lastPose.getHeading() + tangentOffset);

        currentTrajectoryBuilder = new TrajectoryBuilder(lastPose, tangent, currentVelConstraint, currentAccelConstraint, resolution);
    }

    public TrajectorySequence build() {
        pushPath();

        List<TrajectoryMarker> globalMarkers = convertMarkersToGlobal(
                sequenceSegments,
                temporalMarkers, displacementMarkers, spatialMarkers
        );
        projectGlobalMarkersToLocalSegments(globalMarkers, sequenceSegments);

        return new TrajectorySequence(sequenceSegments);
    }

    private List<TrajectoryMarker> convertMarkersToGlobal(
            List<SequenceSegment> sequenceSegments,
            List<TemporalMarker> temporalMarkers,
            List<DisplacementMarker> displacementMarkers,
            List<SpatialMarker> spatialMarkers
    ) {
        ArrayList<TrajectoryMarker> trajectoryMarkers = new ArrayList<>();

        // Convert temporal markers
        for (TemporalMarker marker : temporalMarkers) {
            trajectoryMarkers.add(
                    new TrajectoryMarker(marker.getProducer().produce(currentDuration), marker.getCallback())
            );
        }

        // Convert displacement markers
        for (DisplacementMarker marker : displacementMarkers) {
            double time = displacementToTime(
                    sequenceSegments,
                    marker.getProducer().produce(currentDisplacement)
            );

            trajectoryMarkers.add(
                    new TrajectoryMarker(
                            time,
                            marker.getCallback()
                    )
            );
        }

        // Convert spatial markers
        for (SpatialMarker marker : spatialMarkers) {
            trajectoryMarkers.add(
                    new TrajectoryMarker(
                            pointToTime(sequenceSegments, marker.getPoint()),
                            marker.getCallback()
                    )
            );
        }

        return trajectoryMarkers;
    }

    private void projectGlobalMarkersToLocalSegments(List<TrajectoryMarker> markers, List<SequenceSegment> sequenceSegments) {
        if (sequenceSegments.isEmpty()) return;

        markers.sort(Comparator.comparingDouble(TrajectoryMarker::getTime));

        double timeOffset = 0.0;
        int markerIndex = 0;
        for (SequenceSegment segment : sequenceSegments) {
            while (markerIndex < markers.size()) {
                TrajectoryMarker marker = markers.get(markerIndex);
                if (marker.getTime() >= timeOffset + segment.getDuration()) {
                    break;
                }

                segment.getMarkers().add(new TrajectoryMarker(
                        Math.max(0.0, marker.getTime()) - timeOffset, marker.getCallback()));
                ++markerIndex;
            }

            timeOffset += segment.getDuration();
        }

        SequenceSegment segment = sequenceSegments.get(sequenceSegments.size() - 1);
        while (markerIndex < markers.size()) {
            TrajectoryMarker marker = markers.get(markerIndex);
            segment.getMarkers().add(new TrajectoryMarker(segment.getDuration(), marker.getCallback()));
            ++markerIndex;
        }
    }

    // Taken from Road Runner's TrajectoryGenerator.displacementToTime() since it's private
    // note: this assumes that the profile position is monotonic increasing
    private Double motionProfileDisplacementToTime(MotionProfile profile, double s) {
        double tLo = 0.0;
        double tHi = profile.duration();
        while (!(Math.abs(tLo - tHi) < 1e-6)) {
            double tMid = 0.5 * (tLo + tHi);
            if (profile.get(tMid).getX() > s) {
                tHi = tMid;
            } else {
                tLo = tMid;
            }
        }
        return 0.5 * (tLo + tHi);
    }

    private Double displacementToTime(List<SequenceSegment> sequenceSegments, double s) {
        double currentTime = 0.0;
        double currentDisplacement = 0.0;

        for (SequenceSegment segment : sequenceSegments) {
            if (segment instanceof TrajectorySegment) {
                TrajectorySegment thisSegment = (TrajectorySegment) segment;

                double segmentLength = thisSegment.getTrajectory().getPath().length();

                if (currentDisplacement + segmentLength > s) {
                    double target = s - currentDisplacement;
                    double timeInSegment = motionProfileDisplacementToTime(
                            thisSegment.getTrajectory().getProfile(),
                            target
                    );

                    return currentTime + timeInSegment;
                } else {
                    currentDisplacement += segmentLength;
                }
            }

            currentTime += segment.getDuration();
        }

        return currentTime;
    }

    private Double pointToTime(List<SequenceSegment> sequenceSegments, Vector2d point) {
        class ComparingPoints {
            private final double distanceToPoint;
            private final double totalDisplacement;
            private final double thisPathDisplacement;

            public ComparingPoints(double distanceToPoint, double totalDisplacement, double thisPathDisplacement) {
                this.distanceToPoint = distanceToPoint;
                this.totalDisplacement = totalDisplacement;
                this.thisPathDisplacement = thisPathDisplacement;
            }
        }

        List<ComparingPoints> projectedPoints = new ArrayList<>();

        for (SequenceSegment segment : sequenceSegments) {
            if (segment instanceof TrajectorySegment) {
                TrajectorySegment thisSegment = (TrajectorySegment) segment;

                double displacement = thisSegment.getTrajectory().getPath().project(point, 0.25);
                Vector2d projectedPoint = thisSegment.getTrajectory().getPath().get(displacement).vec();
                double distanceToPoint = point.minus(projectedPoint).norm();

                double totalDisplacement = 0.0;

                for (ComparingPoints comparingPoint : projectedPoints) {
                    totalDisplacement += comparingPoint.totalDisplacement;
                }

                totalDisplacement += displacement;

                projectedPoints.add(new ComparingPoints(distanceToPoint, displacement, totalDisplacement));
            }
        }

        ComparingPoints closestPoint = null;

        for (ComparingPoints comparingPoint : projectedPoints) {
            if (closestPoint == null) {
                closestPoint = comparingPoint;
                continue;
            }

            if (comparingPoint.distanceToPoint < closestPoint.distanceToPoint)
                closestPoint = comparingPoint;
        }

        return displacementToTime(sequenceSegments, closestPoint.thisPathDisplacement);
    }

    private interface AddPathCallback {
        void run();
    }
}
