package com.example.itsad.inventariovirtual;

import android.app.Activity;
import android.os.Bundle;

import com.example.itsad.inventariovirtual.Helpers.StateRecognizerActivity;
import com.example.itsad.inventariovirtual.Models.Item;
import com.example.itsad.inventariovirtual.Repositorio.ItemRepository;

public class MonitoringActivity extends StateRecognizerActivity {

    ItemRepository mItemRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);
        mItemRepository = new ItemRepository(this);
        mItemRepository.setItensPresentStateByInventory(Long.parseLong(getIntent().getStringExtra("_idInventario")), Item.FALSE);
        StartTrackingActivities();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StopTrackingActivities();
    }
}
