package com.artur.office_manager.service;

import com.artur.common.bean.office.AirPort;
import com.artur.common.bean.office.Office;
import com.artur.common.model.Position;
import com.artur.office_manager.exception.NotFoundException;
import com.artur.office_manager.repository.AirportRepository;
import com.artur.office_manager.request.AirportCreateRequest;
import com.artur.office_manager.request.AirportUpdateRequest;
import com.artur.office_manager.request.CreateRequest;
import com.artur.office_manager.request.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AirportService implements OfficeService{

    @Autowired
    private AirportRepository airportRepository;

    @Override
    public Office getById(String id) throws NotFoundException {
        return airportRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Office with specified id " + id + " was not found"));
    }

    @Override
    public List<? extends Office> getAll(){
        return airportRepository.findAll();
    }

    @Override
    public Office create(CreateRequest createRequest){
        AirportCreateRequest airportCreateRequest = (AirportCreateRequest) createRequest;
        return airportRepository.save(new AirPort(
                genId(),
                airportCreateRequest.name(),
                Position.builder()
                        .x(airportCreateRequest.positionX())
                        .y(airportCreateRequest.positionY())
                        .angle(0f)
                        .build()
        ));
    }

    private String genId(){
        String id;
        do{
           id = UUID.randomUUID().toString();
        } while (airportRepository.existsById(id));
        return id;
    }

    @Override
    public Office update(UpdateRequest updateRequest) throws NotFoundException {
        AirportUpdateRequest airportUpdateRequest = (AirportUpdateRequest) updateRequest;
        AirPort airPort = (AirPort) getById(airportUpdateRequest.id());
        if(airportUpdateRequest.name() != null){
            airPort.setName(airportUpdateRequest.name());
        }
        if(airportUpdateRequest.positionX() != null){
            airPort.getPosition().setX(airportUpdateRequest.positionX());
        }
        if(airportUpdateRequest.positionY() != null){
            airPort.getPosition().setX(airportUpdateRequest.positionY());
        }
        return airportRepository.save(airPort);
    }

    @Override
    public void deleteById(String id) {
        airportRepository.deleteById(id);
    }
}
