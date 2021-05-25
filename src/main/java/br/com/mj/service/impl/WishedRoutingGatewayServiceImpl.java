package br.com.mj.service.impl;

import br.com.mj.domain.redis.constants.BrandEnum;
import br.com.mj.domain.redis.constants.OperationEnum;
import br.com.mj.domain.redis.entity.WishedRoutingGateway;
import br.com.mj.repository.WishedRoutingGatewayRepository;
import br.com.mj.service.WishedRoutingGatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.keyvalue.core.IterableConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WishedRoutingGatewayServiceImpl implements WishedRoutingGatewayService {

    private final WishedRoutingGatewayRepository wishedRoutingGatewayRepository;

    @Transactional
    @Override
    public WishedRoutingGateway add(WishedRoutingGateway wishedRoutingGateway) {
        wishedRoutingGateway.setId(UUID.randomUUID().toString());

        return wishedRoutingGatewayRepository.save(wishedRoutingGateway);
    }

    @Transactional
    @Override
    public WishedRoutingGateway updateBalancerRate(String id, Integer balanceRate) throws Exception {
        final WishedRoutingGateway wishedRoutingGateway = findOne(id).orElseThrow(() -> new Exception());
        wishedRoutingGateway.setBalanceRate(balanceRate);

        return wishedRoutingGatewayRepository.save(wishedRoutingGateway);
    }

    @Transactional
    @Override
    public void delete(String id) {
        wishedRoutingGatewayRepository.deleteById(id);
    }

    @Override
    public Optional<WishedRoutingGateway> findOne(String id) {
        return wishedRoutingGatewayRepository.findById(id);
    }

    @Override
    public List<WishedRoutingGateway> findAll() {
        return IterableConverter.toList(wishedRoutingGatewayRepository.findAll());
    }

    @Override
    public List<WishedRoutingGateway> findByGroup(OperationEnum operation, BrandEnum brand) {
        return IterableConverter.toList(wishedRoutingGatewayRepository.findByOperationAndBrand(operation.name(), brand.name()));
    }
}
