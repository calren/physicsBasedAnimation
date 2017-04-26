package com.example.caren.physicsbasedpulltorefresh;

import android.app.Activity;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import static android.support.animation.SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY;
import static android.support.animation.SpringForce.STIFFNESS_VERY_LOW;

public class SliderActivity extends Activity {

    private static final int FINAL_Y_POSITION = 500;
    private View button;
    private View ghostImage;
    private SpringAnimation springAnim;
    private float originalButtonTranslation;

    private SeekBar bounceSeekBar;
    private SeekBar stiffnessSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        ghostImage = findViewById(R.id.image);
        button = findViewById(R.id.button);
        bounceSeekBar = (SeekBar) findViewById(R.id.bounceValueAdjustment);
        stiffnessSeekBar = (SeekBar) findViewById(R.id.stiffnessValueAdjustment);

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
        Log.i("Caren", "b : " + (bounceSeekBar
                .getProgress() / 200f));
        springAnim =
                new SpringAnimation(ghostImage, DynamicAnimation.TRANSLATION_Y, FINAL_Y_POSITION);
        springAnim.getSpring().setStiffness(STIFFNESS_VERY_LOW * (stiffnessSeekBar.getProgress() / 2));
//        springAnim.getSpring().setDampingRatio(bounceSeekBar.getProgress() / 200);
        springAnim.getSpring().setDampingRatio(DAMPING_RATIO_MEDIUM_BOUNCY - (bounceSeekBar
                .getProgress() / 200f));


        springAnim.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation animation, boolean canceled,
                    float value, float velocity) {
                ghostImage.setTranslationY(originalButtonTranslation);
            }
        });
    }
}
