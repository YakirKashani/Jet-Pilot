package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw2.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import Interfaces.Callback_highScoreClicked;
import Logic.HallOfFame;
import Logic.Record;

public class HallOfFameAdapter extends RecyclerView.Adapter<HallOfFameAdapter.RecordViewHolder> {

    private Context context;
    private ArrayList<Record> Records;
    private Callback_highScoreClicked callbackHighScoreClicked;

    public HallOfFameAdapter(Context context, HallOfFame HOF, Callback_highScoreClicked callbackHighScoreClicked) {
        this.context = context;
        this.Records = HOF.getRecords();
        this.callbackHighScoreClicked = callbackHighScoreClicked;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_record_item, parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        Record record = getItem(position);
        holder.Record_LBL_points.setText("" + Records.get(position).getPoints());
    }

    @Override
    public int getItemCount() {
        return Records == null ? 0 : Records.size();
    }

    private Record getItem(int position) {
        return Records.get(position);
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView Record_LBL_points;
        private ImageButton list_BTN_send;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            Record_LBL_points = itemView.findViewById((R.id.Record_LBL_points));
            list_BTN_send = itemView.findViewById(R.id.list_BTN_send);
            list_BTN_send.setOnClickListener(v -> {
                if(callbackHighScoreClicked != null)
                    callbackHighScoreClicked.highScoreClicked(Records.get(getAdapterPosition()).getLat(),Records.get(getAdapterPosition()).getLon());
            });
        }
    }
}
