package com.example.library.Services.Generic;

import com.example.library.Dao.GenericDao;
import com.example.library.domain.generic.GenericDomain;
import com.example.library.exeption.SpecificExceptions.ResourceNotFoundException;
import com.example.library.utils.model.Pagination;
import com.example.library.utils.services.PaginationService;
import com.example.library.utils.services.RefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
public class GenericService<Dao extends GenericDao, Domain extends GenericDomain, IdClass extends Serializable> {
@Autowired
    protected Dao dao;

    @Autowired
  private RefService refService;
    @Autowired
  private PaginationService paginationService;
    public Iterable<Domain> insert(List<Domain> domain) {
        refService.linkChildrenWithParents(domain, new ArrayList<>());
        return ((JpaRepository<Domain, IdClass>) dao).saveAll(domain);
    }
    public Domain getById(IdClass id) {
        Optional<Domain> result = ((JpaRepository<Domain, IdClass>) dao).findById(id);
        if (!result.isPresent()) {
            throw new ResourceNotFoundException(null);
        }
        return result.get();
    }


    public Page getAll(Pagination pagination, int... recordStatus) {

        return recordStatus != null ?
                dao.findAllByStatusWithPagination(paginationService.getPagination(pagination), recordStatus) :
                dao.findAll(paginationService.getPagination(pagination));
    }

    public Domain merge(Domain domain) throws Exception {
        refService.linkChildrenWithParents(domain,new ArrayList<>());
        return ((JpaRepository<Domain, IdClass>) dao).save(domain);
    }
    public Boolean delete(IdClass id) {
        Domain domain = this.getById(id);
        if (domain == null) {
            throw new ResourceNotFoundException(null);
        }
        dao.delete(domain);

        return true;
    }


}