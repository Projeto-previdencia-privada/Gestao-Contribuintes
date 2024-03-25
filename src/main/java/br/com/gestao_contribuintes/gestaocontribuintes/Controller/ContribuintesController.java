package br.com.gestao_contribuintes.gestaocontribuintes.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Contribuintes;
import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Filiacao;
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

    @PostMapping("/{cpf}/dependentes") // Endpoint para cadastrar dependentes
    public ResponseEntity<Filiacao> cadastrarDependente(@PathVariable String cpf, @RequestBody Filiacao dependente) {
        // Verifica se o CPF fornecido corresponde a um contribuinte existente
        if (!contribuintesService.existsByCPF(cpf)) {
            return ResponseEntity.notFound().build(); // Retorna 404 caso não exista contribuinte com o CPF fornecido
        }

        // Define o CPF do contribuinte associado ao dependente
        dependente.setCPF(cpf);

        // Realiza o cadastro do dependente
        Filiacao novoDependente = contribuintesService.cadastrarDependente(cpf, dependente);

        // Retorna o dependente cadastrado junto com o código de status apropriado
        return ResponseEntity.status(HttpStatus.CREATED).body(novoDependente);
    }

    @GetMapping
    public ResponseEntity<List<Contribuintes>> list() {
        List<Contribuintes> contribuintesList = contribuintesService.getAllContribuintes();
        return ResponseEntity.ok(contribuintesList);
    }

    @GetMapping("/{cpf}/dependentes")
    public ResponseEntity<List<Filiacao>> getDependentesByContribuinteCPF(@PathVariable String cpf) {
        // Verifica se o CPF fornecido corresponde a um contribuinte existente
        if (!contribuintesService.existsByCPF(cpf)) {
            return ResponseEntity.notFound().build(); // Retorna 404 caso não exista contribuinte com o CPF fornecido
        }

        // Obtém a lista de dependentes associados ao contribuinte com o CPF fornecido
        List<Filiacao> dependentes = contribuintesService.getDependentesByContribuinteCPF(cpf);

        // Retorna a lista de dependentes junto com o código de status apropriado
        return ResponseEntity.ok(dependentes);
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Contribuintes> update(@PathVariable String cpf, @RequestBody Contribuintes contribuintes) {
        // Verifica se o CPF do caminho corresponde ao CPF do objeto
        if (!cpf.equals(contribuintes.getCPF())) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Contribuintes> updatedContribuintes = contribuintesService.update(contribuintes);
        if (updatedContribuintes.isPresent()) {
            return ResponseEntity.ok(updatedContribuintes.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> delete(@PathVariable("cpf") String cpf) {
        boolean deleted = contribuintesService.delete(cpf);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
