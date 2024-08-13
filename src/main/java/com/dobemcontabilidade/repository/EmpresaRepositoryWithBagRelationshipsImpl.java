package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Empresa;
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
public class EmpresaRepositoryWithBagRelationshipsImpl implements EmpresaRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String EMPRESAS_PARAMETER = "empresas";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Empresa> fetchBagRelationships(Optional<Empresa> empresa) {
        return empresa.map(this::fetchSegmentoCnaes);
    }

    @Override
    public Page<Empresa> fetchBagRelationships(Page<Empresa> empresas) {
        return new PageImpl<>(fetchBagRelationships(empresas.getContent()), empresas.getPageable(), empresas.getTotalElements());
    }

    @Override
    public List<Empresa> fetchBagRelationships(List<Empresa> empresas) {
        return Optional.of(empresas).map(this::fetchSegmentoCnaes).orElse(Collections.emptyList());
    }

    Empresa fetchSegmentoCnaes(Empresa result) {
        return entityManager
            .createQuery("select empresa from Empresa empresa left join fetch empresa.segmentoCnaes where empresa.id = :id", Empresa.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Empresa> fetchSegmentoCnaes(List<Empresa> empresas) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, empresas.size()).forEach(index -> order.put(empresas.get(index).getId(), index));
        List<Empresa> result = entityManager
            .createQuery(
                "select empresa from Empresa empresa left join fetch empresa.segmentoCnaes where empresa in :empresas",
                Empresa.class
            )
            .setParameter(EMPRESAS_PARAMETER, empresas)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
