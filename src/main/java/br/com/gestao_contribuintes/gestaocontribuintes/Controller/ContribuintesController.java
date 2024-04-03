package br.com.gestao_contribuintes.gestaocontribuintes.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestao_contribuintes.gestaocontribuintes.DTO.DependentesDTO;
import br.com.gestao_contribuintes.gestaocontribuintes.DTO.FamiliaDTO;
import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Contribuintes;
import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Dependentes;
import br.com.gestao_contribuintes.gestaocontribuintes.Service.ContribuintesService;

@RestController
@RequestMapping("/contribuintes")
public class ContribuintesController {

    private final ContribuintesService contribuintesService;

    public ContribuintesController(ContribuintesService contribuintesService) {
        this.contribuintesService = contribuintesService;
    }

    @PostMapping
    public ResponseEntity<Contribuintes> create(@RequestBody Contribuintes contribuintes) {
        Contribuintes createdContribuintes = contribuintesService.create(contribuintes);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContribuintes);
    }

    @GetMapping
    public ResponseEntity<List<Contribuintes>> list() {
        List<Contribuintes> contribuintesList = contribuintesService.getAllContribuintes();
        return ResponseEntity.ok(contribuintesList);
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Contribuintes> update(@PathVariable String cpf, @RequestBody Contribuintes contribuintes) {
        if (!cpf.equals(contribuintes.getCPF())) {
            return ResponseEntity.badRequest().build();
        }

        return contribuintesService.update(contribuintes)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{cpf}/conjuge")
    public ResponseEntity<Contribuintes> updateConjuge(@PathVariable String cpf, @RequestBody String cpfConjuge) {
        return contribuintesService.updateCpfConjuge(cpf, cpfConjuge)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<String> delete(@PathVariable("cpf") String cpf) {
        boolean deleted = contribuintesService.delete(cpf);
        if (deleted) {
            return ResponseEntity.ok("Contribuinte excluído");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/familia/{cpf}")
    public ResponseEntity<FamiliaDTO> getFamiliaByContribuinteCPF(@PathVariable String cpf) {
        List<Contribuintes> familia = contribuintesService.getFamiliaByContribuinteCPF(cpf);

        FamiliaDTO familiaDTO = new FamiliaDTO();

        // Definindo o contribuinte principal
        Contribuintes contribuintePrincipal = familia.get(0);
        familiaDTO.setNomeCivilPrincipal(contribuintePrincipal.getNomeCivil());
        familiaDTO.setCpfPrincipal(contribuintePrincipal.getCPF());

        // Iterando sobre os contribuintes para encontrar o cônjuge e os dependentes
        for (int i = 1; i < familia.size(); i++) {
            Contribuintes membroFamilia = familia.get(i);

            // Verificando se é cônjuge
            if (membroFamilia.getCPF().equals(contribuintePrincipal.getCpfConjuge())) {
                familiaDTO.setConjugeNomeCivil(membroFamilia.getNomeCivil());
                familiaDTO.setConjugeCPF(membroFamilia.getCPF());
            } else {
                // Se não for cônjuge, é dependente
                if (familiaDTO.getDependentes() == null) {
                    familiaDTO.setDependentes(new ArrayList<>());
                }

                // Mapeando a lista de Dependentes para DependentesDTO
                List<Dependentes> dependentes = membroFamilia.getDependentes();
                if (dependentes != null && !dependentes.isEmpty()) {
                    for (Dependentes dependente : dependentes) {
                        DependentesDTO dependenteDTO = new DependentesDTO();
                        dependenteDTO.setNomeCivil(dependente.getnomeCivil());
                        dependenteDTO.setCpf(dependente.getCPF());
                        familiaDTO.getDependentes().add(dependenteDTO);
                    }
                }
            }
        }

        return ResponseEntity.ok(familiaDTO);
    }

    // DEPENDENTES
    @GetMapping("/{cpf}/dependentes")
    public ResponseEntity<List<DependentesDTO>> getDependentesByContribuinteCPF(@PathVariable String cpf) {
        try {
            List<Dependentes> dependentes = contribuintesService.getDependentesByContribuinteCPF(cpf);

            // Converter a lista de Dependentes para uma lista de DependentesDTO
            List<DependentesDTO> dependentesDTOList = dependentes.stream()
                    .map(dependente -> {
                        DependentesDTO dependenteDTO = new DependentesDTO();
                        dependenteDTO.setNomeCivil(dependente.getnomeCivil());
                        dependenteDTO.setCpf(dependente.getCPF());
                        return dependenteDTO;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dependentesDTOList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{cpf}/dependentes")
    public ResponseEntity<Contribuintes> addDependente(@PathVariable String cpf, @RequestBody Dependentes dependente) {
        try {
            Contribuintes contribuinte = contribuintesService.addDependente(cpf, dependente);
            return ResponseEntity.ok(contribuinte);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
