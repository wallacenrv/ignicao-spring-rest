package com.algaworks.algatransito.api.controller;

import com.algaworks.algatransito.api.assembler.VeiculoAssembler;
import com.algaworks.algatransito.api.model.VeiculoModel;
import com.algaworks.algatransito.domain.exception.NegocioException;
import com.algaworks.algatransito.domain.model.Veiculo;
import com.algaworks.algatransito.domain.repository.VeiculoRepository;
import com.algaworks.algatransito.domain.service.ApreensaoVeiculoService;
import com.algaworks.algatransito.domain.service.RegistroVeiculoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper modelMapper;

    private final VeiculoAssembler veiculoAssembler;

    private final ApreensaoVeiculoService apreensaoVeiculoService;

    @GetMapping
    public List<VeiculoModel> listar() {
        return veiculoAssembler.toCollectionModel(veiculoRepository.findAll());

    }

    @GetMapping("/{veiculoId}")
    public ResponseEntity<VeiculoModel> buscar(@PathVariable Long veiculoId) {
        return veiculoRepository.findById(veiculoId)
                .map(veiculo -> veiculoAssembler.toModel(veiculo))//convertendo
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

               /* convertendo veiculo em veiculoModel

                .map(veiculo -> {
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
    */
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeiculoModel cadastrar (@RequestBody Veiculo veiculo) {
    return veiculoAssembler.toModel(registroVeiculoService.cadastrar(veiculo));


    }

    @PutMapping("/{veiculoId}/apreensao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apreender(@PathVariable Long veiculoId) {
        apreensaoVeiculoService.apreender(veiculoId);

    }

    @DeleteMapping("/{veiculoId}/apreensao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerApreensao(@PathVariable Long veiculoId) {
        apreensaoVeiculoService.removerApreensao(veiculoId);

    }

//    @ExceptionHandler(NegocioException.class) // metodo que captura excecoes
//    public ResponseEntity<String> capturar(NegocioException e) {
//        return ResponseEntity.badRequest().body(e.getMessage()); // indica que o problema é do consumidor
//    }
}
