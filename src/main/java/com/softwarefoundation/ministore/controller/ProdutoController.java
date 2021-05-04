package com.softwarefoundation.ministore.controller;

import com.softwarefoundation.ministore.dto.ProdutoDto;
import com.softwarefoundation.ministore.entity.Produto;
import com.softwarefoundation.ministore.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    ProdutoService produtoService;
    PagedResourcesAssembler<ProdutoDto> assemblerPaged;

    @Autowired
    public ProdutoController(ProdutoService produtoService, PagedResourcesAssembler<ProdutoDto> assemblerPaged) {
        this.produtoService = produtoService;
        this.assemblerPaged = assemblerPaged;
    }

    @GetMapping(value = "/{id}", produces = {"application/json","application/xml","application/x-yaml"})
    public ProdutoDto findById(@PathVariable("id") Long id){
        ProdutoDto produtoDto = produtoService.findById(id);
        produtoDto.add(linkTo(methodOn(ProdutoController.class).findById(id)).withSelfRel());
        return produtoDto;
    }

    @GetMapping(produces = {"application/json","application/xml","application/x-yaml"})
    public ResponseEntity<?> findByAll(@RequestParam(value = "page",defaultValue = "0") int page,
                                       @RequestParam(value = "limit", defaultValue = "12") int limit,
                                       @RequestParam(value = "direction", defaultValue = "asc") String direction){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "nome"));
        Page<ProdutoDto> produtos = produtoService.findAll(pageable);
        produtos.stream().forEach(p -> p.add(linkTo(methodOn(ProdutoController.class).findById(p.getId())).withSelfRel()));
        PagedModel<EntityModel<ProdutoDto>> pagedModel = assemblerPaged.toModel(produtos);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @PostMapping(produces = {"application/json","application/xml","application/x-yaml"},
            consumes = {"application/json","application/xml","application/x-yaml"})
    public ProdutoDto create(@RequestBody ProdutoDto produtoDto){
        ProdutoDto dto = produtoService.create(produtoDto);
        dto.add(linkTo(methodOn(ProdutoController.class).findById(dto.getId())).withSelfRel());
        return dto;
    }

    @PutMapping(produces = {"application/json","application/xml","application/x-yaml"},
            consumes = {"application/json","application/xml","application/x-yaml"})
    public ProdutoDto update(@RequestBody ProdutoDto produtoDto){
        ProdutoDto dto = produtoService.update(produtoDto);
        dto.add(linkTo(methodOn(ProdutoController.class).findById(produtoDto.getId())).withSelfRel());
        return dto;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestBody ProdutoDto produtoDto){
        produtoService.delete(produtoDto.getId());
        return ResponseEntity.ok().build();
    }

}
