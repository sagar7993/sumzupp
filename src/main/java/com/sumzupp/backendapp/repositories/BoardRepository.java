package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.beans.BoardBean;
import com.sumzupp.backendapp.entities.Board;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by akash.mercer on 07-Jul-17.
 */

@Repository
public interface BoardRepository extends CrudRepository<Board, String> {

    @Query(value = "select count(*) from Board")
    Integer getCount();

    Board findByType(Integer type);

    @Query(value = "select b from Board b where b.id in :boardIds")
    List<Board> findByIds(@Param("boardIds") List<String> boardIds);

    @Query(value = "select new com.sumzupp.backendapp.beans.BoardBean(b.id, b.name) from Board b where b.active = true")
    List<BoardBean> fetchAllActiveBoardBeans();
}
