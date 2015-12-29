package com.ism.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.ism.activity.HostActivity;
import com.ism.object.Global;
import com.ism.utility.Debug;


/**
 * Created by c85 on 06/11/15.
 */
public class ScientificGridAdaptor extends BaseAdapter {
    private Context mContext;
    private String[] formulas;
    private final int btnsize = 40;

    // Constructor
    public ScientificGridAdaptor(Context c, String[] formulas) {
        mContext = c;
        this.formulas = formulas;
    }

    @Override
    public int getCount() {
        return formulas.length;
    }

    @Override
    public Object getItem(int position) {
        return formulas[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Button btnSymbol = new Button(mContext);

        btnSymbol.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnSymbol.setTypeface(Global.myTypeFace.getRalewayExtraBold(), Typeface.BOLD);
        btnSymbol.setTextSize(32);
        btnSymbol.setBackgroundColor(Color.TRANSPARENT);
       // btnSymbol.setBackgroundResource(R.drawable.border);
        btnSymbol.setText(formulas[position]);
        btnSymbol.setAllCaps(false);
       // Debug.i("Char ", formulas[position]);
        btnSymbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((MainActivity) mContext).insertSymbols(((Button) v).getText() + "");
                ((HostActivity)mContext).insertSymbolListener.Scientific(((Button) v).getText() + "");
            }
        });
        return btnSymbol;
    }



}