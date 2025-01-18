package com.algaworks.algatransito.common;

import com.algaworks.algatransito.api.model.VeiculoModel;
import com.algaworks.algatransito.domain.model.Veiculo;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    /* agora o spring pode gerar uma instancia de ModelMapper
     Como o modelMapper consegue atribuir ?

     1- ele tem estrategias de correspondecias de propriedade, mesmo que em estruturas diferentes,


     */

    @Bean
    public ModelMapper modelMapper(){

        var modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Veiculo.class, VeiculoModel.class) // criandio uma mapeamento de tipo
                .addMappings(mapper -> mapper.map(Veiculo::getPlaca, VeiculoModel::setNumeroPlaca));
        return modelMapper;
    }
}
