package org.firstinspires.ftc.teamcode.subsystems.objectdetection;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ObjectVisionProcessor implements VisionProcessor {

    // measure and set
    private static final double pollenDiameter_mm = 200;
    private static final double flowerDiameter_mm = 300;

    // camera horizontal focal length
    private static final double fx = 600;


    // detection thresholds
    private static final double minArea = 250;
    private static final double minCircularity = 0.55;
    private static final double minAspRatio = 0.65;
    private static final double maxAspRatio = 1.50;

    private final Scalar lowerYellow = new Scalar(18, 80, 70);
    private final Scalar upperYellow = new Scalar(38, 255, 255);

    private final Scalar lowerRed = new Scalar(70, 18, 18);
    private final Scalar upperRed = new Scalar(255, 38, 38);


    // camera info
    private int frameWidth;
    private int frameHeight;

    private double cameraCenterX;
    private double cameraCenterY;

    // dataclass
    public static class Detection {
        public final double centerX;
        public final double centerY;

        public final double radius;

        public final double distanceMM;
        public final double angleDegrees;
        public final double lateralOffsetMM;

        public final double circularity;

        public Detection(
                double centerX,
                double centerY,
                double radius,
                double distanceMM,
                double angleDegrees,
                double lateralOffsetMM,
                double circularity
        ) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.radius = radius;
            this.distanceMM = distanceMM;
            this.angleDegrees = angleDegrees;
            this.lateralOffsetMM = lateralOffsetMM;
            this.circularity = circularity;
        }
    }

    private volatile List<Detection> lastDetection = Collections.emptyList();
    private volatile boolean released = false;

    private final Paint circlePaint = new Paint();
    private final Paint textPaint = new Paint();


    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        frameWidth = width;
        frameHeight = height;

        cameraCenterX = width/2.0;
        cameraCenterY = height/2.0;

        circlePaint.setColor(Color.GREEN);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(5);

        textPaint.setColor(Color.GREEN);
        textPaint.setTextSize(35);
    }

    private final Mat hsv = new Mat();

    private final Mat mask = new Mat();

    private final Mat hierarchy = new Mat();

    private final Mat tmp = new Mat();

    private final Mat kernel_open = Imgproc.getStructuringElement(
            Imgproc.MORPH_ELLIPSE,
            new Size(3, 3)
    );

    private final Mat kernel_close = Imgproc.getStructuringElement(
            Imgproc.MORPH_ELLIPSE,
            new Size(5, 5)
    );

    @Override
    public synchronized List<Detection> processFrame(Mat frame, long captureTimeNanos) {
        if (released) return null;

        // to hsv
        Imgproc.cvtColor(
                frame,
                hsv,
                Imgproc.COLOR_RGB2HSV
        );

        Core.inRange(
                hsv,
                lowerYellow,
                upperYellow,
                mask
        );

        Core.inRange(
                hsv,
                lowerRed,
                upperRed,
                tmp
        );

        // merge masks
        Core.add(mask, tmp, mask);

        // Remove small noise
        Imgproc.morphologyEx(
                mask,
                mask,
                Imgproc.MORPH_OPEN,
                kernel_open
        );

        // Fill small gaps
        Imgproc.morphologyEx(
                mask,
                mask,
                Imgproc.MORPH_CLOSE,
                kernel_close
        );


        // Find contours
        List<MatOfPoint> contours = new ArrayList<>();

        Imgproc.findContours(
                mask,
                contours,
                hierarchy,
                Imgproc.RETR_EXTERNAL,
                Imgproc.CHAIN_APPROX_SIMPLE
        );

        List<Detection> detections = new ArrayList<>();

        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);

            if (area < minArea) {
                contour.release();
                continue;
            }

            MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());

            double perimeter = Imgproc.arcLength(contour2f, true);
            double circularity = 0;

            if (perimeter > 0) {
                circularity =
                        (4.0 * Math.PI * area)
                            / (perimeter * perimeter);
            }

            org.opencv.core.Rect box = Imgproc.boundingRect(contour);
            double aspectRatio =
                    (double) box.width
                            / Math.max(1, box.height);

            boolean valid =
                    circularity >= minCircularity
                            && aspectRatio >= minAspRatio
                            && aspectRatio <= maxAspRatio;


            if (valid) {
                Point center = new Point();
                float[] radius = new float[1];

                Imgproc.minEnclosingCircle(
                        contour2f,
                        center,
                        radius
                );

                if (radius[0] > 0) {
                    // Estimate distance
                    double distance =
                            fx * pollenDiameter_mm
                                    / (2.0 * radius[0]);

                    // Estimate angle
                    double angle =
                            Math.toDegrees(
                                    Math.atan(
                                            (center.x - cameraCenterX)
                                                    / fx
                                    )
                            );

                    // Estimate horizontal offset
                    double lateral =
                            distance * Math.tan(
                                    Math.toRadians(angle)
                            );

                    detections.add(
                            new Detection(
                                    center.x,
                                    center.y,
                                    radius[0],
                                    distance,
                                    angle,
                                    lateral,
                                    circularity
                            )
                    );
                }
            }

            contour2f.release();
            contour.release();
        }


        // sort nearest to farthest
        detections.sort(
                Comparator.comparingDouble(
                        ball -> ball.distanceMM
                )
        );


        lastDetection = Collections.unmodifiableList(
                new ArrayList<>(detections)
        );

        return lastDetection;
    }


    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
        if (!(userContext instanceof List<?>)) {
            return;
        }

        List<?> objects = (List<?>) userContext;

        int index = 0;

        for (Object object : objects) {

            if (!(object instanceof Detection)) {
                continue;
            }

            Detection ball = (Detection) object;

            float x = (float) ball.centerX * scaleBmpPxToCanvasPx;
            float y = (float) ball.centerY * scaleBmpPxToCanvasPx;
            float radius = (float) ball.radius * scaleBmpPxToCanvasPx;

            canvas.drawCircle(
                    x,
                    y,
                    radius,
                    circlePaint
            );

            canvas.drawText(
                    String.format(
                            java.util.Locale.US,
                            "#%d %.0fmm",
                            index + 1,
                            ball.distanceMM
                    ),
                    x + radius,
                    y,
                    textPaint
            );

            index++;
        }
    }

    public List<Detection> getLastDetection() {
        return lastDetection;
    }

    public int getDetectionCount() {
        return lastDetection.size();
    }

    public synchronized void release() {
        released = true;

        lastDetection = Collections.emptyList();

        hsv.release();
        mask.release();
        hierarchy.release();
        kernel_open.release();
        kernel_close.release();
    }
}
