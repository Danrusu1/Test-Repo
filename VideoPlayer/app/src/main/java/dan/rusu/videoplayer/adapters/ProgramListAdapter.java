package dan.rusu.videoplayer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import dan.rusu.videoplayer.R;

/**
 *  Created by dan on 07/02/2018.
 */

public class ProgramListAdapter extends RecyclerView.Adapter<ProgramItemViewHolder> {

    @Override
    public ProgramItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.program_list_item, parent, false);
        return new ProgramItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProgramItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 12;
    }
}

class ProgramItemViewHolder extends RecyclerView.ViewHolder {
    private TextView title, dayTime, duration, description;
    private ImageView shareFacebookButton, shareTwitterButton;

    ProgramItemViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.program_title);
        dayTime = itemView.findViewById(R.id.program_day_time);
        duration = itemView.findViewById(R.id.program_duration);
        description = itemView.findViewById(R.id.program_description);
        shareFacebookButton = itemView.findViewById(R.id.share_facebook_button);
        shareTwitterButton = itemView.findViewById(R.id.share_twitter_button);
    }
}
