package nl.ester.persoonsapplicatie;

import nl.ester.persoonsapplicatie.model.Persoon;
import nl.ester.persoonsapplicatie.repository.PersoonsRepository;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class PersoonsService {

    @Autowired
    PersoonsRepository persoonsRepository;

    @Autowired
    ProcessEngine processEngine;

    public void saveAndUpdatePersoon(Persoon persoon) {
        if (persoon != null) {
            if (persoon.getAanspreekTitel() == null)
                persoon.setAanspreekTitel(bepaalAanspreektitel(persoon, processEngine));
            persoonsRepository.save(persoon);
        }
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
        return Period.between(geboorteDatum, LocalDate.now()).getYears();
    }

    public List<Persoon> vindAllePersonen() {
        return (List<Persoon>)persoonsRepository.findAll();
    }
}
