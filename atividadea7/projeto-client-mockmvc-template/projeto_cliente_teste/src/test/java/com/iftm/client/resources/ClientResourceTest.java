package com.iftm.client.resources;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iftm.client.dto.ClientDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.ArrayList;

import javax.print.attribute.standard.Media;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Answers.valueOf;
import static org.hamcrest.Matchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ClientResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



    /**
     * <p><b>Método de teste para verificar o retorno de uma lista de clientes</b></p>
     * <p><b>Entrada:</b> Endpoint "/clients".</p>
     * <p><b>Saída:</b> Lista contendo 12 clientes.</p>
     */
    @Test
    @DisplayName("Verificar se o endpoint get/clients/ retorna todos os clientes existentes")
    public void testarEndPointRetornaTodosOsClientes() {
        //Arrange
        int quantidadeClienteEsperado = 12;
        int quantidadeClienteEsperadoPagina = 12;

        //Act
        ResultActions resultado = assertDoesNotThrow(() -> mockMvc.perform(get("/clients").accept(MediaType.APPLICATION_JSON)));

        //Assert
        assertDoesNotThrow(() -> 
            resultado
            .andExpect(status().isOk())     //'.andExpect' => Esperando o resultado
            .andExpect(jsonPath("$.content").exists())      // 'jsonPath' => Analisa cada elemento do JSON
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content[?(@.id == '%s')]", 1L).exists())
            .andExpect(jsonPath("$.totalElements").value(quantidadeClienteEsperado))
            .andExpect(jsonPath("$.totalElements").exists())
            .andExpect(jsonPath("$.content[0].id", is(4)))      //Verificar o ID na posição 0 do vetor
            .andExpect(jsonPath("$.content.*", isA(ArrayList.class)))
            .andExpect(jsonPath("$.content.*", hasSize(quantidadeClienteEsperado)))
            .andExpect(jsonPath("$.content[*].id", hasItems(4, 7, 8)))  //verificar se tem os IDs 4, 7 e 8 em qualquer ordem no vetor
            );

        // assertDoesNotThrow(() -> resultado.andExpect(jsonPath("$.content").exists())); 
        // assertDoesNotThrow(() -> resultado.andExpect(jsonPath("$.content").exists())); 
    }

    /**
     * <p><b>Método para testar a busca por um ID não existente</b></p>
     * <p><b>Entrada:</b> IdInexistente = 111L</p>
     * <p><b>Saída:</b> JSON contendo mensagem de erro.</p>
     * <p>A validação verifica se o erro e a mensagem existem, além qual erro está retornando.</p>
     */
    @Test
    public void testarBuscarPorIdNaoExistente() {
        //Arrange
        Long idInexistente = 111L;

        //Act
        ResultActions resultado = assertDoesNotThrow(() -> mockMvc.perform(get("/clients/id/{id}", idInexistente).accept(MediaType.APPLICATION_JSON)));

        //Assert
        assertDoesNotThrow(() -> resultado
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").exists())
            .andExpect(jsonPath("$.error").value("Resource not found"))
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.message").value("Entity not found"))
        );
    }

    /**
     * <p><b>Método para testar a buscar por um ID existente</b></p>
     * <p><b>Entrada:</b> idExistente = 1L</p>
     * <p><b>Saída:</b> JSON contendo as informações do cliente com ID 1.</p>
     * <p>A validação verifica se o retorno existe e se o valor é do ID solicitado</p>
     */
    @Test
    public void testarBuscarPorIdExistente() {
        //Arrange
        Long idExistente = 1L;

        //Act
        ResultActions resultado = assertDoesNotThrow(() -> mockMvc.perform(get("/clients/id/{id}", idExistente).accept(MediaType.APPLICATION_JSON)));

        //Assert
        assertDoesNotThrow(() -> resultado
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.id").value(idExistente))
            );
    }

    /**
     * <p><b>Método para testar buscar clientes pelo salário exato</p></b>
     * <p><b>Entrada:</b> salarioResultado = 2500.0</p>
     * <p><b>Saída:</b> JSON contendo informações de 2 clientes.</p></b>
     * <p>A validação verifica se o JSON existe, se é um ArrayList, se a variável <b>income</b>
     *  tem o mesmo valor de <b>salarioResultado</b> e se a quantidade de clientes retornados 
     * <b>(totalElements)</b> é igual a <b>quantidadeClientes</b>.</p>
     */
    @Test
    public void testarBuscarPorSalarioQueRetornaClientes() {
        //Arrange
        Double salarioResultado = 2500.0;
        Integer quantidadeClientes = 3; //Pois existem 3 clientes que recebem R$ 2500.00

        //Act
        ResultActions resultado = assertDoesNotThrow(() -> mockMvc.perform(get("/clients/income/")
                                                                .param("income", String.valueOf(salarioResultado))
                                                                .accept(MediaType.APPLICATION_JSON)));

        //Assert
        assertDoesNotThrow(() -> resultado
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").exists())
            .andExpect(jsonPath("$.content.*", isA(ArrayList.class)))
            .andExpect(jsonPath("$.content.[*].income", everyItem(is(salarioResultado))))
            .andExpect(jsonPath("$.totalElements").value(quantidadeClientes)));
    }

    /**
     * <p><b>Método para testar buscar clientes pelo salário exato e retorna vazio</p></b>
     * <p><b>Entrada:</b> salarioResultado = 4000.0</p>
     * <p><b>Saída:</b> JSON com um ArrayList vazio</p></b>
     * <p>A validação verifica se o JSON existe, se é um ArrayList, se a quantidade de clientes retornados 
     * <b>(totalElements)</b> é igual a <b>quantidadeClientes</b> e se o array está vazio.</p>
     */
    @Test
    public void testarBuscarPorSalarioQueNaoRetornaClientes() {
        //Arrange
        Double salarioResultado = 4000.0;
        Integer quantidadeClientes = 0; //Pois não existe nenhum cliente que tem salário igual a 4000

        //Act
        ResultActions resultado = assertDoesNotThrow(() -> mockMvc.perform(get("/clients/income/")
                                                                .param("income", String.valueOf(salarioResultado))
                                                                .accept(MediaType.APPLICATION_JSON)));

        //Assert
        assertDoesNotThrow(() -> resultado
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").exists())
            .andExpect(jsonPath("$.content.*", isA(ArrayList.class)))
            .andExpect(jsonPath("$.totalElements").value(quantidadeClientes))
            .andExpect(jsonPath("$.content").isEmpty()));
    }
    
    /**
     * <p><b>Método para testar buscar clientes pelo valor acima do salário especificado</p></b>
     * <p><b>Entrada:</b> salarioResultado = 2500.0 , quantidadeDeClientes = 6</p>
     * <p><b>Saída:</b> JSON contendo informações de 6 clientes.</p></b>
     * <p>A validação verifica se o JSON existe, se todos os salários são maiores que 2500,
     *  se a quantidade de clientes retornados <b>(totalElements)</b> é igual a <b>quantidadeClientes</b>.</p>
     */
    @Test
    public void testarBuscarPorSalarioQueRetornaClientesMaiorQueUmValor() {
        //Arrange
        Double salarioResultado = 2500.0;
        Integer quantidadeDeClientes = 6;

        //Act
        ResultActions resultado = assertDoesNotThrow(() -> mockMvc.perform(get("/clients/incomeGreaterThan/")
                                                                    .param("income", String.valueOf(salarioResultado))
                                                                    .accept(MediaType.APPLICATION_JSON)));

        //Assert
        assertDoesNotThrow(() -> resultado
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").exists())
            .andExpect(jsonPath("$.content.[*].income", everyItem(greaterThan(salarioResultado))))
            .andExpect(jsonPath("$.totalElements").value(quantidadeDeClientes)));
    }

    /**
     * <p><b>Método para testar buscar clientes pelo valor acima do salário especificado e não retorna nenhum cliente</p></b>
     * <p><b>Entrada:</b> salarioResultado = 10000.0 , quantidadeDeClientes = 0</p>
     * <p><b>Saída:</b> JSON que não contém informações de clientes.</p></b>
     * <p>A validação verifica se retorna 200 (OK), se retorna um ArrayList vazio,
     *  se a quantidade de clientes retornados <b>(totalElements)</b> é igual a <b>quantidadeClientes</b>.</p>
     */
    @Test
    public void testarBuscarPorSalarioQueNaoRetornaClientesMaiorQueUmValor() {
        //Arrange
        Double salarioResultado = 10000.0;
        Integer quantidadeDeClientes = 0;

        //Act
        ResultActions resultado = assertDoesNotThrow(() -> mockMvc.perform(get("/clients/incomeGreaterThan/")
                                                                .param("income", String.valueOf(salarioResultado))
                                                                .accept(MediaType.APPLICATION_JSON)));

        //Assert
        assertDoesNotThrow(() -> resultado
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isEmpty())
            .andExpect(jsonPath("$.totalElements").value(quantidadeDeClientes)));
    }

    /**
     * <p><b>Método para testar buscar clientes pelo CPF</p></b>
     * <p><b>Entrada:</b> cpf = "10619244884"</p>
     * <p><b>Saída:</b> JSON que contém informações do cliente com o CPF 10619244884.</p></b>
     * <p>A validação verifica se retorna 200 (OK), se o CPF do JSON é igual à variável cpf.</b>.</p>
     */
    @Test
    public void testarBuscarPorCpfValido() {
        //Arrange
        String cpf = "10619244884";

        //Act
        ResultActions resultado = assertDoesNotThrow(() -> mockMvc.perform(get("/clients/cpf/")
                                                                .param("cpf", cpf)
                                                                .accept(MediaType.APPLICATION_JSON)));

        //Assert
        assertDoesNotThrow(() -> resultado
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.[0].cpf").value(cpf)));
    }

    /**
     * <p><b>Método para testar buscar clientes pelo CPF e não retorna</p></b>
     * <p><b>Entrada:</b> cpf = "15363587682"</p>
     * <p><b>Saída:</b> JSON não contém clientes pois o CPF não existe.</p></b>
     * <p>A validação verifica se retorna 200 (OK), se o CPF do JSON é igual à variável cpf.</b>.</p>
     */
    @Test
    public void testarBuscarPorCpfInvalido() {
        //Arrange
        String cpf = "15363587682";

        //Act
        ResultActions resultado = assertDoesNotThrow(() -> mockMvc.perform(get("/clients/cpf/")
                                                                .param("cpf", cpf)
                                                                .accept(MediaType.APPLICATION_JSON)));

        //Assert
        assertDoesNotThrow(() -> resultado
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isEmpty()));
    }

    /**
     * <p><b>Método que retorna um novo cliente</b></p>
     * <p><b>Entradas:</b>
     * String nomeEsperado = "Gregorio McNutt";
     * String cpfEsperado = "15363581698";
     * Double salarioEsperado = 5000.0;
     * String dataEsperada = "1962-01-17T20:50:00Z";
     * Integer childrenEsperado = 4;</p>
     * <p><b>Saída:</b> JSON com o usuário criado.<p>
     */
    @Test
    public void testarInserirNovoClienteComSucesso() throws Exception{
        //Arrange
        String nomeEsperado = "Gregorio McNutt";
        String cpfEsperado = "15363581698";
        Double salarioEsperado = 5000.0;
        String dataEsperada = "1962-01-17T20:50:00Z";
        Integer childrenEsperado = 4;

        ClientDTO dto = new ClientDTO(null, nomeEsperado, cpfEsperado, salarioEsperado, Instant.parse(dataEsperada), childrenEsperado);

        String json = objectMapper.writeValueAsString(dto);

        //Act
        ResultActions resultado = assertDoesNotThrow(() -> mockMvc.perform(post("/clients/")
                                                                .content(json)
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .accept(MediaType.APPLICATION_JSON)));

        //Assert
        assertDoesNotThrow(() -> resultado
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value(nomeEsperado))
            .andExpect(jsonPath("$.cpf").value(cpfEsperado))
            .andExpect(jsonPath("$.income").value(salarioEsperado))
            .andExpect(jsonPath("$.birthDate").value(dataEsperada))
            .andExpect(jsonPath("$.children").value(childrenEsperado)));
    }

    /**
     * <p><b>Método para testar exclusão de um cliente com ID existente</b></p>
     * <p><b>Entrada:</b> idExistente = 1L</p>
     * <p><b>Saída:</b> Status No Content</p>
     */
    @Test
    public void testarDeletarClienteComSucesso() {
        //Arrange
        Long idExistente = 1L;

        //Act
        ResultActions resultado = assertDoesNotThrow(() -> mockMvc.perform(delete("/clients/{id}", idExistente)
                                                                .accept(MediaType.APPLICATION_JSON)));

        //Assert
        assertDoesNotThrow(() -> resultado
            .andExpect(status().isNoContent()));
    }

    /**
     * <p><b>Método para testar exclusão de um cliente com ID existente</b></p>
     * <p><b>Entrada:</b> idInexistente = 111L</p>
     * <p><b>Saída:</b> Status Resource not found/p>
     * <p>Validação para conferir se a mensagem de erro é <b>Resource not Found</b>.</p>
     */
    @Test
    public void testarDeletarClienteSemSucesso() {
        //Arrange
        Long idInexistente = 111L;

        //Act
        ResultActions resultado = assertDoesNotThrow(() -> mockMvc.perform(delete("/clients/{id}", idInexistente)
                                                                .accept(MediaType.APPLICATION_JSON)));

        //Assert
        assertDoesNotThrow(() -> resultado
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("Resource not found")));
    }

    /**
     * <p><b>Método para testar update de informações de clientes</p></b>
     * <p><b>Entrada:</b> nomeEsperado = Leonard Ravenhill , salarioEsperado = 2000.0 , 
     * corpoJsonAtualizado = 
     * {\"id\": 1, \"name\": \"Leonard Ravenhill\", \"cpf\": \"10619244881\", \"income\": 2000.0, \"birthDate\": \"2020-07-13T20:50:00Z\", \"children\": 2}</p>
     * <p><b>Saída:</b> JSON contendo informações do cliente atualizado</p></b>
     * <p>A validação verifica se o status retorna 200 (OK), se o nome foi alterado e se o salário foi alterado.</p>
     */
    @Test
    public void testarAtualizarClienteRetornandoOk() {
        //Arrange
        Long id = 1L;
        String nomeEsperado = "Leonard Ravenhill";
        Double salarioEsperado = 2000.0;
        String corpoJsonAtualizado = "{\"id\": 1, \"name\": \"Leonard Ravenhill\", \"cpf\": \"10619244881\", \"income\": 2000.0, \"birthDate\": \"2020-07-13T20:50:00Z\", \"children\": 2}";

        //Act
        ResultActions resultado = assertDoesNotThrow(() -> mockMvc.perform(put("/clients/{id}", id)
                                                                .content(corpoJsonAtualizado)
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .accept(MediaType.APPLICATION_JSON)));

        //Assert
        assertDoesNotThrow(() -> resultado
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(nomeEsperado))
            .andExpect(jsonPath("$.income").value(salarioEsperado)));
    }

    /**
     * <p><b>Método para testar update de informações de clientes e retornar erro</p></b>
     * <p><b>Entrada:</b> idInexistente = 111L ,  
     * corpoJsonAtualizado = 
     * {\"id\": 1, \"name\": \"Leonard Ravenhill\", \"cpf\": \"10619244881\", \"income\": 2000.0, \"birthDate\": \"2020-07-13T20:50:00Z\", \"children\": 2}</p>
     * <p><b>Saída:</b> JSON contendo mensagem de erro</p></b>
     * <p>A validação verifica se o status retorna 400 (Not Found), se a mensagem "error" existe e se o valor de "error" é "Resource Not Found".</p>
     */
    @Test
    public void testarAtualizarClienteRetornandoNotFound() {
        //Arrange
        Long idInexistente = 111L;
        String corpoJsonAtualizado = "{\"id\": 1, \"name\": \"Leonard Ravenhill\", \"cpf\": \"10619244881\", \"income\": 2000.0, \"birthDate\": \"2020-07-13T20:50:00Z\", \"children\": 2}";


        //Act
        ResultActions resultado = assertDoesNotThrow(() -> mockMvc.perform(put("/clients/{id}", idInexistente)
                                                                .content(corpoJsonAtualizado)
                                                                .contentType(MediaType.APPLICATION_JSON)
                                                                .accept(MediaType.APPLICATION_JSON)));

        //Assert
        assertDoesNotThrow(() -> resultado
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").exists())
            .andExpect(jsonPath("$.error").value("Resource not found")));
    }
}
