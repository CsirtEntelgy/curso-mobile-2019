package ar.com.santander.rio.mbanking.app.commons.accessibilty;

public class CGeneralAcc extends CElementBaseAcc {
    /* access modifiers changed from: protected */
    public String getStringAfterApplyFilter(CAccessibility cAccessibility, String str) {
        return cAccessibility.applyFilterGeneral(str);
    }
}
