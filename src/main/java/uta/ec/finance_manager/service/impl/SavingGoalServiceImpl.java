package uta.ec.finance_manager.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uta.ec.finance_manager.dto.SavingGoalDto;
import uta.ec.finance_manager.entity.SavingGoal;
import uta.ec.finance_manager.entity.User;
import uta.ec.finance_manager.enums.DepositFrequency;
import uta.ec.finance_manager.enums.GoalStatus;
import uta.ec.finance_manager.repository.SavingGoalRepository;
import uta.ec.finance_manager.service.SavingGoalService;
import uta.ec.finance_manager.util.UserUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SavingGoalServiceImpl implements SavingGoalService {

    private final SavingGoalRepository savingGoalRepository;
    private final ModelMapper modelMapper;
    private final UserUtil userUtil;

    @Override
    public SavingGoalDto getById(int id) {
        SavingGoal savingGoal = savingGoalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return savingToDto(savingGoal);
    }

    @Override
    public List<SavingGoalDto> getByUser() {
        int userId = userUtil.getUserId();
        List<SavingGoal> savingGoals = savingGoalRepository.findByUserId(userId);
        return savingGoals.stream()
                .map(this::savingToDto)
                .toList();
    }

    @Override
    public SavingGoalDto save(SavingGoalDto savingGoalDto) {
        SavingGoal savingGoal = dtoToSaving(savingGoalDto);
        if (savingGoalDto.getTargetAmount() <= 0) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "El ahorro debe ser mayor");
        }
        savingGoal.setCurrentStreak(0);
        savingGoal.setLastDepositDate(null);
        savingGoal.setGoalStatus(GoalStatus.ACTIVE);
        SavingGoal saved = savingGoalRepository.save(savingGoal);
        return savingToDto(saved);
    }

    @Override
    public SavingGoalDto update(SavingGoalDto savingGoalDto) {
        Optional<SavingGoal> existingGoalOptional = savingGoalRepository.findById(savingGoalDto.getId());
        if (existingGoalOptional.isPresent()) {
            SavingGoal existingGoal = existingGoalOptional.get();
            modelMapper.map(savingGoalDto, existingGoal);

            if (existingGoal.getCurrentBalance() >= existingGoal.getTargetAmount()) {
                existingGoal.setGoalStatus(GoalStatus.COMPLETED);
            } else if (existingGoal.getDeadline().before(new Date())) {
                existingGoal.setGoalStatus(GoalStatus.EXPIRED);
            }

            SavingGoal updatedGoal = savingGoalRepository.save(existingGoal);
            return savingToDto(updatedGoal);
        }
        return null;
    }

    @Override
    public void delete(int id) {
        savingGoalRepository.deleteById(id);
    }

    private SavingGoal dtoToSaving(SavingGoalDto savingGoalDto) {
        SavingGoal savingGoal = modelMapper.map(savingGoalDto, SavingGoal.class);
        if (savingGoalDto.getUserId() != null) {
            User user = new User();
            user.setId(savingGoalDto.getUserId());
            savingGoal.setUser(user);
        }
        return savingGoal;
    }

    private SavingGoalDto savingToDto(SavingGoal savingGoal) {
        SavingGoalDto savingGoalDto = modelMapper.map(savingGoal, SavingGoalDto.class);
        if (savingGoal.getUser() != null) {
            savingGoalDto.setUserId(savingGoal.getUser().getId());
        }
        return savingGoalDto;
    }
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void updateStreaks() {
        List<SavingGoal> savingGoals = savingGoalRepository.findByGoalStatus(GoalStatus.ACTIVE);

        for (SavingGoal goal : savingGoals) {
            if (goal.getDeadline() != null && goal.getDepositFrequency() != null) {
                boolean goalCompleted = checkGoalCompletion(goal);
                boolean depositOnTime = checkDepositFrequency(goal);

                // Si la meta se ha completado y el depósito fue a tiempo, se incrementa la racha
                if (goalCompleted) {
                    if (depositOnTime) {
                        goal.setCurrentStreak(goal.getCurrentStreak() + 1);
                    } else {
                        goal.setCurrentStreak(0); // Se reinicia la racha si el depósito no fue a tiempo
                    }

                    // Si se ha alcanzado el objetivo, se marca como COMPLETED
                    if (goal.getCurrentBalance() >= goal.getTargetAmount()) {
                        goal.setGoalStatus(GoalStatus.COMPLETED);
                    }
                } else if (goal.getDeadline().before(new Date())) {
                    // Si la fecha límite ha pasado y no se ha cumplido, se marca como EXPIRED
                    goal.setGoalStatus(GoalStatus.EXPIRED);
                } else {
                    goal.setCurrentStreak(0); // Si no se ha cumplido, se reinicia la racha
                }

                savingGoalRepository.save(goal); // Guardar la meta actualizada
            }
        }
    }

    private boolean checkGoalCompletion(SavingGoal goal) {
        boolean completedOnTime = goal.getCurrentBalance() >= goal.getTargetAmount();
        boolean deadlineNotPassed = goal.getDeadline().after(new Date());
        return completedOnTime && deadlineNotPassed; // Verifica si el saldo es suficiente y la fecha límite no ha pasado
    }

    private boolean checkDepositFrequency(SavingGoal goal) {
        Date lastDepositDate = goal.getLastDepositDate();
        DepositFrequency depositFrequency = goal.getDepositFrequency();

        if (lastDepositDate == null) {
            return false; // Si no hay depósitos previos, se considera que no ha cumplido
        }

        long timeDifference = new Date().getTime() - lastDepositDate.getTime();
        long daysDifference = timeDifference / (1000 * 60 * 60 * 24);

        switch (depositFrequency) {
            case DAILY:
                return daysDifference >= 1; // Se verifica que hayan pasado al menos 1 día
            case WEEKLY:
                return daysDifference >= 7; // Se verifica que hayan pasado al menos 7 días
            case MONTHLY:
                return daysDifference >= 30; // Se verifica que hayan pasado al menos 30 días
            default:
                return false; // Si no hay frecuencia válida, no se cumple
        }
    }
}
