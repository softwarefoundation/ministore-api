package com.softwarefoundation.ministore.service.impl;

import com.softwarefoundation.ministore.dto.ProdutoDto;
import com.softwarefoundation.ministore.entity.Produto;
import com.softwarefoundation.ministore.exceptions.ResourceNotFoundException;
import com.softwarefoundation.ministore.repository.ProdutoRepository;
import com.softwarefoundation.ministore.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoDto create(ProdutoDto produtoDto) {
        Produto pr = produtoRepository.save(Produto.create(produtoDto));
        ProdutoDto dto = ProdutoDto.create(pr);
        return dto;
    }

    public Page<ProdutoDto> findAll(Pageable pageable) {
        Page<Produto> page = produtoRepository.findAll(pageable);
        return page.map(this::convertToProdutoDto);
    }

    private ProdutoDto convertToProdutoDto(Produto produto) {
        return ProdutoDto.create(produto);
    }

    public ProdutoDto findById(Long id) {
        var produto = produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        return ProdutoDto.create(produto);
    }


    public ProdutoDto update(ProdutoDto produtoDto) {
        final Optional<Produto> produtoOptional = produtoRepository.findById(produtoDto.getId());
        if (!produtoOptional.isPresent()) {
            new ResourceNotFoundException("No records found for this ID");
        }
        return ProdutoDto.create(produtoRepository.save(Produto.create(produtoDto)));
    }

    public void delete(Long id) {
        var produto = produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        produtoRepository.delete(produto);
    }

}
