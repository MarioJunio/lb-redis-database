package br.com.mj.service.impl;

import br.com.mj.domain.redis.constants.BrandEnum;
import br.com.mj.domain.redis.constants.OperationEnum;
import br.com.mj.domain.redis.entity.CurrentRoutingGateway;
import br.com.mj.repository.CurrentRoutingGatewayRepository;
import br.com.mj.service.CurrentRoutingGatewayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.keyvalue.core.IterableConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrentRoutingGatewayServiceImpl implements CurrentRoutingGatewayService {

    private final CurrentRoutingGatewayRepository currentRoutingGatewayRepository;

    @Transactional
    @Override
    public CurrentRoutingGateway add(CurrentRoutingGateway currentRoutingGateway) {
        return currentRoutingGatewayRepository.save(currentRoutingGateway);
    }

    @Transactional
    @Override
    public CurrentRoutingGateway incrementCount(String id) throws Exception {
        final CurrentRoutingGateway currentRoutingGateway = currentRoutingGatewayRepository
                .findById(id)
                .orElseThrow(() -> new Exception("Current routing not found"));

        currentRoutingGateway.setCount(currentRoutingGateway.getCount() + 1);

        return currentRoutingGatewayRepository.save(currentRoutingGateway);
    }

    @Transactional
    @Override
    public void delete(String id) {
        currentRoutingGatewayRepository.deleteById(id);
    }

    @Override
    public Optional<CurrentRoutingGateway> findOne(String id) {
        return currentRoutingGatewayRepository.findById(id);
    }

    @Override
    public List<CurrentRoutingGateway> findAll() {
        log.info("M=findAll, start");

        final Iterable<CurrentRoutingGateway> all = currentRoutingGatewayRepository.findAll();
        log.info("M=findAll, all={}", all);

        all.forEach(currentRoutingGateway -> {
            final int sumEntriesByGroup = sum(currentRoutingGateway.getOperation(), currentRoutingGateway.getBrand());
            log.info("M=findAll, sumEntriesByGroup={}", sumEntriesByGroup);

            final float rate = currentRoutingGateway.refreshRate(sumEntriesByGroup);
            log.info("M=findAll, rate={}", rate);

            log.info("----------------------------------------");
        });

        return IterableConverter.toList(all);
    }

    @Override
    public Integer sum(OperationEnum operation, BrandEnum brand) {
        log.info("M=sum, operation={}, brand={}", operation, brand);

        final Integer sumEntries = currentRoutingGatewayRepository
                .findByOperationAndBrand(operation.name(), brand.name())
                .map(c -> {
                    log.info("M=sum, c={}", c);
                    return c.stream()
                            .mapToInt(CurrentRoutingGateway::getCount)
                            .sum();
                })
                .orElse(0);

        return sumEntries;
    }
}
