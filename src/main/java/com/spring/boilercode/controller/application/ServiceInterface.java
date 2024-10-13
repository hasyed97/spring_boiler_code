package com.spring.boilercode.controller.application;


import com.spring.boilercode.controller.ReqResDTO.GeneralRequest;

public interface ServiceInterface {
    Long processRequest(GeneralRequest generalRequest);
}
