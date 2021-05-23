package br.com.mj.service;

import br.com.mj.domain.redis.entity.WishedRoutingGateway;
import br.com.mj.domain.request.CaptureRequest;

public interface PlayerDecisionService {

    WishedRoutingGateway findPlayer(final CaptureRequest captureRequest) throws Exception;
}
