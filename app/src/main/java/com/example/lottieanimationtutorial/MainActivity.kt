package com.example.lottieanimationtutorial

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG: String = "로그"
    var isLiked:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        isLiked = false

        //좋아요 버튼에 클릭 리스너를 달아준다. 
        like_btn.setOnClickListener {

            like_btn.playAnimation()

            //좋아요 상태가 아닐때
            if (isLiked == false){
                //애니메이션의 커스텀
                // Custom animation speed or duration.
                //offloat(시작지점, 종료지점).setDuration(지속시간) 1000이 1초
                //시작시점은 0f = 0%이고, 1f = 100%임
                val animator = ValueAnimator.ofFloat(0f, 0.5f).setDuration(2000)
                animator.addUpdateListener {
                    like_btn.setProgress(animation.getAnimatedValue() as Float)
                }
                animator.start()
                isLiked = true
            } else{ //좋아요 상태일때 취소하는 경우

                //애니메이션의 커스텀
                // Custom animation speed or duration.
                //offloat(시작지점, 종료지점).setDuration(지속시간) 1000이 1초
                //시작시점은 0f = 0%이고, 1f = 100%임
                val animator = ValueAnimator.ofFloat(0.5f, 1f).setDuration(1000)
                animator.addUpdateListener {
                    like_btn.setProgress(animation.getAnimatedValue() as Float)
                }
                animator.start()
                isLiked = false
            }
            Log.d(TAG, "MainActivity - onCreate() / 좋아요 버튼이 클릭되었다. isLiked : ${isLiked}")
        }
    }
}