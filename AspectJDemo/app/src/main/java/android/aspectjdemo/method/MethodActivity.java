package android.aspectjdemo.method;

import android.aspectjdemo.animal.Animal;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * @author hiphonezhu@gmail.com
 * @version [AspectJDemo, 17/1/20 10:22]
 */

public class MethodActivity extends AppCompatActivity {
    private static final String TAG = "MethodActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Animal animal = new Animal();
        animal.fly();

        int weight = animal.getWeight();
        Log.e(TAG, "weight: " + weight);

        int height = animal.getHeight();
        height = animal.getHeight(1);

        animal.hurt();
        try {
            animal.hurtThrows();
        } catch (Exception e) {}
    }
}
