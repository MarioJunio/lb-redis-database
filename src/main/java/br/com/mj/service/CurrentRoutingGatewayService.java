package br.com.mj.service;

import br.com.mj.domain.redis.constants.BrandEnum;
import br.com.mj.domain.redis.constants.OperationEnum;
import br.com.mj.domain.redis.entity.CurrentRoutingGateway;

import java.util.List;
import java.util.Optional;

public interface CurrentRoutingGatewayService {

    CurrentRoutingGateway add(CurrentRoutingGateway currentRoutingGateway);

    CurrentRoutingGateway incrementCount(String id) throws Exception;

    void delete(String id);

    Optional<CurrentRoutingGateway> findOne(String id);

    List<CurrentRoutingGateway> findAll();

    Integer sum(OperationEnum operation, BrandEnum brand);
}
