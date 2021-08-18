package br.com.tiagoalcantara.aluraflix.categories.repository;

import br.com.tiagoalcantara.aluraflix.categories.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
