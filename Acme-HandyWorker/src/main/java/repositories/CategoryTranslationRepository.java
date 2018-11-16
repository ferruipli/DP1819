
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.CategoryTranslation;

@Repository
public interface CategoryTranslationRepository extends JpaRepository<CategoryTranslation, Integer> {

}
