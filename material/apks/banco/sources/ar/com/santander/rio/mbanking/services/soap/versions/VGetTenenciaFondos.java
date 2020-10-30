package ar.com.santander.rio.mbanking.services.soap.versions;

import ar.com.santander.rio.mbanking.services.soap.beans.header.ServiceHeaderBean;

public class VGetTenenciaFondos {
    public static final String nameService = "getTenenciaFondos";

    public static ServiceHeaderBean getData(EVersionServices eVersionServices) {
        switch (eVersionServices) {
            case CURRENT:
                return new ServiceHeaderBean(nameService, "1.2");
            case V1_0:
                return new ServiceHeaderBean(nameService, "1.1");
            default:
                return new ServiceHeaderBean(nameService, "1.1");
        }
    }
}
