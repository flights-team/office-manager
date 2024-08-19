package com.artur.office_manager.repository;

import com.artur.common.bean.office.AirPort;
import com.artur.common.bean.office.Office;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends MongoRepository<AirPort, String> {
}
