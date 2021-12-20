package nl.ester.persoonsapplicatie.controller;

import nl.ester.persoonsapplicatie.PersoonsService;
import nl.ester.persoonsapplicatie.model.Adres;
import nl.ester.persoonsapplicatie.model.Persoon;
import nl.ester.persoonsapplicatie.repository.AdresRepository;
import org.camunda.bpm.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/personen")
public class PersoonAdresController {
    @Autowired
    PersoonsService persoonsService;

    @Autowired
    AdresRepository adresRepository;

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
        List<Adres> adressen = (List<Adres>)adresRepository.findAll();
        model.addAttribute("personen", personen);
        model.addAttribute("adressen", adressen);
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

    @GetMapping(path = "/addAdres")
    public String adresForm(Model model){
        model.addAttribute("adres",new Adres());
        return "addadres";
    }
    @PostMapping(path = "/addAdres")
    public String addNewAdres(@ModelAttribute Adres adres, @ModelAttribute Persoon persoon, Model model) {
        try {
            adresRepository.save(adres);
            return "all";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("addAdres", true);
            return "all";
        }
    }



}
