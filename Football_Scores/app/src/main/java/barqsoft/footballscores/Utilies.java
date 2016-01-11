package barqsoft.footballscores;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yehya khaled on 3/3/2015.
 */
public class Utilies {
    public static final int SERIE_A = 357;
    public static final int PREMIER_LEGAUE = 354;
    public static final int CHAMPIONS_LEAGUE = 362;
    public static final int PRIMERA_DIVISION = 358;
    public static final int BUNDESLIGA = 351;

    public static int getLeague(int league_num) {

        switch (league_num) {
            case SERIE_A:
                return R.string.seriaa;

            case PREMIER_LEGAUE:
                return R.string.premierleague;

            case CHAMPIONS_LEAGUE:
                return R.string.champions_league;

            case PRIMERA_DIVISION:
                return R.string.primeradivison;

            case BUNDESLIGA:
                return R.string.bundesliga;

            default:
                return R.string.unknown_league;

        }
    }

    public static String getMatchDay(int match_day, int league_num) {
        if (league_num == CHAMPIONS_LEAGUE) {
            if (match_day <= 6) {
                return "Group Stages, Matchday : 6";
            } else if (match_day == 7 || match_day == 8) {
                return "First Knockout round";
            } else if (match_day == 9 || match_day == 10) {
                return "QuarterFinal";
            } else if (match_day == 11 || match_day == 12) {
                return "SemiFinal";
            } else {
                return "Final";
            }
        } else {
            return "Matchday : " + String.valueOf(match_day);
        }
    }

    public static String getScores(int home_goals, int awaygoals) {
        if (home_goals < 0 || awaygoals < 0) {
            return " - ";
        } else {
            return String.valueOf(home_goals) + " - " + String.valueOf(awaygoals);
        }
    }

    public static int getTeamCrestByTeamName(Context context, String teamname) {
        if (teamname == null) {
            return R.drawable.no_icon;
        }
        if (context.getString(R.string.arsenal).equals(teamname)) {
            return R.drawable.arsenal;
        } else if (context.getString(R.string.manchester_united).equals(teamname)) {
            return R.drawable.manchester_united;
        } else if (context.getString(R.string.swansea_city).equals(teamname)) {
            return R.drawable.swansea_city_afc;
        } else if (context.getString(R.string.leicester_city).equals(teamname)) {
            return R.drawable.leicester_city_fc_hd_logo;
        } else if (context.getString(R.string.everton).equals(teamname)) {
            return R.drawable.everton_fc_logo1;
        } else if (context.getString(R.string.west_ham).equals(teamname)) {
            return R.drawable.west_ham;
        } else if (context.getString(R.string.west_bromwich_albion).equals(teamname)) {
            return R.drawable.west_bromwich_albion_hd_logo;
        } else if (context.getString(R.string.tottenham_hotspur).equals(teamname)) {
            return R.drawable.tottenham_hotspur;
        } else if (context.getString(R.string.sunderland).equals(teamname)) {
            return R.drawable.sunderland;
        } else if (context.getString(R.string.stoke_city).equals(teamname)) {
            return R.drawable.stoke_city;
        } else {
            return R.drawable.no_icon;
        }
    }

    public static String getFormattedDate(long timeInMilliSeconds) {
        Date fragmentdate = new Date(timeInMilliSeconds);
        SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
        return mformat.format(fragmentdate);
    }
}
