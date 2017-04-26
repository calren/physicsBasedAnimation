package com.example.caren.physicsbasedpulltorefresh;

import android.app.Activity;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.view.MotionEvent;
import android.view.View;

import static android.support.animation.SpringForce.DAMPING_RATIO_HIGH_BOUNCY;
import static android.support.animation.SpringForce.STIFFNESS_VERY_LOW;

public class MainActivity extends Activity {

    private static final int FINAL_Y_POSITION = 500;
    private View button;
    private View ghostImage;
    private SpringAnimation springAnim;
    private float originalButtonTranslation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ghostImage = findViewById(R.id.image);
        button = findViewById(R.id.button);
        originalButtonTranslation = button.getTranslationY();

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                    long pressTime = event.getEventTime() - event.getDownTime();
                    resetAnimation(pressTime / 100);
                    springAnim.start();
                }
                return true;
            }
        });
    }

    private void resetAnimation(float stiffness) {
        springAnim =
                new SpringAnimation(ghostImage, DynamicAnimation.TRANSLATION_Y, FINAL_Y_POSITION);
        springAnim.getSpring().setDampingRatio(DAMPING_RATIO_HIGH_BOUNCY);
        springAnim.getSpring().setStiffness(stiffness > 1 ?
                stiffness * STIFFNESS_VERY_LOW :
                1 * STIFFNESS_VERY_LOW);

        springAnim.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation animation, boolean canceled,
                    float value, float velocity) {
                ghostImage.setTranslationY(originalButtonTranslation);
            }
        });
    }
}
