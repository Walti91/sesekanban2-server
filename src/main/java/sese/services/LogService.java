package sese.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sese.entities.Log;
import sese.repositories.LogRepository;
import sese.responses.LogResponse;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogService.class);

    @Autowired
    private LogRepository logRepository;

    @Transactional
    public void addLogEntry(Log log) {

        if (Objects.isNull(log) || Objects.isNull(log.getText()) || Objects.isNull(log.getSystemuser())) {
            LOGGER.error("Log object is faulty: " + log);
            return;
        }

        logRepository.save(log);
    }

    public List<LogResponse> getAll() {
        List<Log> bills = logRepository.findAll();

        List<LogResponse> responseList = bills.stream().map(LogResponse::new).collect(Collectors.toList());
        return responseList;
    }
}
