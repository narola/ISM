package com.ism.author.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ism.author.R;
import com.ism.author.object.MyTypeFace;
import com.ism.author.richeditor.Formula;
import com.ism.author.richeditor.GridAdaptor;
import com.narola.kpa.richtexteditor.view.RichTextEditor;

import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by c166 on 05/12/15.
 */
public class AddQuestionTextDialog extends Dialog implements View.OnClickListener, RichEditor.OnTextChangeListener, RichTextEditor.RichTextListener,
        Formula.FormulaListener, GridAdaptor.InsertSymbolListener {

    private static final String TAG = AddQuestionTextDialog.class.getSimpleName();


    public interface GetTextListener {
        public void getText(String text);
    }

    private Context mContext;
    private TextView tvDialogAddTextTitle, tvDialogAddTextDone, tvDialogAddTextClose;
    private RichTextEditor rteDialogAddText;
    private MyTypeFace myTypeFace;
    private RelativeLayout layoutRich;
    private Formula formula;
    private SelectMediaListener selectMediaListener;
    private AddTextListener addTextListener;
    private String htmlText;


    public AddQuestionTextDialog(Context mContext, SelectMediaListener selectMediaListener, AddTextListener addTextListener, String htmlText) {
        super(mContext);
        this.mContext = mContext;
        this.selectMediaListener = selectMediaListener;
        this.addTextListener = addTextListener;
        this.htmlText = htmlText;
        Window w = getWindow();
        getWindow().getAttributes().windowAnimations =
                R.style.DialogOpenAnimation;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        w.setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_add_text);
        w.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        w.setBackgroundDrawableResource(android.R.color.transparent);

        initializeDialog();


    }

    private void initializeDialog() {

        myTypeFace = new MyTypeFace(mContext);
        tvDialogAddTextTitle = (TextView) findViewById(R.id.tv_dialog_add_text_title);
        tvDialogAddTextDone = (TextView) findViewById(R.id.tv_dialog_add_text_done);
        tvDialogAddTextClose = (TextView) findViewById(R.id.tv_dialog_add_text_close);
        tvDialogAddTextTitle.setTypeface(myTypeFace.getRalewayRegular());
        tvDialogAddTextDone.setTypeface(myTypeFace.getRalewayRegular());
        tvDialogAddTextClose.setTypeface(myTypeFace.getRalewayRegular());
        tvDialogAddTextDone.setOnClickListener(this);
        tvDialogAddTextClose.setOnClickListener(this);


        rteDialogAddText = (RichTextEditor) findViewById(R.id.rte_dialog_add_text);
        rteDialogAddText.getRichEditor().setEditorFontSize(20);
        rteDialogAddText.setRichTextListener((RichTextEditor.RichTextListener) this);
        rteDialogAddText.getRichEditor().setOnTextChangeListener(this);
        rteDialogAddText.setHtml(htmlText);
        layoutRich = (RelativeLayout) findViewById(R.id.rl_dialog_add_text_richlayout);

    }


    @Override
    public void onClick(View v) {

        if (v == tvDialogAddTextDone) {
            addTextListener.SetText(rteDialogAddText.getHtml());
            dismiss();
        } else if (v == tvDialogAddTextClose) {
            dismiss();
        }

    }


    String Text = "";

    @Override
    public void onTextChange(String text) {
        Text = text;
    }


    @Override
    public void imagePicker() {
        selectMediaListener.ImagePicker();
    }

    @Override
    public void videoPicker() {
        selectMediaListener.VideoPicker();
    }

    @Override
    public void openFormulaDialog() {
        formula = new Formula(mContext);
        formula.setFormulaListener((Formula.FormulaListener) this);
        layoutRich.addView(formula);
        formula.gridAdaptor.setInsertSymbolListener((GridAdaptor.InsertSymbolListener) this);
    }

    @Override
    public void close() {
        layoutRich.removeView(formula);
        formula = null;
    }


    /**
     * insert symbol to rich text editor coming from {@link Formula}
     *
     * @param symbol - specific symbol of formula.
     */

    @Override
    public void insertSymbol(String symbol) {
        rteDialogAddText.addSymbols(symbol);
    }

    public void insertImage(String imagePath) {
        rteDialogAddText.insertImage(imagePath);
    }

    public void insertVideo(String videoPath) {
        rteDialogAddText.insertVideo(videoPath);
    }


    public interface SelectMediaListener {
        public void ImagePicker();

        public void VideoPicker();

    }

    public interface AddTextListener {
        public void SetText(String text);
    }
}
