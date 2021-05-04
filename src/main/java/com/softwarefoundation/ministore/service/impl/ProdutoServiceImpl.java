package com.softwarefoundation.ministore.service.impl;

import com.softwarefoundation.ministore.dto.ProdutoDto;
import com.softwarefoundation.ministore.repository.ProdutoRepository;
import com.softwarefoundation.ministore.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoDto create(ProdutoDto produtoDto){
        return null;
    }
}
