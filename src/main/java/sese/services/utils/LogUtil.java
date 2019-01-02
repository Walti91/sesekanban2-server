package sese.services.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sese.entities.Log;
import sese.entities.Systemuser;
import sese.repositories.SystemuserRepository;
import sese.services.LogService;

import java.time.OffsetDateTime;

@Service
public class LogUtil {

    @Autowired
    private LogService logService;

    @Autowired
    private SystemuserRepository systemuserRepository;

    //Since there is currently only one user --> that user is automatically chosen (== mocked)
    private Systemuser currentUser;

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

        logService.addLogEntry(log);
    }
}
