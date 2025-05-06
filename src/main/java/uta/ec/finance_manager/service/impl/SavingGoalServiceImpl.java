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

import java.time.temporal.ChronoUnit;
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
        // Mapear el DTO a la entidad
        SavingGoal savingGoal = dtoToSaving(savingGoalDto);

        // Validar que el targetAmount sea mayor a 0
        if (savingGoalDto.getTargetAmount() <= 0) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "El ahorro debe ser mayor");
        }

        // Obtener el user_id del token usando UserUtil
        int userId = userUtil.getUserId();

        // Crear un objeto User y asignarlo al SavingGoal
        User user = new User();
        user.setId(userId);
        savingGoal.setUser(user);

        // Configurar los valores iniciales
        savingGoal.setCurrentStreak(0);
        savingGoal.setLastDepositDate(null);
        savingGoal.setGoalStatus(GoalStatus.ACTIVE);

        // Guardar la meta en la base de datos
        SavingGoal saved = savingGoalRepository.save(savingGoal);
        return savingToDto(saved);
    }

    @Override
    public SavingGoalDto update(SavingGoalDto savingGoalDto) {
        Optional<SavingGoal> existingGoalOptional = savingGoalRepository.findById(savingGoalDto.getId());
        if (existingGoalOptional.isPresent()) {
            SavingGoal existingGoal = existingGoalOptional.get();
            modelMapper.map(savingGoalDto, existingGoal);

            // Actualizar el balance
            if (savingGoalDto.getCurrentBalance() != null) {
                existingGoal.setCurrentBalance(savingGoalDto.getCurrentBalance());
            }

            // Actualizar la fecha del último depósito y la racha
            if (savingGoalDto.getLastDepositDate() != null) {
                existingGoal.setLastDepositDate(savingGoalDto.getLastDepositDate());

                // Verificar si el depósito está dentro del período de frecuencia
                Date lastDepositDate = existingGoal.getLastDepositDate();
                DepositFrequency depositFrequency = existingGoal.getDepositFrequency();
                long daysDifference = lastDepositDate != null && existingGoal.getLastDepositDate() != null
                        ? ChronoUnit.DAYS.between(existingGoal.getLastDepositDate().toInstant(), new Date().toInstant())
                        : -1;

                boolean depositOnTime = false;
                if (lastDepositDate == null) {
                    // Primer depósito, iniciar racha
                    depositOnTime = true;
                } else {
                    switch (depositFrequency) {
                        case DAILY:
                            depositOnTime = daysDifference <= 1;
                            break;
                        case WEEKLY:
                            depositOnTime = daysDifference <= 7;
                            break;
                        case MONTHLY:
                            depositOnTime = daysDifference <= 30;
                            break;
                        default:
                            depositOnTime = false;
                    }
                }

                // Actualizar la racha
                if (depositOnTime) {
                    existingGoal.setCurrentStreak(existingGoal.getCurrentStreak() + 1);
                } else {
                    existingGoal.setCurrentStreak(1); // Reiniciar racha si no se depositó a tiempo
                }
            }

            // Verificar si la meta se ha completado
            if (existingGoal.getCurrentBalance() >= existingGoal.getTargetAmount()) {
                existingGoal.setGoalStatus(GoalStatus.COMPLETED);
            } else if (existingGoal.getDeadline().before(new Date())) {
                // Si la fecha límite ha pasado y no se ha cumplido, se marca como EXPIRED
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
    @Scheduled(cron = "30 45 * * * *")
    public void updateStreaks() {
        System.out.println("Ejecutando");
        List<SavingGoal> savingGoals = savingGoalRepository.findByGoalStatus(GoalStatus.ACTIVE);

        for (SavingGoal goal : savingGoals) {
            if (goal.getDeadline() != null && goal.getDepositFrequency() != null) {
                Date lastDepositDate = goal.getLastDepositDate();
                DepositFrequency depositFrequency = goal.getDepositFrequency();

                // Si no hay depósito previo, reiniciar racha si ha pasado el período
                if (lastDepositDate == null) {
                    goal.setCurrentStreak(0);
                } else {
                    long daysDifference = ChronoUnit.DAYS.between(lastDepositDate.toInstant(), new Date().toInstant());
                    boolean overdue = false;

                    switch (depositFrequency) {
                        case DAILY:
                            overdue = daysDifference > 1;
                            break;
                        case WEEKLY:
                            overdue = daysDifference > 7;
                            break;
                        case MONTHLY:
                            overdue = daysDifference > 30;
                            break;
                        default:
                            overdue = true;
                    }

                    // Reiniciar racha si el depósito está atrasado
                    if (overdue) {
                        goal.setCurrentStreak(0);
                    }
                }

                // Verificar si la meta ha expirado
                if (goal.getDeadline().before(new Date())) {
                    goal.setGoalStatus(GoalStatus.EXPIRED);
                }

                savingGoalRepository.save(goal); // Guardar la meta actualizada
            }
        }
    }
}