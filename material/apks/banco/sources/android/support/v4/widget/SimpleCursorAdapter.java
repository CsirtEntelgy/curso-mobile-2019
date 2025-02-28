package android.support.v4.widget;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleCursorAdapter extends ResourceCursorAdapter {
    String[] a;
    private int b = -1;
    private CursorToStringConverter c;
    private ViewBinder d;
    @RestrictTo({Scope.LIBRARY_GROUP})
    protected int[] mFrom;
    @RestrictTo({Scope.LIBRARY_GROUP})
    protected int[] mTo;

    public interface CursorToStringConverter {
        CharSequence convertToString(Cursor cursor);
    }

    public interface ViewBinder {
        boolean setViewValue(View view, Cursor cursor, int i);
    }

    @Deprecated
    public SimpleCursorAdapter(Context context, int i, Cursor cursor, String[] strArr, int[] iArr) {
        super(context, i, cursor);
        this.mTo = iArr;
        this.a = strArr;
        a(cursor, strArr);
    }

    public SimpleCursorAdapter(Context context, int i, Cursor cursor, String[] strArr, int[] iArr, int i2) {
        super(context, i, cursor, i2);
        this.mTo = iArr;
        this.a = strArr;
        a(cursor, strArr);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        ViewBinder viewBinder = this.d;
        int length = this.mTo.length;
        int[] iArr = this.mFrom;
        int[] iArr2 = this.mTo;
        for (int i = 0; i < length; i++) {
            View findViewById = view.findViewById(iArr2[i]);
            if (findViewById != null) {
                if (!(viewBinder != null ? viewBinder.setViewValue(findViewById, cursor, iArr[i]) : false)) {
                    String string = cursor.getString(iArr[i]);
                    if (string == null) {
                        string = "";
                    }
                    if (findViewById instanceof TextView) {
                        setViewText((TextView) findViewById, string);
                    } else if (findViewById instanceof ImageView) {
                        setViewImage((ImageView) findViewById, string);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append(findViewById.getClass().getName());
                        sb.append(" is not a ");
                        sb.append(" view that can be bounds by this SimpleCursorAdapter");
                        throw new IllegalStateException(sb.toString());
                    }
                } else {
                    continue;
                }
            }
        }
    }

    public ViewBinder getViewBinder() {
        return this.d;
    }

    public void setViewBinder(ViewBinder viewBinder) {
        this.d = viewBinder;
    }

    public void setViewImage(ImageView imageView, String str) {
        try {
            imageView.setImageResource(Integer.parseInt(str));
        } catch (NumberFormatException unused) {
            imageView.setImageURI(Uri.parse(str));
        }
    }

    public void setViewText(TextView textView, String str) {
        textView.setText(str);
    }

    public int getStringConversionColumn() {
        return this.b;
    }

    public void setStringConversionColumn(int i) {
        this.b = i;
    }

    public CursorToStringConverter getCursorToStringConverter() {
        return this.c;
    }

    public void setCursorToStringConverter(CursorToStringConverter cursorToStringConverter) {
        this.c = cursorToStringConverter;
    }

    public CharSequence convertToString(Cursor cursor) {
        if (this.c != null) {
            return this.c.convertToString(cursor);
        }
        if (this.b > -1) {
            return cursor.getString(this.b);
        }
        return super.convertToString(cursor);
    }

    private void a(Cursor cursor, String[] strArr) {
        if (cursor != null) {
            int length = strArr.length;
            if (this.mFrom == null || this.mFrom.length != length) {
                this.mFrom = new int[length];
            }
            for (int i = 0; i < length; i++) {
                this.mFrom[i] = cursor.getColumnIndexOrThrow(strArr[i]);
            }
            return;
        }
        this.mFrom = null;
    }

    public Cursor swapCursor(Cursor cursor) {
        a(cursor, this.a);
        return super.swapCursor(cursor);
    }

    public void changeCursorAndColumns(Cursor cursor, String[] strArr, int[] iArr) {
        this.a = strArr;
        this.mTo = iArr;
        a(cursor, this.a);
        super.changeCursor(cursor);
    }
}
