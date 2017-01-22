package android.aspectjdemo.field;

import android.aspectjdemo.animal.Animal;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * @author hiphonezhu@gmail.com
 * @version [AspectJDemo, 17/1/20 10:22]
 */

public class FieldActivity extends AppCompatActivity {
    private static final String TAG = "FieldActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Animal animal = new Animal();

        int age = animal.getAge();
        Log.e(TAG, "true age: " + age);
        animal.setAge(11);
        age = animal.getAge();
        Log.e(TAG, "age: " + age);

        animal.targetFunc();
        targetFunc();
    }

    public void targetFunc() {

    }
}
