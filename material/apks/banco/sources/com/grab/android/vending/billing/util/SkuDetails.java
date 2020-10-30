package com.grab.android.vending.billing.util;

import org.json.JSONObject;

public class SkuDetails {
    String a;
    String b;
    String c;
    String d;
    String e;
    String f;
    String g;

    public SkuDetails(String str) {
        this(IabHelper.ITEM_TYPE_INAPP, str);
    }

    public SkuDetails(String str, String str2) {
        this.a = str;
        this.g = str2;
        JSONObject jSONObject = new JSONObject(this.g);
        this.b = jSONObject.optString("productId");
        this.c = jSONObject.optString("type");
        this.d = jSONObject.optString("price");
        this.e = jSONObject.optString("title");
        this.f = jSONObject.optString("description");
    }

    public String getSku() {
        return this.b;
    }

    public String getType() {
        return this.c;
    }

    public String getPrice() {
        return this.d;
    }

    public String getTitle() {
        return this.e;
    }

    public String getDescription() {
        return this.f;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SkuDetails:");
        sb.append(this.g);
        return sb.toString();
    }
}
