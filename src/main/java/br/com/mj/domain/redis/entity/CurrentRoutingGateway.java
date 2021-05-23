package br.com.mj.domain.redis.entity;

import br.com.mj.domain.redis.constants.BrandEnum;
import br.com.mj.domain.redis.constants.GatewayEnum;
import br.com.mj.domain.redis.constants.OperationEnum;
import br.com.mj.domain.redis.constants.ProviderEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@RedisHash("CurrentRoutingGateway")
public class CurrentRoutingGateway implements Serializable {

    @Id
    private String id;

    @Indexed
    private OperationEnum operation;

    @Indexed
    private BrandEnum brand;

    private GatewayEnum gateway;
    private ProviderEnum provider;

    @Builder.Default
    private Integer count = 0;

    @ToString.Exclude
    @Transient
    private transient float rate;

    public float refreshRate(int sumEntries) {
        setRate((sumEntries > 0 && count > 0
                ? count / (float) sumEntries
                : 0) * 100);

        return getRate();
    }
}
