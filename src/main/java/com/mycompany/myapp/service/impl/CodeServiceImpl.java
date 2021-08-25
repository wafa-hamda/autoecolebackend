package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.CodeService;
import com.mycompany.myapp.domain.Code;
import com.mycompany.myapp.repository.CodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Code}.
 */
@Service
@Transactional
public class CodeServiceImpl implements CodeService {

    private final Logger log = LoggerFactory.getLogger(CodeServiceImpl.class);

    private final CodeRepository codeRepository;

    public CodeServiceImpl(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    @Override
    public Code save(Code code) {
        log.debug("Request to save Code : {}", code);
        return codeRepository.save(code);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Code> findAll(Pageable pageable) {
        log.debug("Request to get all Codes");
        return codeRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Code> findOne(Long id) {
        log.debug("Request to get Code : {}", id);
        return codeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Code : {}", id);
        codeRepository.deleteById(id);
    }
}
