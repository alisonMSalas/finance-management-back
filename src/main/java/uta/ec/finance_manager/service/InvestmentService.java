package uta.ec.finance_manager.service;

import uta.ec.finance_manager.dto.InvestmentDto;
import uta.ec.finance_manager.entity.Investment;

import java.util.List;

public interface InvestmentService {
    InvestmentDto save(InvestmentDto investmentDto);
    List<InvestmentDto> getAll();
    InvestmentDto update(InvestmentDto investmentDto);
    void delete(Integer id);
}
