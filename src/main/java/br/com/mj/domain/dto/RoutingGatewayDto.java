package br.com.mj.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoutingGatewayDto {

    private String id;
    private float delta;
}
