package sese.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sese.responses.LogResponse;
import sese.services.LogService;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<LogResponse> getAllLogs() {
        return logService.getAll();
    }
}
