package com.example.mypoetry.Model

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.Gravity
import com.example.mypoetry.R

class VerificationCodeTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    var mCountDownTimer: CountDownTimer?= null
    var countDownSec = 60
    init {
        text = "获取验证码"
        setTextColor(0xff000000.toInt())
        background = context.resources.getDrawable(R.drawable.selector_verification_code)
        gravity = Gravity.CENTER
        setOnClickListener {
            startCountDown()
        }
    }

    private fun startCountDown() {
        isEnabled = false
        // 倒计时处理
        mCountDownTimer = object : CountDownTimer((countDownSec + 1).toLong() * 1000,1000) {
            override fun onTick(millisUntilFinished: Long) {
                val l = millisUntilFinished / 1000
                text = "重新获取 ($l)"
                if (l == 0L) {
                    mCountDownTimer?.cancel()
                    isEnabled = true
                    text = "获取验证码"
                }
            }
            override fun onFinish() {
            }
        }
        mCountDownTimer?.start()
    }

    fun release() {
        mCountDownTimer?.cancel()
        mCountDownTimer = null
    }
}