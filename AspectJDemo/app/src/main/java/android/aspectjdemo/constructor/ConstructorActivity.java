package android.aspectjdemo.constructor;

import android.aspectjdemo.R;
import android.aspectjdemo.animal.Animal;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author hiphonezhu@gmail.com
 * @version [AspectJDemo, 17/1/20 10:22]
 */

public class ConstructorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Animal animal = new Animal();
    }
}
