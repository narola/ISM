package com.ism.scientificcalc.view;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ism.scientificcalc.R;

import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by c161 on 02/09/15.
 */
public class Calc extends LinearLayout {

    private static final String TAG = Calc.class.getSimpleName();

    private TextView mTextDisplayOne;
    private TextView mTextDisplayTwo;
    private HorizontalScrollView mHScrollDisplayOne;
    private ImageButton mButton0;
    private ImageButton mButton1;
    private ImageButton mButton2;
    private ImageButton mButton3;
    private ImageButton mButton4;
    private ImageButton mButton5;
    private ImageButton mButton6;
    private ImageButton mButton7;
    private ImageButton mButton8;
    private ImageButton mButton9;
    private ImageButton mButtonDelete;
    private ImageButton mButtonPercentage;
    private ImageButton mButtonDivision;
    private ImageButton mButtonMultiply;
    private ImageButton mButtonSubstract;
    private ImageButton mButtonAddition;
    private ImageButton mButtonDecimal;
    private ImageButton mButtonEquals;
    private Button mButtonScientificHeader;
    private ImageButton mButtonBracketOpen;
    private ImageButton mButtonBracketClose;
    private ImageButton mButtonLn;
    private ImageButton mButtonLog;
    private ImageButton mButtonTenRaiseX;
    private ImageButton mButtonXInverse;
    private ImageButton mButtonXSquare;
    private ImageButton mButtonXQube;
    private ImageButton mButtonXRaiseY;
    private ImageButton mButtonERaiseX;
    private ImageButton mButtonXFact;
    private ImageButton mButtonSquareRoot;
    private ImageButton mButtonQubeRoot;
    private ImageButton mButtonXRoot;
    private ImageButton mButtonE;
    private ImageButton mButtonSin;
    private ImageButton mButtonCos;
    private ImageButton mButtonTan;
    private ImageButton mButtonPi;
    private ImageButton mButtonSinh;
    private ImageButton mButtonCosh;
    private ImageButton mButtonTanh;
    private ViewStub mStubScientific;

//    private DecimalFormat decimalFormat = new DecimalFormat("@###########");
    private DecimalFormat decimalFormat = new DecimalFormat("#");
    private Context mContext;
    private OnClickListener mNumberClickListener;
    private OnClickListener mOperatorClickListener;
    private OnClickListener mSciFunClickListener;
    private Symbols symbols;

//    private static final String DIGITS = "0123456789";
    private static String OPERATORS_ALL;
    private static String OPERATORS_BASIC;
    private static String ADD;
    private static String SUBTRACT;
    private static String MULTIPLY;
    private static String DIVIDE;
    private static String PERCENTAGE;
    private static String DELETE;
    private static String DECIMAL;
    private static String ZERO;
    private static String EQUALS;
    private static String CARET;
    private static String PI;
    private static String BRACKET_OPEN;
    private static String BRACKET_CLOSE;
//    private static String FACTORIAL;
    private static String E;
//    private static String ROOT;
//    private boolean mIsScientificEnabled;
//    private boolean mIsFormulaSelectable;
//    private boolean mIsAnswerSelectable;
    private boolean mStubVisible = false;
    private boolean mIsCalculationFinished = false;
    private String mDisplayText = "";
    private String mAnswer = "";

    public Calc(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        /*TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Calc);

        try {
            mIsScientificEnabled = typedArray.getBoolean(R.styleable.Calc_setScientificEnabled, true);
            mIsFormulaSelectable = typedArray.getBoolean(R.styleable.Calc_setFormulaSelectable, true);
            mIsAnswerSelectable = typedArray.getBoolean(R.styleable.Calc_setAnswerSelectable, true);
        } finally {
            typedArray.recycle();
        }*/

        setOrientation(LinearLayout.VERTICAL);
        setWeightSum(8);
        setBackgroundResource(R.drawable.bg_shape_display);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_calc, this);

        decimalFormat.setMinimumFractionDigits(0);
        decimalFormat.setMaximumFractionDigits(30);
        decimalFormat.setMinimumIntegerDigits(1);
        decimalFormat.setMaximumIntegerDigits(30);

        symbols = new Symbols();

        ADD = getResources().getString(R.string.tag_addition);
        SUBTRACT = getResources().getString(R.string.tag_substract);
        MULTIPLY = getResources().getString(R.string.tag_multiply);
        DIVIDE = getResources().getString(R.string.tag_division);
        PERCENTAGE = getResources().getString(R.string.tag_percentage);
        DELETE = getResources().getString(R.string.tag_del);
        DECIMAL = getResources().getString(R.string.tag_decimal);
        EQUALS = getResources().getString(R.string.tag_equals);
        ZERO = getResources().getString(R.string.tag_zero);
        CARET = getResources().getString(R.string.tag_raise_y);
        PI = getResources().getString(R.string.tag_pi);
        BRACKET_OPEN = getResources().getString(R.string.tag_bracket_open);
        BRACKET_CLOSE = getResources().getString(R.string.tag_bracket_close);
//        FACTORIAL = getResources().getString(R.string.tag_fact);
        E = getResources().getString(R.string.tag_e);
//        ROOT = getResources().getString(R.string.tag_square_root);
        OPERATORS_BASIC = SUBTRACT + ADD + MULTIPLY + DIVIDE;
        OPERATORS_ALL = OPERATORS_BASIC + PERCENTAGE + CARET;

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTextDisplayOne = (TextView) findViewById(R.id.text_display_one);
        mTextDisplayTwo = (TextView) findViewById(R.id.text_display_two);
        mHScrollDisplayOne = (HorizontalScrollView) findViewById(R.id.hscroll_display_one);
        mButton0 = (ImageButton) findViewById(R.id.image_button_0);
        mButton1 = (ImageButton) findViewById(R.id.image_button_1);
        mButton2 = (ImageButton) findViewById(R.id.image_button_2);
        mButton3 = (ImageButton) findViewById(R.id.image_button_3);
        mButton4 = (ImageButton) findViewById(R.id.image_button_4);
        mButton5 = (ImageButton) findViewById(R.id.image_button_5);
        mButton6 = (ImageButton) findViewById(R.id.image_button_6);
        mButton7 = (ImageButton) findViewById(R.id.image_button_7);
        mButton8 = (ImageButton) findViewById(R.id.image_button_8);
        mButton9 = (ImageButton) findViewById(R.id.image_button_9);
        mButtonDelete = (ImageButton) findViewById(R.id.image_button_delete);
        mButtonPercentage = (ImageButton) findViewById(R.id.image_button_percentage);
        mButtonDivision = (ImageButton) findViewById(R.id.image_button_division);
        mButtonMultiply = (ImageButton) findViewById(R.id.image_button_multiply);
        mButtonSubstract = (ImageButton) findViewById(R.id.image_button_substract);
        mButtonAddition = (ImageButton) findViewById(R.id.image_button_addition);
        mButtonDecimal = (ImageButton) findViewById(R.id.image_button_decimal);
        mButtonEquals = (ImageButton) findViewById(R.id.image_button_equals);
        mButtonScientificHeader = (Button) findViewById(R.id.button_sci_header);

        mStubScientific = (ViewStub) findViewById(R.id.stub_sci_calc);

        mNumberClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                onNumberClicked(v);
            }
        };

        mOperatorClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                onOperatorClicked(v);
            }
        };

        mButton0.setOnClickListener(mNumberClickListener);
        mButton1.setOnClickListener(mNumberClickListener);
        mButton2.setOnClickListener(mNumberClickListener);
        mButton3.setOnClickListener(mNumberClickListener);
        mButton4.setOnClickListener(mNumberClickListener);
        mButton5.setOnClickListener(mNumberClickListener);
        mButton6.setOnClickListener(mNumberClickListener);
        mButton7.setOnClickListener(mNumberClickListener);
        mButton8.setOnClickListener(mNumberClickListener);
        mButton9.setOnClickListener(mNumberClickListener);
        mButtonDecimal.setOnClickListener(mNumberClickListener);
        mButtonDelete.setOnClickListener(mOperatorClickListener);
        mButtonPercentage.setOnClickListener(mOperatorClickListener);
        mButtonDivision.setOnClickListener(mOperatorClickListener);
        mButtonMultiply.setOnClickListener(mOperatorClickListener);
        mButtonSubstract.setOnClickListener(mOperatorClickListener);
        mButtonAddition.setOnClickListener(mOperatorClickListener);
        mButtonEquals.setOnClickListener(mOperatorClickListener);

        mButtonScientificHeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStubVisible) {
                    mStubScientific.setVisibility(GONE);
                } else {
                    mStubScientific.setVisibility(VISIBLE);
                    if (mSciFunClickListener == null) {
                        initScientific();
                    }
                }
                mStubVisible = !mStubVisible;
            }
        });

        mButtonDelete.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clearCalc();
                return false;
            }
        });

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.END;
            mTextDisplayOne.setLayoutParams(layoutParams);
            mTextDisplayTwo.setLayoutParams(layoutParams);
        }
//        mTextDisplayOne.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getInteger(R.integer.display_text_size_large));
//        mTextDisplayTwo.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getInteger(R.integer.display_sub_text_size));


        mTextDisplayOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mIsCalculationFinished) {
                    mTextDisplayTwo.requestFocus();
                    mHScrollDisplayOne.post(new Runnable() {
                        @Override
                        public void run() {
                            mHScrollDisplayOne.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                        }
                    });
                }
                mDisplayText = s.toString();
                /*if (mDisplayText.equals("")) {
                    mTextDisplayOne.setText(ZERO);
                }*/
                /*if (mDisplayText.length() > 17) {

                    mTextDisplayOne.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getInteger(R.integer.display_text_size_small));

                } else if (mDisplayText.length() > 13) {

                    mTextDisplayOne.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getInteger(R.integer.display_text_size_medium));

                } else {

                    mTextDisplayOne.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getInteger(R.integer.display_text_size_large));

                }*/
            }
        });

    }

    private void initScientific() {
        mButtonBracketOpen = (ImageButton) findViewById(R.id.image_button_bracket_open);
        mButtonBracketClose = (ImageButton) findViewById(R.id.image_button_bracket_close);
        mButtonLn = (ImageButton) findViewById(R.id.image_button_ln);
        mButtonLog = (ImageButton) findViewById(R.id.image_button_log);
        mButtonTenRaiseX = (ImageButton) findViewById(R.id.image_button_ten_raise_x);
        mButtonXInverse = (ImageButton) findViewById(R.id.image_button_x_inverse);
        mButtonXSquare = (ImageButton) findViewById(R.id.image_button_square);
        mButtonXQube = (ImageButton) findViewById(R.id.image_button_qube);
        mButtonXRaiseY = (ImageButton) findViewById(R.id.image_button_raise_y);
        mButtonERaiseX = (ImageButton) findViewById(R.id.image_button_e_raise_x);
        mButtonXFact = (ImageButton) findViewById(R.id.image_button_x_fact);
        mButtonSquareRoot = (ImageButton) findViewById(R.id.image_button_square_root);
        mButtonQubeRoot = (ImageButton) findViewById(R.id.image_button_qube_root);
        mButtonXRoot = (ImageButton) findViewById(R.id.image_button_x_root);
        mButtonE = (ImageButton) findViewById(R.id.image_button_e);
        mButtonSin = (ImageButton) findViewById(R.id.image_button_sin);
        mButtonCos = (ImageButton) findViewById(R.id.image_button_cos);
        mButtonTan = (ImageButton) findViewById(R.id.image_button_tan);
        mButtonPi = (ImageButton) findViewById(R.id.image_button_pi);
        mButtonSinh = (ImageButton) findViewById(R.id.image_button_sinh);
        mButtonCosh = (ImageButton) findViewById(R.id.image_button_cosh);
        mButtonTanh = (ImageButton) findViewById(R.id.image_button_tanh);

        mSciFunClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                onScientificClick(v);
            }
        };

        mButtonBracketOpen.setOnClickListener(mSciFunClickListener);
        mButtonBracketClose.setOnClickListener(mSciFunClickListener);
        mButtonLn.setOnClickListener(mSciFunClickListener);
        mButtonLog.setOnClickListener(mSciFunClickListener);
        mButtonTenRaiseX.setOnClickListener(mSciFunClickListener);
        mButtonXInverse.setOnClickListener(mSciFunClickListener);
        mButtonXSquare.setOnClickListener(mSciFunClickListener);
        mButtonXQube.setOnClickListener(mSciFunClickListener);
        mButtonXRaiseY.setOnClickListener(mSciFunClickListener);
        mButtonERaiseX.setOnClickListener(mSciFunClickListener);
        mButtonXFact.setOnClickListener(mSciFunClickListener);
        mButtonSquareRoot.setOnClickListener(mSciFunClickListener);
        mButtonQubeRoot.setOnClickListener(mSciFunClickListener);
        mButtonXRoot.setOnClickListener(mSciFunClickListener);
        mButtonE.setOnClickListener(mSciFunClickListener);
        mButtonSin.setOnClickListener(mSciFunClickListener);
        mButtonCos.setOnClickListener(mSciFunClickListener);
        mButtonTan.setOnClickListener(mSciFunClickListener);
        mButtonPi.setOnClickListener(mSciFunClickListener);
        mButtonSinh.setOnClickListener(mSciFunClickListener);
        mButtonCosh.setOnClickListener(mSciFunClickListener);
        mButtonTanh.setOnClickListener(mSciFunClickListener);
    }

    private void clearCalc() {
//        mTextDisplayOne.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getInteger(R.integer.display_text_size_large));
        mTextDisplayOne.setText("");
        mTextDisplayTwo.setText("");
        mIsCalculationFinished = false;
    }

    private void onNumberClicked(View view) {
        try {
            if (mIsCalculationFinished) {
                clearCalc();
            }
            String buttonText = ((ImageButton)view).getTag().toString();
            if (!(DECIMAL + ZERO).contains(buttonText) || (buttonText.equals(DECIMAL) && !checkDecimalPoint()) || (buttonText.equals(ZERO) && !checkZero())) {
                mTextDisplayOne.append(buttonText);
                calculate(false);
            }
        } catch (Exception e) {
            Log.e(TAG, "onNumberClicked Exception : " + e.toString());
        }
    }

    private boolean checkZero() {
        if (mDisplayText != null && mDisplayText.length() > 0) {
            String operands[] = mDisplayText.split("[" + OPERATORS_ALL + BRACKET_OPEN + BRACKET_CLOSE + "]");
            return operands[operands.length-1].equals(ZERO);
        } else {
            return false;
        }
    }

    private boolean checkDecimalPoint() {
        String function = mTextDisplayOne.getText().toString();
        if (function != null && function.length() > 0 && !(OPERATORS_ALL + BRACKET_OPEN + BRACKET_CLOSE).contains(function.charAt(function.length() - 1) + "")) {
            String operands[] = function.split("[" + OPERATORS_ALL + BRACKET_OPEN + BRACKET_CLOSE + "]");
            return operands[operands.length-1].contains(DECIMAL);
        } else {
            return false;
        }
    }

    private void onOperatorClicked(View view) {
        try {
            String buttonText = ((ImageButton)view).getTag().toString();
            if (buttonText.equals(EQUALS)) {
                mIsCalculationFinished = true;
                calculate(true);
            } else if (buttonText.equalsIgnoreCase(DELETE) && mDisplayText.length() > 0) {
                mIsCalculationFinished = false;
                mTextDisplayOne.setText(mDisplayText.substring(0, mDisplayText.length() - 1) + "");
                if (mDisplayText.length() > 0) {
                    calculate(false);
                } else {
                    mTextDisplayTwo.setText("");
                }
            } else {
                mIsCalculationFinished = false;
                if ((mDisplayText.length() == 0 && buttonText.equals("-"))) {
                    mTextDisplayOne.append(buttonText);
                } else if (mDisplayText.length() > 0) {

                    String lastChar = mDisplayText.charAt(mDisplayText.length() - 1) + "";

                    if (OPERATORS_BASIC.contains(lastChar) && mDisplayText.length() > 1) {

                        String secondLastOperator = mDisplayText.charAt(mDisplayText.length() - 2) + "";

                        if (!OPERATORS_BASIC.contains(secondLastOperator)) {
                            if (buttonText.equals(SUBTRACT) && !lastChar.equals(ADD)) {
                                mTextDisplayOne.append(buttonText);
                            } else {
                                /**
                                 * Change operator
                                 */
                                mTextDisplayOne.setText(mDisplayText.substring(0, mDisplayText.length() - 1) + buttonText);
                            }
                        } else {
                            /**
                             * Change last two operator
                             */
                            mTextDisplayOne.setText(mDisplayText.substring(0, mDisplayText.length() - 2) + buttonText);
                        }

                    } else if (!OPERATORS_BASIC.contains(lastChar)) {
                        /**
                         * Adding operator, need to calculate here previous inputs
                         */
                        mTextDisplayOne.append(buttonText);
                        calculate(false);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "onOperatorClicked Exception : " + e.toString());
        }
    }

    private void onScientificClick(View view) {
        try {
            String buttonText = ((ImageButton) view).getTag().toString();
            if (buttonText.equals(PI) || buttonText.equals(E)
                    || buttonText.equals(BRACKET_OPEN) || buttonText.equals(BRACKET_CLOSE)) {
                mIsCalculationFinished = false;
                mTextDisplayOne.append(buttonText);
            }
            String result = calculate(false);
            if (result != null && result.length() > 0) {
                if (buttonText.equals(getResources().getString(R.string.tag_ln))
                        || buttonText.equals(getResources().getString(R.string.tag_log))
                        || buttonText.equals(getResources().getString(R.string.tag_ten_raise_x))
                        || buttonText.equals(getResources().getString(R.string.tag_inverse))
                        || buttonText.equals(getResources().getString(R.string.tag_e_raise_x))
                        || buttonText.equals(getResources().getString(R.string.tag_square_root))
                        || buttonText.equals(getResources().getString(R.string.tag_cube_root))
                        || buttonText.equals(getResources().getString(R.string.tag_sinh))
                        || buttonText.equals(getResources().getString(R.string.tag_cosh))
                        || buttonText.equals(getResources().getString(R.string.tag_tanh))) {

                    mIsCalculationFinished = true;
                    result = formatAnswer(symbols.eval(buttonText + result));
                    mTextDisplayOne.setText(result);
                    mTextDisplayTwo.setText("");
                } else if (buttonText.equals(getResources().getString(R.string.tag_sin))
                        || buttonText.equals(getResources().getString(R.string.tag_cos))
                        || buttonText.equals(getResources().getString(R.string.tag_tan))) {

                    mIsCalculationFinished = true;
                    if (buttonText.equals(getResources().getString(R.string.tag_tan))
                            && isMultipleOf90Degree(result)) {
                        result = getResources().getString(R.string.nan);
                    } else {
                        result = formatAnswer(symbols.eval(buttonText + result + DIVIDE + "180" + MULTIPLY + PI));
                    }
                    mTextDisplayOne.setText(result);
                    mTextDisplayTwo.setText("");
                } else if (buttonText.equals(getResources().getString(R.string.tag_square))
                        || buttonText.equals(getResources().getString(R.string.tag_cube))
                        || buttonText.equals(getResources().getString(R.string.tag_fact))) {

                    mIsCalculationFinished = true;
                    result = formatAnswer(symbols.eval(result + buttonText));
                    mTextDisplayOne.setText(result);
                    mTextDisplayTwo.setText("");
                } else if (buttonText.equals(getResources().getString(R.string.tag_raise_y))
                        || buttonText.equals(getResources().getString(R.string.tag_x_root))) {

                    mIsCalculationFinished = false;
                    mTextDisplayOne.setText(result + buttonText);
                    mTextDisplayTwo.setText("");
                }
            }
        } catch (SyntaxException e) {
            Log.e(TAG, "onScientificClick aritySyntaxException : " + e.toString());
            mIsCalculationFinished = true;
            mTextDisplayOne.setText(getResources().getString(R.string.error));
        } catch (Exception e) {
            mIsCalculationFinished = true;
            Log.e(TAG, "onScientificClick Exception : " + e.toString());
        }
    }

    private boolean isMultipleOf90Degree(String degree) {
        double value = Double.valueOf(degree);
        if (value == 0) {
            return false;
        } else if (value == 90) {
            return true;
        } else {
            return (value % ((value -= 90) / 360)) == 0;
        }
    }

    /**
     * Perform calculation
     * Call on click of equals or operator
     */
    private String calculate(boolean onEqualOperator) {
        try {
            String expression = mTextDisplayOne.getText().toString();
            if (expression.length() > 0) {

                String lastChar = expression.charAt(expression.length() - 1) + "";

                if (!lastChar.equals(PERCENTAGE) && (OPERATORS_ALL).contains(lastChar)) {
                    expression = expression.substring(0, expression.length() - 1);
                }

                if (onEqualOperator || expression.matches(".*[" + OPERATORS_ALL + BRACKET_OPEN + BRACKET_CLOSE + E + PI + "].*")) {
//                    || expression.matches(".*[" + OPERATORS + BRACKET_OPEN + BRACKET_CLOSE + CARET + FACTORIAL + ROOT + E + PI + "].*")) {
                    mAnswer = formatAnswer(symbols.eval(expression));
                    if (onEqualOperator) {
//                        mTextDisplayOne.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getInteger(R.integer.display_text_size_large));
                        mTextDisplayOne.setText(mAnswer);
                        mTextDisplayTwo.setText("");
                    } else {
                        mTextDisplayTwo.setText(mAnswer);
                    }
                    return mAnswer;
                } else {
                    mTextDisplayTwo.setText("");
                    return expression;
                }
            } else {
                return "";
            }
        } catch (SyntaxException e) {
            Log.e(TAG, "arity SyntaxException : " + e.toString());
            mTextDisplayTwo.setText(onEqualOperator ? getResources().getString(R.string.error) : "");
            return getResources().getString(R.string.error);
        }
    }

    /*public boolean isScientificEnabled() {
        return mIsScientificEnabled;
    }

    public void setScientificEnabled(boolean isScientificEnabled) {
        this.mIsScientificEnabled = isScientificEnabled;
    }

    public boolean isIsFormulaSelectable() {
        return mIsFormulaSelectable;
    }

    public void setIsFormulaSelectable(boolean isFormulaSelectable) {
        this.mIsFormulaSelectable = isFormulaSelectable;
    }

    public boolean isIsAnswerSelectable() {
        return mIsAnswerSelectable;
    }

    public void setAnswerSelectable(boolean isAnswerSelectable) {
        this.mIsAnswerSelectable = isAnswerSelectable;
    }*/

    public void setOnDisplayLongClickListener(final OnDisplayLongClickListener listener) {
        mTextDisplayOne.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onDisplayLongClick(v, mTextDisplayOne.getText().toString());
                return false;
            }
        });
    }

    public static interface OnDisplayLongClickListener {
        void onDisplayLongClick(View view, String displayText);
    }

    private String formatAnswer(double value) {
        try {
            if (Double.isInfinite(value) || value <= 999999999999.0) {
                return decimalFormat.format(value);
            } else if (Double.isNaN(value)) {
                return getResources().getString(R.string.nan);
            } else {
                String formattedString = new BigDecimal(value).toPlainString();
//                Log.e(TAG, "formattedString : " + formattedString);
                if (value > 9000000000000000.0) {
                    return value + "";
                }
                return formattedString;
            }
        } catch (Exception e) {
            Log.e(TAG, "formatAnswer Exception : " + e.toString());
            return getResources().getString(R.string.error);
        }
    }

}
