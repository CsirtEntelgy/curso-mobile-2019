package com.facebook.internal;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import com.facebook.android.R;

public class LikeButton extends Button {
    private boolean a;

    public LikeButton(Context context, boolean z) {
        super(context);
        this.a = z;
        a();
    }

    public void setLikeState(boolean z) {
        if (z != this.a) {
            this.a = z;
            b();
        }
    }

    private void a() {
        setGravity(16);
        setTextColor(getResources().getColor(R.color.com_facebook_likebutton_text_color));
        setTextSize(0, getResources().getDimension(R.dimen.com_facebook_likebutton_text_size));
        setTypeface(Typeface.DEFAULT_BOLD);
        setCompoundDrawablePadding(getResources().getDimensionPixelSize(R.dimen.com_facebook_likebutton_compound_drawable_padding));
        setPadding(getResources().getDimensionPixelSize(R.dimen.com_facebook_likebutton_padding_left), getResources().getDimensionPixelSize(R.dimen.com_facebook_likebutton_padding_top), getResources().getDimensionPixelSize(R.dimen.com_facebook_likebutton_padding_right), getResources().getDimensionPixelSize(R.dimen.com_facebook_likebutton_padding_bottom));
        b();
    }

    private void b() {
        if (this.a) {
            setBackgroundResource(R.drawable.com_facebook_button_like_selected);
            setCompoundDrawablesWithIntrinsicBounds(R.drawable.com_facebook_button_like_icon_selected, 0, 0, 0);
            setText(getResources().getString(R.string.com_facebook_like_button_liked));
            return;
        }
        setBackgroundResource(R.drawable.com_facebook_button_like);
        setCompoundDrawablesWithIntrinsicBounds(R.drawable.com_facebook_button_like_icon, 0, 0, 0);
        setText(getResources().getString(R.string.com_facebook_like_button_not_liked));
    }
}
