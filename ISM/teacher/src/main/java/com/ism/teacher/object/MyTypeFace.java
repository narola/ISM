package com.ism.teacher.object;

import android.content.Context;
import android.graphics.Typeface;

import com.ism.teacher.constants.AppConstant;


/**
 * Created by c162 on 09/10/15.
 */
public class MyTypeFace {
    Typeface RalewayBlack, RalewayBlackItalic, RalewayBold, RalewayBoldItalic,
            RalewayExtraBold, RalewayExtraBoldItalic, RalewayExtraLight, RalewayExtraLightItalic,
            RalewayItalic, RalewayLight, RalewayLightItalic, RalewayMedium, RalewayMediumItalic, RalewayRegular,
            RalewaySemiBold, RalewaySemiBoldItalic, RalewayThin, RalewayThinItalic;
    Context context;

    /**
     * Set Custom Fonts
     **/
    public MyTypeFace(Context context) {
        this.context = context;
        RalewayBlack = setCustomFont(AppConstant.RalewayBlack);
        RalewayBlackItalic = setCustomFont(AppConstant.RalewayBlackItalic);
        RalewayBold = setCustomFont(AppConstant.RalewayBold);
        RalewayBoldItalic = setCustomFont(AppConstant.RalewayBoldItalic);
        RalewayExtraBold = setCustomFont(AppConstant.RalewayExtraBold);
        RalewayExtraBoldItalic = setCustomFont(AppConstant.RalewayExtraBoldItalic);
        RalewayExtraLight = setCustomFont(AppConstant.RalewayExtraLight);
        RalewayExtraLightItalic = setCustomFont(AppConstant.RalewayExtraLightItalic);
        RalewayItalic = setCustomFont(AppConstant.RalewayItalic);
        RalewayLight = setCustomFont(AppConstant.RalewayLight);
        RalewayLightItalic = setCustomFont(AppConstant.RalewayLightItalic);
        RalewayMedium = setCustomFont(AppConstant.RalewayMedium);
        RalewayMediumItalic = setCustomFont(AppConstant.RalewayMediumItalic);
        RalewayRegular = setCustomFont(AppConstant.RalewayRegular);
        RalewaySemiBold = setCustomFont(AppConstant.RalewaySemiBold);
        RalewaySemiBoldItalic = setCustomFont(AppConstant.RalewaySemiBoldItalic);
        RalewayThin = setCustomFont(AppConstant.RalewayThin);
        RalewayThinItalic = setCustomFont(AppConstant.RalewayThinItalic);

    }

    public Typeface setCustomFont(String fontType) {

        Typeface customFont = Typeface.createFromAsset(context
                .getAssets(), fontType);

        return customFont;
    }

    public Typeface getRalewayBlack() {
        return RalewayBlack;
    }

    public void setRalewayBlack(Typeface ralewayBlack) {
        RalewayBlack = ralewayBlack;
    }

    public Typeface getRalewayBlackItalic() {
        return RalewayBlackItalic;
    }

    public void setRalewayBlackItalic(Typeface ralewayBlackItalic) {
        RalewayBlackItalic = ralewayBlackItalic;
    }

    public Typeface getRalewayBold() {
        return RalewayBold;
    }

    public void setRalewayBold(Typeface ralewayBold) {
        RalewayBold = ralewayBold;
    }

    public Typeface getRalewayBoldItalic() {
        return RalewayBoldItalic;
    }

    public void setRalewayBoldItalic(Typeface ralewayBoldItalic) {
        RalewayBoldItalic = ralewayBoldItalic;
    }

    public Typeface getRalewayExtraBold() {
        return RalewayExtraBold;
    }

    public void setRalewayExtraBold(Typeface ralewayExtraBold) {
        RalewayExtraBold = ralewayExtraBold;
    }

    public Typeface getRalewayExtraBoldItalic() {
        return RalewayExtraBoldItalic;
    }

    public void setRalewayExtraBoldItalic(Typeface ralewayExtraBoldItalic) {
        RalewayExtraBoldItalic = ralewayExtraBoldItalic;
    }

    public Typeface getRalewayExtraLight() {
        return RalewayExtraLight;
    }

    public void setRalewayExtraLight(Typeface ralewayExtraLight) {
        RalewayExtraLight = ralewayExtraLight;
    }

    public Typeface getRalewayExtraLightItalic() {
        return RalewayExtraLightItalic;
    }

    public void setRalewayExtraLightItalic(Typeface ralewayExtraLightItalic) {
        RalewayExtraLightItalic = ralewayExtraLightItalic;
    }

    public Typeface getRalewayItalic() {
        return RalewayItalic;
    }

    public void setRalewayItalic(Typeface ralewayItalic) {
        RalewayItalic = ralewayItalic;
    }

    public Typeface getRalewayLight() {
        return RalewayLight;
    }

    public void setRalewayLight(Typeface ralewayLight) {
        RalewayLight = ralewayLight;
    }

    public Typeface getRalewayLightItalic() {
        return RalewayLightItalic;
    }

    public void setRalewayLightItalic(Typeface ralewayLightItalic) {
        RalewayLightItalic = ralewayLightItalic;
    }

    public Typeface getRalewayMedium() {
        return RalewayMedium;
    }

    public void setRalewayMedium(Typeface ralewayMedium) {
        RalewayMedium = ralewayMedium;
    }

    public Typeface getRalewayMediumItalic() {
        return RalewayMediumItalic;
    }

    public void setRalewayMediumItalic(Typeface ralewayMediumItalic) {
        RalewayMediumItalic = ralewayMediumItalic;
    }

    public Typeface getRalewayRegular() {
        return RalewayRegular;
    }

    public void setRalewayRegular(Typeface ralewayRegular) {
        RalewayRegular = ralewayRegular;
    }

    public Typeface getRalewaySemiBold() {
        return RalewaySemiBold;
    }

    public void setRalewaySemiBold(Typeface ralewaySemiBold) {
        RalewaySemiBold = ralewaySemiBold;
    }

    public Typeface getRalewaySemiBoldItalic() {
        return RalewaySemiBoldItalic;
    }

    public void setRalewaySemiBoldItalic(Typeface ralewaySemiBoldItalic) {
        RalewaySemiBoldItalic = ralewaySemiBoldItalic;
    }

    public Typeface getRalewayThin() {
        return RalewayThin;
    }

    public void setRalewayThin(Typeface ralewayThin) {
        RalewayThin = ralewayThin;
    }

    public Typeface getRalewayThinItalic() {
        return RalewayThinItalic;
    }

    public void setRalewayThinItalic(Typeface ralewayThinItalic) {
        RalewayThinItalic = ralewayThinItalic;
    }
}
