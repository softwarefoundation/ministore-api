package com.softwarefoundation.ministore.service.impl;

import com.softwarefoundation.ministore.dto.ProdutoDto;
import com.softwarefoundation.ministore.entity.Produto;
import com.softwarefoundation.ministore.exceptions.ResourceNotFoundException;
import com.softwarefoundation.ministore.message.ProdutoSendMessage;
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
    private ProdutoSendMessage produtoSendMessage;

    @Autowired
    public ProdutoServiceImpl(ProdutoRepository produtoRepository, ProdutoSendMessage produtoSendMessage) {
        this.produtoRepository = produtoRepository;
        this.produtoSendMessage = produtoSendMessage;
    }

    public ProdutoDto create(ProdutoDto produtoDto) {
        Produto produto = produtoRepository.save(produtoDto.toEntity());
        produtoSendMessage.sendMessage(produto.toDto());
        return produto.toDto();
    }

    public Page<ProdutoDto> findAll(Pageable pageable) {
        Page<Produto> page = produtoRepository.findAll(pageable);
        return page.map(p -> p.toDto());
    }

    public ProdutoDto findById(Long id) {
        var produto = produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        return produto.toDto();
    }


    public ProdutoDto update(ProdutoDto produtoDto) {
        final Optional<Produto> produtoOptional = produtoRepository.findById(produtoDto.getId());
        if (!produtoOptional.isPresent()) {
            new ResourceNotFoundException("No records found for this ID");
        }
        Produto produto = produtoRepository.save(produtoDto.toEntity());
        return produto.toDto();
    }

    public void delete(Long id) {
        var produto = produtoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        produtoRepository.delete(produto);
    }

}
