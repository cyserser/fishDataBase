package com.example.fishdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import pl.droidsonroids.gif.GifImageView;

public class Fishing extends AppCompatActivity {

    private Button btn_fish;
    private TextView tvFishCaughtName, tvFishCaughtSize;
    private ImageView ivFishCaught;
    private ProgressBar progressBar;
    private Random randomGenerator;
    private ArrayList<String> fishArray;
    private DatabaseReference readReference;
    private DatabaseReference writeReference;
    private Timer timer;
    private TimerTask timerTask;
    private Handler timerHandler;
    private GifImageView gifImageView;

    public Fishing() {
       randomGenerator = new Random();
       fishArray = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fishing);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_fish = findViewById(R.id.btn_startFish);
        tvFishCaughtName = findViewById(R.id.tvFishCaughtName);
        tvFishCaughtSize = findViewById(R.id.tvFishCaughtSize);
        ivFishCaught = findViewById(R.id.ivFishCaught);
        progressBar = findViewById(R.id.fishingProgressBar);
        gifImageView = findViewById(R.id.ivGif);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

//        readReference = database.getReference("fishes");
//        readReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ds: snapshot.getChildren()){
//                    String key = ds.getKey();
//                    fishArray.add(key);
//
//                }
//                System.out.println("El tamaño es: "+fishArray.size()); // tamaño 3
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });

        // Boton on CallBack
        btn_fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFishesFromFirebase(new FishListCallback() {
                    @Override
                    public void onCallback(ArrayList<String> value) {
                        String stringID = UUID.randomUUID().toString().toUpperCase();
                        final String randomFish = getRandom();
                        final String randomSize = getRandomSize();
                        writeReference = database.getReference("fishesCaught/"+randomFish+"_"+stringID);
                        FetchFishesCaught fetchFishesCaught = new FetchFishesCaught(randomFish,randomSize);
                        writeReference.setValue(fetchFishesCaught);

                        tvFishCaughtName.setText(randomFish);
                        tvFishCaughtSize.setText(randomSize);
                        String image = tvFishCaughtName.getText().toString().toLowerCase();
                        ivFishCaught.setImageResource(getResources().getIdentifier(image, "drawable", getPackageName()));
                        gifImageView.setImageResource(R.drawable.loading);

                        tvFishCaughtName.setText("???");
                        tvFishCaughtSize.setText("???");
                        ivFishCaught.setVisibility(View.INVISIBLE);
                        gifImageView.setVisibility(View.VISIBLE);
                        btn_fish.setEnabled(false);

                        setUpObserver();

                        // Temporizador de 5 segundos
                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tvFishCaughtName.setText(randomFish);
                                tvFishCaughtSize.setText(randomSize);

                                gifImageView.setVisibility(View.INVISIBLE);
                                ivFishCaught.setVisibility(View.VISIBLE);
                                btn_fish.setEnabled(true);
                            }
                        }, 5000);


                    }
                });
            }
        });

    }

    // CallBack
    public interface FishListCallback {
        void onCallback(ArrayList<String> value);
    }

    // Firebase es asyncrono -> Callback es necesario
    public void getFishesFromFirebase(final FishListCallback myCallback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        readReference = database.getReference("fishes");
        readReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    String key = ds.getKey();
                    fishArray.add(key);

                }
                myCallback.onCallback(fishArray);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

    // Método para obtener un random item del arraylist
    private String getRandom(){
        int index = randomGenerator.nextInt(fishArray.size());
        String string = fishArray.get(index);
        return string;
    }

    // Método que nos devuelve un número random entre 15 y 35
    private String getRandomSize(){
        int low = 15;
        int high = 35;
        int randomSize = randomGenerator.nextInt(high-low)+low;
        return String.valueOf(randomSize);
    }

    // Para el progressbar...
    private void startAnimation() {
        int width = progressBar.getWidth();
        progressBar.setMax(width);

        ValueAnimator animator = ValueAnimator.ofInt(0, width);
        animator.setInterpolator(new LinearInterpolator());
        animator.setStartDelay(0);
        animator.setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                progressBar.setProgress(value);
            }
        });

        animator.start();
    }

    private void setUpObserver() {
        progressBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                startAnimation();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    progressBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                else {
                    progressBar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

}