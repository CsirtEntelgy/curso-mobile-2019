package android.support.v7.internal.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build.VERSION;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.LruCache;
import android.support.v7.appcompat.R;
import android.util.Log;
import android.util.SparseArray;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public final class TintManager {
    private static final int[] COLORFILTER_COLOR_BACKGROUND_MULTIPLY = {R.drawable.abc_popup_background_mtrl_mult, R.drawable.abc_cab_background_internal_bg, R.drawable.abc_menu_hardkey_panel_mtrl_mult};
    private static final int[] COLORFILTER_COLOR_CONTROL_ACTIVATED = {R.drawable.abc_textfield_activated_mtrl_alpha, R.drawable.abc_textfield_search_activated_mtrl_alpha, R.drawable.abc_cab_background_top_mtrl_alpha, R.drawable.abc_text_cursor_material};
    private static final int[] COLORFILTER_TINT_COLOR_CONTROL_NORMAL = {R.drawable.abc_textfield_search_default_mtrl_alpha, R.drawable.abc_textfield_default_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha};
    private static final ColorFilterLruCache COLOR_FILTER_CACHE = new ColorFilterLruCache(6);
    private static final boolean DEBUG = false;
    private static final Mode DEFAULT_MODE = Mode.SRC_IN;
    private static final WeakHashMap<Context, TintManager> INSTANCE_CACHE = new WeakHashMap<>();
    public static final boolean SHOULD_BE_USED = (VERSION.SDK_INT < 21);
    private static final String TAG = "TintManager";
    private static final int[] TINT_CHECKABLE_BUTTON_LIST = {R.drawable.abc_btn_check_material, R.drawable.abc_btn_radio_material};
    private static final int[] TINT_COLOR_CONTROL_NORMAL = {R.drawable.abc_ic_ab_back_mtrl_am_alpha, R.drawable.abc_ic_go_search_api_mtrl_alpha, R.drawable.abc_ic_search_api_mtrl_alpha, R.drawable.abc_ic_commit_search_api_mtrl_alpha, R.drawable.abc_ic_clear_mtrl_alpha, R.drawable.abc_ic_menu_share_mtrl_alpha, R.drawable.abc_ic_menu_copy_mtrl_am_alpha, R.drawable.abc_ic_menu_cut_mtrl_alpha, R.drawable.abc_ic_menu_selectall_mtrl_alpha, R.drawable.abc_ic_menu_paste_mtrl_am_alpha, R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha, R.drawable.abc_ic_voice_search_api_mtrl_alpha};
    private static final int[] TINT_COLOR_CONTROL_STATE_LIST = {R.drawable.abc_edit_text_material, R.drawable.abc_tab_indicator_material, R.drawable.abc_textfield_search_material, R.drawable.abc_spinner_mtrl_am_alpha, R.drawable.abc_spinner_textfield_background_material, R.drawable.abc_ratingbar_full_material, R.drawable.abc_switch_track_mtrl_alpha, R.drawable.abc_switch_thumb_material, R.drawable.abc_btn_default_mtrl_shape, R.drawable.abc_btn_borderless_material};
    private final WeakReference<Context> mContextRef;
    private ColorStateList mDefaultColorStateList;
    private SparseArray<ColorStateList> mTintLists;

    private static class ColorFilterLruCache extends LruCache<Integer, PorterDuffColorFilter> {
        public ColorFilterLruCache(int maxSize) {
            super(maxSize);
        }

        /* access modifiers changed from: 0000 */
        public PorterDuffColorFilter get(int color, Mode mode) {
            return (PorterDuffColorFilter) get(Integer.valueOf(generateCacheKey(color, mode)));
        }

        /* access modifiers changed from: 0000 */
        public PorterDuffColorFilter put(int color, Mode mode, PorterDuffColorFilter filter) {
            return (PorterDuffColorFilter) put(Integer.valueOf(generateCacheKey(color, mode)), filter);
        }

        private static int generateCacheKey(int color, Mode mode) {
            return ((color + 31) * 31) + mode.hashCode();
        }
    }

    public static Drawable getDrawable(Context context, int resId) {
        if (isInTintList(resId)) {
            return get(context).getDrawable(resId);
        }
        return ContextCompat.getDrawable(context, resId);
    }

    public static TintManager get(Context context) {
        TintManager tm = (TintManager) INSTANCE_CACHE.get(context);
        if (tm != null) {
            return tm;
        }
        TintManager tm2 = new TintManager(context);
        INSTANCE_CACHE.put(context, tm2);
        return tm2;
    }

    private TintManager(Context context) {
        this.mContextRef = new WeakReference<>(context);
    }

    public Drawable getDrawable(int resId) {
        return getDrawable(resId, false);
    }

    public Drawable getDrawable(int resId, boolean failIfNotKnown) {
        Context context = (Context) this.mContextRef.get();
        if (context == null) {
            return null;
        }
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        if (drawable == null) {
            return drawable;
        }
        if (VERSION.SDK_INT >= 8) {
            drawable = drawable.mutate();
        }
        ColorStateList tintList = getTintList(resId);
        if (tintList != null) {
            Drawable drawable2 = DrawableCompat.wrap(drawable);
            DrawableCompat.setTintList(drawable2, tintList);
            Mode tintMode = getTintMode(resId);
            if (tintMode == null) {
                return drawable2;
            }
            DrawableCompat.setTintMode(drawable2, tintMode);
            return drawable2;
        } else if (resId == R.drawable.abc_cab_background_top_material) {
            return new LayerDrawable(new Drawable[]{getDrawable(R.drawable.abc_cab_background_internal_bg), getDrawable(R.drawable.abc_cab_background_top_mtrl_alpha)});
        } else if (resId == R.drawable.abc_seekbar_track_material) {
            LayerDrawable ld = (LayerDrawable) drawable;
            setPorterDuffColorFilter(ld.findDrawableByLayerId(16908288), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal), DEFAULT_MODE);
            setPorterDuffColorFilter(ld.findDrawableByLayerId(16908303), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal), DEFAULT_MODE);
            setPorterDuffColorFilter(ld.findDrawableByLayerId(16908301), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated), DEFAULT_MODE);
            return drawable;
        } else if (tintDrawableUsingColorFilter(resId, drawable) || !failIfNotKnown) {
            return drawable;
        } else {
            return null;
        }
    }

    public final boolean tintDrawableUsingColorFilter(int resId, Drawable drawable) {
        Context context = (Context) this.mContextRef.get();
        if (context == null) {
            return false;
        }
        Mode tintMode = DEFAULT_MODE;
        boolean colorAttrSet = false;
        int colorAttr = 0;
        int alpha = -1;
        if (arrayContains(COLORFILTER_TINT_COLOR_CONTROL_NORMAL, resId)) {
            colorAttr = R.attr.colorControlNormal;
            colorAttrSet = true;
        } else if (arrayContains(COLORFILTER_COLOR_CONTROL_ACTIVATED, resId)) {
            colorAttr = R.attr.colorControlActivated;
            colorAttrSet = true;
        } else if (arrayContains(COLORFILTER_COLOR_BACKGROUND_MULTIPLY, resId)) {
            colorAttr = 16842801;
            colorAttrSet = true;
            tintMode = Mode.MULTIPLY;
        } else if (resId == R.drawable.abc_list_divider_mtrl_alpha) {
            colorAttr = 16842800;
            colorAttrSet = true;
            alpha = Math.round(40.8f);
        }
        if (!colorAttrSet) {
            return false;
        }
        drawable.setColorFilter(getPorterDuffColorFilter(ThemeUtils.getThemeAttrColor(context, colorAttr), tintMode));
        if (alpha != -1) {
            drawable.setAlpha(alpha);
        }
        return true;
    }

    private static boolean arrayContains(int[] array, int value) {
        for (int id : array) {
            if (id == value) {
                return true;
            }
        }
        return false;
    }

    private static boolean isInTintList(int drawableId) {
        return arrayContains(TINT_COLOR_CONTROL_NORMAL, drawableId) || arrayContains(COLORFILTER_TINT_COLOR_CONTROL_NORMAL, drawableId) || arrayContains(COLORFILTER_COLOR_CONTROL_ACTIVATED, drawableId) || arrayContains(TINT_COLOR_CONTROL_STATE_LIST, drawableId) || arrayContains(COLORFILTER_COLOR_BACKGROUND_MULTIPLY, drawableId) || arrayContains(TINT_CHECKABLE_BUTTON_LIST, drawableId) || drawableId == R.drawable.abc_cab_background_top_material;
    }

    /* access modifiers changed from: 0000 */
    public final Mode getTintMode(int resId) {
        if (resId == R.drawable.abc_switch_thumb_material) {
            return Mode.MULTIPLY;
        }
        return null;
    }

    public final ColorStateList getTintList(int resId) {
        ColorStateList tint = null;
        Context context = (Context) this.mContextRef.get();
        if (context != null) {
            if (this.mTintLists != null) {
                tint = (ColorStateList) this.mTintLists.get(resId);
            }
            if (tint == null) {
                if (resId == R.drawable.abc_edit_text_material) {
                    tint = createEditTextColorStateList(context);
                } else if (resId == R.drawable.abc_switch_track_mtrl_alpha) {
                    tint = createSwitchTrackColorStateList(context);
                } else if (resId == R.drawable.abc_switch_thumb_material) {
                    tint = createSwitchThumbColorStateList(context);
                } else if (resId == R.drawable.abc_btn_default_mtrl_shape || resId == R.drawable.abc_btn_borderless_material) {
                    tint = createDefaultButtonColorStateList(context);
                } else if (resId == R.drawable.abc_btn_colored_material) {
                    tint = createColoredButtonColorStateList(context);
                } else if (resId == R.drawable.abc_spinner_mtrl_am_alpha || resId == R.drawable.abc_spinner_textfield_background_material) {
                    tint = createSpinnerColorStateList(context);
                } else if (arrayContains(TINT_COLOR_CONTROL_NORMAL, resId)) {
                    tint = ThemeUtils.getThemeAttrColorStateList(context, R.attr.colorControlNormal);
                } else if (arrayContains(TINT_COLOR_CONTROL_STATE_LIST, resId)) {
                    tint = getDefaultColorStateList(context);
                } else if (arrayContains(TINT_CHECKABLE_BUTTON_LIST, resId)) {
                    tint = createCheckableButtonColorStateList(context);
                } else if (resId == R.drawable.abc_seekbar_thumb_material) {
                    tint = createSeekbarThumbColorStateList(context);
                }
                if (tint != null) {
                    if (this.mTintLists == null) {
                        this.mTintLists = new SparseArray<>();
                    }
                    this.mTintLists.append(resId, tint);
                }
            }
        }
        return tint;
    }

    private ColorStateList getDefaultColorStateList(Context context) {
        if (this.mDefaultColorStateList == null) {
            int colorControlNormal = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal);
            int colorControlActivated = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
            int[][] states = new int[7][];
            int[] colors = new int[7];
            states[0] = ThemeUtils.DISABLED_STATE_SET;
            colors[0] = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlNormal);
            int i = 0 + 1;
            states[i] = ThemeUtils.FOCUSED_STATE_SET;
            colors[i] = colorControlActivated;
            int i2 = i + 1;
            states[i2] = ThemeUtils.ACTIVATED_STATE_SET;
            colors[i2] = colorControlActivated;
            int i3 = i2 + 1;
            states[i3] = ThemeUtils.PRESSED_STATE_SET;
            colors[i3] = colorControlActivated;
            int i4 = i3 + 1;
            states[i4] = ThemeUtils.CHECKED_STATE_SET;
            colors[i4] = colorControlActivated;
            int i5 = i4 + 1;
            states[i5] = ThemeUtils.SELECTED_STATE_SET;
            colors[i5] = colorControlActivated;
            int i6 = i5 + 1;
            states[i6] = ThemeUtils.EMPTY_STATE_SET;
            colors[i6] = colorControlNormal;
            int i7 = i6 + 1;
            this.mDefaultColorStateList = new ColorStateList(states, colors);
        }
        return this.mDefaultColorStateList;
    }

    private ColorStateList createCheckableButtonColorStateList(Context context) {
        int[][] states = new int[3][];
        int[] colors = new int[3];
        states[0] = ThemeUtils.DISABLED_STATE_SET;
        colors[0] = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlNormal);
        int i = 0 + 1;
        states[i] = ThemeUtils.CHECKED_STATE_SET;
        colors[i] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
        int i2 = i + 1;
        states[i2] = ThemeUtils.EMPTY_STATE_SET;
        colors[i2] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal);
        int i3 = i2 + 1;
        return new ColorStateList(states, colors);
    }

    private ColorStateList createSwitchTrackColorStateList(Context context) {
        int[][] states = new int[3][];
        int[] colors = new int[3];
        states[0] = ThemeUtils.DISABLED_STATE_SET;
        colors[0] = ThemeUtils.getThemeAttrColor(context, 16842800, 0.1f);
        int i = 0 + 1;
        states[i] = ThemeUtils.CHECKED_STATE_SET;
        colors[i] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated, 0.3f);
        int i2 = i + 1;
        states[i2] = ThemeUtils.EMPTY_STATE_SET;
        colors[i2] = ThemeUtils.getThemeAttrColor(context, 16842800, 0.3f);
        int i3 = i2 + 1;
        return new ColorStateList(states, colors);
    }

    private ColorStateList createSwitchThumbColorStateList(Context context) {
        int[][] states = new int[3][];
        int[] colors = new int[3];
        ColorStateList thumbColor = ThemeUtils.getThemeAttrColorStateList(context, R.attr.colorSwitchThumbNormal);
        if (thumbColor == null || !thumbColor.isStateful()) {
            states[0] = ThemeUtils.DISABLED_STATE_SET;
            colors[0] = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorSwitchThumbNormal);
            int i = 0 + 1;
            states[i] = ThemeUtils.CHECKED_STATE_SET;
            colors[i] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
            int i2 = i + 1;
            states[i2] = ThemeUtils.EMPTY_STATE_SET;
            colors[i2] = ThemeUtils.getThemeAttrColor(context, R.attr.colorSwitchThumbNormal);
            int i3 = i2 + 1;
        } else {
            states[0] = ThemeUtils.DISABLED_STATE_SET;
            colors[0] = thumbColor.getColorForState(states[0], 0);
            int i4 = 0 + 1;
            states[i4] = ThemeUtils.CHECKED_STATE_SET;
            colors[i4] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
            int i5 = i4 + 1;
            states[i5] = ThemeUtils.EMPTY_STATE_SET;
            colors[i5] = thumbColor.getDefaultColor();
            int i6 = i5 + 1;
        }
        return new ColorStateList(states, colors);
    }

    private ColorStateList createEditTextColorStateList(Context context) {
        int[][] states = new int[3][];
        int[] colors = new int[3];
        states[0] = ThemeUtils.DISABLED_STATE_SET;
        colors[0] = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlNormal);
        int i = 0 + 1;
        states[i] = ThemeUtils.NOT_PRESSED_OR_FOCUSED_STATE_SET;
        colors[i] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal);
        int i2 = i + 1;
        states[i2] = ThemeUtils.EMPTY_STATE_SET;
        colors[i2] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
        int i3 = i2 + 1;
        return new ColorStateList(states, colors);
    }

    private ColorStateList createDefaultButtonColorStateList(Context context) {
        return createButtonColorStateList(context, R.attr.colorButtonNormal);
    }

    private ColorStateList createColoredButtonColorStateList(Context context) {
        return createButtonColorStateList(context, R.attr.colorAccent);
    }

    private ColorStateList createButtonColorStateList(Context context, int baseColorAttr) {
        int[][] states = new int[4][];
        int[] colors = new int[4];
        int baseColor = ThemeUtils.getThemeAttrColor(context, baseColorAttr);
        int colorControlHighlight = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlHighlight);
        states[0] = ThemeUtils.DISABLED_STATE_SET;
        colors[0] = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorButtonNormal);
        int i = 0 + 1;
        states[i] = ThemeUtils.PRESSED_STATE_SET;
        colors[i] = ColorUtils.compositeColors(colorControlHighlight, baseColor);
        int i2 = i + 1;
        states[i2] = ThemeUtils.FOCUSED_STATE_SET;
        colors[i2] = ColorUtils.compositeColors(colorControlHighlight, baseColor);
        int i3 = i2 + 1;
        states[i3] = ThemeUtils.EMPTY_STATE_SET;
        colors[i3] = baseColor;
        int i4 = i3 + 1;
        return new ColorStateList(states, colors);
    }

    private ColorStateList createSpinnerColorStateList(Context context) {
        int[][] states = new int[3][];
        int[] colors = new int[3];
        states[0] = ThemeUtils.DISABLED_STATE_SET;
        colors[0] = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlNormal);
        int i = 0 + 1;
        states[i] = ThemeUtils.NOT_PRESSED_OR_FOCUSED_STATE_SET;
        colors[i] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal);
        int i2 = i + 1;
        states[i2] = ThemeUtils.EMPTY_STATE_SET;
        colors[i2] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
        int i3 = i2 + 1;
        return new ColorStateList(states, colors);
    }

    private ColorStateList createSeekbarThumbColorStateList(Context context) {
        int[][] states = new int[2][];
        int[] colors = new int[2];
        states[0] = ThemeUtils.DISABLED_STATE_SET;
        colors[0] = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlActivated);
        int i = 0 + 1;
        states[i] = ThemeUtils.EMPTY_STATE_SET;
        colors[i] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
        int i2 = i + 1;
        return new ColorStateList(states, colors);
    }

    public static void tintDrawable(Drawable drawable, TintInfo tint, int[] state) {
        if (!shouldMutateBackground(drawable) || drawable.mutate() == drawable) {
            if (tint.mHasTintList || tint.mHasTintMode) {
                drawable.setColorFilter(createTintFilter(tint.mHasTintList ? tint.mTintList : null, tint.mHasTintMode ? tint.mTintMode : DEFAULT_MODE, state));
            } else {
                drawable.clearColorFilter();
            }
            if (VERSION.SDK_INT <= 10) {
                drawable.invalidateSelf();
                return;
            }
            return;
        }
        Log.d(TAG, "Mutated drawable is not the same instance as the input.");
    }

    private static boolean shouldMutateBackground(Drawable drawable) {
        if (VERSION.SDK_INT >= 16) {
            return true;
        }
        if (drawable instanceof LayerDrawable) {
            if (VERSION.SDK_INT < 16) {
                return false;
            }
            return true;
        } else if (drawable instanceof InsetDrawable) {
            if (VERSION.SDK_INT < 14) {
                return false;
            }
            return true;
        } else if (!(drawable instanceof DrawableContainer)) {
            return true;
        } else {
            ConstantState state = drawable.getConstantState();
            if (!(state instanceof DrawableContainerState)) {
                return true;
            }
            for (Drawable child : ((DrawableContainerState) state).getChildren()) {
                if (!shouldMutateBackground(child)) {
                    return false;
                }
            }
            return true;
        }
    }

    private static PorterDuffColorFilter createTintFilter(ColorStateList tint, Mode tintMode, int[] state) {
        if (tint == null || tintMode == null) {
            return null;
        }
        return getPorterDuffColorFilter(tint.getColorForState(state, 0), tintMode);
    }

    private static PorterDuffColorFilter getPorterDuffColorFilter(int color, Mode mode) {
        PorterDuffColorFilter filter = COLOR_FILTER_CACHE.get(color, mode);
        if (filter != null) {
            return filter;
        }
        PorterDuffColorFilter filter2 = new PorterDuffColorFilter(color, mode);
        COLOR_FILTER_CACHE.put(color, mode, filter2);
        return filter2;
    }

    private static void setPorterDuffColorFilter(Drawable d, int color, Mode mode) {
        if (mode == null) {
            mode = DEFAULT_MODE;
        }
        d.setColorFilter(getPorterDuffColorFilter(color, mode));
    }
}
