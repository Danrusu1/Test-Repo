package dan.rusu.videoplayer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.github.captain_miao.optroundcardview.OptRoundCardView;

import dan.rusu.videoplayer.adapters.ProgramListAdapter;

/**
 *  Created by dan on 07/02/2018.
 */

public class MainActivity extends AppCompatActivity {


    private View mContent;
    private View mainContent;

    private View channelImage;
    private View channelTitle;
    private View programTitle;

    private BottomSheetBehavior<OptRoundCardView> bottomSheetProgramsBehavior;
    private BottomSheetBehavior<OptRoundCardView> bottomSheetSettingsBehavior;

    private int parallaxOffset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContent = findViewById(android.R.id.content);

        final OptRoundCardView bottomSheetPrograms = findViewById(R.id.programs_bottom_sheet_card);
        final OptRoundCardView bottomSheetSettings = findViewById(R.id.settings_bottom_sheet_card);

        if (bottomSheetPrograms != null) {
            mainContent = findViewById(R.id.main_content);
            channelImage = findViewById(R.id.channel_image);
            channelTitle = findViewById(R.id.channel_title);
            programTitle = findViewById(R.id.program_title);

            parallaxOffset = getResources().getDimensionPixelSize(R.dimen.main_content_parallax_offset);

            bottomSheetProgramsBehavior = BottomSheetBehavior.from(bottomSheetPrograms);
            bottomSheetProgramsBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

            bottomSheetPrograms.findViewById(R.id.dragView).setOnClickListener(v -> {
                switch (bottomSheetProgramsBehavior.getState()) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        bottomSheetProgramsBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        bottomSheetProgramsBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;
                }
            });

            bottomSheetProgramsBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {

                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    bottomSheetSettings.setTranslationY(slideOffset * bottomSheetSettingsBehavior.getPeekHeight());
                    mainContent.setTranslationY(-slideOffset * parallaxOffset);
                    float alpha = 1 - slideOffset * 1.4f;
                    if (alpha > 0) {
                        channelImage.setAlpha(alpha);
                        channelTitle.setAlpha(alpha);
                        programTitle.setAlpha(alpha);
                    }
                }
            });
        }

        bottomSheetSettingsBehavior = BottomSheetBehavior.from(bottomSheetSettings);
        bottomSheetSettingsBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        bottomSheetSettings.findViewById(R.id.dragView).setOnClickListener(v -> {
            switch (bottomSheetSettingsBehavior.getState()) {
                case BottomSheetBehavior.STATE_COLLAPSED:
                    bottomSheetSettingsBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    break;
                case BottomSheetBehavior.STATE_EXPANDED:
                    bottomSheetSettingsBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    break;
            }
        });

        if (bottomSheetPrograms != null) {
            bottomSheetSettingsBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {

                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    bottomSheetPrograms.setTranslationY(slideOffset * bottomSheetProgramsBehavior.getPeekHeight());
                    mainContent.setTranslationY(-slideOffset * parallaxOffset);
                    float alpha = 1 - slideOffset * 1.4f;
                    if (alpha > 0) {
                        channelImage.setAlpha(alpha);
                        channelTitle.setAlpha(alpha);
                        programTitle.setAlpha(alpha);
                    }
                }
            });
        }


        final RecyclerView programsList = findViewById(R.id.programs_list);
        programsList.setLayoutManager(new LinearLayoutManager(this));
        programsList.setHasFixedSize(true);
        programsList.setAdapter(new ProgramListAdapter());

        mContent.post(() -> {
            if (bottomSheetProgramsBehavior != null) {
                bottomSheetProgramsBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                bottomSheetProgramsBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bottomSheetProgramsBehavior.setHideable(false);
            }

            bottomSheetSettingsBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            bottomSheetSettingsBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            bottomSheetSettingsBehavior.setHideable(false);

        });


        findViewById(R.id.program_description).setOnClickListener(v -> {
            programsList.smoothScrollToPosition(1);
            if (bottomSheetProgramsBehavior != null) {
                bottomSheetProgramsBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
            bottomSheetSettingsBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });
    }


    @Override
    public void onBackPressed() {
        if (bottomSheetProgramsBehavior != null &&
                bottomSheetProgramsBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetProgramsBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            return;
        }

        if (bottomSheetSettingsBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetSettingsBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            return;
        }

        super.onBackPressed();
    }


    private void showToast(int messageId) {
        showToast(getString(messageId));
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }


}
