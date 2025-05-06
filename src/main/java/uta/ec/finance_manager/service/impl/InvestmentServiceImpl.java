package uta.ec.finance_manager.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uta.ec.finance_manager.dto.InvestmentDto;
import uta.ec.finance_manager.entity.Investment;
import uta.ec.finance_manager.repository.InvestmentRepository;
import uta.ec.finance_manager.repository.UserRepository;
import uta.ec.finance_manager.service.InvestmentService;
import uta.ec.finance_manager.util.UserUtil;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor

public class InvestmentServiceImpl implements InvestmentService {

    private final UserUtil userUtil;
    private final InvestmentRepository investmentRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public InvestmentDto save(InvestmentDto investmentDto) {
        Integer userId = userUtil.getUserId();
        investmentDto.setUserId(userId);
        return investmentToDto(investmentRepository.save(dtoToInvestment(investmentDto)));
    }

    @Override
    public List<InvestmentDto> getAll() {
        Integer userId = userUtil.getUserId();
        List<Investment> investments = investmentRepository.findByUserId(userId);
        return investments.stream()
                .map(this::investmentToDto)
                .toList();
    }

    @Override
    public InvestmentDto update(InvestmentDto investmentDto) {
        Integer userId = userUtil.getUserId();
        Investment existing = investmentRepository.findById(investmentDto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inversión no encontrada"));

        investmentDto.setUserId(userId);
        Investment updated = investmentRepository.save(dtoToInvestment(investmentDto));
        return investmentToDto(updated);
    }

    @Override
    public void delete(Integer id) {
        Integer userId = userUtil.getUserId();
        Investment investment = investmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inversión no encontrada"));
        investmentRepository.delete(investment);
    }

    private InvestmentDto investmentToDto(Investment investment){
        InvestmentDto investmentDto = modelMapper.map(investment, InvestmentDto.class);
        investmentDto.setUserId(investment.getUser().getId());

        Date now = new Date();
        Date start = investment.getStartDate();
        Date end = investment.getEndDate();

        if (now.before(start)) {
            investmentDto.setProgress(0);
        } else if (now.after(end)) {
            investmentDto.setProgress(100);
        } else {
            long totalDuration = end.getTime() - start.getTime();
            long elapsed = now.getTime() - start.getTime();
            int progress = (int) ((elapsed * 100.0) / totalDuration);
            investmentDto.setProgress(progress);
        }

        return investmentDto;
    }


    private Investment dtoToInvestment(InvestmentDto investmentDto){
        Investment investment = modelMapper.map(investmentDto, Investment.class);
        investment.setUser(userRepository.findById(investmentDto.getUserId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario")));
        return investment;
    }
}
