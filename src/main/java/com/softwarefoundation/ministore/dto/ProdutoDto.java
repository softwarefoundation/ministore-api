package com.softwarefoundation.ministore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.softwarefoundation.ministore.entity.Produto;
import lombok.*;
import org.modelmapper.ModelMapper;

@JsonPropertyOrder({"id","nome","estoque","preco"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProdutoDto {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("estoque")
    private Integer estoque;
    @JsonProperty("preco")
    private Double preco;

    public static ProdutoDto create(Produto produto){
        return new ModelMapper().map(produto, ProdutoDto.class);
    }

}
