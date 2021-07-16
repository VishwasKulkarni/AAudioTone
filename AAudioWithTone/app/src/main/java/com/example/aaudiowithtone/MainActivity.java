package com.example.aaudiowithtone;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioFormat;
import android.os.Bundle;
import android.view.View;
import android.view.MotionEvent;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static boolean playing = false;
    static boolean clicked_16bit = true;
    static boolean clicked_float = true;
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		View topHalf = findViewById(R.id.tophalf);
        View bottomHalf = findViewById(R.id.bottomHalf);

        /** PCM 16Bit tone Playback **/
        topHalf.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    if(isTouchOnLayout(view, motionEvent)){
                        if(clicked_16bit){
							clicked_16bit = false;
							clicked_float = true;
							if(playing){
								if(stopPlayBack())
							playing = false;
							}
							initAudioEngine();
							createPlayBack(AudioFormat.ENCODING_PCM_16BIT);
							playing = true;
						}
                    }
                }
                return false;
            }
        });

        /** PCM Float tone Playback **/
        bottomHalf.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    if(isTouchOnLayout(view, motionEvent)) {
                        if (clicked_float) {
                            clicked_float = false;
                            clicked_16bit = true;
                            if (playing) {
                                if (stopPlayBack())
                                    playing = false;
                            }
                            initAudioEngine();
                            createPlayBack(AudioFormat.ENCODING_PCM_FLOAT);
                            playing = true;
                        }
                    }
                }
                return false;
            }
        });

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
    }
    private boolean isTouchOnLayout(View view, MotionEvent motionEvent) {
        if(motionEvent.getX() < 0 || motionEvent.getY() < 0 ||
                motionEvent.getX() > view.getMeasuredWidth() ||
                motionEvent.getY() > view.getMeasuredHeight())
            return false;
        return true;
    }
    @Override
    protected void onPause(){
        if(playing){
            stopPlayBack();
            playing = false;
        }
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

     /** A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     **/

    public native String stringFromJNI();

    public native boolean initAudioEngine();

    public native int createPlayBack(int mFormat);

    private native boolean stopPlayBack();
}
