package android.support.v7.view.menu;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuBuilder.ItemInvoker;
import android.support.v7.view.menu.MenuView.ItemView;
import android.support.v7.widget.ActionMenuView.ActionMenuChildView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.ListPopupWindow.ForwardingListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Toast;

public class ActionMenuItemView extends AppCompatTextView implements ItemView, OnClickListener, OnLongClickListener, ActionMenuChildView {
    private static final int MAX_ICON_SIZE = 32;
    private static final String TAG = "ActionMenuItemView";
    private boolean mAllowTextWithIcon;
    private boolean mExpandedFormat;
    private ForwardingListener mForwardingListener;
    private Drawable mIcon;
    /* access modifiers changed from: private */
    public MenuItemImpl mItemData;
    /* access modifiers changed from: private */
    public ItemInvoker mItemInvoker;
    private int mMaxIconSize;
    private int mMinWidth;
    /* access modifiers changed from: private */
    public PopupCallback mPopupCallback;
    private int mSavedPaddingLeft;
    private CharSequence mTitle;

    private class ActionMenuItemForwardingListener extends ForwardingListener {
        public ActionMenuItemForwardingListener() {
            super(ActionMenuItemView.this);
        }

        public ListPopupWindow getPopup() {
            if (ActionMenuItemView.this.mPopupCallback != null) {
                return ActionMenuItemView.this.mPopupCallback.getPopup();
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public boolean onForwardingStarted() {
            if (ActionMenuItemView.this.mItemInvoker == null || !ActionMenuItemView.this.mItemInvoker.invokeItem(ActionMenuItemView.this.mItemData)) {
                return false;
            }
            ListPopupWindow popup = getPopup();
            if (popup == null || !popup.isShowing()) {
                return false;
            }
            return true;
        }
    }

    public static abstract class PopupCallback {
        public abstract ListPopupWindow getPopup();
    }

    public ActionMenuItemView(Context context) {
        this(context, null);
    }

    public ActionMenuItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionMenuItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Resources res = context.getResources();
        this.mAllowTextWithIcon = res.getBoolean(R.bool.abc_config_allowActionMenuItemTextWithIcon);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ActionMenuItemView, defStyle, 0);
        this.mMinWidth = a.getDimensionPixelSize(R.styleable.ActionMenuItemView_android_minWidth, 0);
        a.recycle();
        this.mMaxIconSize = (int) ((32.0f * res.getDisplayMetrics().density) + 0.5f);
        setOnClickListener(this);
        setOnLongClickListener(this);
        this.mSavedPaddingLeft = -1;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (VERSION.SDK_INT >= 8) {
            super.onConfigurationChanged(newConfig);
        }
        this.mAllowTextWithIcon = getContext().getResources().getBoolean(R.bool.abc_config_allowActionMenuItemTextWithIcon);
        updateTextButtonVisibility();
    }

    public void setPadding(int l, int t, int r, int b) {
        this.mSavedPaddingLeft = l;
        super.setPadding(l, t, r, b);
    }

    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    public void initialize(MenuItemImpl itemData, int menuType) {
        this.mItemData = itemData;
        setIcon(itemData.getIcon());
        setTitle(itemData.getTitleForItemView(this));
        setId(itemData.getItemId());
        setVisibility(itemData.isVisible() ? 0 : 8);
        setEnabled(itemData.isEnabled());
        if (itemData.hasSubMenu() && this.mForwardingListener == null) {
            this.mForwardingListener = new ActionMenuItemForwardingListener();
        }
    }

    public boolean onTouchEvent(MotionEvent e) {
        if (!this.mItemData.hasSubMenu() || this.mForwardingListener == null || !this.mForwardingListener.onTouch(this, e)) {
            return super.onTouchEvent(e);
        }
        return true;
    }

    public void onClick(View v) {
        if (this.mItemInvoker != null) {
            this.mItemInvoker.invokeItem(this.mItemData);
        }
    }

    public void setItemInvoker(ItemInvoker invoker) {
        this.mItemInvoker = invoker;
    }

    public void setPopupCallback(PopupCallback popupCallback) {
        this.mPopupCallback = popupCallback;
    }

    public boolean prefersCondensedTitle() {
        return true;
    }

    public void setCheckable(boolean checkable) {
    }

    public void setChecked(boolean checked) {
    }

    public void setExpandedFormat(boolean expandedFormat) {
        if (this.mExpandedFormat != expandedFormat) {
            this.mExpandedFormat = expandedFormat;
            if (this.mItemData != null) {
                this.mItemData.actionFormatChanged();
            }
        }
    }

    private void updateTextButtonVisibility() {
        boolean visible;
        boolean z = false;
        if (!TextUtils.isEmpty(this.mTitle)) {
            visible = true;
        } else {
            visible = false;
        }
        if (this.mIcon == null || (this.mItemData.showsTextAsAction() && (this.mAllowTextWithIcon || this.mExpandedFormat))) {
            z = true;
        }
        setText(visible & z ? this.mTitle : null);
    }

    public void setIcon(Drawable icon) {
        this.mIcon = icon;
        if (icon != null) {
            int width = icon.getIntrinsicWidth();
            int height = icon.getIntrinsicHeight();
            if (width > this.mMaxIconSize) {
                float scale = ((float) this.mMaxIconSize) / ((float) width);
                width = this.mMaxIconSize;
                height = (int) (((float) height) * scale);
            }
            if (height > this.mMaxIconSize) {
                float scale2 = ((float) this.mMaxIconSize) / ((float) height);
                height = this.mMaxIconSize;
                width = (int) (((float) width) * scale2);
            }
            icon.setBounds(0, 0, width, height);
        }
        setCompoundDrawables(icon, null, null, null);
        updateTextButtonVisibility();
    }

    public boolean hasText() {
        return !TextUtils.isEmpty(getText());
    }

    public void setShortcut(boolean showShortcut, char shortcutKey) {
    }

    public void setTitle(CharSequence title) {
        this.mTitle = title;
        setContentDescription(this.mTitle);
        updateTextButtonVisibility();
    }

    public boolean showsIcon() {
        return true;
    }

    public boolean needsDividerBefore() {
        return hasText() && this.mItemData.getIcon() == null;
    }

    public boolean needsDividerAfter() {
        return hasText();
    }

    public boolean onLongClick(View v) {
        if (hasText()) {
            return false;
        }
        int[] screenPos = new int[2];
        Rect displayFrame = new Rect();
        getLocationOnScreen(screenPos);
        getWindowVisibleDisplayFrame(displayFrame);
        Context context = getContext();
        int width = getWidth();
        int height = getHeight();
        int midy = screenPos[1] + (height / 2);
        int referenceX = screenPos[0] + (width / 2);
        if (ViewCompat.getLayoutDirection(v) == 0) {
            referenceX = context.getResources().getDisplayMetrics().widthPixels - referenceX;
        }
        Toast cheatSheet = Toast.makeText(context, this.mItemData.getTitle(), 0);
        if (midy < displayFrame.height()) {
            cheatSheet.setGravity(8388661, referenceX, (screenPos[1] + height) - displayFrame.top);
        } else {
            cheatSheet.setGravity(81, 0, height);
        }
        cheatSheet.show();
        return true;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        boolean textVisible = hasText();
        if (textVisible && this.mSavedPaddingLeft >= 0) {
            super.setPadding(this.mSavedPaddingLeft, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int oldMeasuredWidth = getMeasuredWidth();
        int targetWidth = widthMode == Integer.MIN_VALUE ? Math.min(widthSize, this.mMinWidth) : this.mMinWidth;
        if (widthMode != 1073741824 && this.mMinWidth > 0 && oldMeasuredWidth < targetWidth) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(targetWidth, 1073741824), heightMeasureSpec);
        }
        if (!textVisible && this.mIcon != null) {
            super.setPadding((getMeasuredWidth() - this.mIcon.getBounds().width()) / 2, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
    }
}
