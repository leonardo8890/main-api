package com.leonardo.mainapi.model;

public record OcorrenciaDto(
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
        Situacao situacao
){}
