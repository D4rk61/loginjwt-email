package dev.blackshark.persistance.repository.pagin;

import dev.blackshark.persistance.entity.Producto;
import org.hibernate.metamodel.model.domain.spi.ListPersistentAttribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoPaginRepository extends PagingAndSortingRepository<Producto, Long> {

    Page<Producto> findAll(Pageable pageable);
}
