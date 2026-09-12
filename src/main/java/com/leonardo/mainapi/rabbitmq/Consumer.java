package com.leonardo.mainapi.rabbitmq;

import com.leonardo.mainapi.model.OcorrenciaDto;
import com.leonardo.mainapi.model.OcorrenciaModel;
import com.leonardo.mainapi.repository.OcorrenciaRepository;
import com.leonardo.mainapi.service.OcorrenciaService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class Consumer {
    // O que está na fila? OcorrenciaDto

    @Autowired
    OcorrenciaRepository ocorrenciaRepository;

    ObjectMapper mapper = new ObjectMapper();

    @RabbitListener(queues = "${fila-registro-ocorrencia}")
    public void consumer(@Payload String requestOcorrenciaDto){
        System.out.println("Requisição obtida: " + requestOcorrenciaDto);
        OcorrenciaDto ocorrenciaDto = mapper.readValue(requestOcorrenciaDto, OcorrenciaDto.class);
        OcorrenciaModel ocorrencia = new OcorrenciaModel(
                ocorrenciaDto.id_cld(),
                ocorrenciaDto.cidade(),
                ocorrenciaDto.bairro(),
                ocorrenciaDto.rua(),
                ocorrenciaDto.numero(),
                ocorrenciaDto.lat(),
                ocorrenciaDto.lon(),
                ocorrenciaDto.tipo(),
                ocorrenciaDto.dataRegistro(),
                ocorrenciaDto.dataSolucao(),
                ocorrenciaDto.situacao()
        );
        ocorrenciaRepository.save(ocorrencia);
        System.out.println("Requisição salva"+"\n");
    }
}
