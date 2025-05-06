package uta.ec.finance_manager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uta.ec.finance_manager.dto.InvestmentDto;
import uta.ec.finance_manager.service.InvestmentService;

import java.util.List;

@RestController
@RequestMapping("/investment")
@RequiredArgsConstructor
public class InvestmentController {
    private final InvestmentService investmentService;

    @PostMapping
    public InvestmentDto save(InvestmentDto investmentDto){
        System.out.println(investmentDto.getName());
        System.out.println(investmentDto.getInitialAmount());
        return investmentService.save(investmentDto);
    }

    @GetMapping
    public List<InvestmentDto> getAll(){
        return investmentService.getAll();
    }

    @PutMapping
    public InvestmentDto update(InvestmentDto investmentDto){
        return investmentService.update(investmentDto);
    }

    @DeleteMapping
    public void delete(Integer id) {
        investmentService.delete(id);
    }
}
