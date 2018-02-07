package dan.rusu.videoplayer;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;

import dan.rusu.videoplayer.ui.BottomSheetComponent;

/**
 *  Created by dan on 07/02/2018.
 */

public class MainActivity extends AppCompatActivity {


    private BottomSheetComponent mBottomSheetComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinator);
        final ConstraintLayout constraintLayout = findViewById(R.id.main_content);
        mBottomSheetComponent = new BottomSheetComponent(coordinatorLayout, constraintLayout);

        findViewById(R.id.program_description).setOnClickListener(v -> {
            mBottomSheetComponent.scrollPersistentProgramsSheet();
        });
    }


    @Override
    public void onBackPressed() {
        mBottomSheetComponent.closePersistentBottomSheets();
        super.onBackPressed();
    }

}
