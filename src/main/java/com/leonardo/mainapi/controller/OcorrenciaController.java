package com.leonardo.mainapi.controller;

import com.leonardo.mainapi.exception.UnknownIdOcorrencia;
import com.leonardo.mainapi.model.OcorrenciaDto;
import com.leonardo.mainapi.model.OcorrenciaModel;
import com.leonardo.mainapi.model.Situacao;
import com.leonardo.mainapi.repository.OcorrenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OcorrenciaController {

    @Autowired
    OcorrenciaRepository ocorrenciaRepository;

    @GetMapping("/registro/ocorrencia/total")
    public ResponseEntity total(){
        return ResponseEntity.ok(ocorrenciaRepository.findAll());
    }

    @GetMapping("/registro/ocorrencia/aberto")
    public ResponseEntity pendente(){
        return ResponseEntity.ok(ocorrenciaRepository.findBySituacao(Situacao.ABERTO));
    }

    @PutMapping("/registro/ocorrencia/situacao/{id}")
    public ResponseEntity situacao(@PathVariable Long id){
        Optional<OcorrenciaModel> ocorrencia = ocorrenciaRepository.findById(id);
        //Ocorrência encontrada
        if(ocorrencia.isPresent()){
            ocorrencia.get().setSituacao(Situacao.RESOLVIDO);
            ocorrenciaRepository.save(ocorrencia.get());
            return ResponseEntity.ok().build();
        }
        //return ResponseEntity.badRequest().build();
        throw new UnknownIdOcorrencia("Ocorrência inexistente com esse id");
    }

    @PostMapping("/registro/ocorrencia/nova")
    public ResponseEntity nova(@RequestBody OcorrenciaDto dto){
        OcorrenciaModel ocorrencia = new OcorrenciaModel(
                dto.id_cld(),
                dto.cidade(),
                dto.bairro(),
                dto.rua(),
                dto.numero(),
                dto.lat(),
                dto.lon(),
                dto.tipo(),
                dto.dataRegistro(),
                dto.dataSolucao(),
                dto.situacao()
        );
        ocorrenciaRepository.save(ocorrencia);
        return ResponseEntity.ok().build();
    }

}
