package com.ism.teacher.richeditor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.ism.teacher.R;


/**
 * Created by c85 on 06/11/15.
 */
public class GridAdaptor extends BaseAdapter {
    private Context mContext;

    private String[] formulas;
    private final int btnsize = 50;

    // Constructor
    public GridAdaptor(Context c, String[] formulas) {
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

        btnSymbol.setLayoutParams(new GridView.LayoutParams(btnsize, btnsize));
        btnSymbol.setTypeface(null, Typeface.BOLD);
        btnSymbol.setTextSize(25);
        btnSymbol.setBackgroundColor(Color.TRANSPARENT);
        btnSymbol.setBackgroundResource(R.drawable.border);
        btnSymbol.setText(formulas[position]);

        btnSymbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((MainActivity) mContext).insertSymbols(((Button) v).getText() + "");
                insertSymbolListener.insertSymbol(((Button) v).getText() + "");
            }
        });
        return btnSymbol;
    }


    public interface InsertSymbolListener {
        public void insertSymbol(String symbol);
    }


    public void setInsertSymbolListener(InsertSymbolListener insertSymbolListener) {
        this.insertSymbolListener = insertSymbolListener;
    }

    private InsertSymbolListener insertSymbolListener;


}