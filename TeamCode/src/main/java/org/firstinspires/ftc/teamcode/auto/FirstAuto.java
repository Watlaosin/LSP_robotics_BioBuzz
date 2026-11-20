
package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.pedro.Constants;

import static com.pedropathing.api.Paths.line;

@Autonomous(name = "First Auto")
public class FirstAuto extends OpMode {

    private Follower follower;

    private final PoseFactory p = PoseFactory.degrees();

    // Starting position
    private final Pose startPose = p.of(0, 0, 0);

    // Move forward 24 inches
    private final Pose endPose = p.of(24, 0, 0);

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
    }

    @Override
    public void start() {
        follower.follow(
                line(startPose, endPose)
                        .linear(startPose, endPose)
        );
    }

    @Override
    public void loop() {
        follower.update();

        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.update();
    }
}
