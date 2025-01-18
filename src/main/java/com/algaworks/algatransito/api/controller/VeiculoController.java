package com.algaworks.algatransito.api.controller;

import com.algaworks.algatransito.api.model.VeiculoModel;
import com.algaworks.algatransito.domain.exception.NegocioException;
import com.algaworks.algatransito.domain.model.Veiculo;
import com.algaworks.algatransito.domain.repository.VeiculoRepository;
import com.algaworks.algatransito.domain.service.RegistroVeiculoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoRepository veiculoRepository;

    private final RegistroVeiculoService registroVeiculoService;

    @GetMapping
    public List<Veiculo> listar() {
        return veiculoRepository.findAll();

    }

    @GetMapping("/{veiculoId}")
    public ResponseEntity<VeiculoModel> buscar(@PathVariable Long veiculoId) {
        return veiculoRepository.findById(veiculoId)
                .map(veiculo -> { //convertendo veiculo em veiculoModel
                    var veiculoModel = new VeiculoModel();
                    veiculoModel.setId(veiculo.getId());
                    veiculoModel.setNomeProprietario(veiculo.getProprietario().getNome());
                    veiculoModel.setMarca(veiculo.getMarca());
                    veiculoModel.setModelo(veiculo.getModelo());
                    veiculoModel.setPlaca(veiculo.getPlaca());
                    veiculoModel.setStatus(veiculo.getStatus());
                    veiculoModel.setDataCadastro(veiculo.getDataCadastro());
                    veiculoModel.setDataApreensao(veiculo.getDataApreensao());

                    return veiculoModel;
                })
                //transformar um optna; de veiculo em um optinal de Response
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Veiculo cadastrar (@RequestBody Veiculo veiculo) {
     return registroVeiculoService.cadastrar(veiculo);
    }

//    @ExceptionHandler(NegocioException.class) // metodo que captura excecoes
//    public ResponseEntity<String> capturar(NegocioException e) {
//        return ResponseEntity.badRequest().body(e.getMessage()); // indica que o problema é do consumidor
//    }
}
