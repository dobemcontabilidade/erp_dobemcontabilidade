package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.EmpresaModelo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class EmpresaModeloRepositoryWithBagRelationshipsImpl implements EmpresaModeloRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String EMPRESAMODELOS_PARAMETER = "empresaModelos";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<EmpresaModelo> fetchBagRelationships(Optional<EmpresaModelo> empresaModelo) {
        return empresaModelo.map(this::fetchSegmentoCnaes);
    }

    @Override
    public Page<EmpresaModelo> fetchBagRelationships(Page<EmpresaModelo> empresaModelos) {
        return new PageImpl<>(
            fetchBagRelationships(empresaModelos.getContent()),
            empresaModelos.getPageable(),
            empresaModelos.getTotalElements()
        );
    }

    @Override
    public List<EmpresaModelo> fetchBagRelationships(List<EmpresaModelo> empresaModelos) {
        return Optional.of(empresaModelos).map(this::fetchSegmentoCnaes).orElse(Collections.emptyList());
    }

    EmpresaModelo fetchSegmentoCnaes(EmpresaModelo result) {
        return entityManager
            .createQuery(
                "select empresaModelo from EmpresaModelo empresaModelo left join fetch empresaModelo.segmentoCnaes where empresaModelo.id = :id",
                EmpresaModelo.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<EmpresaModelo> fetchSegmentoCnaes(List<EmpresaModelo> empresaModelos) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, empresaModelos.size()).forEach(index -> order.put(empresaModelos.get(index).getId(), index));
        List<EmpresaModelo> result = entityManager
            .createQuery(
                "select empresaModelo from EmpresaModelo empresaModelo left join fetch empresaModelo.segmentoCnaes where empresaModelo in :empresaModelos",
                EmpresaModelo.class
            )
            .setParameter(EMPRESAMODELOS_PARAMETER, empresaModelos)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
