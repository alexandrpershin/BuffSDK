package com.buffup.app

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.buffup.sdk.api.ErrorType
import com.buffup.sdk.ui.buffview.BuffView

/**
 *  Client side
 *  Inflate [BuffView]
 *  Initialize and set errorCallback, @see [BuffView.initialize]
 * */

class FullscreenActivity : AppCompatActivity() {

    private lateinit var buffView: BuffView

    private lateinit var videoView: VideoView

    private val url = "https://buffup-public.s3.eu-west-2.amazonaws.com/video/toronto+nba+cut+3.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)

        buffView = findViewById(R.id.buff_view)
        videoView = findViewById(R.id.video)

        videoView.setVideoPath(url)

        buffView.initialize(onError = {
            handleSdkError(it)
        })

        videoView.setOnPreparedListener {
            videoView.start()
        }

    }

    /**
     * This method is responsible for handling errors from SDK
     * */

    private fun handleSdkError(errorType: ErrorType) {
        when (errorType) {
            is ErrorType.GenericError -> {
                val message = errorType.message ?: getString(errorType.resId)
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
            is ErrorType.InternetError -> {
                val message = getString(errorType.resId)
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     *  Resume work of SDK
     * */

    override fun onResume() {
        super.onResume()
        buffView.onResume()
    }

    /**
     *  Pause work of SDK
     * */

    override fun onPause() {
        super.onPause()
        buffView.onPause()
    }

}
