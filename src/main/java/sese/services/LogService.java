package sese.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sese.entities.Log;
import sese.entities.Systemuser;
import sese.repositories.LogRepository;
import sese.repositories.SystemuserRepository;
import sese.responses.LogResponse;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogService.class);

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private SystemuserRepository systemuserRepository;

    /**
     * Since there is currently only one user --> that user is automatically chosen (== mocked)
     * @param message
     */
    public void logAction(String message){
        Log log = new Log();

        Systemuser currentUser = systemuserRepository.findAll().get(0);

        log.setSystemuser(currentUser);
        log.setText(message);
        log.setTimestamp(OffsetDateTime.now());

        addLogEntry(log);
    }

    @Transactional
    void addLogEntry(Log log) {

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
