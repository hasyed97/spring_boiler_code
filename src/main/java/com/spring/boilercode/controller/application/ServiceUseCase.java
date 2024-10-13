package com.spring.boilercode.controller.application;

import com.spring.boilercode.controller.ReqResDTO.GeneralRequest;
import com.spring.boilercode.utils.RequestHelper;
import com.spring.boilercode.UseCase;


@UseCase
public class ServiceUseCase implements ServiceInterface {
    private final RequestHelper requestHelper;

    public ServiceUseCase(RequestHelper requestHelper) {
        this.requestHelper = requestHelper;
    }

    @Override
    public Long processRequest(GeneralRequest generalRequest){

        //  TODO: Integrate API Calling code
        //      ResponseDTO postRequestRes = requestHelper.callExternalPostApi(endPoint, param, body, ResponseDTO.class);

        return 0L;
    }

}
