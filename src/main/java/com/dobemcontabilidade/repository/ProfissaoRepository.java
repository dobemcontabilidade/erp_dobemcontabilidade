package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Profissao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Profissao entity.
 */
@Repository
public interface ProfissaoRepository extends JpaRepository<Profissao, Long>, JpaSpecificationExecutor<Profissao> {
    default Optional<Profissao> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Profissao> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Profissao> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select profissao from Profissao profissao left join fetch profissao.socio",
        countQuery = "select count(profissao) from Profissao profissao"
    )
    Page<Profissao> findAllWithToOneRelationships(Pageable pageable);

    @Query("select profissao from Profissao profissao left join fetch profissao.socio")
    List<Profissao> findAllWithToOneRelationships();

    @Query("select profissao from Profissao profissao left join fetch profissao.socio where profissao.id =:id")
    Optional<Profissao> findOneWithToOneRelationships(@Param("id") Long id);
}
