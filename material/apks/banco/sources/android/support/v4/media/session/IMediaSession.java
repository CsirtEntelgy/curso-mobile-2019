package android.support.v4.media.session;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.MediaSessionCompat.QueueItem;
import android.text.TextUtils;
import android.view.KeyEvent;
import java.util.List;

public interface IMediaSession extends IInterface {

    public static abstract class Stub extends Binder implements IMediaSession {

        static class Proxy implements IMediaSession {
            private IBinder a;

            Proxy(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void sendCommand(String str, Bundle bundle, ResultReceiverWrapper resultReceiverWrapper) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (resultReceiverWrapper != null) {
                        obtain.writeInt(1);
                        resultReceiverWrapper.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean sendMediaButton(KeyEvent keyEvent) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    boolean z = true;
                    if (keyEvent != null) {
                        obtain.writeInt(1);
                        keyEvent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        z = false;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    obtain.writeStrongBinder(iMediaControllerCallback != null ? iMediaControllerCallback.asBinder() : null);
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    obtain.writeStrongBinder(iMediaControllerCallback != null ? iMediaControllerCallback.asBinder() : null);
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isTransportControlEnabled() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    boolean z = false;
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getPackageName() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getTag() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public PendingIntent getLaunchPendingIntent() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public long getFlags() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public ParcelableVolumeInfo getVolumeAttributes() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (ParcelableVolumeInfo) ParcelableVolumeInfo.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void adjustVolume(int i, int i2, String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.a.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setVolumeTo(int i, int i2, String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.a.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public MediaMetadataCompat getMetadata() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.a.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (MediaMetadataCompat) MediaMetadataCompat.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public PlaybackStateCompat getPlaybackState() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.a.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (PlaybackStateCompat) PlaybackStateCompat.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public List<QueueItem> getQueue() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.a.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(QueueItem.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public CharSequence getQueueTitle() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.a.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Bundle getExtras() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.a.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getRatingType() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.a.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isCaptioningEnabled() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    boolean z = false;
                    this.a.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getRepeatMode() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.a.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isShuffleModeEnabledRemoved() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    boolean z = false;
                    this.a.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getShuffleMode() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.a.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    if (mediaDescriptionCompat != null) {
                        obtain.writeInt(1);
                        mediaDescriptionCompat.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void addQueueItemAt(MediaDescriptionCompat mediaDescriptionCompat, int i) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    if (mediaDescriptionCompat != null) {
                        obtain.writeInt(1);
                        mediaDescriptionCompat.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    this.a.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    if (mediaDescriptionCompat != null) {
                        obtain.writeInt(1);
                        mediaDescriptionCompat.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void removeQueueItemAt(int i) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    obtain.writeInt(i);
                    this.a.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void prepare() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.a.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void prepareFromMediaId(String str, Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void prepareFromSearch(String str, Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void prepareFromUri(Uri uri, Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    if (uri != null) {
                        obtain.writeInt(1);
                        uri.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void play() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.a.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void playFromMediaId(String str, Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void playFromSearch(String str, Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void playFromUri(Uri uri, Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    if (uri != null) {
                        obtain.writeInt(1);
                        uri.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void skipToQueueItem(long j) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    obtain.writeLong(j);
                    this.a.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void pause() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.a.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void stop() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.a.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void next() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.a.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void previous() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.a.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void fastForward() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.a.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void rewind() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.a.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void seekTo(long j) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    obtain.writeLong(j);
                    this.a.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void rate(RatingCompat ratingCompat) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    if (ratingCompat != null) {
                        obtain.writeInt(1);
                        ratingCompat.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void rateWithExtras(RatingCompat ratingCompat, Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    if (ratingCompat != null) {
                        obtain.writeInt(1);
                        ratingCompat.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setCaptioningEnabled(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setRepeatMode(int i) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    obtain.writeInt(i);
                    this.a.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setShuffleModeEnabledRemoved(boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    obtain.writeInt(z ? 1 : 0);
                    this.a.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setShuffleMode(int i) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    obtain.writeInt(i);
                    this.a.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void sendCustomAction(String str, Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "android.support.v4.media.session.IMediaSession");
        }

        public static IMediaSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.support.v4.media.session.IMediaSession");
            if (queryLocalInterface == null || !(queryLocalInterface instanceof IMediaSession)) {
                return new Proxy(iBinder);
            }
            return (IMediaSession) queryLocalInterface;
        }

        /* JADX WARNING: type inference failed for: r1v0 */
        /* JADX WARNING: type inference failed for: r1v1, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r1v3, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r1v4, types: [android.support.v4.media.session.MediaSessionCompat$ResultReceiverWrapper] */
        /* JADX WARNING: type inference failed for: r1v6, types: [android.support.v4.media.session.MediaSessionCompat$ResultReceiverWrapper] */
        /* JADX WARNING: type inference failed for: r1v7, types: [android.view.KeyEvent] */
        /* JADX WARNING: type inference failed for: r1v9, types: [android.view.KeyEvent] */
        /* JADX WARNING: type inference failed for: r1v10, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r1v12, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r1v13, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r1v15, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r1v16, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r1v18, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r1v19, types: [android.support.v4.media.RatingCompat] */
        /* JADX WARNING: type inference failed for: r1v21, types: [android.support.v4.media.RatingCompat] */
        /* JADX WARNING: type inference failed for: r1v22, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r1v24, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r1v25, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r1v27, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r1v28, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r1v30, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r1v31, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r1v33, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r1v34, types: [android.support.v4.media.MediaDescriptionCompat] */
        /* JADX WARNING: type inference failed for: r1v36, types: [android.support.v4.media.MediaDescriptionCompat] */
        /* JADX WARNING: type inference failed for: r1v37, types: [android.support.v4.media.MediaDescriptionCompat] */
        /* JADX WARNING: type inference failed for: r1v39, types: [android.support.v4.media.MediaDescriptionCompat] */
        /* JADX WARNING: type inference failed for: r1v40, types: [android.support.v4.media.MediaDescriptionCompat] */
        /* JADX WARNING: type inference failed for: r1v42, types: [android.support.v4.media.MediaDescriptionCompat] */
        /* JADX WARNING: type inference failed for: r1v43 */
        /* JADX WARNING: type inference failed for: r1v44 */
        /* JADX WARNING: type inference failed for: r1v45 */
        /* JADX WARNING: type inference failed for: r1v46 */
        /* JADX WARNING: type inference failed for: r1v47 */
        /* JADX WARNING: type inference failed for: r1v48 */
        /* JADX WARNING: type inference failed for: r1v49 */
        /* JADX WARNING: type inference failed for: r1v50 */
        /* JADX WARNING: type inference failed for: r1v51 */
        /* JADX WARNING: type inference failed for: r1v52 */
        /* JADX WARNING: type inference failed for: r1v53 */
        /* JADX WARNING: type inference failed for: r1v54 */
        /* JADX WARNING: type inference failed for: r1v55 */
        /* JADX WARNING: type inference failed for: r1v56 */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v0
          assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], android.support.v4.media.session.MediaSessionCompat$ResultReceiverWrapper, android.os.Bundle, android.view.KeyEvent, android.support.v4.media.RatingCompat, android.support.v4.media.MediaDescriptionCompat]
          uses: [android.os.Bundle, android.support.v4.media.session.MediaSessionCompat$ResultReceiverWrapper, android.view.KeyEvent, android.support.v4.media.RatingCompat, android.support.v4.media.MediaDescriptionCompat]
          mth insns count: 440
        	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
        	at jadx.core.ProcessClass.process(ProcessClass.java:35)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
         */
        /* JADX WARNING: Unknown variable types count: 15 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r4, android.os.Parcel r5, android.os.Parcel r6, int r7) {
            /*
                r3 = this;
                r0 = 51
                r1 = 0
                r2 = 1
                if (r4 == r0) goto L_0x042d
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                if (r4 == r0) goto L_0x0427
                r0 = 0
                switch(r4) {
                    case 1: goto L_0x03f8;
                    case 2: goto L_0x03d9;
                    case 3: goto L_0x03c5;
                    case 4: goto L_0x03b1;
                    case 5: goto L_0x03a1;
                    case 6: goto L_0x0391;
                    case 7: goto L_0x0381;
                    case 8: goto L_0x0368;
                    case 9: goto L_0x0358;
                    case 10: goto L_0x033f;
                    case 11: goto L_0x0327;
                    case 12: goto L_0x030f;
                    case 13: goto L_0x0303;
                    case 14: goto L_0x02e4;
                    case 15: goto L_0x02c5;
                    case 16: goto L_0x029a;
                    case 17: goto L_0x028a;
                    case 18: goto L_0x027e;
                    case 19: goto L_0x0272;
                    case 20: goto L_0x0266;
                    case 21: goto L_0x025a;
                    case 22: goto L_0x024e;
                    case 23: goto L_0x0242;
                    case 24: goto L_0x0232;
                    case 25: goto L_0x0217;
                    case 26: goto L_0x01f8;
                    case 27: goto L_0x01df;
                    case 28: goto L_0x01c6;
                    case 29: goto L_0x01b6;
                    case 30: goto L_0x019d;
                    case 31: goto L_0x0184;
                    case 32: goto L_0x0174;
                    case 33: goto L_0x0168;
                    case 34: goto L_0x0149;
                    case 35: goto L_0x012a;
                    case 36: goto L_0x00ff;
                    case 37: goto L_0x00ef;
                    case 38: goto L_0x00df;
                    case 39: goto L_0x00cf;
                    case 40: goto L_0x00bc;
                    case 41: goto L_0x00a1;
                    case 42: goto L_0x0082;
                    case 43: goto L_0x0067;
                    case 44: goto L_0x0057;
                    case 45: goto L_0x0047;
                    case 46: goto L_0x0034;
                    case 47: goto L_0x0024;
                    case 48: goto L_0x0014;
                    default: goto L_0x000f;
                }
            L_0x000f:
                boolean r4 = super.onTransact(r4, r5, r6, r7)
                return r4
            L_0x0014:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                r3.setShuffleMode(r4)
                r6.writeNoException()
                return r2
            L_0x0024:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                int r4 = r3.getShuffleMode()
                r6.writeNoException()
                r6.writeInt(r4)
                return r2
            L_0x0034:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0040
                r0 = 1
            L_0x0040:
                r3.setCaptioningEnabled(r0)
                r6.writeNoException()
                return r2
            L_0x0047:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                boolean r4 = r3.isCaptioningEnabled()
                r6.writeNoException()
                r6.writeInt(r4)
                return r2
            L_0x0057:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                r3.removeQueueItemAt(r4)
                r6.writeNoException()
                return r2
            L_0x0067:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x007b
                android.os.Parcelable$Creator<android.support.v4.media.MediaDescriptionCompat> r4 = android.support.v4.media.MediaDescriptionCompat.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r1 = r4
                android.support.v4.media.MediaDescriptionCompat r1 = (android.support.v4.media.MediaDescriptionCompat) r1
            L_0x007b:
                r3.removeQueueItem(r1)
                r6.writeNoException()
                return r2
            L_0x0082:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0096
                android.os.Parcelable$Creator<android.support.v4.media.MediaDescriptionCompat> r4 = android.support.v4.media.MediaDescriptionCompat.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r1 = r4
                android.support.v4.media.MediaDescriptionCompat r1 = (android.support.v4.media.MediaDescriptionCompat) r1
            L_0x0096:
                int r4 = r5.readInt()
                r3.addQueueItemAt(r1, r4)
                r6.writeNoException()
                return r2
            L_0x00a1:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x00b5
                android.os.Parcelable$Creator<android.support.v4.media.MediaDescriptionCompat> r4 = android.support.v4.media.MediaDescriptionCompat.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r1 = r4
                android.support.v4.media.MediaDescriptionCompat r1 = (android.support.v4.media.MediaDescriptionCompat) r1
            L_0x00b5:
                r3.addQueueItem(r1)
                r6.writeNoException()
                return r2
            L_0x00bc:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x00c8
                r0 = 1
            L_0x00c8:
                r3.setShuffleModeEnabledRemoved(r0)
                r6.writeNoException()
                return r2
            L_0x00cf:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                r3.setRepeatMode(r4)
                r6.writeNoException()
                return r2
            L_0x00df:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                boolean r4 = r3.isShuffleModeEnabledRemoved()
                r6.writeNoException()
                r6.writeInt(r4)
                return r2
            L_0x00ef:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                int r4 = r3.getRepeatMode()
                r6.writeNoException()
                r6.writeInt(r4)
                return r2
            L_0x00ff:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0113
                android.os.Parcelable$Creator r4 = android.net.Uri.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                android.net.Uri r4 = (android.net.Uri) r4
                goto L_0x0114
            L_0x0113:
                r4 = r1
            L_0x0114:
                int r7 = r5.readInt()
                if (r7 == 0) goto L_0x0123
                android.os.Parcelable$Creator r7 = android.os.Bundle.CREATOR
                java.lang.Object r5 = r7.createFromParcel(r5)
                r1 = r5
                android.os.Bundle r1 = (android.os.Bundle) r1
            L_0x0123:
                r3.prepareFromUri(r4, r1)
                r6.writeNoException()
                return r2
            L_0x012a:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                java.lang.String r4 = r5.readString()
                int r7 = r5.readInt()
                if (r7 == 0) goto L_0x0142
                android.os.Parcelable$Creator r7 = android.os.Bundle.CREATOR
                java.lang.Object r5 = r7.createFromParcel(r5)
                r1 = r5
                android.os.Bundle r1 = (android.os.Bundle) r1
            L_0x0142:
                r3.prepareFromSearch(r4, r1)
                r6.writeNoException()
                return r2
            L_0x0149:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                java.lang.String r4 = r5.readString()
                int r7 = r5.readInt()
                if (r7 == 0) goto L_0x0161
                android.os.Parcelable$Creator r7 = android.os.Bundle.CREATOR
                java.lang.Object r5 = r7.createFromParcel(r5)
                r1 = r5
                android.os.Bundle r1 = (android.os.Bundle) r1
            L_0x0161:
                r3.prepareFromMediaId(r4, r1)
                r6.writeNoException()
                return r2
            L_0x0168:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                r3.prepare()
                r6.writeNoException()
                return r2
            L_0x0174:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                int r4 = r3.getRatingType()
                r6.writeNoException()
                r6.writeInt(r4)
                return r2
            L_0x0184:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                android.os.Bundle r4 = r3.getExtras()
                r6.writeNoException()
                if (r4 == 0) goto L_0x0199
                r6.writeInt(r2)
                r4.writeToParcel(r6, r2)
                goto L_0x019c
            L_0x0199:
                r6.writeInt(r0)
            L_0x019c:
                return r2
            L_0x019d:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                java.lang.CharSequence r4 = r3.getQueueTitle()
                r6.writeNoException()
                if (r4 == 0) goto L_0x01b2
                r6.writeInt(r2)
                android.text.TextUtils.writeToParcel(r4, r6, r2)
                goto L_0x01b5
            L_0x01b2:
                r6.writeInt(r0)
            L_0x01b5:
                return r2
            L_0x01b6:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                java.util.List r4 = r3.getQueue()
                r6.writeNoException()
                r6.writeTypedList(r4)
                return r2
            L_0x01c6:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                android.support.v4.media.session.PlaybackStateCompat r4 = r3.getPlaybackState()
                r6.writeNoException()
                if (r4 == 0) goto L_0x01db
                r6.writeInt(r2)
                r4.writeToParcel(r6, r2)
                goto L_0x01de
            L_0x01db:
                r6.writeInt(r0)
            L_0x01de:
                return r2
            L_0x01df:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                android.support.v4.media.MediaMetadataCompat r4 = r3.getMetadata()
                r6.writeNoException()
                if (r4 == 0) goto L_0x01f4
                r6.writeInt(r2)
                r4.writeToParcel(r6, r2)
                goto L_0x01f7
            L_0x01f4:
                r6.writeInt(r0)
            L_0x01f7:
                return r2
            L_0x01f8:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                java.lang.String r4 = r5.readString()
                int r7 = r5.readInt()
                if (r7 == 0) goto L_0x0210
                android.os.Parcelable$Creator r7 = android.os.Bundle.CREATOR
                java.lang.Object r5 = r7.createFromParcel(r5)
                r1 = r5
                android.os.Bundle r1 = (android.os.Bundle) r1
            L_0x0210:
                r3.sendCustomAction(r4, r1)
                r6.writeNoException()
                return r2
            L_0x0217:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x022b
                android.os.Parcelable$Creator<android.support.v4.media.RatingCompat> r4 = android.support.v4.media.RatingCompat.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r1 = r4
                android.support.v4.media.RatingCompat r1 = (android.support.v4.media.RatingCompat) r1
            L_0x022b:
                r3.rate(r1)
                r6.writeNoException()
                return r2
            L_0x0232:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                long r4 = r5.readLong()
                r3.seekTo(r4)
                r6.writeNoException()
                return r2
            L_0x0242:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                r3.rewind()
                r6.writeNoException()
                return r2
            L_0x024e:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                r3.fastForward()
                r6.writeNoException()
                return r2
            L_0x025a:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                r3.previous()
                r6.writeNoException()
                return r2
            L_0x0266:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                r3.next()
                r6.writeNoException()
                return r2
            L_0x0272:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                r3.stop()
                r6.writeNoException()
                return r2
            L_0x027e:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                r3.pause()
                r6.writeNoException()
                return r2
            L_0x028a:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                long r4 = r5.readLong()
                r3.skipToQueueItem(r4)
                r6.writeNoException()
                return r2
            L_0x029a:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x02ae
                android.os.Parcelable$Creator r4 = android.net.Uri.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                android.net.Uri r4 = (android.net.Uri) r4
                goto L_0x02af
            L_0x02ae:
                r4 = r1
            L_0x02af:
                int r7 = r5.readInt()
                if (r7 == 0) goto L_0x02be
                android.os.Parcelable$Creator r7 = android.os.Bundle.CREATOR
                java.lang.Object r5 = r7.createFromParcel(r5)
                r1 = r5
                android.os.Bundle r1 = (android.os.Bundle) r1
            L_0x02be:
                r3.playFromUri(r4, r1)
                r6.writeNoException()
                return r2
            L_0x02c5:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                java.lang.String r4 = r5.readString()
                int r7 = r5.readInt()
                if (r7 == 0) goto L_0x02dd
                android.os.Parcelable$Creator r7 = android.os.Bundle.CREATOR
                java.lang.Object r5 = r7.createFromParcel(r5)
                r1 = r5
                android.os.Bundle r1 = (android.os.Bundle) r1
            L_0x02dd:
                r3.playFromSearch(r4, r1)
                r6.writeNoException()
                return r2
            L_0x02e4:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                java.lang.String r4 = r5.readString()
                int r7 = r5.readInt()
                if (r7 == 0) goto L_0x02fc
                android.os.Parcelable$Creator r7 = android.os.Bundle.CREATOR
                java.lang.Object r5 = r7.createFromParcel(r5)
                r1 = r5
                android.os.Bundle r1 = (android.os.Bundle) r1
            L_0x02fc:
                r3.playFromMediaId(r4, r1)
                r6.writeNoException()
                return r2
            L_0x0303:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                r3.play()
                r6.writeNoException()
                return r2
            L_0x030f:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                int r7 = r5.readInt()
                java.lang.String r5 = r5.readString()
                r3.setVolumeTo(r4, r7, r5)
                r6.writeNoException()
                return r2
            L_0x0327:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                int r7 = r5.readInt()
                java.lang.String r5 = r5.readString()
                r3.adjustVolume(r4, r7, r5)
                r6.writeNoException()
                return r2
            L_0x033f:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                android.support.v4.media.session.ParcelableVolumeInfo r4 = r3.getVolumeAttributes()
                r6.writeNoException()
                if (r4 == 0) goto L_0x0354
                r6.writeInt(r2)
                r4.writeToParcel(r6, r2)
                goto L_0x0357
            L_0x0354:
                r6.writeInt(r0)
            L_0x0357:
                return r2
            L_0x0358:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                long r4 = r3.getFlags()
                r6.writeNoException()
                r6.writeLong(r4)
                return r2
            L_0x0368:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                android.app.PendingIntent r4 = r3.getLaunchPendingIntent()
                r6.writeNoException()
                if (r4 == 0) goto L_0x037d
                r6.writeInt(r2)
                r4.writeToParcel(r6, r2)
                goto L_0x0380
            L_0x037d:
                r6.writeInt(r0)
            L_0x0380:
                return r2
            L_0x0381:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                java.lang.String r4 = r3.getTag()
                r6.writeNoException()
                r6.writeString(r4)
                return r2
            L_0x0391:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                java.lang.String r4 = r3.getPackageName()
                r6.writeNoException()
                r6.writeString(r4)
                return r2
            L_0x03a1:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                boolean r4 = r3.isTransportControlEnabled()
                r6.writeNoException()
                r6.writeInt(r4)
                return r2
            L_0x03b1:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                android.support.v4.media.session.IMediaControllerCallback r4 = android.support.v4.media.session.IMediaControllerCallback.Stub.asInterface(r4)
                r3.unregisterCallbackListener(r4)
                r6.writeNoException()
                return r2
            L_0x03c5:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                android.support.v4.media.session.IMediaControllerCallback r4 = android.support.v4.media.session.IMediaControllerCallback.Stub.asInterface(r4)
                r3.registerCallbackListener(r4)
                r6.writeNoException()
                return r2
            L_0x03d9:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x03ed
                android.os.Parcelable$Creator r4 = android.view.KeyEvent.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r1 = r4
                android.view.KeyEvent r1 = (android.view.KeyEvent) r1
            L_0x03ed:
                boolean r4 = r3.sendMediaButton(r1)
                r6.writeNoException()
                r6.writeInt(r4)
                return r2
            L_0x03f8:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                java.lang.String r4 = r5.readString()
                int r7 = r5.readInt()
                if (r7 == 0) goto L_0x0410
                android.os.Parcelable$Creator r7 = android.os.Bundle.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r5)
                android.os.Bundle r7 = (android.os.Bundle) r7
                goto L_0x0411
            L_0x0410:
                r7 = r1
            L_0x0411:
                int r0 = r5.readInt()
                if (r0 == 0) goto L_0x0420
                android.os.Parcelable$Creator<android.support.v4.media.session.MediaSessionCompat$ResultReceiverWrapper> r0 = android.support.v4.media.session.MediaSessionCompat.ResultReceiverWrapper.CREATOR
                java.lang.Object r5 = r0.createFromParcel(r5)
                r1 = r5
                android.support.v4.media.session.MediaSessionCompat$ResultReceiverWrapper r1 = (android.support.v4.media.session.MediaSessionCompat.ResultReceiverWrapper) r1
            L_0x0420:
                r3.sendCommand(r4, r7, r1)
                r6.writeNoException()
                return r2
            L_0x0427:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r6.writeString(r4)
                return r2
            L_0x042d:
                java.lang.String r4 = "android.support.v4.media.session.IMediaSession"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0441
                android.os.Parcelable$Creator<android.support.v4.media.RatingCompat> r4 = android.support.v4.media.RatingCompat.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                android.support.v4.media.RatingCompat r4 = (android.support.v4.media.RatingCompat) r4
                goto L_0x0442
            L_0x0441:
                r4 = r1
            L_0x0442:
                int r7 = r5.readInt()
                if (r7 == 0) goto L_0x0451
                android.os.Parcelable$Creator r7 = android.os.Bundle.CREATOR
                java.lang.Object r5 = r7.createFromParcel(r5)
                r1 = r5
                android.os.Bundle r1 = (android.os.Bundle) r1
            L_0x0451:
                r3.rateWithExtras(r4, r1)
                r6.writeNoException()
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.session.IMediaSession.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat);

    void addQueueItemAt(MediaDescriptionCompat mediaDescriptionCompat, int i);

    void adjustVolume(int i, int i2, String str);

    void fastForward();

    Bundle getExtras();

    long getFlags();

    PendingIntent getLaunchPendingIntent();

    MediaMetadataCompat getMetadata();

    String getPackageName();

    PlaybackStateCompat getPlaybackState();

    List<QueueItem> getQueue();

    CharSequence getQueueTitle();

    int getRatingType();

    int getRepeatMode();

    int getShuffleMode();

    String getTag();

    ParcelableVolumeInfo getVolumeAttributes();

    boolean isCaptioningEnabled();

    boolean isShuffleModeEnabledRemoved();

    boolean isTransportControlEnabled();

    void next();

    void pause();

    void play();

    void playFromMediaId(String str, Bundle bundle);

    void playFromSearch(String str, Bundle bundle);

    void playFromUri(Uri uri, Bundle bundle);

    void prepare();

    void prepareFromMediaId(String str, Bundle bundle);

    void prepareFromSearch(String str, Bundle bundle);

    void prepareFromUri(Uri uri, Bundle bundle);

    void previous();

    void rate(RatingCompat ratingCompat);

    void rateWithExtras(RatingCompat ratingCompat, Bundle bundle);

    void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback);

    void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat);

    void removeQueueItemAt(int i);

    void rewind();

    void seekTo(long j);

    void sendCommand(String str, Bundle bundle, ResultReceiverWrapper resultReceiverWrapper);

    void sendCustomAction(String str, Bundle bundle);

    boolean sendMediaButton(KeyEvent keyEvent);

    void setCaptioningEnabled(boolean z);

    void setRepeatMode(int i);

    void setShuffleMode(int i);

    void setShuffleModeEnabledRemoved(boolean z);

    void setVolumeTo(int i, int i2, String str);

    void skipToQueueItem(long j);

    void stop();

    void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback);
}
