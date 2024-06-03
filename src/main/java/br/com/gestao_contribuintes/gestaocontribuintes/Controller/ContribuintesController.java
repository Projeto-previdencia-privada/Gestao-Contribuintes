package br.com.gestao_contribuintes.gestaocontribuintes.Controller;

import java.util.List;
import java.util.Map;
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
import io.swagger.v3.oas.annotations.Operation;

import br.com.gestao_contribuintes.gestaocontribuintes.DTO.ContribuintesInfo;
import br.com.gestao_contribuintes.gestaocontribuintes.DTO.CadastroContribuintesDTO;
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

    @Operation(summary = "Registra Contribuinte", description = "Cria o registro de um contribuinte", tags = "Contribuintes" )
    @PostMapping
    public ResponseEntity<Map<String, String>> create(@RequestBody Contribuintes contribuintes) {
        // Verifica se o CPF está preenchido
        if (contribuintes.getCPF() == null || contribuintes.getCPF().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "O campo CPF deve ser preenchido."));
        }

        // Verifica se o CPF é válido
        if (!isValidCPF(contribuintes.getCPF())) {
            return ResponseEntity.badRequest().body(Map.of("error", "CPF inválido."));
        }

        // Verifica se o CPF já está cadastrado
        if (contribuintesRepository.existsByCPF(contribuintes.getCPF())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "O CPF " + contribuintes.getCPF() + " já está cadastrado."));
        }

        // Se o CPF não existe, cria o contribuinte
        contribuintesService.create(contribuintes);

        // Retorna uma resposta de sucesso
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Contribuinte registrado com sucesso"));
    }

    @Operation(summary = "Lista Contribuintes", description = "Busca uma lista de contribuintes", tags = "Contribuintes" )
    @GetMapping
    public ResponseEntity<List<Contribuintes>> list() {
        List<Contribuintes> contribuintesList = contribuintesService.getAllContribuintes();
        return ResponseEntity.ok(contribuintesList);
    }

    @Operation(summary = "Informação", description = "Busca informações referente a contribuição do contribuinte", tags = "Contribuintes" )
    @GetMapping("/{cpf}")
    public ResponseEntity<Map<String, Object>> getContribuinteByCPF(@PathVariable String cpf) {
        // Verifica se o CPF fornecido é válido
        if (!isValidCPF(cpf)) {
            Map<String, Object> error = Map.of("error", "CPF fornecido é inválido");
            return ResponseEntity.badRequest().body(error);
        }

        // Tenta recuperar o contribuinte pelo CPF
        Optional<Contribuintes> contribuinteOptional = contribuintesService.getContribuinteByCPF(cpf);

        // Verifica se o contribuinte foi encontrado
        if (contribuinteOptional.isPresent()) {
            Contribuintes contribuinte = contribuinteOptional.get();
            ContribuintesInfo contribuintesInfo = new ContribuintesInfo(contribuinte);
            Map<String, Object> success = Map.of("info", contribuintesInfo);
            return ResponseEntity.ok(success);
        } else {
            // Se o contribuinte não foi encontrado, retorna uma resposta adequada
            Map<String, Object> notFound = Map.of("error", "CPF não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound);
        }

    }

    
    @Operation(summary = "Cadastro", description = "Busca informações referente ao cadastro do contribuinte", tags = "CadastroContribuintes" )
    @GetMapping("/cadastroAtualizacao/{cpf}")
    public ResponseEntity<Map<String, Object>> getContribuinteAtualizaCadByCPF(@PathVariable String cpf) {
        // Verifica se o CPF fornecido é válido
        if (!isValidCPF(cpf)) {
            Map<String, Object> error = Map.of("error", "CPF fornecido é inválido");
            return ResponseEntity.badRequest().body(error);
        }

        // Tenta recuperar o contribuinte pelo CPF
        Optional<Contribuintes> contribuinteOptional = contribuintesService.getContribuinteByCPF(cpf);

        // Verifica se o contribuinte foi encontrado
        if (contribuinteOptional.isPresent()) {
            Contribuintes contribuinte = contribuinteOptional.get();
            CadastroContribuintesDTO CadastroContribuintesDTO = new CadastroContribuintesDTO(contribuinte);
            Map<String, Object> success = Map.of("info", CadastroContribuintesDTO);
            return ResponseEntity.ok(success);
        } else {
            // Se o contribuinte não foi encontrado, retorna uma resposta adequada
            Map<String, Object> notFound = Map.of("error", "CPF não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound);
        }

    }

    // VALIDAÇÃO DE CPF
    private boolean isValidCPF(String cpf) {
        // Remove caracteres não numéricos do CPF
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF possui exatamente 11 dígitos
        if (!cpf.matches("^\\d{11}$")) {
            return false;
        }

        // Verifica se todos os dígitos são iguais, um caso inválido conhecido
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Se passou por todas as verificações, é considerado válido
        return true;
    }

    @Operation(summary = "Atualiza Contribuinte", description = "Atualiza as informações do contribuinte", tags = "Contribuintes" )
    @PutMapping("/{cpf}")
    public ResponseEntity<?> update(@PathVariable String cpf, @RequestBody Contribuintes contribuintes) {
        // IMPEDE DO CPF DE SER ATUALIZADO
        if (contribuintes.getCPF() != null && !contribuintes.getCPF().equals(cpf)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Alteração não permitida"));
        }

        // Verifica se o CPF é válido
        if (!isValidCPF(cpf)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "CPF inválido"));
        }

        // Define o CPF da URL no objeto contribuintes para garantir consistência
        contribuintes.setCPF(cpf);

        // Tenta atualizar o contribuinte
        Optional<Contribuintes> updatedContribuintes = contribuintesService.update(contribuintes);

        // Verifica se o contribuinte foi atualizado com sucesso
        if (updatedContribuintes.isPresent()) {
            return ResponseEntity.ok(Map.of("message", "Dados do contribuinte atualizados com sucesso"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "CPF não encontrado"));
        }
    }

    @Operation(summary = "Atualiza Dependente", description = "Atualiza as informações do dependente", tags = "Dependentes" )
    @PutMapping("/dependentes/{cpfContribuinte}/{cpfDependente}")
    public ResponseEntity<Map<String, Object>> updateDependente(
            @PathVariable String cpfContribuinte,
            @PathVariable String cpfDependente,
            @RequestBody Dependentes dependente) {

        // Validações de CPF para contribuinte e dependente
        if (!isValidCPF(cpfContribuinte) || !isValidCPF(cpfDependente)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "CPF inválido na URL"));
        }

        if (dependente.getCPF() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Alteração não é permitida"));
        }

        try {
            // Atualiza o dependente
            contribuintesService.updateDependente(cpfContribuinte, cpfDependente, dependente);

            // Retorna apenas a mensagem de sucesso
            return ResponseEntity.ok(Map.of("message", "Dados do dependente atualizados com sucesso"));

        } catch (IllegalArgumentException e) {
            // Lida com erros conhecidos
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "CPF não encontrado"));
        } catch (Exception e) {
            // Lida com exceções inesperadas
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro ao atualizar o dependente"));
        }
    }

    @Operation(summary = "Exclui Contribuinte", description = "Exclui o registro de um contribuinte", tags = "Contribuintes" )
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("cpf") String cpf) {
        // Verifica se o CPF fornecido é válido
        if (!isValidCPF(cpf)) {
            Map<String, Object> error = Map.of("error", "CPF fornecido é inválido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        try {
            boolean deleted = contribuintesService.delete(cpf);
            if (deleted) {
                Map<String, Object> success = Map.of("message", "Contribuinte excluído com sucesso");
                return ResponseEntity.ok(success);
            } else {
                Map<String, Object> notFound = Map.of("error", "CPF não encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound);
            }
        } catch (DataIntegrityViolationException e) {
            // Em caso de erro, retorna uma mensagem apropriada
            Map<String, Object> internalError = Map.of("error",
                    "Exclusão não pode ser realizada, desvincule o dependente do contribuinte.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(internalError);
        }
    }

    @Operation(summary = "Exclui Dependente", description = "Desvincula o dependente de um contribuinte", tags = "Dependentes" )
    @DeleteMapping("/dependentes/{cpfContribuinte}/{cpfDependente}")
    public ResponseEntity<Map<String, Object>> deleteDependente(
            @PathVariable String cpfContribuinte,
            @PathVariable String cpfDependente) {
        // Verifica se o CPF do contribuinte é válido
        if (!isValidCPF(cpfContribuinte)) {
            Map<String, Object> error = Map.of("error", "CPF do contribuinte inválido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        // Verifica se o CPF do dependente é válido
        if (!isValidCPF(cpfDependente)) {
            Map<String, Object> error = Map.of("error", "CPF do dependente inválido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        try {
            // Verifica se o dependente existe
            if (!contribuintesService.dependenteExists(cpfContribuinte, cpfDependente)) {
                Map<String, Object> notFound = Map.of("error", "CPF do dependente não encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound);
            }

            // Exclui o dependente
            contribuintesService.deleteDependente(cpfContribuinte, cpfDependente);
            Map<String, Object> success = Map.of("message", "Dependente excluído com sucesso");
            return ResponseEntity.ok(success);

        } catch (Exception e) {
            // Em caso de exceção, retorne uma mensagem adequada
            Map<String, Object> internalError = Map.of("error", "Erro ao excluir o dependente");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(internalError);
        }
    }

    @Operation(summary = "Lista Familia", description = "Lista os familiares de um contribuinte", tags = "Família" )
    @GetMapping("/familia/{cpf}")
    public ResponseEntity<Map<String, Object>> getFamiliaByContribuinteCPF(@PathVariable String cpf) {
        // Verifica se o CPF fornecido é válido
        if (!isValidCPF(cpf)) {
            Map<String, Object> error = Map.of("error", "CPF fornecido é inválido");
            return ResponseEntity.badRequest().body(error);
        }

        // Verifica se o CPF do contribuinte está na base de dados
        if (!contribuintesRepository.existsByCPF(cpf)) {
            Map<String, Object> notFound = Map.of("error", "CPF não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound);
        }

        // Obtém a lista de membros da família do contribuinte com o CPF fornecido
        FamiliaDTO familiaDTO = contribuintesService.getFamiliaDTOByPrincipalCPF(cpf);

        // Retorna a lista de membros da família
        Map<String, Object> success = Map.of("familia", familiaDTO);
        return ResponseEntity.ok(success);
    }

    @Operation(summary = "Lista Dependente", description = "Lista os dependentes de um contribuinte", tags = "Dependentes" )
    @GetMapping("/{cpf}/dependentes")
    public ResponseEntity<Map<String, Object>> getDependentesByContribuinteCPF(@PathVariable String cpf) {
        // Verifica se o CPF fornecido é válido
        if (!isValidCPF(cpf)) {
            Map<String, Object> error = Map.of("error", "CPF fornecido na url é inválido");
            return ResponseEntity.badRequest().body(error);
        }

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

            // Criar um mapa para o resultado
            Map<String, Object> success = Map.of("dependentes", dependentesDTOList);
            return ResponseEntity.ok(success);

        } catch (IllegalArgumentException e) {
            // Retorna uma mensagem de erro detalhada em caso de exceção
            Map<String, Object> notFound = Map.of("error", "CPF do contribuinte não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound);
        }
    }

    @Operation(summary = "Registra Dependente", description = "Vincula um dependente a um contribuinte", tags = "Dependentes" )
    @PostMapping("/{cpf}/dependentes")
    public ResponseEntity<Map<String, Object>> addDependente(@PathVariable String cpf, @RequestBody Dependentes dependente) {
        // Verifica se o CPF do contribuinte é válido
        if (!isValidCPF(cpf)) {
            Map<String, Object> error = Map.of("error", "CPF fornecido na URL é inválido");
            return ResponseEntity.badRequest().body(error);
        }
    
        // Verifica se o CPF do dependente é válido
        if (!isValidCPF(dependente.getCPF())) {
            Map<String, Object> error = Map.of("error", "CPF do dependente é inválido");
            return ResponseEntity.badRequest().body(error);
        }
    
        // Verifica se o contribuinte existe
        if (!contribuintesRepository.existsByCPF(cpf)) {
            Map<String, Object> error = Map.of("error", "CPF fornecido na URL não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    
        // Verifica se o dependente existe como contribuinte
        if (!contribuintesRepository.existsByCPF(dependente.getCPF())) {
            Map<String, Object> error = Map.of("error", "Dependente não é um contribuinte registrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    
        try {
            contribuintesService.addDependente(cpf, dependente);
            Map<String, Object> success = Map.of("message", "Dependente vinculado com sucesso ao CPF: " + cpf);
            return ResponseEntity.status(HttpStatus.CREATED).body(success);
        } catch (Exception e) {
            Map<String, Object> conflict = Map.of("error", "Dependente já está vinculado ao CPF: " + cpf);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(conflict);
        }
    }

    @Operation(summary = "Ativar Contribuinte", description = "Ativa o registro de um contribuinte", tags = "Contribuintes")
    @PutMapping("/{cpf}/ativar")
    public ResponseEntity<Map<String, Object>> ativarContribuinte(@PathVariable String cpf) {
        Optional<Contribuintes> contribuinteOptional = contribuintesService.getContribuinteByCPF(cpf);
        if (contribuinteOptional.isPresent()) {
            contribuintesService.ativarContribuinte(cpf);
            Map<String, Object> success = Map.of("message", "Contribuinte ativado com sucesso");
            return ResponseEntity.ok(success);
        } else {
            Map<String, Object> notFound = Map.of("error", "CPF não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound);
        }
    }

    @Operation(summary = "Inativar Contribuinte", description = "Inativa o registro de um contribuinte", tags = "Contribuintes")
    @PutMapping("/{cpf}/inativar")
    public ResponseEntity<Map<String, Object>> inativarContribuinte(@PathVariable String cpf) {
        Optional<Contribuintes> contribuinteOptional = contribuintesService.getContribuinteByCPF(cpf);
        if (contribuinteOptional.isPresent()) {
            contribuintesService.inativarContribuinte(cpf);
            Map<String, Object> success = Map.of("message", "Contribuinte inativado com sucesso");
            return ResponseEntity.ok(success);
        } else {
            Map<String, Object> notFound = Map.of("error", "CPF não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound);
        }
    }
    

}