package br.com.mj.service;

import br.com.mj.domain.redis.constants.BrandEnum;
import br.com.mj.domain.redis.constants.OperationEnum;
import br.com.mj.domain.redis.entity.WishedRoutingGateway;

import java.util.List;
import java.util.Optional;

public interface WishedRoutingGatewayService {

    WishedRoutingGateway add(WishedRoutingGateway wishedRoutingGateway);

    WishedRoutingGateway updateBalancerRate(String id, Integer balanceRate) throws Exception;

    void delete(String id);

    Optional<WishedRoutingGateway> findOne(String id);

    List<WishedRoutingGateway> findAll();

    List<WishedRoutingGateway> findByGroup(OperationEnum operation, BrandEnum brand);
}
