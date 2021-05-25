package br.com.mj.repository;

import br.com.mj.domain.redis.entity.WishedRoutingGateway;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishedRoutingGatewayRepository extends CrudRepository<WishedRoutingGateway, String> {

    Iterable<WishedRoutingGateway> findByOperationAndBrand(final String operation, final String brand);
}
