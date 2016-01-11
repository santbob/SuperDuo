package barqsoft.footballscores.widgets;

import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;

/**
 * Created by santhosh on 10/01/16.
 * RemoteViewService for the Collection Widget
 */
public class ScoresRemoteViewsService extends RemoteViewsService {

    private static final String[] GAME_COLUMNS = {
            DatabaseContract.ScoresTable.MATCH_ID,
            DatabaseContract.ScoresTable.DATE_COL,
            DatabaseContract.ScoresTable.LEAGUE_COL,
            DatabaseContract.ScoresTable.HOME_COL,
            DatabaseContract.ScoresTable.AWAY_COL,
            DatabaseContract.ScoresTable.HOME_GOALS_COL,
            DatabaseContract.ScoresTable.AWAY_GOALS_COL
    };

    static final int INDEX_MATCH_ID = 0;
    static final int INDEX_MATCH_DATE = 1;
    static final int INDEX_MATCH_LEAGUE = 2;
    static final int INDEX_MATCH_HOME_TEAM = 3;
    static final int INDEX_MATCH_AWAY_TEAM = 4;
    static final int INDEX_MATCH_HOME_TEAM_GOALS = 5;
    static final int INDEX_MATCH_AWAY_TEAM_GOALS = 6;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {
                // Nothing to do
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }
                // This method is called by the app hosting the widget (e.g., the launcher)
                // However, our ContentProvider is not exported so it doesn't have access to the
                // data. Therefore we need to clear (and finally restore) the calling identity so
                // that calls use our process and permission
                final long identityToken = Binder.clearCallingIdentity();

                String[] filterByDate = {Utilies.getFormattedDate(System.currentTimeMillis())};
                data = getContentResolver().query(DatabaseContract.ScoresTable.buildScoreWithDate(), GAME_COLUMNS, null, filterByDate, null);
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION || data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_match_list_item);
                int matchId = data.getInt(INDEX_MATCH_ID);
                int league = data.getInt(INDEX_MATCH_LEAGUE);
                String date = data.getString(INDEX_MATCH_DATE);
                String homeTeamName = data.getString(INDEX_MATCH_HOME_TEAM);
                String awayTeamName = data.getString(INDEX_MATCH_AWAY_TEAM);
                String homeTeamGoals = data.getString(INDEX_MATCH_HOME_TEAM_GOALS);
                String awayTeamGoals = data.getString(INDEX_MATCH_AWAY_TEAM_GOALS);

                views.setTextViewText(R.id.home_team_name, homeTeamName);
                views.setTextViewText(R.id.away_team_name, awayTeamName);
                views.setTextViewText(R.id.score, getString(R.string.widget_score, homeTeamGoals, awayTeamGoals));
                if (position % 2 == 1) {
                    views.setInt(R.id.widget_list_item, "setBackgroundResource", R.color.light_grey);
                } else {
                    views.setInt(R.id.widget_list_item, "setBackgroundResource", R.color.white);
                }

                final Intent fillInIntent = new Intent();
                views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);
                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.scores_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position))
                    return data.getLong(INDEX_MATCH_ID);
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
