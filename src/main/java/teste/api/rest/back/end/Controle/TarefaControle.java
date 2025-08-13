package teste.api.rest.back.end.Controle;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import teste.api.rest.back.end.Service.TarefaService;
import teste.api.rest.back.end.model.Tarefa;
import teste.api.rest.back.end.model.Tarefa.PrioridadeTarefas;
import teste.api.rest.back.end.model.Tarefa.StatusTarefas;

@RestController
@RequestMapping("/tarefa")
public class TarefaControle {

    private final TarefaService tarefaService;

    public TarefaControle(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    //  Criar Tarefa 
    @PostMapping
    public ResponseEntity<Tarefa> criar(@Valid @RequestBody Tarefa tarefa) {
        Tarefa novaTarefa = tarefaService.criarTarefa(tarefa);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novaTarefa.getId())
                .toUri();

        return ResponseEntity.created(location).body(novaTarefa);
    }

    // Atualizar Tarefa
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @Valid @RequestBody Tarefa tarefa) {
        return tarefaService.atualizarTarefa(id, tarefa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Buscar Tarefa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscar(@PathVariable Long id) {
        return tarefaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Deletar Tarefa
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletar(@PathVariable Long id) {
        tarefaService.deletarTarefa(id);

        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Tarefa deletada com sucesso");

        return ResponseEntity.ok(response);
    }

    // Atualizar Status da Tarefa 
    @PatchMapping("/{id}/status")
    public ResponseEntity<Tarefa> atualizarStatus(@PathVariable Long id,
                                                  @RequestBody Map<String, String> statusRequest) {
        String novoStatus = statusRequest.get("status");
        Tarefa tarefaAtualizada = tarefaService.atualizarStatus(id, novoStatus);
        return ResponseEntity.ok(tarefaAtualizada);
    }

    // Listar Tarefas com Filtros 
    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTarefas(
            @RequestParam(required = false) StatusTarefas status,
            @RequestParam(required = false) PrioridadeTarefas prioridade,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime vencimentoInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime vencimentoFim
    ) {
        List<Tarefa> tarefas = tarefaService.listarTarefasComFiltro(status, prioridade, vencimentoInicio, vencimentoFim);
        return ResponseEntity.ok(tarefas);
    }
}