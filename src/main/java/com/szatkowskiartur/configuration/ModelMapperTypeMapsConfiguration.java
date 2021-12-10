package com.szatkowskiartur.configuration;

import com.szatkowskiartur.portfolio.Portfolio;
import com.szatkowskiartur.portfolio.PortfolioDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ModelMapperTypeMapsConfiguration implements ApplicationRunner {

    private final ModelMapper mapper;

    @Override
    public void run (ApplicationArguments args) throws Exception {
        TypeMap<Portfolio, PortfolioDTO> typeMap = mapper.createTypeMap(Portfolio.class, PortfolioDTO.class);
        typeMap.addMapping(Portfolio::getId, PortfolioDTO::setOwner);
    }
}
