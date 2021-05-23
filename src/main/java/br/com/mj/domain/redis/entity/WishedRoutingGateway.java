package br.com.mj.domain.redis.entity;

import br.com.mj.domain.redis.constants.BrandEnum;
import br.com.mj.domain.redis.constants.GatewayEnum;
import br.com.mj.domain.redis.constants.OperationEnum;
import br.com.mj.domain.redis.constants.ProviderEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash("WishedRoutingGateway")
public class WishedRoutingGateway implements Serializable {

    @Id
    private String id;

    private OperationEnum operation;
    private BrandEnum brand;
    private GatewayEnum gateway;
    private ProviderEnum provider;
    private Integer balanceRate;
}
