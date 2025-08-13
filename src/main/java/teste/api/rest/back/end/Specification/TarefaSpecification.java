package teste.api.rest.back.end.Specification;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import teste.api.rest.back.end.model.Tarefa;
import teste.api.rest.back.end.model.Tarefa.PrioridadeTarefas;
import teste.api.rest.back.end.model.Tarefa.StatusTarefas;

public class TarefaSpecification {

    public static Specification<Tarefa> hasStatus(StatusTarefas status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Tarefa> hasPrioridade(PrioridadeTarefas prioridade) {
        return (root, query, cb) -> prioridade == null ? null : cb.equal(root.get("prioridade"), prioridade);
    }
	
    public static Specification<Tarefa> vencimentoBetween(LocalDateTime inicio, LocalDateTime fim) {
        return (root, query, cb) -> {
            if (inicio == null && fim == null) return null;
            if (inicio != null && fim != null) return cb.between(root.get("dataVencimento"), inicio, fim);
            if (inicio != null) return cb.greaterThanOrEqualTo(root.get("dataVencimento"), inicio);
            return cb.lessThanOrEqualTo(root.get("dataVencimento"), fim);
        };
    }
}
