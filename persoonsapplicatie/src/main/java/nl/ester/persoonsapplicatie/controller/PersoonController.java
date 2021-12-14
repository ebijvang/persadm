package nl.ester.persoonsapplicatie.controller;

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
    PersoonsRepository persoonsRepository;

    @Autowired
    ProcessEngine processEngine;

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }


    @GetMapping(value = "all")
    public String getPersonen(Model model,
                              @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        List<Persoon> personen = (List<Persoon>) persoonsRepository.findAll();
//        personen.forEach(persoon -> persoon.setAanspreekTitel(bepaalAanspreektitel(persoon, processEngine)));
        personen.forEach(persoon-> persoon.setAanspreekTitel("test"));
        model.addAttribute("personen", personen);
        return "all";
    }

    private String bepaalAanspreektitel(Persoon persoon, ProcessEngine processEngine) {
        DecisionService decisionService = processEngine.getDecisionService();
        VariableMap variables = Variables.createVariables()
                .putValue("leeftijd", bepaalLeeftijd(persoon.getGeboorteDatum()))
                .putValue("geslacht", persoon.getGeslacht());
        DmnDecisionTableResult result = decisionService.evaluateDecisionTableByKey("Decision_081yllr", variables);
        return result.getSingleEntry();
    }

    private Integer bepaalLeeftijd(LocalDate geboorteDatum) {
        return Period.between(geboorteDatum,LocalDate.now()).getYears();
    }


    @GetMapping(path = "/add")
    public String persoonForm(Model model){
        model.addAttribute("persoon",new Persoon());
        return "add";
    }
    @PostMapping(path = "/add")
    public String addNewPersoon(@ModelAttribute Persoon persoon, Model model) {
        try {
            persoonsRepository.save(persoon);
            return "all";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
         model.addAttribute("add", true);
            return "all";
        }
    }




}
