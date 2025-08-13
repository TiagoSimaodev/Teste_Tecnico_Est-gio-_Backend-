package teste.api.rest.back.end.Teste.Controler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import teste.api.rest.back.end.model.Tarefa;
import teste.api.rest.back.end.model.Tarefa.StatusTarefas;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // Carrega o contexto completo da aplicação
@AutoConfigureMockMvc // Fornece uma instância do MockMvc
public class TarefaControleTest  {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void criarTarefa_deveRetornar201() throws Exception {
        // Criando objeto de teste
        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Nova Tarefa");
        tarefa.setDescricao("Descrição");
        tarefa.setDataVencimento(LocalDateTime.now());
        tarefa.setStatus(StatusTarefas.PENDENTE);

        // Executando o POST e validando a resposta
        mockMvc.perform(MockMvcRequestBuilders.post("/tarefa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tarefa)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Nova Tarefa"))
                .andExpect(jsonPath("$.descricao").value("Descrição"))
                .andExpect(jsonPath("$.status").value("PENDENTE"));
    }
}