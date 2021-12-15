package nl.ester.persoonsapplicatie.controller;

import nl.ester.persoonsapplicatie.PersoonsService;
import nl.ester.persoonsapplicatie.model.Persoon;
import nl.ester.persoonsapplicatie.repository.PersoonsRepository;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Controller
@RequestMapping(path = "/personen")
public class PersoonController {
    @Autowired
    PersoonsService persoonsService;

    @Autowired
    ProcessEngine processEngine;

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/all")
    public String all() {
        return "all";
    }

    @GetMapping(value = "all")
    public String getPersonen(Model model,
                              @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        List<Persoon> personen = persoonsService.vindAllePersonen();
        model.addAttribute("personen", personen);
        return "all";
    }



    @GetMapping(path = "/add")
    public String persoonForm(Model model){
        model.addAttribute("persoon",new Persoon());
        return "add";
    }
    @PostMapping(path = "/add")
    public String addNewPersoon(@ModelAttribute Persoon persoon, Model model) {
        try {
            persoonsService.saveAndUpdatePersoon(persoon);
            return "all";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
         model.addAttribute("add", true);
            return "all";
        }
    }




}
