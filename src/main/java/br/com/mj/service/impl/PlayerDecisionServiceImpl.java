package br.com.mj.service.impl;

import br.com.mj.domain.dto.RoutingGatewayDto;
import br.com.mj.domain.redis.entity.CurrentRoutingGateway;
import br.com.mj.domain.redis.entity.WishedRoutingGateway;
import br.com.mj.domain.request.CaptureRequest;
import br.com.mj.service.CurrentRoutingGatewayService;
import br.com.mj.service.PlayerDecisionService;
import br.com.mj.service.WishedRoutingGatewayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerDecisionServiceImpl implements PlayerDecisionService {

    private final WishedRoutingGatewayService wishedRoutingGatewayService;

    private final CurrentRoutingGatewayService currentRoutingGatewayService;

    @Override
    public synchronized WishedRoutingGateway findPlayer(CaptureRequest captureRequest) throws Exception {
        final Integer sumEntries = currentRoutingGatewayService.sum(captureRequest.getOperation(), captureRequest.getBrand());
        log.info("M=findPlayer, sumEntries={}", sumEntries);
        log.info("--------------------------------------------------------");

        final RoutingGatewayDto maxDelta = RoutingGatewayDto.builder()
                .delta(Float.NEGATIVE_INFINITY)
                .build();

        for (WishedRoutingGateway wishedRoutingGateway : wishedRoutingGatewayService.findByGroup(captureRequest.getOperation(), captureRequest.getBrand())) {
            final CurrentRoutingGateway currentRoutingGateway = currentRoutingGatewayService
                    .findOne(wishedRoutingGateway.getId())
                    .orElse(new CurrentRoutingGateway());

            log.info("M=findPlayer, wishedRoutingGateway={}", wishedRoutingGateway);
            log.info("M=findPlayer, currentRoutingGateway={}", currentRoutingGateway);

            float currentRouteRate = currentRoutingGateway.refreshRate(sumEntries);

            log.info("M=findPlayer, wishedRouteRate={}", wishedRoutingGateway.getBalanceRate());
            log.info("M=findPlayer, currentRouteRate={}", currentRouteRate);

            float delta = wishedRoutingGateway.getBalanceRate() - currentRouteRate;

            log.info("M=findPlayer, delta={}", delta);

            if (delta > maxDelta.getDelta()) {
                maxDelta.setId(wishedRoutingGateway.getId());
                maxDelta.setDelta(delta);

                log.info("M=findPlayer, max find, maxDelta={}", maxDelta);
            }

            log.info("--------------------------------------------------------");
        }

        if (ObjectUtils.isEmpty(maxDelta.getId())) {
            throw new Exception("Wished table of routing gateways is empty");
        }

        log.info("M=findPlayer, selected maxDelta={}", maxDelta);

        // retrieve the wished routing gateway selected
        final WishedRoutingGateway wishedGatewaySelected = wishedRoutingGatewayService
                .findOne(maxDelta.getId())
                .orElseThrow(() -> new Exception(String.format("Wished gateway not found using id: %s", maxDelta.getId())));

        log.info("M=findPlayer, wishedGatewaySelected={}", wishedGatewaySelected);

        final Optional<CurrentRoutingGateway> currentGatewaySelected = currentRoutingGatewayService.findOne(maxDelta.getId());

        log.info("M=findPlayer, currentGatewaySelected={}", currentGatewaySelected);

        // check if routing gateway is not present on current table
        if (currentGatewaySelected.isEmpty()) {
            final CurrentRoutingGateway currentGatewaySaved = currentRoutingGatewayService.add(CurrentRoutingGateway.builder()
                    .id(maxDelta.getId())
                    .operation(wishedGatewaySelected.getOperation())
                    .brand(wishedGatewaySelected.getBrand())
                    .gateway(wishedGatewaySelected.getGateway())
                    .provider(wishedGatewaySelected.getProvider())
                    .count(1)
                    .build());

            log.info("M=findPlayer, created, currentGatewaySaved={}", currentGatewaySaved);
        } else {

            try {
                final CurrentRoutingGateway currentRoutingIncremented = currentRoutingGatewayService.incrementCount(currentGatewaySelected.get().getId());
                log.info("M=findPlayer, incremented, currentGatewaySaved={}", currentRoutingIncremented);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return wishedGatewaySelected;
    }
}
