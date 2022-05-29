package sky.pro.pet_bot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.pet_bot.model.Report;

public interface ReportRepository extends JpaRepository<Report,Long> {
}
