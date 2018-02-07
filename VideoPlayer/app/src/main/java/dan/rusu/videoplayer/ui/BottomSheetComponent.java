package dan.rusu.videoplayer.ui;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.captain_miao.optroundcardview.OptRoundCardView;

import dan.rusu.videoplayer.R;
import dan.rusu.videoplayer.adapters.ProgramListAdapter;

/**
 *  Created by dan on 07/02/2018.
 */

public class BottomSheetComponent {

    private ConstraintLayout mConstraintLayout;

    private final RecyclerView programsList;
    private View mainContent;
    private int parallaxOffset;


    private BottomSheetBehavior<OptRoundCardView> bottomSheetProgramsBehavior;
    private BottomSheetBehavior<OptRoundCardView> bottomSheetSettingsBehavior;


    public BottomSheetComponent(final CoordinatorLayout coordinatorLayout, final ConstraintLayout constraintLayout) {

        this.mConstraintLayout = constraintLayout;

        parallaxOffset = constraintLayout.getContext().getApplicationContext().getResources().getDimensionPixelSize(R.dimen.main_content_parallax_offset);
        mainContent = constraintLayout.findViewById(R.id.main_content);


        final OptRoundCardView bottomSheetPrograms = coordinatorLayout.findViewById(R.id.programs_bottom_sheet_card);
        final OptRoundCardView bottomSheetSettings = coordinatorLayout.findViewById(R.id.settings_bottom_sheet_card);

        if (bottomSheetPrograms != null) {

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
                    setAlpha(slideOffset);
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
                    setAlpha(slideOffset);
                }
            });
        }

        programsList = coordinatorLayout.findViewById(R.id.programs_list);
        programsList.setLayoutManager(new LinearLayoutManager(mConstraintLayout.getContext()));
        programsList.setHasFixedSize(true);
        programsList.setAdapter(new ProgramListAdapter());

    }

    private void setAlpha(float slideOffset) {
        float alpha = 1 - slideOffset * 1.4f;
        if (alpha > 0) {
            mConstraintLayout.findViewById(R.id.channel_image).setAlpha(alpha);
            mConstraintLayout.findViewById(R.id.channel_title).setAlpha(alpha);
            mConstraintLayout.findViewById(R.id.program_title).setAlpha(alpha);
        }
    }


    public void scrollPersistentProgramsSheet() {
        programsList.smoothScrollToPosition(1);
        if (bottomSheetProgramsBehavior != null)
            bottomSheetProgramsBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        if (bottomSheetSettingsBehavior != null)
            bottomSheetSettingsBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    public void closePersistentBottomSheets() {
        if (bottomSheetProgramsBehavior != null &&
                bottomSheetProgramsBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetProgramsBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        if (bottomSheetSettingsBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetSettingsBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }
}
