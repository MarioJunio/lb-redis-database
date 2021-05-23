package br.com.mj.resource;

import br.com.mj.domain.redis.entity.WishedRoutingGateway;
import br.com.mj.service.WishedRoutingGatewayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wisheds")
@RequiredArgsConstructor
@Slf4j
public class WishedResource {

    private final WishedRoutingGatewayService wishedRoutingGatewayService;

    @PostMapping
    public ResponseEntity<WishedRoutingGateway> add(@RequestBody WishedRoutingGateway wishedRoutingGateway) {
        return ResponseEntity
                .created(null)
                .body(wishedRoutingGatewayService.add(wishedRoutingGateway));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<WishedRoutingGateway>> addBulk(@RequestBody List<WishedRoutingGateway> wisheds) {
        return ResponseEntity
                .created(null)
                .body(wisheds.stream().map(w -> wishedRoutingGatewayService.add(w)).collect(Collectors.toList()));
    }

    @GetMapping
    public ResponseEntity<List<WishedRoutingGateway>> getAll() {
        final List<WishedRoutingGateway> wishedGateways = wishedRoutingGatewayService.findAll();

        return CollectionUtils.isEmpty(wishedGateways)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(wishedGateways);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBalancerRate(@PathVariable String id, @RequestParam("rate") Integer rate) {
        try {
            return ResponseEntity.ok(wishedRoutingGatewayService.updateBalancerRate(id, rate));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id) {
        wishedRoutingGatewayService.delete(id);
    }
}
