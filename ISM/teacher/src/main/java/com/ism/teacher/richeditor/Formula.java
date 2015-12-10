package com.ism.teacher.richeditor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.ism.teacher.R;


/**
 * {@link Formula} is a {@link RelativeLayout} exteded view class for handle different mathematic equations.
 * Created by c85 on 06/11/15.
 */
public class Formula extends RelativeLayout {

    private Button btnClose;
    private FormulaListener formulaListener;
    private GridView gridSymbols;
    public GridAdaptor gridAdaptor;
    private Context mContext;

    /**
     * handle formula event to {@link}
     */
    public interface FormulaListener {
        /**
         * remove view from its parent activity.
         */
        public void close();
    }

    public Formula(Context context) {
        super(context);
//        this.formulaListener = (FormulaListener) context;
        mContext = context;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        RelativeLayout layout = (RelativeLayout) layoutInflater.inflate(R.layout.layout_formula, this);
        init(layout);
        // RelativeLayout layout = (RelativeLayout) inflate(context, R.layout.layout_formula, this);
    }

    public void setFormulaListener(FormulaListener formulaListener) {
        this.formulaListener = formulaListener;
    }


    /**
     * initialize all objects of {@link Formula} class
     *
     * @param layout - relative main layout.
     */
    private void init(RelativeLayout layout) {
        btnClose = (Button) layout.findViewById(R.id.btn_close);
        gridSymbols = (GridView) layout.findViewById(R.id.gv_formula);

        events();


        String[] formulas = {
                getResources().getString(R.string.sigma),
                getResources().getString(R.string.sum),
                getResources().getString(R.string.forall),
                getResources().getString(R.string.part),
                getResources().getString(R.string.and),
                getResources().getString(R.string.or),
                getResources().getString(R.string.ne),
                getResources().getString(R.string.asymp),
                getResources().getString(R.string.cong),
                getResources().getString(R.string.perp),
                getResources().getString(R.string.le),
                getResources().getString(R.string.ge),
                getResources().getString(R.string.equiv),
                getResources().getString(R.string.prop),
                getResources().getString(R.string.rightwardsarrow),
                getResources().getString(R.string.emptyset),
                getResources().getString(R.string.element_of),
                getResources().getString(R.string.not_an_element_of),
                getResources().getString(R.string.subset_of),
                getResources().getString(R.string.superset_of),
                getResources().getString(R.string.not_a_subset_of),
                getResources().getString(R.string.subset_of_or_equal_to),
                getResources().getString(R.string.superset_of_or_equal_to),
                getResources().getString(R.string.alef),
                getResources().getString(R.string.infinity),
                getResources().getString(R.string.angle),
                getResources().getString(R.string.integral),
                getResources().getString(R.string.script_capital_P),
                getResources().getString(R.string.blackletter_capital_I),
                getResources().getString(R.string.blackletter_capital_R),
                getResources().getString(R.string.contains_as_member),
                getResources().getString(R.string.product),
                getResources().getString(R.string.intersection),
                getResources().getString(R.string.union)

        };


        gridAdaptor = new GridAdaptor(mContext, formulas);
        gridSymbols.setAdapter(gridAdaptor);

    }

    /**
     * add all events for layout controls
     */
    private void events() {
        btnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                formulaListener.close();
            }
        });
    }


}
