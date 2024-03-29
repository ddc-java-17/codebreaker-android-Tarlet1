package edu.cnm.deepdive.codebreaker.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import edu.cnm.deepdive.codebreaker.model.entity.GameResult;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import java.util.List;

@Dao
public interface GameResultDao {

  String RANKING_QUERY = "SELECT * "
      + "FROM game_result "
      + "WHERE code_length = :codeLength "
      + "ORDER BY guess_count ASC, duration ASC";
  String RANKING_QUERY_FOR_RESULT = "SELECT * "
      + "FROM game_result "
      + "WHERE code_length = :codeLength "
      + "AND user_id = :userId "
      + "ORDER BY guess_count ASC, duration ASC";
  String TRUNCATION_QUERY = "DELETE FROM game_result";

  @Insert
  Single<Long> insert(GameResult gameResult);

  @Query(RANKING_QUERY)
  LiveData<List<GameResult>> getRankedResults(int codeLength);

  @Query(RANKING_QUERY_FOR_RESULT)
  LiveData<List<GameResult>> getRankedResults(int codeLength, long userId);

  @Query(TRUNCATION_QUERY)
  Completable truncateResults();

  default Single<GameResult> insertAndUpdate(GameResult gameResult) {
    return insert(gameResult)
        .map((id) -> {
          gameResult.setId(id);
          return gameResult;
        });
  }
}
