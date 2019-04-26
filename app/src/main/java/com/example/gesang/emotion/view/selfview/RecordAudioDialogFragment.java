package com.example.gesang.emotion.view.selfview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gesang.emotion.R;
import com.example.gesang.emotion.service.RecordService;
import com.melnykov.fab.FloatingActionButton;

import java.io.File;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import kotlin.reflect.KClass;

/**
 * 开始录音的 DialogFragment
 *
 * Created by developerHaoz on 2017/8/12.
 */

public class RecordAudioDialogFragment extends DialogFragment {

    private static final String TAG = "RecordAudioDialogFragme";

    private int mRecordPromptCount = 0;

    private boolean mStartRecording = true;
    private boolean mPauseRecording = true;

    long timeWhenPaused = 0;

    private FloatingActionButton mFabRecord;
    private Chronometer mChronometerTime;
    private ImageView mIvClose;

    private OnAudioCancelListener mListener;

    public static RecordAudioDialogFragment newInstance() {
        RecordAudioDialogFragment dialogFragment = new RecordAudioDialogFragment();
        Bundle bundle = new Bundle();
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_record_audio, null);
        initView(view);

        mFabRecord.setColorNormal(getResources().getColor(R.color.back_white));
        mFabRecord.setColorPressed(getResources().getColor(R.color.left_item_background));

        mFabRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(getActivity()
//                            , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1);
//                }else {
                    onRecord(mStartRecording);
                    mStartRecording = !mStartRecording;
//                }
            }
        });

        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancel();
            }
        });

        builder.setCancelable(false);
        builder.setView(view);
        return builder.create();
    }

    private void initView(View view) {
        mChronometerTime = view.findViewById(R.id.record_audio_chronometer_time);
        mFabRecord =  view.findViewById(R.id.record_audio_fab_record);
        mIvClose = view.findViewById(R.id.record_audio_iv_close);
    }

    private void onRecord(boolean start) {
//        KClass kc = kotlin.jvm.JvmClassMappingKt.getKotlinClass(RecordService.class);

        Intent intent = new Intent(getActivity(), RecordService.class);

        if (start) {
            // start recording
            mFabRecord.setImageResource(R.drawable.icon_stop);
            //mPauseButton.setVisibility(View.VISIBLE);
            File folder = new File(Environment.getExternalStorageDirectory() + "/Emotion");
            if (!folder.exists()) {
                //folder /SoundRecorder doesn't exist, create the folder
                folder.mkdir();
            }

            //start Chronometer
            mChronometerTime.setBase(SystemClock.elapsedRealtime());
            mChronometerTime.start();

            //start RecordingService
            Objects.requireNonNull(getActivity()).startService(intent);
            //keep screen on while recording
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            //stop recording
            mFabRecord.setImageResource(R.drawable.icon_recorder_start);
            //mPauseButton.setVisibility(View.GONE);
            mChronometerTime.stop();
            timeWhenPaused = 0;

            Objects.requireNonNull(getActivity()).stopService(intent);
            //allow the screen to turn off again once recording is finished
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            dismiss();
        }
    }

    public void setOnCancelListener(OnAudioCancelListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    onRecord(mStartRecording);
                }
                break;
        }
    }

    public interface OnAudioCancelListener {
        void onCancel();
    }
}
