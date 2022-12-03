package com.despair.corp.monokouma.mafuckingreufinal.data.model;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.despair.corp.monokouma.mafuckingreufinal.R;

public enum Room {

    Paris(R.string.paris, R.drawable.paris_drawable, R.color.paris_color),
    Tokyo(R.string.tokyo, R.drawable.tokyo_drawable, R.color.tokyo_color),
    New_York(R.string.new_york, R.drawable.new_york_drawable, R.color.new_york_color),
    Pekin(R.string.pekin, R.drawable.pekin_drawable, R.color.pekin_color),
    Singapour(R.string.singapor, R.drawable.singapor_drawable, R.color.singapor_color),
    Chicago(R.string.chicago, R.drawable.chicago_drawable, R.color.chicago_color),
    Berlin(R.string.berlin, R.drawable.berlin_drawable, R.color.berlin_color),
    Moscou(R.string.moscow, R.drawable.moscow_drawable, R.color.moscow_color),
    Sydney(R.string.sydney, R.drawable.sydney_drawable, R.color.sydney_color),
    Rio_De_Janeiro(R.string.rio_de_janeiro, R.drawable.rio_drawable, R.color.rio_color);


    @StringRes
    private final int name;

    @DrawableRes
    private final int iconRes;

    @ColorRes
    private final int colorRes;

    Room(@StringRes int name,
         @DrawableRes int iconRes,
         int colorRes) {
        this.name = name;
        this.iconRes = iconRes;
        this.colorRes = colorRes;
    }

    @StringRes
    public int getStringResName() {
        return name;
    }

    @DrawableRes
    public int getDrawableResIcon() {
        return iconRes;
    }

    @ColorRes
    public int getColorRes() {
        return colorRes;
    }
}
