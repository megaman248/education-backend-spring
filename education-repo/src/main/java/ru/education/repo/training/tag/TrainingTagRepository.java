package ru.education.repo.training.tag;

import org.springframework.stereotype.Repository;
import ru.education.repo.RootRepository;

@Repository
public interface TrainingTagRepository extends RootRepository<TrainingTagEntity, Long> {
}
