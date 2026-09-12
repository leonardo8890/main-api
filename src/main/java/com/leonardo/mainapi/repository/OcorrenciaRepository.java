package com.leonardo.mainapi.repository;

import com.leonardo.mainapi.model.OcorrenciaModel;
import com.leonardo.mainapi.model.Situacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OcorrenciaRepository extends JpaRepository<OcorrenciaModel, Long> {
    List<OcorrenciaModel> findByDataSolucao(String dataSolucao);
    List<OcorrenciaModel> findBySituacao(Situacao situacao);
}
