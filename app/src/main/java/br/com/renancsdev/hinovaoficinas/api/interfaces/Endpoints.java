package br.com.renancsdev.hinovaoficinas.api.interfaces;

import br.com.renancsdev.hinovaoficinas.model.indicacao.ClasseEntradaIndicacao;
import br.com.renancsdev.hinovaoficinas.model.oficina.ResulListOficinas;
import br.com.renancsdev.hinovaoficinas.model.retorno.RespostaEnvio;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Endpoints {

    @GET("/ProvaConhecimentoWebApi/Api/Oficina?codigoAssociacao=601")
    Call<ResulListOficinas> getOficinas();

    @POST("/ProvaConhecimentoWebApi/Api/Indicacao")
    Call<RespostaEnvio> sendIndicacao(@Body ClasseEntradaIndicacao classeEntradaIndicacao);

}
