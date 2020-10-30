package com.twincoders.twinpush.sdk.controllers;

import com.twincoders.twinpush.sdk.entities.InboxNotification;

public interface NotificationListItemView {

    public interface Listener {
        void onNotificationLongClicked(InboxNotification inboxNotification);

        void onNotificationSelected(InboxNotification inboxNotification);
    }

    void setListener(Listener listener);

    void setNotification(InboxNotification inboxNotification);
}
