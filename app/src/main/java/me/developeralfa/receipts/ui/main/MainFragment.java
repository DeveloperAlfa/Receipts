package me.developeralfa.receipts.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import me.developeralfa.receipts.R;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    Button save;
    View view1;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.main_fragment, container, false);
        save = view.findViewById(R.id.save);
        view1 = view.findViewById(R.id.bill);
        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               view1.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(view1.getDrawingCache());
                view1.setDrawingCacheEnabled(false);
                store(bitmap,"bill"+System.currentTimeMillis()+".png");
            }
        });
        // TODO: Use the ViewModel
    }

    public void store(Bitmap bm, String fileName){
        final String dirPath = Environment.getExternalStoragePublicDirectory("DCIM").getPath();
        File dir = new File(dirPath);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dirPath, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            Toast.makeText(MainFragment.this.getActivity(), "Saved", Toast.LENGTH_SHORT).show();
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
