package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.ListPopupWindow.ForwardingListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.ThemedSpinnerAdapter;

public class AppCompatSpinner extends Spinner implements TintableBackgroundView {
    private static final int[] ATTRS_ANDROID_SPINNERMODE = {16843505};
    private static final boolean IS_AT_LEAST_JB;
    /* access modifiers changed from: private */
    public static final boolean IS_AT_LEAST_M;
    private static final int MAX_ITEMS_MEASURED = 15;
    private static final int MODE_DIALOG = 0;
    private static final int MODE_DROPDOWN = 1;
    private static final int MODE_THEME = -1;
    private static final String TAG = "AppCompatSpinner";
    private AppCompatBackgroundHelper mBackgroundTintHelper;
    private AppCompatDrawableManager mDrawableManager;
    /* access modifiers changed from: private */
    public int mDropDownWidth;
    private ForwardingListener mForwardingListener;
    /* access modifiers changed from: private */
    public DropdownPopup mPopup;
    private Context mPopupContext;
    private boolean mPopupSet;
    private SpinnerAdapter mTempAdapter;
    /* access modifiers changed from: private */
    public final Rect mTempRect;

    private static class DropDownAdapter implements ListAdapter, SpinnerAdapter {
        private SpinnerAdapter mAdapter;
        private ListAdapter mListAdapter;

        public DropDownAdapter(@Nullable SpinnerAdapter adapter, @Nullable Theme dropDownTheme) {
            this.mAdapter = adapter;
            if (adapter instanceof ListAdapter) {
                this.mListAdapter = (ListAdapter) adapter;
            }
            if (dropDownTheme == null) {
                return;
            }
            if (AppCompatSpinner.IS_AT_LEAST_M && (adapter instanceof ThemedSpinnerAdapter)) {
                ThemedSpinnerAdapter themedAdapter = (ThemedSpinnerAdapter) adapter;
                if (themedAdapter.getDropDownViewTheme() != dropDownTheme) {
                    themedAdapter.setDropDownViewTheme(dropDownTheme);
                }
            } else if (adapter instanceof ThemedSpinnerAdapter) {
                ThemedSpinnerAdapter themedAdapter2 = (ThemedSpinnerAdapter) adapter;
                if (themedAdapter2.getDropDownViewTheme() == null) {
                    themedAdapter2.setDropDownViewTheme(dropDownTheme);
                }
            }
        }

        public int getCount() {
            if (this.mAdapter == null) {
                return 0;
            }
            return this.mAdapter.getCount();
        }

        public Object getItem(int position) {
            if (this.mAdapter == null) {
                return null;
            }
            return this.mAdapter.getItem(position);
        }

        public long getItemId(int position) {
            if (this.mAdapter == null) {
                return -1;
            }
            return this.mAdapter.getItemId(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            return getDropDownView(position, convertView, parent);
        }

        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if (this.mAdapter == null) {
                return null;
            }
            return this.mAdapter.getDropDownView(position, convertView, parent);
        }

        public boolean hasStableIds() {
            return this.mAdapter != null && this.mAdapter.hasStableIds();
        }

        public void registerDataSetObserver(DataSetObserver observer) {
            if (this.mAdapter != null) {
                this.mAdapter.registerDataSetObserver(observer);
            }
        }

        public void unregisterDataSetObserver(DataSetObserver observer) {
            if (this.mAdapter != null) {
                this.mAdapter.unregisterDataSetObserver(observer);
            }
        }

        public boolean areAllItemsEnabled() {
            ListAdapter adapter = this.mListAdapter;
            if (adapter != null) {
                return adapter.areAllItemsEnabled();
            }
            return true;
        }

        public boolean isEnabled(int position) {
            ListAdapter adapter = this.mListAdapter;
            if (adapter != null) {
                return adapter.isEnabled(position);
            }
            return true;
        }

        public int getItemViewType(int position) {
            return 0;
        }

        public int getViewTypeCount() {
            return 1;
        }

        public boolean isEmpty() {
            return getCount() == 0;
        }
    }

    private class DropdownPopup extends ListPopupWindow {
        /* access modifiers changed from: private */
        public ListAdapter mAdapter;
        private CharSequence mHintText;
        private final Rect mVisibleRect = new Rect();

        public DropdownPopup(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            setAnchorView(AppCompatSpinner.this);
            setModal(true);
            setPromptPosition(0);
            setOnItemClickListener(new OnItemClickListener(AppCompatSpinner.this) {
                public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                    AppCompatSpinner.this.setSelection(position);
                    if (AppCompatSpinner.this.getOnItemClickListener() != null) {
                        AppCompatSpinner.this.performItemClick(v, position, DropdownPopup.this.mAdapter.getItemId(position));
                    }
                    DropdownPopup.this.dismiss();
                }
            });
        }

        public void setAdapter(ListAdapter adapter) {
            super.setAdapter(adapter);
            this.mAdapter = adapter;
        }

        public CharSequence getHintText() {
            return this.mHintText;
        }

        public void setPromptText(CharSequence hintText) {
            this.mHintText = hintText;
        }

        /* access modifiers changed from: 0000 */
        public void computeContentWidth() {
            int hOffset;
            Drawable background = getBackground();
            int hOffset2 = 0;
            if (background != null) {
                background.getPadding(AppCompatSpinner.this.mTempRect);
                hOffset2 = ViewUtils.isLayoutRtl(AppCompatSpinner.this) ? AppCompatSpinner.this.mTempRect.right : -AppCompatSpinner.this.mTempRect.left;
            } else {
                Rect access$300 = AppCompatSpinner.this.mTempRect;
                AppCompatSpinner.this.mTempRect.right = 0;
                access$300.left = 0;
            }
            int spinnerPaddingLeft = AppCompatSpinner.this.getPaddingLeft();
            int spinnerPaddingRight = AppCompatSpinner.this.getPaddingRight();
            int spinnerWidth = AppCompatSpinner.this.getWidth();
            if (AppCompatSpinner.this.mDropDownWidth == -2) {
                int contentWidth = AppCompatSpinner.this.compatMeasureContentWidth((SpinnerAdapter) this.mAdapter, getBackground());
                int contentWidthLimit = (AppCompatSpinner.this.getContext().getResources().getDisplayMetrics().widthPixels - AppCompatSpinner.this.mTempRect.left) - AppCompatSpinner.this.mTempRect.right;
                if (contentWidth > contentWidthLimit) {
                    contentWidth = contentWidthLimit;
                }
                setContentWidth(Math.max(contentWidth, (spinnerWidth - spinnerPaddingLeft) - spinnerPaddingRight));
            } else if (AppCompatSpinner.this.mDropDownWidth == -1) {
                setContentWidth((spinnerWidth - spinnerPaddingLeft) - spinnerPaddingRight);
            } else {
                setContentWidth(AppCompatSpinner.this.mDropDownWidth);
            }
            if (ViewUtils.isLayoutRtl(AppCompatSpinner.this)) {
                hOffset = hOffset2 + ((spinnerWidth - spinnerPaddingRight) - getWidth());
            } else {
                hOffset = hOffset2 + spinnerPaddingLeft;
            }
            setHorizontalOffset(hOffset);
        }

        public void show() {
            boolean wasShowing = isShowing();
            computeContentWidth();
            setInputMethodMode(2);
            super.show();
            getListView().setChoiceMode(1);
            setSelection(AppCompatSpinner.this.getSelectedItemPosition());
            if (!wasShowing) {
                ViewTreeObserver vto = AppCompatSpinner.this.getViewTreeObserver();
                if (vto != null) {
                    final OnGlobalLayoutListener layoutListener = new OnGlobalLayoutListener() {
                        public void onGlobalLayout() {
                            if (!DropdownPopup.this.isVisibleToUser(AppCompatSpinner.this)) {
                                DropdownPopup.this.dismiss();
                                return;
                            }
                            DropdownPopup.this.computeContentWidth();
                            DropdownPopup.super.show();
                        }
                    };
                    vto.addOnGlobalLayoutListener(layoutListener);
                    setOnDismissListener(new OnDismissListener() {
                        public void onDismiss() {
                            ViewTreeObserver vto = AppCompatSpinner.this.getViewTreeObserver();
                            if (vto != null) {
                                vto.removeGlobalOnLayoutListener(layoutListener);
                            }
                        }
                    });
                }
            }
        }

        /* access modifiers changed from: private */
        public boolean isVisibleToUser(View view) {
            return ViewCompat.isAttachedToWindow(view) && view.getGlobalVisibleRect(this.mVisibleRect);
        }
    }

    static {
        boolean z;
        boolean z2;
        if (VERSION.SDK_INT >= 23) {
            z = true;
        } else {
            z = false;
        }
        IS_AT_LEAST_M = z;
        if (VERSION.SDK_INT >= 16) {
            z2 = true;
        } else {
            z2 = false;
        }
        IS_AT_LEAST_JB = z2;
    }

    public AppCompatSpinner(Context context) {
        this(context, (AttributeSet) null);
    }

    public AppCompatSpinner(Context context, int mode) {
        this(context, null, R.attr.spinnerStyle, mode);
    }

    public AppCompatSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.spinnerStyle);
    }

    public AppCompatSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    public AppCompatSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        this(context, attrs, defStyleAttr, mode, null);
    }

    public AppCompatSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode, Theme popupTheme) {
        super(context, attrs, defStyleAttr);
        this.mTempRect = new Rect();
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.Spinner, defStyleAttr, 0);
        this.mDrawableManager = AppCompatDrawableManager.get();
        this.mBackgroundTintHelper = new AppCompatBackgroundHelper(this, this.mDrawableManager);
        if (popupTheme != null) {
            this.mPopupContext = new ContextThemeWrapper(context, popupTheme);
        } else {
            int popupThemeResId = a.getResourceId(R.styleable.Spinner_popupTheme, 0);
            if (popupThemeResId != 0) {
                this.mPopupContext = new ContextThemeWrapper(context, popupThemeResId);
            } else {
                this.mPopupContext = !IS_AT_LEAST_M ? context : null;
            }
        }
        if (this.mPopupContext != null) {
            if (mode == -1) {
                if (VERSION.SDK_INT >= 11) {
                    TypedArray aa = null;
                    try {
                        TypedArray aa2 = context.obtainStyledAttributes(attrs, ATTRS_ANDROID_SPINNERMODE, defStyleAttr, 0);
                        if (aa2.hasValue(0)) {
                            mode = aa2.getInt(0, 0);
                        }
                        if (aa2 != null) {
                            aa2.recycle();
                        }
                    } catch (Exception e) {
                        Log.i(TAG, "Could not read android:spinnerMode", e);
                        if (aa != null) {
                            aa.recycle();
                        }
                    } catch (Throwable th) {
                        if (aa != null) {
                            aa.recycle();
                        }
                        throw th;
                    }
                } else {
                    mode = 1;
                }
            }
            if (mode == 1) {
                final DropdownPopup popup = new DropdownPopup(this.mPopupContext, attrs, defStyleAttr);
                TintTypedArray pa = TintTypedArray.obtainStyledAttributes(this.mPopupContext, attrs, R.styleable.Spinner, defStyleAttr, 0);
                this.mDropDownWidth = pa.getLayoutDimension(R.styleable.Spinner_android_dropDownWidth, -2);
                popup.setBackgroundDrawable(pa.getDrawable(R.styleable.Spinner_android_popupBackground));
                popup.setPromptText(a.getString(R.styleable.Spinner_android_prompt));
                pa.recycle();
                this.mPopup = popup;
                this.mForwardingListener = new ForwardingListener(this) {
                    public ListPopupWindow getPopup() {
                        return popup;
                    }

                    public boolean onForwardingStarted() {
                        if (!AppCompatSpinner.this.mPopup.isShowing()) {
                            AppCompatSpinner.this.mPopup.show();
                        }
                        return true;
                    }
                };
            }
        }
        CharSequence[] entries = a.getTextArray(R.styleable.Spinner_android_entries);
        if (entries != null) {
            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(context, 17367048, entries);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            setAdapter((SpinnerAdapter) adapter);
        }
        a.recycle();
        this.mPopupSet = true;
        if (this.mTempAdapter != null) {
            setAdapter(this.mTempAdapter);
            this.mTempAdapter = null;
        }
        this.mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    public Context getPopupContext() {
        if (this.mPopup != null) {
            return this.mPopupContext;
        }
        if (IS_AT_LEAST_M) {
            return super.getPopupContext();
        }
        return null;
    }

    public void setPopupBackgroundDrawable(Drawable background) {
        if (this.mPopup != null) {
            this.mPopup.setBackgroundDrawable(background);
        } else if (IS_AT_LEAST_JB) {
            super.setPopupBackgroundDrawable(background);
        }
    }

    public void setPopupBackgroundResource(@DrawableRes int resId) {
        setPopupBackgroundDrawable(ContextCompat.getDrawable(getPopupContext(), resId));
    }

    public Drawable getPopupBackground() {
        if (this.mPopup != null) {
            return this.mPopup.getBackground();
        }
        if (IS_AT_LEAST_JB) {
            return super.getPopupBackground();
        }
        return null;
    }

    public void setDropDownVerticalOffset(int pixels) {
        if (this.mPopup != null) {
            this.mPopup.setVerticalOffset(pixels);
        } else if (IS_AT_LEAST_JB) {
            super.setDropDownVerticalOffset(pixels);
        }
    }

    public int getDropDownVerticalOffset() {
        if (this.mPopup != null) {
            return this.mPopup.getVerticalOffset();
        }
        if (IS_AT_LEAST_JB) {
            return super.getDropDownVerticalOffset();
        }
        return 0;
    }

    public void setDropDownHorizontalOffset(int pixels) {
        if (this.mPopup != null) {
            this.mPopup.setHorizontalOffset(pixels);
        } else if (IS_AT_LEAST_JB) {
            super.setDropDownHorizontalOffset(pixels);
        }
    }

    public int getDropDownHorizontalOffset() {
        if (this.mPopup != null) {
            return this.mPopup.getHorizontalOffset();
        }
        if (IS_AT_LEAST_JB) {
            return super.getDropDownHorizontalOffset();
        }
        return 0;
    }

    public void setDropDownWidth(int pixels) {
        if (this.mPopup != null) {
            this.mDropDownWidth = pixels;
        } else if (IS_AT_LEAST_JB) {
            super.setDropDownWidth(pixels);
        }
    }

    public int getDropDownWidth() {
        if (this.mPopup != null) {
            return this.mDropDownWidth;
        }
        if (IS_AT_LEAST_JB) {
            return super.getDropDownWidth();
        }
        return 0;
    }

    public void setAdapter(SpinnerAdapter adapter) {
        if (!this.mPopupSet) {
            this.mTempAdapter = adapter;
            return;
        }
        super.setAdapter(adapter);
        if (this.mPopup != null) {
            this.mPopup.setAdapter(new DropDownAdapter(adapter, (this.mPopupContext == null ? getContext() : this.mPopupContext).getTheme()));
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mPopup != null && this.mPopup.isShowing()) {
            this.mPopup.dismiss();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.mForwardingListener == null || !this.mForwardingListener.onTouch(this, event)) {
            return super.onTouchEvent(event);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mPopup != null && MeasureSpec.getMode(widthMeasureSpec) == Integer.MIN_VALUE) {
            setMeasuredDimension(Math.min(Math.max(getMeasuredWidth(), compatMeasureContentWidth(getAdapter(), getBackground())), MeasureSpec.getSize(widthMeasureSpec)), getMeasuredHeight());
        }
    }

    public boolean performClick() {
        if (this.mPopup == null) {
            return super.performClick();
        }
        if (!this.mPopup.isShowing()) {
            this.mPopup.show();
        }
        return true;
    }

    public void setPrompt(CharSequence prompt) {
        if (this.mPopup != null) {
            this.mPopup.setPromptText(prompt);
        } else {
            super.setPrompt(prompt);
        }
    }

    public CharSequence getPrompt() {
        return this.mPopup != null ? this.mPopup.getHintText() : super.getPrompt();
    }

    public void setBackgroundResource(@DrawableRes int resId) {
        super.setBackgroundResource(resId);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundResource(resId);
        }
    }

    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundDrawable(background);
        }
    }

    public void setSupportBackgroundTintList(@Nullable ColorStateList tint) {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintList(tint);
        }
    }

    @Nullable
    public ColorStateList getSupportBackgroundTintList() {
        if (this.mBackgroundTintHelper != null) {
            return this.mBackgroundTintHelper.getSupportBackgroundTintList();
        }
        return null;
    }

    public void setSupportBackgroundTintMode(@Nullable Mode tintMode) {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintMode(tintMode);
        }
    }

    @Nullable
    public Mode getSupportBackgroundTintMode() {
        if (this.mBackgroundTintHelper != null) {
            return this.mBackgroundTintHelper.getSupportBackgroundTintMode();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySupportBackgroundTint();
        }
    }

    /* access modifiers changed from: private */
    public int compatMeasureContentWidth(SpinnerAdapter adapter, Drawable background) {
        if (adapter == null) {
            return 0;
        }
        int width = 0;
        View itemView = null;
        int itemType = 0;
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
        int start = Math.max(0, getSelectedItemPosition());
        int end = Math.min(adapter.getCount(), start + 15);
        for (int i = Math.max(0, start - (15 - (end - start))); i < end; i++) {
            int positionType = adapter.getItemViewType(i);
            if (positionType != itemType) {
                itemType = positionType;
                itemView = null;
            }
            itemView = adapter.getView(i, itemView, this);
            if (itemView.getLayoutParams() == null) {
                itemView.setLayoutParams(new LayoutParams(-2, -2));
            }
            itemView.measure(widthMeasureSpec, heightMeasureSpec);
            width = Math.max(width, itemView.getMeasuredWidth());
        }
        if (background == null) {
            return width;
        }
        background.getPadding(this.mTempRect);
        return width + this.mTempRect.left + this.mTempRect.right;
    }
}
