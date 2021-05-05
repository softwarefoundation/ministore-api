package com.softwarefoundation.ministore.entity;

import com.softwarefoundation.ministore.dto.ProdutoDto;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TB01_PRODUTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "ESTOQUE")
    private Integer estoque;
    @Column(name = "PRECO")
    private Double preco;

    public ProdutoDto toDto(){
        return new ModelMapper().map(this, ProdutoDto.class);
    }

}
