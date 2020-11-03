package com.buffup.sdk.extensions

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

/**
 * View extensions
 * */


fun View.changeBackgroundWithAnimation(drawableFrom: Int, drawableTo: Int) {
    val colorAnimation: ValueAnimator =
        ValueAnimator.ofObject(ArgbEvaluator(), drawableFrom, drawableTo)
    colorAnimation.duration = 400L

    colorAnimation.addUpdateListener { animator ->
        setBackgroundResource(animator.animatedValue as Int)
        requestLayout()
    }
    colorAnimation.start()
}

fun View.makeGone() {
    this.visibility = View.GONE
}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.setVisible(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun View.makeInVisible() {
    visibility = View.INVISIBLE
}


fun View.disableClick() {
    isFocusable = false
    isClickable = false
}

fun View.enableClick() {
    isFocusable = true
    isClickable = true
}

fun View.slideDown() {
    YoYo.with(Techniques.SlideOutDown).duration(500).onEnd { makeVisible() }
        .playOn(this)
}

fun View.slideDownWithDelay(delay: Long = 2000L) {
    postDelayed({
        YoYo.with(Techniques.SlideOutDown).duration(500).onEnd { makeVisible() }
            .playOn(this)
    }, delay)
}

fun View.slideUp() {
    YoYo.with(Techniques.SlideInUp).duration(500).onEnd { makeVisible() }
        .playOn(this)
}

fun View.fadeOut() {
    this.animate().alpha(0.0f).setDuration(500L).setListener(object : Animator.AnimatorListener {

        override fun onAnimationEnd(animation: Animator?) {
            postDelayed({
                alpha = 1f
            }, 4000)
        }

        override fun onAnimationStart(animation: Animator?) {}
        override fun onAnimationCancel(animation: Animator?) {}
        override fun onAnimationRepeat(animation: Animator?) {}

    })
}
