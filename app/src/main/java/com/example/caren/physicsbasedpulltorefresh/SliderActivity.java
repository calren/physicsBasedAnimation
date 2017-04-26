package com.example.caren.physicsbasedpulltorefresh;

import android.app.Activity;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import static android.support.animation.SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY;
import static android.support.animation.SpringForce.DAMPING_RATIO_NO_BOUNCY;
import static android.support.animation.SpringForce.STIFFNESS_VERY_LOW;

public class SliderActivity extends Activity {

    private static final int FINAL_Y_POSITION = 500;
    private View button;
    private View ghostImage;
    private SpringAnimation springAnim;
    private float originalButtonTranslation;

    private SeekBar bounceSeekBar;
    private SeekBar stiffnessSeekBar;

    private TextView bounceValue;
    private TextView stiffnessValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        ghostImage = findViewById(R.id.image);
        button = findViewById(R.id.button);
        bounceSeekBar = (SeekBar) findViewById(R.id.bounceValueAdjustment);
        stiffnessSeekBar = (SeekBar) findViewById(R.id.stiffnessValueAdjustment);
        bounceValue = (TextView) findViewById(R.id.dampingRatioValue);
        stiffnessValue = (TextView) findViewById(R.id.stiffnessValue);

        bounceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                bounceValue.setText(
                        "damping ratio value: " + (DAMPING_RATIO_MEDIUM_BOUNCY - (i / 200f)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        stiffnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                stiffnessValue.setText("stiffness value: " + (STIFFNESS_VERY_LOW * (i / 2)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        originalButtonTranslation = button.getTranslationY();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetAnimation();
                springAnim.start();
            }
        });
    }

    private void resetAnimation() {
        springAnim =
                new SpringAnimation(ghostImage, DynamicAnimation.TRANSLATION_Y, FINAL_Y_POSITION);

        if (stiffnessSeekBar.getProgress() == 0) {
            springAnim.getSpring().setStiffness(STIFFNESS_VERY_LOW);
        } else {
            springAnim.getSpring()
                    .setStiffness(STIFFNESS_VERY_LOW * (stiffnessSeekBar.getProgress() / 2));
        }

        if (bounceSeekBar.getProgress() == 0) {
            springAnim.getSpring().setDampingRatio(DAMPING_RATIO_NO_BOUNCY);
        } else {
            springAnim.getSpring()
                    .setDampingRatio(
                            DAMPING_RATIO_MEDIUM_BOUNCY - (bounceSeekBar.getProgress() / 200f));
        }


        springAnim.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation animation, boolean canceled,
                    float value, float velocity) {
                ghostImage.setTranslationY(originalButtonTranslation);
            }
        });
    }
}

