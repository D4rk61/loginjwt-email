package dev.blackshark.persistance.repository.pagin;

import dev.blackshark.persistance.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioPaginRepository extends PagingAndSortingRepository<Usuario, Long>{

    Page<Usuario> findAll(Pageable pageable);
}
