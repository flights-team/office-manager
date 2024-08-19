package com.artur.office_manager.service;

import com.artur.common.bean.office.Office;
import com.artur.office_manager.exception.NotFoundException;
import com.artur.office_manager.request.CreateRequest;
import com.artur.office_manager.request.UpdateRequest;

import java.util.List;

public interface OfficeService {

    Office create(CreateRequest createRequest);

    Office update(UpdateRequest updateRequest) throws NotFoundException;

    Office getById(String id) throws NotFoundException;

    List<? extends Office> getAll();

    void deleteById(String id);

}
