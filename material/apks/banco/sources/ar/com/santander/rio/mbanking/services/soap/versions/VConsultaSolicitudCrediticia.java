package ar.com.santander.rio.mbanking.services.soap.versions;

import ar.com.santander.rio.mbanking.services.soap.beans.header.ServiceHeaderBean;

public class VConsultaSolicitudCrediticia {
    public static final String nameService = "consultaSolicitudCrediticia";

    public static ServiceHeaderBean getData(EVersionServices eVersionServices) {
        switch (eVersionServices) {
            case CURRENT:
                return new ServiceHeaderBean("consultaSolicitudCrediticia", "1.1");
            case V1_0:
                return new ServiceHeaderBean("consultaSolicitudCrediticia", "1.0");
            case V1_1:
                return new ServiceHeaderBean("consultaSolicitudCrediticia", "1.1");
            default:
                return new ServiceHeaderBean("consultaSolicitudCrediticia", "1.1");
        }
    }
}
