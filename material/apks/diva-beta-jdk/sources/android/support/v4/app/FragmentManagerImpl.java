package android.support.v4.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.v4.app.BackStackRecord.TransitionState;
import android.support.v4.app.Fragment.SavedState;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.LogWriter;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: FragmentManager */
final class FragmentManagerImpl extends FragmentManager implements LayoutInflaterFactory {
    static final Interpolator ACCELERATE_CUBIC = new AccelerateInterpolator(1.5f);
    static final Interpolator ACCELERATE_QUINT = new AccelerateInterpolator(2.5f);
    static final int ANIM_DUR = 220;
    public static final int ANIM_STYLE_CLOSE_ENTER = 3;
    public static final int ANIM_STYLE_CLOSE_EXIT = 4;
    public static final int ANIM_STYLE_FADE_ENTER = 5;
    public static final int ANIM_STYLE_FADE_EXIT = 6;
    public static final int ANIM_STYLE_OPEN_ENTER = 1;
    public static final int ANIM_STYLE_OPEN_EXIT = 2;
    static boolean DEBUG = false;
    static final Interpolator DECELERATE_CUBIC = new DecelerateInterpolator(1.5f);
    static final Interpolator DECELERATE_QUINT = new DecelerateInterpolator(2.5f);
    static final boolean HONEYCOMB;
    static final String TAG = "FragmentManager";
    static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
    static final String TARGET_STATE_TAG = "android:target_state";
    static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
    static final String VIEW_STATE_TAG = "android:view_state";
    static Field sAnimationListenerField = null;
    ArrayList<Fragment> mActive;
    ArrayList<Fragment> mAdded;
    ArrayList<Integer> mAvailBackStackIndices;
    ArrayList<Integer> mAvailIndices;
    ArrayList<BackStackRecord> mBackStack;
    ArrayList<OnBackStackChangedListener> mBackStackChangeListeners;
    ArrayList<BackStackRecord> mBackStackIndices;
    FragmentContainer mContainer;
    FragmentController mController;
    ArrayList<Fragment> mCreatedMenus;
    int mCurState = 0;
    boolean mDestroyed;
    Runnable mExecCommit = new Runnable() {
        public void run() {
            FragmentManagerImpl.this.execPendingActions();
        }
    };
    boolean mExecutingActions;
    boolean mHavePendingDeferredStart;
    FragmentHostCallback mHost;
    boolean mNeedMenuInvalidate;
    String mNoTransactionsBecause;
    Fragment mParent;
    ArrayList<Runnable> mPendingActions;
    SparseArray<Parcelable> mStateArray = null;
    Bundle mStateBundle = null;
    boolean mStateSaved;
    Runnable[] mTmpActions;

    /* compiled from: FragmentManager */
    static class AnimateOnHWLayerIfNeededListener implements AnimationListener {
        private AnimationListener mOrignalListener = null;
        private boolean mShouldRunOnHWLayer = false;
        /* access modifiers changed from: private */
        public View mView = null;

        public AnimateOnHWLayerIfNeededListener(View v, Animation anim) {
            if (v != null && anim != null) {
                this.mView = v;
            }
        }

        public AnimateOnHWLayerIfNeededListener(View v, Animation anim, AnimationListener listener) {
            if (v != null && anim != null) {
                this.mOrignalListener = listener;
                this.mView = v;
            }
        }

        @CallSuper
        public void onAnimationStart(Animation animation) {
            if (this.mView != null) {
                this.mShouldRunOnHWLayer = FragmentManagerImpl.shouldRunOnHWLayer(this.mView, animation);
                if (this.mShouldRunOnHWLayer) {
                    this.mView.post(new Runnable() {
                        public void run() {
                            ViewCompat.setLayerType(AnimateOnHWLayerIfNeededListener.this.mView, 2, null);
                        }
                    });
                }
            }
            if (this.mOrignalListener != null) {
                this.mOrignalListener.onAnimationStart(animation);
            }
        }

        @CallSuper
        public void onAnimationEnd(Animation animation) {
            if (this.mView != null && this.mShouldRunOnHWLayer) {
                this.mView.post(new Runnable() {
                    public void run() {
                        ViewCompat.setLayerType(AnimateOnHWLayerIfNeededListener.this.mView, 0, null);
                    }
                });
            }
            if (this.mOrignalListener != null) {
                this.mOrignalListener.onAnimationEnd(animation);
            }
        }

        public void onAnimationRepeat(Animation animation) {
            if (this.mOrignalListener != null) {
                this.mOrignalListener.onAnimationRepeat(animation);
            }
        }
    }

    /* compiled from: FragmentManager */
    static class FragmentTag {
        public static final int[] Fragment = {16842755, 16842960, 16842961};
        public static final int Fragment_id = 1;
        public static final int Fragment_name = 0;
        public static final int Fragment_tag = 2;

        FragmentTag() {
        }
    }

    FragmentManagerImpl() {
    }

    static {
        boolean z = false;
        if (VERSION.SDK_INT >= 11) {
            z = true;
        }
        HONEYCOMB = z;
    }

    static boolean modifiesAlpha(Animation anim) {
        if (anim instanceof AlphaAnimation) {
            return true;
        }
        if (anim instanceof AnimationSet) {
            List<Animation> anims = ((AnimationSet) anim).getAnimations();
            for (int i = 0; i < anims.size(); i++) {
                if (anims.get(i) instanceof AlphaAnimation) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean shouldRunOnHWLayer(View v, Animation anim) {
        return VERSION.SDK_INT >= 19 && ViewCompat.getLayerType(v) == 0 && ViewCompat.hasOverlappingRendering(v) && modifiesAlpha(anim);
    }

    private void throwException(RuntimeException ex) {
        Log.e(TAG, ex.getMessage());
        Log.e(TAG, "Activity state:");
        PrintWriter pw = new PrintWriter(new LogWriter(TAG));
        if (this.mHost != null) {
            try {
                this.mHost.onDump("  ", null, pw, new String[0]);
            } catch (Exception e) {
                Log.e(TAG, "Failed dumping state", e);
            }
        } else {
            try {
                dump("  ", null, pw, new String[0]);
            } catch (Exception e2) {
                Log.e(TAG, "Failed dumping state", e2);
            }
        }
        throw ex;
    }

    public FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

    public boolean executePendingTransactions() {
        return execPendingActions();
    }

    public void popBackStack() {
        enqueueAction(new Runnable() {
            public void run() {
                FragmentManagerImpl.this.popBackStackState(FragmentManagerImpl.this.mHost.getHandler(), null, -1, 0);
            }
        }, false);
    }

    public boolean popBackStackImmediate() {
        checkStateLoss();
        executePendingTransactions();
        return popBackStackState(this.mHost.getHandler(), null, -1, 0);
    }

    public void popBackStack(final String name, final int flags) {
        enqueueAction(new Runnable() {
            public void run() {
                FragmentManagerImpl.this.popBackStackState(FragmentManagerImpl.this.mHost.getHandler(), name, -1, flags);
            }
        }, false);
    }

    public boolean popBackStackImmediate(String name, int flags) {
        checkStateLoss();
        executePendingTransactions();
        return popBackStackState(this.mHost.getHandler(), name, -1, flags);
    }

    public void popBackStack(final int id, final int flags) {
        if (id < 0) {
            throw new IllegalArgumentException("Bad id: " + id);
        }
        enqueueAction(new Runnable() {
            public void run() {
                FragmentManagerImpl.this.popBackStackState(FragmentManagerImpl.this.mHost.getHandler(), null, id, flags);
            }
        }, false);
    }

    public boolean popBackStackImmediate(int id, int flags) {
        checkStateLoss();
        executePendingTransactions();
        if (id >= 0) {
            return popBackStackState(this.mHost.getHandler(), null, id, flags);
        }
        throw new IllegalArgumentException("Bad id: " + id);
    }

    public int getBackStackEntryCount() {
        if (this.mBackStack != null) {
            return this.mBackStack.size();
        }
        return 0;
    }

    public BackStackEntry getBackStackEntryAt(int index) {
        return (BackStackEntry) this.mBackStack.get(index);
    }

    public void addOnBackStackChangedListener(OnBackStackChangedListener listener) {
        if (this.mBackStackChangeListeners == null) {
            this.mBackStackChangeListeners = new ArrayList<>();
        }
        this.mBackStackChangeListeners.add(listener);
    }

    public void removeOnBackStackChangedListener(OnBackStackChangedListener listener) {
        if (this.mBackStackChangeListeners != null) {
            this.mBackStackChangeListeners.remove(listener);
        }
    }

    public void putFragment(Bundle bundle, String key, Fragment fragment) {
        if (fragment.mIndex < 0) {
            throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        bundle.putInt(key, fragment.mIndex);
    }

    public Fragment getFragment(Bundle bundle, String key) {
        int index = bundle.getInt(key, -1);
        if (index == -1) {
            return null;
        }
        if (index >= this.mActive.size()) {
            throwException(new IllegalStateException("Fragment no longer exists for key " + key + ": index " + index));
        }
        Fragment f = (Fragment) this.mActive.get(index);
        if (f != null) {
            return f;
        }
        throwException(new IllegalStateException("Fragment no longer exists for key " + key + ": index " + index));
        return f;
    }

    public List<Fragment> getFragments() {
        return this.mActive;
    }

    public SavedState saveFragmentInstanceState(Fragment fragment) {
        if (fragment.mIndex < 0) {
            throwException(new IllegalStateException("Fragment " + fragment + " is not currently in the FragmentManager"));
        }
        if (fragment.mState <= 0) {
            return null;
        }
        Bundle result = saveFragmentBasicState(fragment);
        if (result != null) {
            return new SavedState(result);
        }
        return null;
    }

    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        if (this.mParent != null) {
            DebugUtils.buildShortClassTag(this.mParent, sb);
        } else {
            DebugUtils.buildShortClassTag(this.mHost, sb);
        }
        sb.append("}}");
        return sb.toString();
    }

    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        String innerPrefix = prefix + "    ";
        if (this.mActive != null) {
            int N = this.mActive.size();
            if (N > 0) {
                writer.print(prefix);
                writer.print("Active Fragments in ");
                writer.print(Integer.toHexString(System.identityHashCode(this)));
                writer.println(":");
                for (int i = 0; i < N; i++) {
                    Fragment f = (Fragment) this.mActive.get(i);
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i);
                    writer.print(": ");
                    writer.println(f);
                    if (f != null) {
                        f.dump(innerPrefix, fd, writer, args);
                    }
                }
            }
        }
        if (this.mAdded != null) {
            int N2 = this.mAdded.size();
            if (N2 > 0) {
                writer.print(prefix);
                writer.println("Added Fragments:");
                for (int i2 = 0; i2 < N2; i2++) {
                    Fragment f2 = (Fragment) this.mAdded.get(i2);
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i2);
                    writer.print(": ");
                    writer.println(f2.toString());
                }
            }
        }
        if (this.mCreatedMenus != null) {
            int N3 = this.mCreatedMenus.size();
            if (N3 > 0) {
                writer.print(prefix);
                writer.println("Fragments Created Menus:");
                for (int i3 = 0; i3 < N3; i3++) {
                    Fragment f3 = (Fragment) this.mCreatedMenus.get(i3);
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i3);
                    writer.print(": ");
                    writer.println(f3.toString());
                }
            }
        }
        if (this.mBackStack != null) {
            int N4 = this.mBackStack.size();
            if (N4 > 0) {
                writer.print(prefix);
                writer.println("Back Stack:");
                for (int i4 = 0; i4 < N4; i4++) {
                    BackStackRecord bs = (BackStackRecord) this.mBackStack.get(i4);
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i4);
                    writer.print(": ");
                    writer.println(bs.toString());
                    bs.dump(innerPrefix, fd, writer, args);
                }
            }
        }
        synchronized (this) {
            if (this.mBackStackIndices != null) {
                int N5 = this.mBackStackIndices.size();
                if (N5 > 0) {
                    writer.print(prefix);
                    writer.println("Back Stack Indices:");
                    for (int i5 = 0; i5 < N5; i5++) {
                        BackStackRecord bs2 = (BackStackRecord) this.mBackStackIndices.get(i5);
                        writer.print(prefix);
                        writer.print("  #");
                        writer.print(i5);
                        writer.print(": ");
                        writer.println(bs2);
                    }
                }
            }
            if (this.mAvailBackStackIndices != null && this.mAvailBackStackIndices.size() > 0) {
                writer.print(prefix);
                writer.print("mAvailBackStackIndices: ");
                writer.println(Arrays.toString(this.mAvailBackStackIndices.toArray()));
            }
        }
        if (this.mPendingActions != null) {
            int N6 = this.mPendingActions.size();
            if (N6 > 0) {
                writer.print(prefix);
                writer.println("Pending Actions:");
                for (int i6 = 0; i6 < N6; i6++) {
                    Runnable r = (Runnable) this.mPendingActions.get(i6);
                    writer.print(prefix);
                    writer.print("  #");
                    writer.print(i6);
                    writer.print(": ");
                    writer.println(r);
                }
            }
        }
        writer.print(prefix);
        writer.println("FragmentManager misc state:");
        writer.print(prefix);
        writer.print("  mHost=");
        writer.println(this.mHost);
        writer.print(prefix);
        writer.print("  mContainer=");
        writer.println(this.mContainer);
        if (this.mParent != null) {
            writer.print(prefix);
            writer.print("  mParent=");
            writer.println(this.mParent);
        }
        writer.print(prefix);
        writer.print("  mCurState=");
        writer.print(this.mCurState);
        writer.print(" mStateSaved=");
        writer.print(this.mStateSaved);
        writer.print(" mDestroyed=");
        writer.println(this.mDestroyed);
        if (this.mNeedMenuInvalidate) {
            writer.print(prefix);
            writer.print("  mNeedMenuInvalidate=");
            writer.println(this.mNeedMenuInvalidate);
        }
        if (this.mNoTransactionsBecause != null) {
            writer.print(prefix);
            writer.print("  mNoTransactionsBecause=");
            writer.println(this.mNoTransactionsBecause);
        }
        if (this.mAvailIndices != null && this.mAvailIndices.size() > 0) {
            writer.print(prefix);
            writer.print("  mAvailIndices: ");
            writer.println(Arrays.toString(this.mAvailIndices.toArray()));
        }
    }

    static Animation makeOpenCloseAnimation(Context context, float startScale, float endScale, float startAlpha, float endAlpha) {
        AnimationSet set = new AnimationSet(false);
        ScaleAnimation scale = new ScaleAnimation(startScale, endScale, startScale, endScale, 1, 0.5f, 1, 0.5f);
        scale.setInterpolator(DECELERATE_QUINT);
        scale.setDuration(220);
        set.addAnimation(scale);
        AlphaAnimation alpha = new AlphaAnimation(startAlpha, endAlpha);
        alpha.setInterpolator(DECELERATE_CUBIC);
        alpha.setDuration(220);
        set.addAnimation(alpha);
        return set;
    }

    static Animation makeFadeAnimation(Context context, float start, float end) {
        AlphaAnimation anim = new AlphaAnimation(start, end);
        anim.setInterpolator(DECELERATE_CUBIC);
        anim.setDuration(220);
        return anim;
    }

    /* access modifiers changed from: 0000 */
    public Animation loadAnimation(Fragment fragment, int transit, boolean enter, int transitionStyle) {
        Animation animObj = fragment.onCreateAnimation(transit, enter, fragment.mNextAnim);
        if (animObj != null) {
            return animObj;
        }
        if (fragment.mNextAnim != 0) {
            Animation anim = AnimationUtils.loadAnimation(this.mHost.getContext(), fragment.mNextAnim);
            if (anim != null) {
                return anim;
            }
        }
        if (transit == 0) {
            return null;
        }
        int styleIndex = transitToStyleIndex(transit, enter);
        if (styleIndex < 0) {
            return null;
        }
        switch (styleIndex) {
            case 1:
                return makeOpenCloseAnimation(this.mHost.getContext(), 1.125f, 1.0f, 0.0f, 1.0f);
            case 2:
                return makeOpenCloseAnimation(this.mHost.getContext(), 1.0f, 0.975f, 1.0f, 0.0f);
            case 3:
                return makeOpenCloseAnimation(this.mHost.getContext(), 0.975f, 1.0f, 0.0f, 1.0f);
            case 4:
                return makeOpenCloseAnimation(this.mHost.getContext(), 1.0f, 1.075f, 1.0f, 0.0f);
            case 5:
                return makeFadeAnimation(this.mHost.getContext(), 0.0f, 1.0f);
            case 6:
                return makeFadeAnimation(this.mHost.getContext(), 1.0f, 0.0f);
            default:
                if (transitionStyle == 0 && this.mHost.onHasWindowAnimations()) {
                    transitionStyle = this.mHost.onGetWindowAnimations();
                }
                if (transitionStyle == 0) {
                    return null;
                }
                return null;
        }
    }

    public void performPendingDeferredStart(Fragment f) {
        if (!f.mDeferStart) {
            return;
        }
        if (this.mExecutingActions) {
            this.mHavePendingDeferredStart = true;
            return;
        }
        f.mDeferStart = false;
        moveToState(f, this.mCurState, 0, 0, false);
    }

    private void setHWLayerAnimListenerIfAlpha(View v, Animation anim) {
        if (v != null && anim != null && shouldRunOnHWLayer(v, anim)) {
            AnimationListener originalListener = null;
            try {
                if (sAnimationListenerField == null) {
                    sAnimationListenerField = Animation.class.getDeclaredField("mListener");
                    sAnimationListenerField.setAccessible(true);
                }
                originalListener = (AnimationListener) sAnimationListenerField.get(anim);
            } catch (NoSuchFieldException e) {
                Log.e(TAG, "No field with the name mListener is found in Animation class", e);
            } catch (IllegalAccessException e2) {
                Log.e(TAG, "Cannot access Animation's mListener field", e2);
            }
            anim.setAnimationListener(new AnimateOnHWLayerIfNeededListener(v, anim, originalListener));
        }
    }

    /* access modifiers changed from: 0000 */
    public void setRetainLoader(boolean retain) {
        if (this.mActive != null) {
            for (int i = 0; i < this.mActive.size(); i++) {
                Fragment f = (Fragment) this.mActive.get(i);
                if (f != null) {
                    f.mRetainLoader = retain;
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x021a, code lost:
        r12.mSavedFragmentState = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x021e, code lost:
        if (r13 <= 3) goto L_0x023f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0222, code lost:
        if (DEBUG == false) goto L_0x023c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x0224, code lost:
        android.util.Log.v(TAG, "moveto STARTED: " + r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x023c, code lost:
        r12.performStart();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x0240, code lost:
        if (r13 <= 4) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x0244, code lost:
        if (DEBUG == false) goto L_0x025e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x0246, code lost:
        android.util.Log.v(TAG, "moveto RESUMED: " + r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x025e, code lost:
        r12.mResumed = true;
        r12.performResume();
        r12.mSavedFragmentState = null;
        r12.mSavedViewState = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x027b, code lost:
        r12.mView = android.support.v4.app.NoSaveStateFrameLayout.wrap(r12.mView);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x0285, code lost:
        r12.mInnerView = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x0295, code lost:
        if (r13 >= 1) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x0299, code lost:
        if (r11.mDestroyed == false) goto L_0x02a7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x029d, code lost:
        if (r12.mAnimatingAway == null) goto L_0x02a7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x029f, code lost:
        r9 = r12.mAnimatingAway;
        r12.mAnimatingAway = null;
        r9.clearAnimation();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x02a9, code lost:
        if (r12.mAnimatingAway == null) goto L_0x038e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x02ab, code lost:
        r12.mStateAfterAnimating = r13;
        r13 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x02d6, code lost:
        if (r13 >= 4) goto L_0x02f7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x02da, code lost:
        if (DEBUG == false) goto L_0x02f4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x02dc, code lost:
        android.util.Log.v(TAG, "movefrom STARTED: " + r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x02f4, code lost:
        r12.performStop();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x02f8, code lost:
        if (r13 >= 3) goto L_0x0319;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x02fc, code lost:
        if (DEBUG == false) goto L_0x0316;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x02fe, code lost:
        android.util.Log.v(TAG, "movefrom STOPPED: " + r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x0316, code lost:
        r12.performReallyStop();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x031a, code lost:
        if (r13 >= 2) goto L_0x0294;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x031e, code lost:
        if (DEBUG == false) goto L_0x0338;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x0320, code lost:
        android.util.Log.v(TAG, "movefrom ACTIVITY_CREATED: " + r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x033a, code lost:
        if (r12.mView == null) goto L_0x034b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x0342, code lost:
        if (r11.mHost.onShouldSaveFragmentState(r12) == false) goto L_0x034b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x0346, code lost:
        if (r12.mSavedViewState != null) goto L_0x034b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x0348, code lost:
        saveFragmentViewState(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:0x034b, code lost:
        r12.performDestroyView();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x0350, code lost:
        if (r12.mView == null) goto L_0x0383;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:0x0354, code lost:
        if (r12.mContainer == null) goto L_0x0383;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:165:0x0356, code lost:
        r6 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:166:0x0359, code lost:
        if (r11.mCurState <= 0) goto L_0x0364;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x035d, code lost:
        if (r11.mDestroyed != false) goto L_0x0364;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:169:0x035f, code lost:
        r6 = loadAnimation(r12, r14, false, r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x0364, code lost:
        if (r6 == null) goto L_0x037c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x0366, code lost:
        r8 = r12;
        r12.mAnimatingAway = r12.mView;
        r12.mStateAfterAnimating = r13;
        r6.setAnimationListener(new android.support.v4.app.FragmentManagerImpl.AnonymousClass5(r11, r12.mView, r6));
        r12.mView.startAnimation(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x037c, code lost:
        r12.mContainer.removeView(r12.mView);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x0383, code lost:
        r12.mContainer = null;
        r12.mView = null;
        r12.mInnerView = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:0x0390, code lost:
        if (DEBUG == false) goto L_0x03aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x0392, code lost:
        android.util.Log.v(TAG, "movefrom CREATED: " + r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x03ac, code lost:
        if (r12.mRetaining != false) goto L_0x03b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:179:0x03ae, code lost:
        r12.performDestroy();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:0x03b1, code lost:
        r12.mCalled = false;
        r12.onDetach();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:181:0x03b9, code lost:
        if (r12.mCalled != false) goto L_0x03da;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:0x03d9, code lost:
        throw new android.support.v4.app.SuperNotCalledException("Fragment " + r12 + " did not call through to super.onDetach()");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:184:0x03da, code lost:
        if (r16 != false) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:186:0x03de, code lost:
        if (r12.mRetaining != false) goto L_0x03e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:187:0x03e0, code lost:
        makeInactive(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:188:0x03e5, code lost:
        r12.mHost = null;
        r12.mParentFragment = null;
        r12.mFragmentManager = null;
        r12.mChildFragmentManager = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0148, code lost:
        if (r13 <= 1) goto L_0x021d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x014c, code lost:
        if (DEBUG == false) goto L_0x0166;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x014e, code lost:
        android.util.Log.v(TAG, "moveto ACTIVITY_CREATED: " + r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0168, code lost:
        if (r12.mFromLayout != false) goto L_0x020c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x016a, code lost:
        r7 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x016d, code lost:
        if (r12.mContainerId == 0) goto L_0x01be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x016f, code lost:
        r7 = (android.view.ViewGroup) r11.mContainer.onFindViewById(r12.mContainerId);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0179, code lost:
        if (r7 != null) goto L_0x01be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x017d, code lost:
        if (r12.mRestored != false) goto L_0x01be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x017f, code lost:
        throwException(new java.lang.IllegalArgumentException("No view found for id 0x" + java.lang.Integer.toHexString(r12.mContainerId) + " (" + r12.getResources().getResourceName(r12.mContainerId) + ") for fragment " + r12));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x01be, code lost:
        r12.mContainer = r7;
        r12.mView = r12.performCreateView(r12.getLayoutInflater(r12.mSavedFragmentState), r7, r12.mSavedFragmentState);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01d0, code lost:
        if (r12.mView == null) goto L_0x0285;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x01d2, code lost:
        r12.mInnerView = r12.mView;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x01da, code lost:
        if (android.os.Build.VERSION.SDK_INT < 11) goto L_0x027b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01dc, code lost:
        android.support.v4.view.ViewCompat.setSaveFromParentEnabled(r12.mView, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x01e2, code lost:
        if (r7 == null) goto L_0x01fa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x01e4, code lost:
        r6 = loadAnimation(r12, r14, true, r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x01e9, code lost:
        if (r6 == null) goto L_0x01f5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x01eb, code lost:
        setHWLayerAnimListenerIfAlpha(r12.mView, r6);
        r12.mView.startAnimation(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x01f5, code lost:
        r7.addView(r12.mView);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x01fc, code lost:
        if (r12.mHidden == false) goto L_0x0205;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x01fe, code lost:
        r12.mView.setVisibility(8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x0205, code lost:
        r12.onViewCreated(r12.mView, r12.mSavedFragmentState);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x020c, code lost:
        r12.performActivityCreated(r12.mSavedFragmentState);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x0213, code lost:
        if (r12.mView == null) goto L_0x021a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x0215, code lost:
        r12.restoreViewState(r12.mSavedFragmentState);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void moveToState(android.support.v4.app.Fragment r12, int r13, int r14, int r15, boolean r16) {
        /*
            r11 = this;
            boolean r0 = r12.mAdded
            if (r0 == 0) goto L_0x0008
            boolean r0 = r12.mDetached
            if (r0 == 0) goto L_0x000c
        L_0x0008:
            r0 = 1
            if (r13 <= r0) goto L_0x000c
            r13 = 1
        L_0x000c:
            boolean r0 = r12.mRemoving
            if (r0 == 0) goto L_0x0016
            int r0 = r12.mState
            if (r13 <= r0) goto L_0x0016
            int r13 = r12.mState
        L_0x0016:
            boolean r0 = r12.mDeferStart
            if (r0 == 0) goto L_0x0023
            int r0 = r12.mState
            r1 = 4
            if (r0 >= r1) goto L_0x0023
            r0 = 3
            if (r13 <= r0) goto L_0x0023
            r13 = 3
        L_0x0023:
            int r0 = r12.mState
            if (r0 >= r13) goto L_0x0289
            boolean r0 = r12.mFromLayout
            if (r0 == 0) goto L_0x0030
            boolean r0 = r12.mInLayout
            if (r0 != 0) goto L_0x0030
        L_0x002f:
            return
        L_0x0030:
            android.view.View r0 = r12.mAnimatingAway
            if (r0 == 0) goto L_0x0041
            r0 = 0
            r12.mAnimatingAway = r0
            int r2 = r12.mStateAfterAnimating
            r3 = 0
            r4 = 0
            r5 = 1
            r0 = r11
            r1 = r12
            r0.moveToState(r1, r2, r3, r4, r5)
        L_0x0041:
            int r0 = r12.mState
            switch(r0) {
                case 0: goto L_0x0049;
                case 1: goto L_0x0147;
                case 2: goto L_0x021d;
                case 3: goto L_0x021d;
                case 4: goto L_0x023f;
                default: goto L_0x0046;
            }
        L_0x0046:
            r12.mState = r13
            goto L_0x002f
        L_0x0049:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x0065
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "moveto CREATED: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r12)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x0065:
            android.os.Bundle r0 = r12.mSavedFragmentState
            if (r0 == 0) goto L_0x00b1
            android.os.Bundle r0 = r12.mSavedFragmentState
            android.support.v4.app.FragmentHostCallback r1 = r11.mHost
            android.content.Context r1 = r1.getContext()
            java.lang.ClassLoader r1 = r1.getClassLoader()
            r0.setClassLoader(r1)
            android.os.Bundle r0 = r12.mSavedFragmentState
            java.lang.String r1 = "android:view_state"
            android.util.SparseArray r0 = r0.getSparseParcelableArray(r1)
            r12.mSavedViewState = r0
            android.os.Bundle r0 = r12.mSavedFragmentState
            java.lang.String r1 = "android:target_state"
            android.support.v4.app.Fragment r0 = r11.getFragment(r0, r1)
            r12.mTarget = r0
            android.support.v4.app.Fragment r0 = r12.mTarget
            if (r0 == 0) goto L_0x009b
            android.os.Bundle r0 = r12.mSavedFragmentState
            java.lang.String r1 = "android:target_req_state"
            r2 = 0
            int r0 = r0.getInt(r1, r2)
            r12.mTargetRequestCode = r0
        L_0x009b:
            android.os.Bundle r0 = r12.mSavedFragmentState
            java.lang.String r1 = "android:user_visible_hint"
            r2 = 1
            boolean r0 = r0.getBoolean(r1, r2)
            r12.mUserVisibleHint = r0
            boolean r0 = r12.mUserVisibleHint
            if (r0 != 0) goto L_0x00b1
            r0 = 1
            r12.mDeferStart = r0
            r0 = 3
            if (r13 <= r0) goto L_0x00b1
            r13 = 3
        L_0x00b1:
            android.support.v4.app.FragmentHostCallback r0 = r11.mHost
            r12.mHost = r0
            android.support.v4.app.Fragment r0 = r11.mParent
            r12.mParentFragment = r0
            android.support.v4.app.Fragment r0 = r11.mParent
            if (r0 == 0) goto L_0x00f2
            android.support.v4.app.Fragment r0 = r11.mParent
            android.support.v4.app.FragmentManagerImpl r0 = r0.mChildFragmentManager
        L_0x00c1:
            r12.mFragmentManager = r0
            r0 = 0
            r12.mCalled = r0
            android.support.v4.app.FragmentHostCallback r0 = r11.mHost
            android.content.Context r0 = r0.getContext()
            r12.onAttach(r0)
            boolean r0 = r12.mCalled
            if (r0 != 0) goto L_0x00f9
            android.support.v4.app.SuperNotCalledException r0 = new android.support.v4.app.SuperNotCalledException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Fragment "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r12)
            java.lang.String r2 = " did not call through to super.onAttach()"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x00f2:
            android.support.v4.app.FragmentHostCallback r0 = r11.mHost
            android.support.v4.app.FragmentManagerImpl r0 = r0.getFragmentManagerImpl()
            goto L_0x00c1
        L_0x00f9:
            android.support.v4.app.Fragment r0 = r12.mParentFragment
            if (r0 != 0) goto L_0x0102
            android.support.v4.app.FragmentHostCallback r0 = r11.mHost
            r0.onAttachFragment(r12)
        L_0x0102:
            boolean r0 = r12.mRetaining
            if (r0 != 0) goto L_0x010b
            android.os.Bundle r0 = r12.mSavedFragmentState
            r12.performCreate(r0)
        L_0x010b:
            r0 = 0
            r12.mRetaining = r0
            boolean r0 = r12.mFromLayout
            if (r0 == 0) goto L_0x0147
            android.os.Bundle r0 = r12.mSavedFragmentState
            android.view.LayoutInflater r0 = r12.getLayoutInflater(r0)
            r1 = 0
            android.os.Bundle r2 = r12.mSavedFragmentState
            android.view.View r0 = r12.performCreateView(r0, r1, r2)
            r12.mView = r0
            android.view.View r0 = r12.mView
            if (r0 == 0) goto L_0x0276
            android.view.View r0 = r12.mView
            r12.mInnerView = r0
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 11
            if (r0 < r1) goto L_0x026c
            android.view.View r0 = r12.mView
            r1 = 0
            android.support.v4.view.ViewCompat.setSaveFromParentEnabled(r0, r1)
        L_0x0135:
            boolean r0 = r12.mHidden
            if (r0 == 0) goto L_0x0140
            android.view.View r0 = r12.mView
            r1 = 8
            r0.setVisibility(r1)
        L_0x0140:
            android.view.View r0 = r12.mView
            android.os.Bundle r1 = r12.mSavedFragmentState
            r12.onViewCreated(r0, r1)
        L_0x0147:
            r0 = 1
            if (r13 <= r0) goto L_0x021d
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x0166
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "moveto ACTIVITY_CREATED: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r12)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x0166:
            boolean r0 = r12.mFromLayout
            if (r0 != 0) goto L_0x020c
            r7 = 0
            int r0 = r12.mContainerId
            if (r0 == 0) goto L_0x01be
            android.support.v4.app.FragmentContainer r0 = r11.mContainer
            int r1 = r12.mContainerId
            android.view.View r7 = r0.onFindViewById(r1)
            android.view.ViewGroup r7 = (android.view.ViewGroup) r7
            if (r7 != 0) goto L_0x01be
            boolean r0 = r12.mRestored
            if (r0 != 0) goto L_0x01be
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "No view found for id 0x"
            java.lang.StringBuilder r1 = r1.append(r2)
            int r2 = r12.mContainerId
            java.lang.String r2 = java.lang.Integer.toHexString(r2)
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = " ("
            java.lang.StringBuilder r1 = r1.append(r2)
            android.content.res.Resources r2 = r12.getResources()
            int r3 = r12.mContainerId
            java.lang.String r2 = r2.getResourceName(r3)
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = ") for fragment "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r12)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            r11.throwException(r0)
        L_0x01be:
            r12.mContainer = r7
            android.os.Bundle r0 = r12.mSavedFragmentState
            android.view.LayoutInflater r0 = r12.getLayoutInflater(r0)
            android.os.Bundle r1 = r12.mSavedFragmentState
            android.view.View r0 = r12.performCreateView(r0, r7, r1)
            r12.mView = r0
            android.view.View r0 = r12.mView
            if (r0 == 0) goto L_0x0285
            android.view.View r0 = r12.mView
            r12.mInnerView = r0
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 11
            if (r0 < r1) goto L_0x027b
            android.view.View r0 = r12.mView
            r1 = 0
            android.support.v4.view.ViewCompat.setSaveFromParentEnabled(r0, r1)
        L_0x01e2:
            if (r7 == 0) goto L_0x01fa
            r0 = 1
            android.view.animation.Animation r6 = r11.loadAnimation(r12, r14, r0, r15)
            if (r6 == 0) goto L_0x01f5
            android.view.View r0 = r12.mView
            r11.setHWLayerAnimListenerIfAlpha(r0, r6)
            android.view.View r0 = r12.mView
            r0.startAnimation(r6)
        L_0x01f5:
            android.view.View r0 = r12.mView
            r7.addView(r0)
        L_0x01fa:
            boolean r0 = r12.mHidden
            if (r0 == 0) goto L_0x0205
            android.view.View r0 = r12.mView
            r1 = 8
            r0.setVisibility(r1)
        L_0x0205:
            android.view.View r0 = r12.mView
            android.os.Bundle r1 = r12.mSavedFragmentState
            r12.onViewCreated(r0, r1)
        L_0x020c:
            android.os.Bundle r0 = r12.mSavedFragmentState
            r12.performActivityCreated(r0)
            android.view.View r0 = r12.mView
            if (r0 == 0) goto L_0x021a
            android.os.Bundle r0 = r12.mSavedFragmentState
            r12.restoreViewState(r0)
        L_0x021a:
            r0 = 0
            r12.mSavedFragmentState = r0
        L_0x021d:
            r0 = 3
            if (r13 <= r0) goto L_0x023f
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x023c
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "moveto STARTED: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r12)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x023c:
            r12.performStart()
        L_0x023f:
            r0 = 4
            if (r13 <= r0) goto L_0x0046
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x025e
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "moveto RESUMED: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r12)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x025e:
            r0 = 1
            r12.mResumed = r0
            r12.performResume()
            r0 = 0
            r12.mSavedFragmentState = r0
            r0 = 0
            r12.mSavedViewState = r0
            goto L_0x0046
        L_0x026c:
            android.view.View r0 = r12.mView
            android.view.ViewGroup r0 = android.support.v4.app.NoSaveStateFrameLayout.wrap(r0)
            r12.mView = r0
            goto L_0x0135
        L_0x0276:
            r0 = 0
            r12.mInnerView = r0
            goto L_0x0147
        L_0x027b:
            android.view.View r0 = r12.mView
            android.view.ViewGroup r0 = android.support.v4.app.NoSaveStateFrameLayout.wrap(r0)
            r12.mView = r0
            goto L_0x01e2
        L_0x0285:
            r0 = 0
            r12.mInnerView = r0
            goto L_0x020c
        L_0x0289:
            int r0 = r12.mState
            if (r0 <= r13) goto L_0x0046
            int r0 = r12.mState
            switch(r0) {
                case 1: goto L_0x0294;
                case 2: goto L_0x0319;
                case 3: goto L_0x02f7;
                case 4: goto L_0x02d5;
                case 5: goto L_0x02b0;
                default: goto L_0x0292;
            }
        L_0x0292:
            goto L_0x0046
        L_0x0294:
            r0 = 1
            if (r13 >= r0) goto L_0x0046
            boolean r0 = r11.mDestroyed
            if (r0 == 0) goto L_0x02a7
            android.view.View r0 = r12.mAnimatingAway
            if (r0 == 0) goto L_0x02a7
            android.view.View r9 = r12.mAnimatingAway
            r0 = 0
            r12.mAnimatingAway = r0
            r9.clearAnimation()
        L_0x02a7:
            android.view.View r0 = r12.mAnimatingAway
            if (r0 == 0) goto L_0x038e
            r12.mStateAfterAnimating = r13
            r13 = 1
            goto L_0x0046
        L_0x02b0:
            r0 = 5
            if (r13 >= r0) goto L_0x02d5
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x02cf
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "movefrom RESUMED: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r12)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x02cf:
            r12.performPause()
            r0 = 0
            r12.mResumed = r0
        L_0x02d5:
            r0 = 4
            if (r13 >= r0) goto L_0x02f7
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x02f4
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "movefrom STARTED: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r12)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x02f4:
            r12.performStop()
        L_0x02f7:
            r0 = 3
            if (r13 >= r0) goto L_0x0319
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x0316
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "movefrom STOPPED: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r12)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x0316:
            r12.performReallyStop()
        L_0x0319:
            r0 = 2
            if (r13 >= r0) goto L_0x0294
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x0338
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "movefrom ACTIVITY_CREATED: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r12)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x0338:
            android.view.View r0 = r12.mView
            if (r0 == 0) goto L_0x034b
            android.support.v4.app.FragmentHostCallback r0 = r11.mHost
            boolean r0 = r0.onShouldSaveFragmentState(r12)
            if (r0 == 0) goto L_0x034b
            android.util.SparseArray<android.os.Parcelable> r0 = r12.mSavedViewState
            if (r0 != 0) goto L_0x034b
            r11.saveFragmentViewState(r12)
        L_0x034b:
            r12.performDestroyView()
            android.view.View r0 = r12.mView
            if (r0 == 0) goto L_0x0383
            android.view.ViewGroup r0 = r12.mContainer
            if (r0 == 0) goto L_0x0383
            r6 = 0
            int r0 = r11.mCurState
            if (r0 <= 0) goto L_0x0364
            boolean r0 = r11.mDestroyed
            if (r0 != 0) goto L_0x0364
            r0 = 0
            android.view.animation.Animation r6 = r11.loadAnimation(r12, r14, r0, r15)
        L_0x0364:
            if (r6 == 0) goto L_0x037c
            r8 = r12
            android.view.View r0 = r12.mView
            r12.mAnimatingAway = r0
            r12.mStateAfterAnimating = r13
            android.view.View r10 = r12.mView
            android.support.v4.app.FragmentManagerImpl$5 r0 = new android.support.v4.app.FragmentManagerImpl$5
            r0.<init>(r10, r6, r8)
            r6.setAnimationListener(r0)
            android.view.View r0 = r12.mView
            r0.startAnimation(r6)
        L_0x037c:
            android.view.ViewGroup r0 = r12.mContainer
            android.view.View r1 = r12.mView
            r0.removeView(r1)
        L_0x0383:
            r0 = 0
            r12.mContainer = r0
            r0 = 0
            r12.mView = r0
            r0 = 0
            r12.mInnerView = r0
            goto L_0x0294
        L_0x038e:
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x03aa
            java.lang.String r0 = "FragmentManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "movefrom CREATED: "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r12)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
        L_0x03aa:
            boolean r0 = r12.mRetaining
            if (r0 != 0) goto L_0x03b1
            r12.performDestroy()
        L_0x03b1:
            r0 = 0
            r12.mCalled = r0
            r12.onDetach()
            boolean r0 = r12.mCalled
            if (r0 != 0) goto L_0x03da
            android.support.v4.app.SuperNotCalledException r0 = new android.support.v4.app.SuperNotCalledException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Fragment "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r12)
            java.lang.String r2 = " did not call through to super.onDetach()"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x03da:
            if (r16 != 0) goto L_0x0046
            boolean r0 = r12.mRetaining
            if (r0 != 0) goto L_0x03e5
            r11.makeInactive(r12)
            goto L_0x0046
        L_0x03e5:
            r0 = 0
            r12.mHost = r0
            r0 = 0
            r12.mParentFragment = r0
            r0 = 0
            r12.mFragmentManager = r0
            r0 = 0
            r12.mChildFragmentManager = r0
            goto L_0x0046
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.moveToState(android.support.v4.app.Fragment, int, int, int, boolean):void");
    }

    /* access modifiers changed from: 0000 */
    public void moveToState(Fragment f) {
        moveToState(f, this.mCurState, 0, 0, false);
    }

    /* access modifiers changed from: 0000 */
    public void moveToState(int newState, boolean always) {
        moveToState(newState, 0, 0, always);
    }

    /* access modifiers changed from: 0000 */
    public void moveToState(int newState, int transit, int transitStyle, boolean always) {
        if (this.mHost == null && newState != 0) {
            throw new IllegalStateException("No host");
        } else if (always || this.mCurState != newState) {
            this.mCurState = newState;
            if (this.mActive != null) {
                boolean loadersRunning = false;
                for (int i = 0; i < this.mActive.size(); i++) {
                    Fragment f = (Fragment) this.mActive.get(i);
                    if (f != null) {
                        moveToState(f, newState, transit, transitStyle, false);
                        if (f.mLoaderManager != null) {
                            loadersRunning |= f.mLoaderManager.hasRunningLoaders();
                        }
                    }
                }
                if (!loadersRunning) {
                    startPendingDeferredFragments();
                }
                if (this.mNeedMenuInvalidate && this.mHost != null && this.mCurState == 5) {
                    this.mHost.onSupportInvalidateOptionsMenu();
                    this.mNeedMenuInvalidate = false;
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void startPendingDeferredFragments() {
        if (this.mActive != null) {
            for (int i = 0; i < this.mActive.size(); i++) {
                Fragment f = (Fragment) this.mActive.get(i);
                if (f != null) {
                    performPendingDeferredStart(f);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void makeActive(Fragment f) {
        if (f.mIndex < 0) {
            if (this.mAvailIndices == null || this.mAvailIndices.size() <= 0) {
                if (this.mActive == null) {
                    this.mActive = new ArrayList<>();
                }
                f.setIndex(this.mActive.size(), this.mParent);
                this.mActive.add(f);
            } else {
                f.setIndex(((Integer) this.mAvailIndices.remove(this.mAvailIndices.size() - 1)).intValue(), this.mParent);
                this.mActive.set(f.mIndex, f);
            }
            if (DEBUG) {
                Log.v(TAG, "Allocated fragment index " + f);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void makeInactive(Fragment f) {
        if (f.mIndex >= 0) {
            if (DEBUG) {
                Log.v(TAG, "Freeing fragment index " + f);
            }
            this.mActive.set(f.mIndex, null);
            if (this.mAvailIndices == null) {
                this.mAvailIndices = new ArrayList<>();
            }
            this.mAvailIndices.add(Integer.valueOf(f.mIndex));
            this.mHost.inactivateFragment(f.mWho);
            f.initState();
        }
    }

    public void addFragment(Fragment fragment, boolean moveToStateNow) {
        if (this.mAdded == null) {
            this.mAdded = new ArrayList<>();
        }
        if (DEBUG) {
            Log.v(TAG, "add: " + fragment);
        }
        makeActive(fragment);
        if (fragment.mDetached) {
            return;
        }
        if (this.mAdded.contains(fragment)) {
            throw new IllegalStateException("Fragment already added: " + fragment);
        }
        this.mAdded.add(fragment);
        fragment.mAdded = true;
        fragment.mRemoving = false;
        if (fragment.mHasMenu && fragment.mMenuVisible) {
            this.mNeedMenuInvalidate = true;
        }
        if (moveToStateNow) {
            moveToState(fragment);
        }
    }

    public void removeFragment(Fragment fragment, int transition, int transitionStyle) {
        boolean inactive;
        int i;
        if (DEBUG) {
            Log.v(TAG, "remove: " + fragment + " nesting=" + fragment.mBackStackNesting);
        }
        if (!fragment.isInBackStack()) {
            inactive = true;
        } else {
            inactive = false;
        }
        if (!fragment.mDetached || inactive) {
            if (this.mAdded != null) {
                this.mAdded.remove(fragment);
            }
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.mAdded = false;
            fragment.mRemoving = true;
            if (inactive) {
                i = 0;
            } else {
                i = 1;
            }
            moveToState(fragment, i, transition, transitionStyle, false);
        }
    }

    public void hideFragment(Fragment fragment, int transition, int transitionStyle) {
        if (DEBUG) {
            Log.v(TAG, "hide: " + fragment);
        }
        if (!fragment.mHidden) {
            fragment.mHidden = true;
            if (fragment.mView != null) {
                Animation anim = loadAnimation(fragment, transition, false, transitionStyle);
                if (anim != null) {
                    setHWLayerAnimListenerIfAlpha(fragment.mView, anim);
                    fragment.mView.startAnimation(anim);
                }
                fragment.mView.setVisibility(8);
            }
            if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.onHiddenChanged(true);
        }
    }

    public void showFragment(Fragment fragment, int transition, int transitionStyle) {
        if (DEBUG) {
            Log.v(TAG, "show: " + fragment);
        }
        if (fragment.mHidden) {
            fragment.mHidden = false;
            if (fragment.mView != null) {
                Animation anim = loadAnimation(fragment, transition, true, transitionStyle);
                if (anim != null) {
                    setHWLayerAnimListenerIfAlpha(fragment.mView, anim);
                    fragment.mView.startAnimation(anim);
                }
                fragment.mView.setVisibility(0);
            }
            if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible) {
                this.mNeedMenuInvalidate = true;
            }
            fragment.onHiddenChanged(false);
        }
    }

    public void detachFragment(Fragment fragment, int transition, int transitionStyle) {
        if (DEBUG) {
            Log.v(TAG, "detach: " + fragment);
        }
        if (!fragment.mDetached) {
            fragment.mDetached = true;
            if (fragment.mAdded) {
                if (this.mAdded != null) {
                    if (DEBUG) {
                        Log.v(TAG, "remove from detach: " + fragment);
                    }
                    this.mAdded.remove(fragment);
                }
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.mNeedMenuInvalidate = true;
                }
                fragment.mAdded = false;
                moveToState(fragment, 1, transition, transitionStyle, false);
            }
        }
    }

    public void attachFragment(Fragment fragment, int transition, int transitionStyle) {
        if (DEBUG) {
            Log.v(TAG, "attach: " + fragment);
        }
        if (fragment.mDetached) {
            fragment.mDetached = false;
            if (!fragment.mAdded) {
                if (this.mAdded == null) {
                    this.mAdded = new ArrayList<>();
                }
                if (this.mAdded.contains(fragment)) {
                    throw new IllegalStateException("Fragment already added: " + fragment);
                }
                if (DEBUG) {
                    Log.v(TAG, "add from attach: " + fragment);
                }
                this.mAdded.add(fragment);
                fragment.mAdded = true;
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.mNeedMenuInvalidate = true;
                }
                moveToState(fragment, this.mCurState, transition, transitionStyle, false);
            }
        }
    }

    public Fragment findFragmentById(int id) {
        if (this.mAdded != null) {
            for (int i = this.mAdded.size() - 1; i >= 0; i--) {
                Fragment f = (Fragment) this.mAdded.get(i);
                if (f != null && f.mFragmentId == id) {
                    return f;
                }
            }
        }
        if (this.mActive != null) {
            for (int i2 = this.mActive.size() - 1; i2 >= 0; i2--) {
                Fragment f2 = (Fragment) this.mActive.get(i2);
                if (f2 != null && f2.mFragmentId == id) {
                    return f2;
                }
            }
        }
        return null;
    }

    public Fragment findFragmentByTag(String tag) {
        if (!(this.mAdded == null || tag == null)) {
            for (int i = this.mAdded.size() - 1; i >= 0; i--) {
                Fragment f = (Fragment) this.mAdded.get(i);
                if (f != null && tag.equals(f.mTag)) {
                    return f;
                }
            }
        }
        if (!(this.mActive == null || tag == null)) {
            for (int i2 = this.mActive.size() - 1; i2 >= 0; i2--) {
                Fragment f2 = (Fragment) this.mActive.get(i2);
                if (f2 != null && tag.equals(f2.mTag)) {
                    return f2;
                }
            }
        }
        return null;
    }

    public Fragment findFragmentByWho(String who) {
        if (!(this.mActive == null || who == null)) {
            for (int i = this.mActive.size() - 1; i >= 0; i--) {
                Fragment f = (Fragment) this.mActive.get(i);
                if (f != null) {
                    Fragment f2 = f.findFragmentByWho(who);
                    if (f2 != null) {
                        return f2;
                    }
                }
            }
        }
        return null;
    }

    private void checkStateLoss() {
        if (this.mStateSaved) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        } else if (this.mNoTransactionsBecause != null) {
            throw new IllegalStateException("Can not perform this action inside of " + this.mNoTransactionsBecause);
        }
    }

    public void enqueueAction(Runnable action, boolean allowStateLoss) {
        if (!allowStateLoss) {
            checkStateLoss();
        }
        synchronized (this) {
            if (this.mDestroyed || this.mHost == null) {
                throw new IllegalStateException("Activity has been destroyed");
            }
            if (this.mPendingActions == null) {
                this.mPendingActions = new ArrayList<>();
            }
            this.mPendingActions.add(action);
            if (this.mPendingActions.size() == 1) {
                this.mHost.getHandler().removeCallbacks(this.mExecCommit);
                this.mHost.getHandler().post(this.mExecCommit);
            }
        }
    }

    public int allocBackStackIndex(BackStackRecord bse) {
        synchronized (this) {
            if (this.mAvailBackStackIndices == null || this.mAvailBackStackIndices.size() <= 0) {
                if (this.mBackStackIndices == null) {
                    this.mBackStackIndices = new ArrayList<>();
                }
                int index = this.mBackStackIndices.size();
                if (DEBUG) {
                    Log.v(TAG, "Setting back stack index " + index + " to " + bse);
                }
                this.mBackStackIndices.add(bse);
                return index;
            }
            int index2 = ((Integer) this.mAvailBackStackIndices.remove(this.mAvailBackStackIndices.size() - 1)).intValue();
            if (DEBUG) {
                Log.v(TAG, "Adding back stack index " + index2 + " with " + bse);
            }
            this.mBackStackIndices.set(index2, bse);
            return index2;
        }
    }

    public void setBackStackIndex(int index, BackStackRecord bse) {
        synchronized (this) {
            if (this.mBackStackIndices == null) {
                this.mBackStackIndices = new ArrayList<>();
            }
            int N = this.mBackStackIndices.size();
            if (index < N) {
                if (DEBUG) {
                    Log.v(TAG, "Setting back stack index " + index + " to " + bse);
                }
                this.mBackStackIndices.set(index, bse);
            } else {
                while (N < index) {
                    this.mBackStackIndices.add(null);
                    if (this.mAvailBackStackIndices == null) {
                        this.mAvailBackStackIndices = new ArrayList<>();
                    }
                    if (DEBUG) {
                        Log.v(TAG, "Adding available back stack index " + N);
                    }
                    this.mAvailBackStackIndices.add(Integer.valueOf(N));
                    N++;
                }
                if (DEBUG) {
                    Log.v(TAG, "Adding back stack index " + index + " with " + bse);
                }
                this.mBackStackIndices.add(bse);
            }
        }
    }

    public void freeBackStackIndex(int index) {
        synchronized (this) {
            this.mBackStackIndices.set(index, null);
            if (this.mAvailBackStackIndices == null) {
                this.mAvailBackStackIndices = new ArrayList<>();
            }
            if (DEBUG) {
                Log.v(TAG, "Freeing back stack index " + index);
            }
            this.mAvailBackStackIndices.add(Integer.valueOf(index));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0085, code lost:
        r8.mExecutingActions = true;
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0089, code lost:
        if (r2 >= r4) goto L_0x009d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x008b, code lost:
        r8.mTmpActions[r2].run();
        r8.mTmpActions[r2] = null;
        r2 = r2 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean execPendingActions() {
        /*
            r8 = this;
            r7 = 0
            boolean r5 = r8.mExecutingActions
            if (r5 == 0) goto L_0x000d
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "Recursive entry to executePendingTransactions"
            r5.<init>(r6)
            throw r5
        L_0x000d:
            android.os.Looper r5 = android.os.Looper.myLooper()
            android.support.v4.app.FragmentHostCallback r6 = r8.mHost
            android.os.Handler r6 = r6.getHandler()
            android.os.Looper r6 = r6.getLooper()
            if (r5 == r6) goto L_0x0025
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "Must be called from main thread of process"
            r5.<init>(r6)
            throw r5
        L_0x0025:
            r0 = 0
        L_0x0026:
            monitor-enter(r8)
            java.util.ArrayList<java.lang.Runnable> r5 = r8.mPendingActions     // Catch:{ all -> 0x009a }
            if (r5 == 0) goto L_0x0033
            java.util.ArrayList<java.lang.Runnable> r5 = r8.mPendingActions     // Catch:{ all -> 0x009a }
            int r5 = r5.size()     // Catch:{ all -> 0x009a }
            if (r5 != 0) goto L_0x005a
        L_0x0033:
            monitor-exit(r8)     // Catch:{ all -> 0x009a }
            boolean r5 = r8.mHavePendingDeferredStart
            if (r5 == 0) goto L_0x00a8
            r3 = 0
            r2 = 0
        L_0x003a:
            java.util.ArrayList<android.support.v4.app.Fragment> r5 = r8.mActive
            int r5 = r5.size()
            if (r2 >= r5) goto L_0x00a1
            java.util.ArrayList<android.support.v4.app.Fragment> r5 = r8.mActive
            java.lang.Object r1 = r5.get(r2)
            android.support.v4.app.Fragment r1 = (android.support.v4.app.Fragment) r1
            if (r1 == 0) goto L_0x0057
            android.support.v4.app.LoaderManagerImpl r5 = r1.mLoaderManager
            if (r5 == 0) goto L_0x0057
            android.support.v4.app.LoaderManagerImpl r5 = r1.mLoaderManager
            boolean r5 = r5.hasRunningLoaders()
            r3 = r3 | r5
        L_0x0057:
            int r2 = r2 + 1
            goto L_0x003a
        L_0x005a:
            java.util.ArrayList<java.lang.Runnable> r5 = r8.mPendingActions     // Catch:{ all -> 0x009a }
            int r4 = r5.size()     // Catch:{ all -> 0x009a }
            java.lang.Runnable[] r5 = r8.mTmpActions     // Catch:{ all -> 0x009a }
            if (r5 == 0) goto L_0x0069
            java.lang.Runnable[] r5 = r8.mTmpActions     // Catch:{ all -> 0x009a }
            int r5 = r5.length     // Catch:{ all -> 0x009a }
            if (r5 >= r4) goto L_0x006d
        L_0x0069:
            java.lang.Runnable[] r5 = new java.lang.Runnable[r4]     // Catch:{ all -> 0x009a }
            r8.mTmpActions = r5     // Catch:{ all -> 0x009a }
        L_0x006d:
            java.util.ArrayList<java.lang.Runnable> r5 = r8.mPendingActions     // Catch:{ all -> 0x009a }
            java.lang.Runnable[] r6 = r8.mTmpActions     // Catch:{ all -> 0x009a }
            r5.toArray(r6)     // Catch:{ all -> 0x009a }
            java.util.ArrayList<java.lang.Runnable> r5 = r8.mPendingActions     // Catch:{ all -> 0x009a }
            r5.clear()     // Catch:{ all -> 0x009a }
            android.support.v4.app.FragmentHostCallback r5 = r8.mHost     // Catch:{ all -> 0x009a }
            android.os.Handler r5 = r5.getHandler()     // Catch:{ all -> 0x009a }
            java.lang.Runnable r6 = r8.mExecCommit     // Catch:{ all -> 0x009a }
            r5.removeCallbacks(r6)     // Catch:{ all -> 0x009a }
            monitor-exit(r8)     // Catch:{ all -> 0x009a }
            r5 = 1
            r8.mExecutingActions = r5
            r2 = 0
        L_0x0089:
            if (r2 >= r4) goto L_0x009d
            java.lang.Runnable[] r5 = r8.mTmpActions
            r5 = r5[r2]
            r5.run()
            java.lang.Runnable[] r5 = r8.mTmpActions
            r6 = 0
            r5[r2] = r6
            int r2 = r2 + 1
            goto L_0x0089
        L_0x009a:
            r5 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x009a }
            throw r5
        L_0x009d:
            r8.mExecutingActions = r7
            r0 = 1
            goto L_0x0026
        L_0x00a1:
            if (r3 != 0) goto L_0x00a8
            r8.mHavePendingDeferredStart = r7
            r8.startPendingDeferredFragments()
        L_0x00a8:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.execPendingActions():boolean");
    }

    /* access modifiers changed from: 0000 */
    public void reportBackStackChanged() {
        if (this.mBackStackChangeListeners != null) {
            for (int i = 0; i < this.mBackStackChangeListeners.size(); i++) {
                ((OnBackStackChangedListener) this.mBackStackChangeListeners.get(i)).onBackStackChanged();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void addBackStackState(BackStackRecord state) {
        if (this.mBackStack == null) {
            this.mBackStack = new ArrayList<>();
        }
        this.mBackStack.add(state);
        reportBackStackChanged();
    }

    /* access modifiers changed from: 0000 */
    public boolean popBackStackState(Handler handler, String name, int id, int flags) {
        if (this.mBackStack == null) {
            return false;
        }
        if (name == null && id < 0 && (flags & 1) == 0) {
            int last = this.mBackStack.size() - 1;
            if (last < 0) {
                return false;
            }
            BackStackRecord bss = (BackStackRecord) this.mBackStack.remove(last);
            SparseArray<Fragment> firstOutFragments = new SparseArray<>();
            SparseArray<Fragment> lastInFragments = new SparseArray<>();
            bss.calculateBackFragments(firstOutFragments, lastInFragments);
            bss.popFromBackStack(true, null, firstOutFragments, lastInFragments);
            reportBackStackChanged();
        } else {
            int index = -1;
            if (name != null || id >= 0) {
                int index2 = this.mBackStack.size() - 1;
                while (index >= 0) {
                    BackStackRecord bss2 = (BackStackRecord) this.mBackStack.get(index);
                    if ((name != null && name.equals(bss2.getName())) || (id >= 0 && id == bss2.mIndex)) {
                        break;
                    }
                    index2 = index - 1;
                }
                if (index < 0) {
                    return false;
                }
                if ((flags & 1) != 0) {
                    index--;
                    while (index >= 0) {
                        BackStackRecord bss3 = (BackStackRecord) this.mBackStack.get(index);
                        if ((name == null || !name.equals(bss3.getName())) && (id < 0 || id != bss3.mIndex)) {
                            break;
                        }
                        index--;
                    }
                }
            }
            if (index == this.mBackStack.size() - 1) {
                return false;
            }
            ArrayList<BackStackRecord> states = new ArrayList<>();
            for (int i = this.mBackStack.size() - 1; i > index; i--) {
                states.add(this.mBackStack.remove(i));
            }
            int LAST = states.size() - 1;
            SparseArray<Fragment> firstOutFragments2 = new SparseArray<>();
            SparseArray<Fragment> lastInFragments2 = new SparseArray<>();
            for (int i2 = 0; i2 <= LAST; i2++) {
                ((BackStackRecord) states.get(i2)).calculateBackFragments(firstOutFragments2, lastInFragments2);
            }
            TransitionState state = null;
            int i3 = 0;
            while (i3 <= LAST) {
                if (DEBUG) {
                    Log.v(TAG, "Popping back stack state: " + states.get(i3));
                }
                state = ((BackStackRecord) states.get(i3)).popFromBackStack(i3 == LAST, state, firstOutFragments2, lastInFragments2);
                i3++;
            }
            reportBackStackChanged();
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public ArrayList<Fragment> retainNonConfig() {
        ArrayList<Fragment> fragments = null;
        if (this.mActive != null) {
            for (int i = 0; i < this.mActive.size(); i++) {
                Fragment f = (Fragment) this.mActive.get(i);
                if (f != null && f.mRetainInstance) {
                    if (fragments == null) {
                        fragments = new ArrayList<>();
                    }
                    fragments.add(f);
                    f.mRetaining = true;
                    f.mTargetIndex = f.mTarget != null ? f.mTarget.mIndex : -1;
                    if (DEBUG) {
                        Log.v(TAG, "retainNonConfig: keeping retained " + f);
                    }
                }
            }
        }
        return fragments;
    }

    /* access modifiers changed from: 0000 */
    public void saveFragmentViewState(Fragment f) {
        if (f.mInnerView != null) {
            if (this.mStateArray == null) {
                this.mStateArray = new SparseArray<>();
            } else {
                this.mStateArray.clear();
            }
            f.mInnerView.saveHierarchyState(this.mStateArray);
            if (this.mStateArray.size() > 0) {
                f.mSavedViewState = this.mStateArray;
                this.mStateArray = null;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public Bundle saveFragmentBasicState(Fragment f) {
        Bundle result = null;
        if (this.mStateBundle == null) {
            this.mStateBundle = new Bundle();
        }
        f.performSaveInstanceState(this.mStateBundle);
        if (!this.mStateBundle.isEmpty()) {
            result = this.mStateBundle;
            this.mStateBundle = null;
        }
        if (f.mView != null) {
            saveFragmentViewState(f);
        }
        if (f.mSavedViewState != null) {
            if (result == null) {
                result = new Bundle();
            }
            result.putSparseParcelableArray(VIEW_STATE_TAG, f.mSavedViewState);
        }
        if (!f.mUserVisibleHint) {
            if (result == null) {
                result = new Bundle();
            }
            result.putBoolean(USER_VISIBLE_HINT_TAG, f.mUserVisibleHint);
        }
        return result;
    }

    /* access modifiers changed from: 0000 */
    public Parcelable saveAllState() {
        execPendingActions();
        if (HONEYCOMB) {
            this.mStateSaved = true;
        }
        if (this.mActive == null || this.mActive.size() <= 0) {
            return null;
        }
        int N = this.mActive.size();
        FragmentState[] active = new FragmentState[N];
        boolean haveFragments = false;
        for (int i = 0; i < N; i++) {
            Fragment f = (Fragment) this.mActive.get(i);
            if (f != null) {
                if (f.mIndex < 0) {
                    throwException(new IllegalStateException("Failure saving state: active " + f + " has cleared index: " + f.mIndex));
                }
                haveFragments = true;
                FragmentState fs = new FragmentState(f);
                active[i] = fs;
                if (f.mState <= 0 || fs.mSavedFragmentState != null) {
                    fs.mSavedFragmentState = f.mSavedFragmentState;
                } else {
                    fs.mSavedFragmentState = saveFragmentBasicState(f);
                    if (f.mTarget != null) {
                        if (f.mTarget.mIndex < 0) {
                            throwException(new IllegalStateException("Failure saving state: " + f + " has target not in fragment manager: " + f.mTarget));
                        }
                        if (fs.mSavedFragmentState == null) {
                            fs.mSavedFragmentState = new Bundle();
                        }
                        putFragment(fs.mSavedFragmentState, TARGET_STATE_TAG, f.mTarget);
                        if (f.mTargetRequestCode != 0) {
                            fs.mSavedFragmentState.putInt(TARGET_REQUEST_CODE_STATE_TAG, f.mTargetRequestCode);
                        }
                    }
                }
                if (DEBUG) {
                    Log.v(TAG, "Saved state of " + f + ": " + fs.mSavedFragmentState);
                }
            }
        }
        if (haveFragments) {
            int[] added = null;
            BackStackState[] backStack = null;
            if (this.mAdded != null) {
                int N2 = this.mAdded.size();
                if (N2 > 0) {
                    added = new int[N2];
                    for (int i2 = 0; i2 < N2; i2++) {
                        added[i2] = ((Fragment) this.mAdded.get(i2)).mIndex;
                        if (added[i2] < 0) {
                            throwException(new IllegalStateException("Failure saving state: active " + this.mAdded.get(i2) + " has cleared index: " + added[i2]));
                        }
                        if (DEBUG) {
                            Log.v(TAG, "saveAllState: adding fragment #" + i2 + ": " + this.mAdded.get(i2));
                        }
                    }
                }
            }
            if (this.mBackStack != null) {
                int N3 = this.mBackStack.size();
                if (N3 > 0) {
                    backStack = new BackStackState[N3];
                    for (int i3 = 0; i3 < N3; i3++) {
                        backStack[i3] = new BackStackState((BackStackRecord) this.mBackStack.get(i3));
                        if (DEBUG) {
                            Log.v(TAG, "saveAllState: adding back stack #" + i3 + ": " + this.mBackStack.get(i3));
                        }
                    }
                }
            }
            FragmentManagerState fms = new FragmentManagerState();
            fms.mActive = active;
            fms.mAdded = added;
            fms.mBackStack = backStack;
            return fms;
        } else if (!DEBUG) {
            return null;
        } else {
            Log.v(TAG, "saveAllState: no fragments!");
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public void restoreAllState(Parcelable state, List<Fragment> nonConfig) {
        if (state != null) {
            FragmentManagerState fms = (FragmentManagerState) state;
            if (fms.mActive != null) {
                if (nonConfig != null) {
                    for (int i = 0; i < nonConfig.size(); i++) {
                        Fragment f = (Fragment) nonConfig.get(i);
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: re-attaching retained " + f);
                        }
                        FragmentState fs = fms.mActive[f.mIndex];
                        fs.mInstance = f;
                        f.mSavedViewState = null;
                        f.mBackStackNesting = 0;
                        f.mInLayout = false;
                        f.mAdded = false;
                        f.mTarget = null;
                        if (fs.mSavedFragmentState != null) {
                            fs.mSavedFragmentState.setClassLoader(this.mHost.getContext().getClassLoader());
                            f.mSavedViewState = fs.mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
                            f.mSavedFragmentState = fs.mSavedFragmentState;
                        }
                    }
                }
                this.mActive = new ArrayList<>(fms.mActive.length);
                if (this.mAvailIndices != null) {
                    this.mAvailIndices.clear();
                }
                for (int i2 = 0; i2 < fms.mActive.length; i2++) {
                    FragmentState fs2 = fms.mActive[i2];
                    if (fs2 != null) {
                        Fragment f2 = fs2.instantiate(this.mHost, this.mParent);
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: active #" + i2 + ": " + f2);
                        }
                        this.mActive.add(f2);
                        fs2.mInstance = null;
                    } else {
                        this.mActive.add(null);
                        if (this.mAvailIndices == null) {
                            this.mAvailIndices = new ArrayList<>();
                        }
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: avail #" + i2);
                        }
                        this.mAvailIndices.add(Integer.valueOf(i2));
                    }
                }
                if (nonConfig != null) {
                    for (int i3 = 0; i3 < nonConfig.size(); i3++) {
                        Fragment f3 = (Fragment) nonConfig.get(i3);
                        if (f3.mTargetIndex >= 0) {
                            if (f3.mTargetIndex < this.mActive.size()) {
                                f3.mTarget = (Fragment) this.mActive.get(f3.mTargetIndex);
                            } else {
                                Log.w(TAG, "Re-attaching retained fragment " + f3 + " target no longer exists: " + f3.mTargetIndex);
                                f3.mTarget = null;
                            }
                        }
                    }
                }
                if (fms.mAdded != null) {
                    this.mAdded = new ArrayList<>(fms.mAdded.length);
                    for (int i4 = 0; i4 < fms.mAdded.length; i4++) {
                        Fragment f4 = (Fragment) this.mActive.get(fms.mAdded[i4]);
                        if (f4 == null) {
                            throwException(new IllegalStateException("No instantiated fragment for index #" + fms.mAdded[i4]));
                        }
                        f4.mAdded = true;
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: added #" + i4 + ": " + f4);
                        }
                        if (this.mAdded.contains(f4)) {
                            throw new IllegalStateException("Already added!");
                        }
                        this.mAdded.add(f4);
                    }
                } else {
                    this.mAdded = null;
                }
                if (fms.mBackStack != null) {
                    this.mBackStack = new ArrayList<>(fms.mBackStack.length);
                    for (int i5 = 0; i5 < fms.mBackStack.length; i5++) {
                        BackStackRecord bse = fms.mBackStack[i5].instantiate(this);
                        if (DEBUG) {
                            Log.v(TAG, "restoreAllState: back stack #" + i5 + " (index " + bse.mIndex + "): " + bse);
                            bse.dump("  ", new PrintWriter(new LogWriter(TAG)), false);
                        }
                        this.mBackStack.add(bse);
                        if (bse.mIndex >= 0) {
                            setBackStackIndex(bse.mIndex, bse);
                        }
                    }
                    return;
                }
                this.mBackStack = null;
            }
        }
    }

    public void attachController(FragmentHostCallback host, FragmentContainer container, Fragment parent) {
        if (this.mHost != null) {
            throw new IllegalStateException("Already attached");
        }
        this.mHost = host;
        this.mContainer = container;
        this.mParent = parent;
    }

    public void noteStateNotSaved() {
        this.mStateSaved = false;
    }

    public void dispatchCreate() {
        this.mStateSaved = false;
        moveToState(1, false);
    }

    public void dispatchActivityCreated() {
        this.mStateSaved = false;
        moveToState(2, false);
    }

    public void dispatchStart() {
        this.mStateSaved = false;
        moveToState(4, false);
    }

    public void dispatchResume() {
        this.mStateSaved = false;
        moveToState(5, false);
    }

    public void dispatchPause() {
        moveToState(4, false);
    }

    public void dispatchStop() {
        this.mStateSaved = true;
        moveToState(3, false);
    }

    public void dispatchReallyStop() {
        moveToState(2, false);
    }

    public void dispatchDestroyView() {
        moveToState(1, false);
    }

    public void dispatchDestroy() {
        this.mDestroyed = true;
        execPendingActions();
        moveToState(0, false);
        this.mHost = null;
        this.mContainer = null;
        this.mParent = null;
    }

    public void dispatchConfigurationChanged(Configuration newConfig) {
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment f = (Fragment) this.mAdded.get(i);
                if (f != null) {
                    f.performConfigurationChanged(newConfig);
                }
            }
        }
    }

    public void dispatchLowMemory() {
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment f = (Fragment) this.mAdded.get(i);
                if (f != null) {
                    f.performLowMemory();
                }
            }
        }
    }

    public boolean dispatchCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        boolean show = false;
        ArrayList<Fragment> newMenus = null;
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment f = (Fragment) this.mAdded.get(i);
                if (f != null && f.performCreateOptionsMenu(menu, inflater)) {
                    show = true;
                    if (newMenus == null) {
                        newMenus = new ArrayList<>();
                    }
                    newMenus.add(f);
                }
            }
        }
        if (this.mCreatedMenus != null) {
            for (int i2 = 0; i2 < this.mCreatedMenus.size(); i2++) {
                Fragment f2 = (Fragment) this.mCreatedMenus.get(i2);
                if (newMenus == null || !newMenus.contains(f2)) {
                    f2.onDestroyOptionsMenu();
                }
            }
        }
        this.mCreatedMenus = newMenus;
        return show;
    }

    public boolean dispatchPrepareOptionsMenu(Menu menu) {
        boolean show = false;
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment f = (Fragment) this.mAdded.get(i);
                if (f != null && f.performPrepareOptionsMenu(menu)) {
                    show = true;
                }
            }
        }
        return show;
    }

    public boolean dispatchOptionsItemSelected(MenuItem item) {
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment f = (Fragment) this.mAdded.get(i);
                if (f != null && f.performOptionsItemSelected(item)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dispatchContextItemSelected(MenuItem item) {
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment f = (Fragment) this.mAdded.get(i);
                if (f != null && f.performContextItemSelected(item)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void dispatchOptionsMenuClosed(Menu menu) {
        if (this.mAdded != null) {
            for (int i = 0; i < this.mAdded.size(); i++) {
                Fragment f = (Fragment) this.mAdded.get(i);
                if (f != null) {
                    f.performOptionsMenuClosed(menu);
                }
            }
        }
    }

    public static int reverseTransit(int transit) {
        switch (transit) {
            case FragmentTransaction.TRANSIT_FRAGMENT_OPEN /*4097*/:
                return 8194;
            case FragmentTransaction.TRANSIT_FRAGMENT_FADE /*4099*/:
                return FragmentTransaction.TRANSIT_FRAGMENT_FADE;
            case 8194:
                return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
            default:
                return 0;
        }
    }

    public static int transitToStyleIndex(int transit, boolean enter) {
        switch (transit) {
            case FragmentTransaction.TRANSIT_FRAGMENT_OPEN /*4097*/:
                return enter ? 1 : 2;
            case FragmentTransaction.TRANSIT_FRAGMENT_FADE /*4099*/:
                return enter ? 5 : 6;
            case 8194:
                return enter ? 3 : 4;
            default:
                return -1;
        }
    }

    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        int containerId;
        Fragment fragment;
        int i;
        if (!"fragment".equals(name)) {
            return null;
        }
        String fname = attrs.getAttributeValue(null, "class");
        TypedArray a = context.obtainStyledAttributes(attrs, FragmentTag.Fragment);
        if (fname == null) {
            fname = a.getString(0);
        }
        int id = a.getResourceId(1, -1);
        String tag = a.getString(2);
        a.recycle();
        if (!Fragment.isSupportFragmentClass(this.mHost.getContext(), fname)) {
            return null;
        }
        if (parent != null) {
            containerId = parent.getId();
        } else {
            containerId = 0;
        }
        if (containerId == -1 && id == -1 && tag == null) {
            throw new IllegalArgumentException(attrs.getPositionDescription() + ": Must specify unique android:id, android:tag, or have a parent with an id for " + fname);
        }
        if (id != -1) {
            fragment = findFragmentById(id);
        } else {
            fragment = null;
        }
        if (fragment == null && tag != null) {
            fragment = findFragmentByTag(tag);
        }
        if (fragment == null && containerId != -1) {
            fragment = findFragmentById(containerId);
        }
        if (DEBUG) {
            Log.v(TAG, "onCreateView: id=0x" + Integer.toHexString(id) + " fname=" + fname + " existing=" + fragment);
        }
        if (fragment == null) {
            fragment = Fragment.instantiate(context, fname);
            fragment.mFromLayout = true;
            if (id != 0) {
                i = id;
            } else {
                i = containerId;
            }
            fragment.mFragmentId = i;
            fragment.mContainerId = containerId;
            fragment.mTag = tag;
            fragment.mInLayout = true;
            fragment.mFragmentManager = this;
            fragment.mHost = this.mHost;
            fragment.onInflate(this.mHost.getContext(), attrs, fragment.mSavedFragmentState);
            addFragment(fragment, true);
        } else if (fragment.mInLayout) {
            throw new IllegalArgumentException(attrs.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(id) + ", tag " + tag + ", or parent id 0x" + Integer.toHexString(containerId) + " with another fragment for " + fname);
        } else {
            fragment.mInLayout = true;
            if (!fragment.mRetaining) {
                fragment.onInflate(this.mHost.getContext(), attrs, fragment.mSavedFragmentState);
            }
        }
        if (this.mCurState >= 1 || !fragment.mFromLayout) {
            moveToState(fragment);
        } else {
            moveToState(fragment, 1, 0, 0, false);
        }
        if (fragment.mView == null) {
            throw new IllegalStateException("Fragment " + fname + " did not create a view.");
        }
        if (id != 0) {
            fragment.mView.setId(id);
        }
        if (fragment.mView.getTag() == null) {
            fragment.mView.setTag(tag);
        }
        return fragment.mView;
    }

    /* access modifiers changed from: 0000 */
    public LayoutInflaterFactory getLayoutInflaterFactory() {
        return this;
    }
}
