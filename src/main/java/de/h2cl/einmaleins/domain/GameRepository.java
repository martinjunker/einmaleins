package de.h2cl.einmaleins.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameRepository extends JpaRepository<Game, String> {

    long countGamesByOwnerId(String id);

    List<Game> findAllByOwnerId(String ownerId);

    List<Game> findAllByOwnerIdNotLike(String ownerId);

    List<Game> findAllByOwnerIdOrBorrowerId(String personId);

    @Modifying
    @Query("UPDATE Game g SET g.borrowerId = :borrowerId WHERE g.id = :gameId")
    void updateBorrowerId(@Param("gameId") String gameId, @Param("borrowerId") String borrowerId);


}
