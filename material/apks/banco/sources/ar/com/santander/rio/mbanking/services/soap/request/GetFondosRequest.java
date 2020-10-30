package ar.com.santander.rio.mbanking.services.soap.request;

import android.content.Context;
import ar.com.santander.rio.mbanking.app.ui.Constants;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import ar.com.santander.rio.mbanking.services.soap.beans.GetFondosRequestBean;
import ar.com.santander.rio.mbanking.services.soap.beans.GetFondosResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.CategoriaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.FondoBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.GetFondosBodyResponseBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.HonorariosFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.HorariosFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.LegalesFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaCategoriasFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.body.ListaFondosBean;
import ar.com.santander.rio.mbanking.services.soap.beans.header.PrivateHeaderBean;
import ar.com.santander.rio.mbanking.services.soap.errors.ErrorRequestServer;
import com.google.gson.Gson;
import com.indra.httpclient.beans.IBeanRequestWS;
import com.indra.httpclient.beans.IBeanWS;
import com.indra.httpclient.json.JSONArray;
import com.indra.httpclient.json.JSONException;
import com.indra.httpclient.json.JSONObject;
import java.util.ArrayList;

public abstract class GetFondosRequest extends PrivateBaseRequest implements IBeanRequestWS {
    private GetFondosRequestBean mGetFondosRequestBean;
    private GetFondosResponseBean mGetFondosResponseBean;

    public int getMethod() {
        return 1;
    }

    public GetFondosRequest(Context context, GetFondosRequestBean getFondosRequestBean, ErrorRequestServer errorRequestServer) {
        super(context);
        this.mGetFondosRequestBean = getFondosRequestBean;
        this.mErrorRequestServer = errorRequestServer;
    }

    public void sendRequest(String str) {
        this.mXmlBeanSender.sendRequest(this, str);
    }

    public IBeanWS getBeanToRequest() {
        return this.mGetFondosRequestBean;
    }

    public Class getBeanResponseClass() {
        if (this.mGetFondosResponseBean == null) {
            this.mGetFondosResponseBean = new GetFondosResponseBean();
        }
        return this.mGetFondosResponseBean.getClass();
    }

    public ErrorRequestServer getErrorRequestServer() {
        return this.mErrorRequestServer;
    }

    public void setErrorRequestServer(ErrorRequestServer errorRequestServer) {
        this.mErrorRequestServer = errorRequestServer;
    }

    public boolean parserResponse(JSONObject jSONObject) {
        boolean parserResponse = super.parserResponse(jSONObject);
        if (parserResponse) {
            Gson gson = new Gson();
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("soapenv:Envelope").getJSONObject("soapenv:Body").getJSONObject("xml");
                GetFondosResponseBean getFondosResponseBean = new GetFondosResponseBean();
                PrivateHeaderBean privateHeaderBean = (PrivateHeaderBean) gson.fromJson(jSONObject2.getJSONObject("header").toString(), PrivateHeaderBean.class);
                GetFondosBodyResponseBean getFondosBodyResponseBean = new GetFondosBodyResponseBean();
                JSONObject jSONObject3 = jSONObject2.getJSONObject("body");
                ListaCategoriasFondosBean listaCategoriasFondosBean = new ListaCategoriasFondosBean();
                if (!jSONObject3.has("categoriasFondos") || !jSONObject3.getJSONObject("categoriasFondos").has("categoria")) {
                    listaCategoriasFondosBean.setCategoriasFondosBean(new ArrayList());
                    getFondosBodyResponseBean.setListaCategoriasFondosBean(listaCategoriasFondosBean);
                } else {
                    Object obj = jSONObject3.getJSONObject("categoriasFondos").get("categoria");
                    if (obj instanceof JSONArray) {
                        ArrayList arrayList = new ArrayList();
                        for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                            arrayList.add(getCategoriaObject(gson, ((JSONArray) obj).getJSONObject(i)));
                        }
                        listaCategoriasFondosBean.setCategoriasFondosBean(arrayList);
                        getFondosBodyResponseBean.setListaCategoriasFondosBean(listaCategoriasFondosBean);
                    } else if (obj instanceof JSONObject) {
                        ArrayList arrayList2 = new ArrayList();
                        arrayList2.add(getCategoriaObject(gson, jSONObject3.getJSONObject("categoriasFondos").getJSONObject("categoria")));
                        listaCategoriasFondosBean.setCategoriasFondosBean(arrayList2);
                        getFondosBodyResponseBean.setListaCategoriasFondosBean(listaCategoriasFondosBean);
                    }
                }
                getFondosResponseBean.headerBean = privateHeaderBean;
                getFondosResponseBean.getFondosBodyResponseBean = getFondosBodyResponseBean;
                onResponseBean(getFondosResponseBean);
            } catch (JSONException e) {
                onUnknowError(e);
            }
        }
        return parserResponse;
    }

    private CategoriaFondosBean getCategoriaObject(Gson gson, JSONObject jSONObject) {
        CategoriaFondosBean categoriaFondosBean = new CategoriaFondosBean();
        ListaFondosBean listaFondosBean = new ListaFondosBean();
        try {
            categoriaFondosBean.setIdCategoria(jSONObject.has("idCategoria") ? jSONObject.getString("idCategoria") : "");
            categoriaFondosBean.setNombreCategoria(jSONObject.has("nombreCategoria") ? jSONObject.getString("nombreCategoria") : "");
            if (jSONObject.has(Constants.DIR_STORAGE_FUNDS) && jSONObject.getJSONObject(Constants.DIR_STORAGE_FUNDS).has("fondo")) {
                Object obj = jSONObject.getJSONObject(Constants.DIR_STORAGE_FUNDS).get("fondo");
                if (obj instanceof JSONArray) {
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                        arrayList.add(getFondoObject(gson, ((JSONArray) obj).getJSONObject(i)));
                    }
                    listaFondosBean.setFondosBean(arrayList);
                    categoriaFondosBean.setFondosBean(listaFondosBean);
                } else if (obj instanceof JSONObject) {
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add(getFondoObject(gson, jSONObject.getJSONObject(Constants.DIR_STORAGE_FUNDS).getJSONObject("fondo")));
                    listaFondosBean.setFondosBean(arrayList2);
                    categoriaFondosBean.setFondosBean(listaFondosBean);
                }
            }
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return categoriaFondosBean;
    }

    private FondoBean getFondoObject(Gson gson, JSONObject jSONObject) {
        JSONObject jSONObject2;
        String str;
        FondoBean fondoBean = new FondoBean();
        LegalesFondosBean legalesFondosBean = new LegalesFondosBean();
        try {
            fondoBean.setId(jSONObject.has("idFondo") ? jSONObject.getString("idFondo") : "");
            fondoBean.setNombre(jSONObject.has("nombre") ? jSONObject.getString("nombre") : "");
            fondoBean.setAptoSuscrip(jSONObject.has("aptoSuscrip") ? jSONObject.getString("aptoSuscrip") : "");
            fondoBean.setVariacionCotizaDiaria(jSONObject.has("variacionCotizaDiaria") ? jSONObject.getString("variacionCotizaDiaria") : "");
            fondoBean.setCantidadCuotapartes(jSONObject.has("cantidadCuotapartes") ? jSONObject.getString("cantidadCuotapartes") : "");
            fondoBean.setValorCuotaParte(jSONObject.has("valorCuotaparte") ? jSONObject.getString("valorCuotaparte") : "");
            fondoBean.setImporte(jSONObject.has("importe") ? jSONObject.getString("importe") : "");
            fondoBean.setMoneda(jSONObject.has(TarjetasConstants.MONEDA) ? jSONObject.getString(TarjetasConstants.MONEDA) : "");
            fondoBean.setPlazoPago(jSONObject.has("plazoPago") ? jSONObject.getString("plazoPago") : "");
            fondoBean.setHorarioDesde(jSONObject.has("horarioDesde") ? jSONObject.getString("horarioDesde") : "");
            fondoBean.setHorarioHasta(jSONObject.has("horarioHasta") ? jSONObject.getString("horarioHasta") : "");
            fondoBean.setValorCuota(jSONObject.has("valorCuota") ? jSONObject.getString("valorCuota") : "");
            fondoBean.setValorUltimoDia(jSONObject.has("valorUltimoDia") ? jSONObject.getString("valorUltimoDia") : "");
            fondoBean.setValorUltimoMes(jSONObject.has("valorUltimoMes") ? jSONObject.getString("valorUltimoMes") : "");
            fondoBean.setValorUltimoTrimestre(jSONObject.has("valorUltimoTrimestre") ? jSONObject.getString("valorUltimoTrimestre") : "");
            fondoBean.setValorUltimoAno(jSONObject.has("valorUltimoAno") ? jSONObject.getString("valorUltimoAno") : "");
            if (jSONObject.has("honorarios") && (jSONObject.get("honorarios") instanceof JSONObject)) {
                fondoBean.setHonorarios(new HonorariosFondosBean(jSONObject.getJSONObject("honorarios").has("admin") ? jSONObject.getJSONObject("honorarios").getString("admin") : "", jSONObject.getJSONObject("honorarios").has("entrada") ? jSONObject.getJSONObject("honorarios").getString("entrada") : "", jSONObject.getJSONObject("honorarios").has("salida") ? jSONObject.getJSONObject("honorarios").getString("salida") : ""));
            }
            if (jSONObject.has("horarios") && (jSONObject.get("horarios") instanceof JSONObject)) {
                fondoBean.setHorarios(new HorariosFondosBean(jSONObject.getJSONObject("horarios").has("apertura") ? jSONObject.getJSONObject("horarios").getString("apertura") : "", jSONObject.getJSONObject("horarios").has("cierre") ? jSONObject.getJSONObject("horarios").getString("cierre") : ""));
            }
            if (jSONObject.has("leyendas") && (jSONObject.getJSONObject("leyendas").has("leyendaLegal") || jSONObject.getJSONObject("leyendas").has("legal"))) {
                if (jSONObject.getJSONObject("leyendas").has("leyendaLegal")) {
                    jSONObject2 = jSONObject.getJSONObject("leyendas");
                    str = "leyendaLegal";
                } else {
                    jSONObject2 = jSONObject.getJSONObject("leyendas");
                    str = "legal";
                }
                Object obj = jSONObject2.get(str);
                if (obj instanceof JSONArray) {
                    ArrayList arrayList = new ArrayList();
                    for (int i = 0; i < ((JSONArray) obj).length(); i++) {
                        arrayList.add((String) gson.fromJson(((JSONArray) obj).getJSONObject(i).toString(), String.class));
                    }
                    legalesFondosBean.setLeyendaLegales(arrayList);
                    fondoBean.setListaLegales(legalesFondosBean);
                } else if (obj instanceof String) {
                    ArrayList arrayList2 = new ArrayList();
                    arrayList2.add((String) obj);
                    legalesFondosBean.setLeyendaLegales(arrayList2);
                    fondoBean.setListaLegales(legalesFondosBean);
                }
            }
        } catch (JSONException e) {
            onUnknowError(e);
        }
        return fondoBean;
    }
}
