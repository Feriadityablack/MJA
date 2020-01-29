package com.feri.murahjaya.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AccelerateDecelerateInterpolator
import com.feri.murahjaya.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {

    private val runnable = Runnable {
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val animatorSet = AnimatorSet()
        animatorSet.interpolator = AccelerateDecelerateInterpolator()
        animatorSet.duration = 1500

        val scaleXAnimator = ObjectAnimator.ofFloat(logo, "scaleX", 0.0f, 1.0f)
        val scaleYAnimator = ObjectAnimator.ofFloat(logo, "scaleY", 0.0f, 1.0f)

        animatorSet.play(scaleXAnimator).with(scaleYAnimator)
        animatorSet.start()

        Handler().postDelayed(runnable, 2500)
    }
}
