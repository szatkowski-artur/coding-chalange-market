package com.szatkowskiartur.portfolio;

import com.szatkowskiartur.portfolio_entry.PortfolioEntry;
import com.szatkowskiartur.user.User;
import lombok.Data;

import java.util.List;

@Data
public class PortfolioDTO {

    private Long id;

    private Long owner;

    private List<PortfolioEntry> products;

    private Double totalValue;

    private Double creditUsd;




}
