package ru.education.repo.training;

import org.springframework.stereotype.Repository;
import ru.education.repo.RootRepository;

@Repository
public interface TrainingRepository extends RootRepository<TrainingEntity, Long> {
}
