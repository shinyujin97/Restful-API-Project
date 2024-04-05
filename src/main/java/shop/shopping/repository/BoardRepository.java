package shop.shopping.repository;

import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.shopping.entity.Board;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board , Long> {


}
