package com.lhh.playaudiotest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
private MediaPlayer mediaPlayer = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button play = findViewById(R.id.play);
        Button pause = findViewById(R.id.pause);
        Button stop = findViewById(R.id.stop);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else{
            initMediaPlayer();//初始化MediaPlayer
        }
    }

    private void initMediaPlayer() {
        try {
        File file = new File(Environment.getExternalStorageDirectory(),"iii.mp3");//要在此放一首music.mp3在此目录下
        Log.d("音乐文件路径", file.getPath());

            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();//让MediaPlayer进入准备状态
            Log.d("TAG", "initMediaPlayer: ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                initMediaPlayer();
            }else {
                Toast.makeText(this, "拒绝此权限无法使用程序", Toast.LENGTH_SHORT).show();
                finish();
            }
            break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();//释放资源
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play :
               if(!mediaPlayer.isPlaying()){
                   mediaPlayer.start();//开始播放
               }
                break;
            case R.id.pause :
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();//暂停播放
                }
                break;
            case R.id.stop :
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.reset();//停止播放
                    initMediaPlayer();
                }
                break;
                default:
                    break;
        }


    }
}
