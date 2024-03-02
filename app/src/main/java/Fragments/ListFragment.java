package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw2.R;

import Adapters.HallOfFameAdapter;
import Interfaces.Callback_highScoreClicked;
import Logic.HallOfFame;

public class ListFragment extends Fragment {
    private RecyclerView RecordsBoard_LST_Records;
    private HallOfFame HOF;
    private Callback_highScoreClicked callbackHighScoreClicked;
    private Context context;
    public void setCallbackHighScoreClicked(Callback_highScoreClicked callbackHighScoreClicked){
        this.callbackHighScoreClicked = callbackHighScoreClicked;
    }
    public ListFragment(Context context) {
        this.context = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_records_board,container,false);
        findViews(view);
        initViews();
        return view;
    }
    private void findViews(View view) {
        RecordsBoard_LST_Records = view.findViewById(R.id.RecordsBoard_LST_Records);
    }
    private void initViews() {
        HOF = new HallOfFame();
        HallOfFameAdapter HOFadapter = new HallOfFameAdapter(context,HOF,callbackHighScoreClicked);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        RecordsBoard_LST_Records.setLayoutManager(linearLayoutManager);
        RecordsBoard_LST_Records.setAdapter(HOFadapter);
    }
}
