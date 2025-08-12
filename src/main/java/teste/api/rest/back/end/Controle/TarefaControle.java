package teste.api.rest.back.end.Controle;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	
	// cria a tarefa
	@PostMapping
	public ResponseEntity<Tarefa> criar(@RequestBody Tarefa tarefas) {
		Tarefa criada = tarefaService.criarTarefa(tarefas);
		return ResponseEntity.ok(criada);
		
	}
	
	
	// atualiza a tarefa
	@PutMapping({"/{id}"})
	public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @RequestBody Tarefa tarefas){
		Tarefa atualizada = tarefaService.atualizarTarefa(id, tarefas);
		return ResponseEntity.ok(atualizada);
	}
	
	
	//Busca Tarefas por id
	@GetMapping("/{id}")
	public ResponseEntity<Tarefa> buscar(@PathVariable Long id){
		return tarefaService.buscarPorId(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	
	// deleta a tarefa
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deletar(@PathVariable Long id) {
		tarefaService.deletarTarefa(id);
		
		 Map<String, String> response = new HashMap<>();
		    response.put("mensagem", "Tarefa deletada com sucesso");
		    response.put("idDeletado", String.valueOf(id));
		
		return ResponseEntity.ok(response);
	}
	
	
	// para atualizar status
	@PatchMapping("/{id}/status")
	public ResponseEntity<Tarefa> atualizarStatus(@PathVariable Long id, @RequestBody Map<String, String> statusRequest) {
	    String novoStatus = statusRequest.get("status");
	    Tarefa tarefaAtualizada = tarefaService.atualizarStatus(id, novoStatus);
	    return ResponseEntity.ok(tarefaAtualizada);
	}
	
	@GetMapping
	public ResponseEntity<List<Tarefa>> listarTarefas(
	    @RequestParam(required = false) StatusTarefas status,
	    @RequestParam(required = false) PrioridadeTarefas prioridade,
	    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime vencimentoAntes,
	    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime vencimentoDepois
	) {
	    List<Tarefa> tarefas = tarefaService.listarTarefasComFiltro(status, prioridade, vencimentoAntes, vencimentoDepois);
	    return ResponseEntity.ok(tarefas);
	}
		
 
}
