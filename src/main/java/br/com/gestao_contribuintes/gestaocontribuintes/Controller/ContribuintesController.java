package br.com.gestao_contribuintes.gestaocontribuintes.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
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

import br.com.gestao_contribuintes.gestaocontribuintes.DTO.ContribuintesInfo;
import br.com.gestao_contribuintes.gestaocontribuintes.DTO.DependentesDTO;
import br.com.gestao_contribuintes.gestaocontribuintes.DTO.FamiliaDTO;
import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Contribuintes;
import br.com.gestao_contribuintes.gestaocontribuintes.Entity.Dependentes;
import br.com.gestao_contribuintes.gestaocontribuintes.Repository.ContribuintesRepository;
import br.com.gestao_contribuintes.gestaocontribuintes.Service.ContribuintesService;

@RestController
@RequestMapping("/contribuintes")
public class ContribuintesController {

    private final ContribuintesService contribuintesService;
    private final ContribuintesRepository contribuintesRepository;

    public ContribuintesController(ContribuintesService contribuintesService,
            ContribuintesRepository contribuintesRepository) {
        this.contribuintesService = contribuintesService;
        this.contribuintesRepository = contribuintesRepository;
    }

    // Cria o registro de um contribuinte
    @PostMapping
    public ResponseEntity<String> create(@RequestBody Contribuintes contribuintes) {
        // Verifica se o CPF está preenchido
        if (contribuintes.getCPF() == null || contribuintes.getCPF().isEmpty()) {
            return ResponseEntity.badRequest().body("O campo CPF deve ser preenchido.");
        }

        // Verifica se o CPF já está cadastrado
        if (contribuintesRepository.existsByCPF(contribuintes.getCPF())) {
            return ResponseEntity.badRequest().body("O CPF " + contribuintes.getCPF() + " já está cadastrado.");
        }

        // Se o CPF não existe, cria o contribuinte
        contribuintesService.create(contribuintes);

        // Retorna uma resposta de sucesso
        return ResponseEntity.status(HttpStatus.CREATED).body("Contribuinte registrado com sucesso");
    }

    // Busca uma lista de contribuintes
    @GetMapping
    public ResponseEntity<List<Contribuintes>> list() {
        List<Contribuintes> contribuintesList = contribuintesService.getAllContribuintes();
        return ResponseEntity.ok(contribuintesList);
    }

    // Busca a informação do contribuinte
    @GetMapping("/{cpf}")
    public ResponseEntity<?> getContribuinteByCPF(@PathVariable String cpf) {
        // Verifica se o CPF fornecido é válido
        if (!isValidCPF(cpf)) {
            return ResponseEntity.badRequest().body("CPF fornecido é inválido");
        }

        // Tenta recuperar o contribuinte pelo CPF
        Optional<Contribuintes> contribuinteOptional = contribuintesService.getContribuinteByCPF(cpf);

        // Verifica se o contribuinte foi encontrado
        if (contribuinteOptional.isPresent()) {
            Contribuintes contribuinte = contribuinteOptional.get();
            return ResponseEntity.ok(new ContribuintesInfo(contribuinte));
        } else {
            // Se o contribuinte não foi encontrado, retorna uma resposta adequada
            return ResponseEntity.badRequest().body("CPF não encontrado.");

        }
    }

    private boolean isValidCPF(String cpf) {
        // Remove caracteres não numéricos do CPF
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF possui exatamente 11 dígitos
        return cpf.length() == 11;
    }

    // Atualiza as informações do contribuinte
    @PutMapping("/{cpf}")
    public ResponseEntity<String> update(@PathVariable String cpf, @RequestBody Contribuintes contribuintes) {
        if (!cpf.equals(contribuintes.getCPF())) {
            return ResponseEntity.badRequest().build();
        }
        return contribuintesService.update(contribuintes)
                .map(updatedContribuintes -> ResponseEntity.ok("Dados do contribuinte atualizados com sucesso"))
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualiza as informações de um dependente
    @PutMapping("/dependentes/{cpfContribuinte}/{cpfDependente}")
    public ResponseEntity<?> updateDependente(@PathVariable String cpfContribuinte, @PathVariable String cpfDependente,
            @RequestBody Dependentes dependente) {
        try {
            // Verifica se o dependente existe
            if (!contribuintesService.dependenteExists(cpfContribuinte, cpfDependente)) {
                return ResponseEntity.notFound().build();
            }

            // Atualiza o dependente
            Dependentes updatedDependente = contribuintesService.updateDependente(cpfContribuinte, cpfDependente,
                    dependente);
            return ResponseEntity.ok(updatedDependente);
        } catch (Exception e) {
            // Em caso de exceção, retorne uma mensagem adequada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar o dependente.");
        }
    }

    // Exclui o registro de um contribuinte
    @DeleteMapping("/{cpf}")
    public ResponseEntity<String> delete(@PathVariable("cpf") String cpf) {
        try {
            boolean deleted = contribuintesService.delete(cpf);
            if (deleted) {
                return ResponseEntity.ok("Contribuinte excluído com sucesso.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Exclusão não pode ser realizada, desvincule o dependente do contribuinte.");
        }
    }

    // Exclui um dependente
    @DeleteMapping("/dependentes/{cpfContribuinte}/{cpfDependente}")
    public ResponseEntity<?> deleteDependente(@PathVariable String cpfContribuinte,
            @PathVariable String cpfDependente) {
        try {
            // Verifica se o dependente existe
            if (!contribuintesService.dependenteExists(cpfContribuinte, cpfDependente)) {
                return ResponseEntity.notFound().build();
            }

            // Exclui o dependente
            contribuintesService.deleteDependente(cpfContribuinte, cpfDependente);
            return ResponseEntity.ok("Dependente excluído com sucesso.");
        } catch (Exception e) {
            // Em caso de exceção, retorne uma mensagem adequada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao excluir o dependente.");
        }
    }

    // Traz a lista da familia de um contribuinte
    @GetMapping("/familia/{cpf}")
    public ResponseEntity<FamiliaDTO> getFamiliaByContribuinteCPF(@PathVariable String cpf) {
        return ResponseEntity.ok(contribuintesService.getFamiliaDTOByPrincipalCPF(cpf));
    }

    // Traz a lista de dependentes de um contribuinte
    @GetMapping("/{cpf}/dependentes")
    public ResponseEntity<?> getDependentesByContribuinteCPF(@PathVariable String cpf) {
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
            // Retorna a mensagem desejada em caso de exceção IllegalArgumentException
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("CPF fornecido é inválido ou não corresponde a nenhum contribuinte.");
        }
    }

    @PostMapping("/{cpf}/dependentes")
    public ResponseEntity<String> addDependente(@PathVariable String cpf, @RequestBody Dependentes dependente) {
        try {
            contribuintesService.addDependente(cpf, dependente);
            return ResponseEntity.status(HttpStatus.CREATED).body("Dependente vinculado ao CPF: " + cpf);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Dependente já se encontra vinculado ao CPF: " + cpf);
        }
    }

}