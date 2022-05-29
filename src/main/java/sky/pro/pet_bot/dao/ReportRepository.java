package sky.pro.pet_bot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.pet_bot.model.Report;

/**
 * репозиторий для хранения отчетов владельца животного
 */

public interface ReportRepository extends JpaRepository<Report,Long> {
}
