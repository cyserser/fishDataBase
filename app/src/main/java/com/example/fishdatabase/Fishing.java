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

/**
 * En esta pantalla se pretende realizar una mini simulación de pesca
 * el cual dandole al botón de "Fish" empezará un progressBar y cuando
 * se llene nos mostrará el pez capturado y su tamaño
 */

public class Fishing extends AppCompatActivity {

    // Declaracion de botones, textviews etc.
    private Button btn_fish;
    private TextView tvFishCaughtName, tvFishCaughtSize;
    private ImageView ivFishCaught;
    private ProgressBar progressBar;
    private Random randomGenerator;
    private ArrayList<String> fishArray;
    private DatabaseReference readReference;
    private DatabaseReference writeReference;

    // Utilizaremos GifImageView para reproducir ficheros Gif
    private GifImageView gifImageView;

    // Constructor que inicializa el randomGenerador y el arrayList
    public Fishing() {
       randomGenerator = new Random();
       fishArray = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fishing);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Referenciamos
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
        /**
         * Este botón de "Fish" hara que empiece la simulación de la pesca
         * lo más importante aqui es que hemos utilizado llamadas asincronas
         * ya que firebase funciona de esa manera. Todos estos apectos se
         * explicara mas en detalle en el código inferior.
         */
        btn_fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // CallBack
                getFishesFromFirebase(new FishListCallback() {

                    @Override
                    public void onCallback(ArrayList<String> value) {
                        // Generamos un UUID único asi se podra almacenar en Firebase los mismos peces
                        String stringID = UUID.randomUUID().toString().toUpperCase();
                        /* Creamos dos variables que seran finales, es decir que matendra el valor random
                        para esta llamada de método y que no se cambie tras la siguiente llamada.
                        */
                        final String randomFish = getRandom();
                        final String randomSize = getRandomSize();
                        /*Escribimos en Firebase en la ruta "fishesCaught" junto con el pez random y el UUID random generado anteriormente
                        así como su tamaño random tambien.
                         */
                        writeReference = database.getReference("fishesCaught/"+randomFish+"_"+stringID);
                        FetchFishesCaught fetchFishesCaught = new FetchFishesCaught(randomFish,randomSize);
                        writeReference.setValue(fetchFishesCaught);

                        //Actualizamos el texto que nos mostrara en pantalla del pez random con su size random
                        tvFishCaughtName.setText(randomFish);
                        tvFishCaughtSize.setText(randomSize);
                        //El nombre del pez lo convertimos a lowercase puesto que en drawable solo acepta ficheros en minuscula
                        String image = tvFishCaughtName.getText().toString().toLowerCase();
                        // Aqui le haremos un set de la imagen dependiendo del nombre del pez
                        ivFishCaught.setImageResource(getResources().getIdentifier(image, "drawable", getPackageName()));
                        // Aqui se cargará el gif loading cuando se este progresando el progressbar al pescar
                        gifImageView.setImageResource(R.drawable.loading);

                        // Mostramos los textos en ??? para que el usuario no sepa que pez va a salir
                        tvFishCaughtName.setText("???");
                        tvFishCaughtSize.setText("???");
                        // Al darle a fish la imagen por defecto desaparecerá y aparecerá el gif
                        ivFishCaught.setVisibility(View.INVISIBLE);
                        gifImageView.setVisibility(View.VISIBLE);
                        // Desabilitamos el boton de pescar, hasta que no se llene el progressbar
                        btn_fish.setEnabled(false);

                        setUpObserver();

                        // Temporizador de 5 segundos realizado con runnable y handler
                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            /**
                             * Cuando se termine los 5 segundos actualizará los textos
                             * desaparecerá el gif y aparecerá la imagen del pez pescado
                             * tambien se habilitara el boton "fish"
                             */
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

    // Para el progressbar... de duración de 5 sec
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

    // Para el progressBar
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