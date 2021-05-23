package br.com.mj.resource;

import br.com.mj.domain.redis.entity.CurrentRoutingGateway;
import br.com.mj.service.CurrentRoutingGatewayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/currents")
@RequiredArgsConstructor
@Slf4j
public class CurrentResource {

    private final CurrentRoutingGatewayService currentRoutingGatewayService;

    @PostMapping
    public ResponseEntity<CurrentRoutingGateway> add(@RequestBody CurrentRoutingGateway currentRoutingGateway) {
        return ResponseEntity
                .created(null)
                .body(currentRoutingGatewayService.add(currentRoutingGateway));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void removeAll() {
        currentRoutingGatewayService.findAll().forEach(c -> currentRoutingGatewayService.delete(c.getId()));
    }

    @GetMapping
    public ResponseEntity<List<CurrentRoutingGateway>> getAll() {
        final List<CurrentRoutingGateway> currentGateways = currentRoutingGatewayService.findAll();

        return CollectionUtils.isEmpty(currentGateways)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(currentGateways);
    }
}
