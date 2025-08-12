package teste.api.rest.back.end.model;

import java.time.LocalDateTime;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "tabela")
public class Tarefa {
	
	public Tarefa() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message = "O titulo é obrigatório")
	private String titulo;
	
	private String descricao;
	
	private LocalDateTime dataVencimento;
	
	public enum StatusTarefas {
		PENDENTE,
		CONCLUIDO,
		CANCELADO
	}
	
	public enum PrioridadeTarefas {
        BAIXA,
        MEDIA,
        ALTA
    }
	

    @Enumerated(EnumType.STRING)
    private StatusTarefas status;

    @Enumerated(EnumType.STRING)
    private PrioridadeTarefas prioridade;

    //quando buscar a tarefa pai ela não é carregada automaticamente do banco, só será carrega quando acessar o campo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarefa_pai_id")
    private Tarefa tarefaPai;

    @OneToMany(mappedBy = "tarefaPai", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarefa> subtarefas;
	
	

	
	
	
	// Métodos auxiliares para facilitar a manipulação da lista de subtarefas

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDateTime getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDateTime dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public StatusTarefas getStatus() {
		return status;
	}

	public void setStatus(StatusTarefas status) {
		this.status = status;
	}

	public PrioridadeTarefas getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(PrioridadeTarefas prioridade) {
		this.prioridade = prioridade;
	}

	public Tarefa getTarefaPai() {
		return tarefaPai;
	}

	public void setTarefaPai(Tarefa tarefaPai) {
		this.tarefaPai = tarefaPai;
	}

	public List<Tarefa> getSubtarefas() {
		return subtarefas;
	}

	public void setSubtarefas(List<Tarefa> subtarefas) {
		this.subtarefas = subtarefas;
	}

	public void adicionarSubtarefa(Tarefa subtarefa) {
       //Adiciona a subtarefa na lista da tarefa pai
		subtarefas.add(subtarefa);
       //Define a tarefa pai da subtarefa como esta instância
		subtarefa.setTarefaPai(this);
    }

    public void removerSubtarefa(Tarefa subtarefa) {
      
    	//Remove a subtarefa da lista da tarefa pai
    	subtarefas.remove(subtarefa);
    	
    	//Remove a referência da tarefa pai da subtarefa
        subtarefa.setTarefaPai(null);
    }
}
	
	

