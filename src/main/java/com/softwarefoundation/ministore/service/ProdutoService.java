package com.softwarefoundation.ministore.service;

import com.softwarefoundation.ministore.dto.ProdutoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdutoService {

    ProdutoDto findById(Long id);

    Page<ProdutoDto> findAll(Pageable pageable);

}
