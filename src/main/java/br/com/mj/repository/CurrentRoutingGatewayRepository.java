package br.com.mj.repository;

import br.com.mj.domain.redis.entity.CurrentRoutingGateway;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrentRoutingGatewayRepository extends CrudRepository<CurrentRoutingGateway, String> {

    Optional<List<CurrentRoutingGateway>> findByOperationAndBrand(final String operation, final String brand);
}
