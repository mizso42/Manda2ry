package application.controller;

import application.service.ModeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModeratorController {

    private ModeratorService service;

    @Autowired
    public ModeratorController(ModeratorService service) {
        this.service = service;
    }
}
