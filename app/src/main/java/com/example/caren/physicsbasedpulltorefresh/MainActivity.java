package com.example.caren.physicsbasedpulltorefresh;

import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import static android.support.animation.SpringForce.DAMPING_RATIO_HIGH_BOUNCY;
import static android.support.animation.SpringForce.DAMPING_RATIO_LOW_BOUNCY;
import static android.support.animation.SpringForce.STIFFNESS_LOW;
import static android.support.animation.SpringForce.STIFFNESS_VERY_LOW;

public class MainActivity extends AppCompatActivity {

    private View button;
    private View img;
    private SpringAnimation springAnim;
    private float originalButtonTranslation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.image);
        button = findViewById(R.id.button);
        originalButtonTranslation = button.getTranslationY();

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                    long pressTime = event.getEventTime() - event.getDownTime();
                    resetAnimation(pressTime / 120);
                    springAnim.start();
                }
                return true;
            }
        });
    }

    private void resetAnimation(float stiffness) {
        springAnim = new SpringAnimation(img, DynamicAnimation.TRANSLATION_Y, 500);
        springAnim.getSpring().setDampingRatio(DAMPING_RATIO_HIGH_BOUNCY);
        springAnim.getSpring().setStiffness(stiffness > 1 ? stiffness * STIFFNESS_VERY_LOW : 1 * STIFFNESS_VERY_LOW);

        springAnim.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                img.setTranslationY(originalButtonTranslation);
            }
        });
    }
}
