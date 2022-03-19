package pro.sky.school.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pro.sky.school.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends PagingAndSortingRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId(Long studentId);
}