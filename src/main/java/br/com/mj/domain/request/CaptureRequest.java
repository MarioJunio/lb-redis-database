package br.com.mj.domain.request;

import br.com.mj.domain.redis.constants.BrandEnum;
import br.com.mj.domain.redis.constants.OperationEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class CaptureRequest implements Serializable {

    private OperationEnum operation;
    private BrandEnum brand;
}
