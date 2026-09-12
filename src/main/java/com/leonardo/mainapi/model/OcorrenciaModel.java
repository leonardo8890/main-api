package com.leonardo.mainapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="ocorrencia")
@Getter
@Setter
@NoArgsConstructor
public class OcorrenciaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String id_cld;
    private String cidade;
    private String bairro;
    private String rua;
    private String numero;
    private String lat;
    private String lon;
    private String tipo;
    private String dataRegistro;
    private String dataSolucao;
    private Situacao situacao;

    public OcorrenciaModel(
            String id_cld,
            String cidade,
            String bairro,
            String rua,
            String numero,
            String lat,
            String lon,
            String tipo,
            String dataRegistro,
            String dataSolucao,
            Situacao situacao){
        this.id_cld = id_cld;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.lat = lat;
        this.lon = lon;
        this.tipo = tipo;
        this.dataRegistro = dataRegistro;
        this.dataSolucao = dataSolucao;
        this.situacao = situacao;
    }
}
