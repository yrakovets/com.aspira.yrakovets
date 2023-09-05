package com.aspira.yrakovets;

import com.aspira.yrakovets.data.*;
import com.aspira.yrakovets.parser.LeagueParser;
import com.aspira.yrakovets.parser.SportParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) throws IOException {
        List<Sport> sports = loadSport();
        List<LeagueWithSportName> topLeagues = selectTopLeagues(sports);
        List<Match> topMatches = topLeagues.stream()
                .map(Application::getMatch)
                .filter(Objects::nonNull)
                .filter(Application::hasBets)
                .collect(Collectors.toList());
        topMatches.forEach(Application::printMatch);
    }

    private static boolean hasBets(Match match) {
        return match.event.getMarkets() != null;
    }

    private static void printMatch(Match match) {
        System.out.println(match.sportName + ", " + match.league.getName());

        Date date = new Date(match.event.getKickoff());
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss z");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println("    " + match.event.getName() + ", " + sdf.format(date) + ", " + match.event.getId());

        for (Market market : match.event.getMarkets()) {
            System.out.println("        " + market.getName());
            for (Runner runner : market.getRunners()) {
                System.out.println("            " + runner.getName() + ", "
                        + runner.getPrice() + ", " + runner.getId());
            }
        }

    }

    private static Match getMatch(LeagueWithSportName leagueWithSportName) {
        try {
            EventContainer eventContainer = loadEventContainer(leagueWithSportName.league.getId());

            if (!eventContainer.getEvents().isEmpty()) {
                return new Match(leagueWithSportName.sportName, leagueWithSportName.league,
                        eventContainer.getEvents().get(0));
            }
        } catch (IOException ignored) {

        }
        return null;
    }

    private static EventContainer loadEventContainer(Long leagueId) throws IOException {
        //TODO make link and paramaters parsed not hardcoded, if possible
        URL linkUrl2 = new URL("https://leonbets.com/api-2/betline/events/all?ctag=en-US&league_id=" + leagueId
                + "&hideClosed=true&flags=reg,urlv2,mm2,rrc,nodup");
        HttpURLConnection con2 = (HttpURLConnection) linkUrl2.openConnection();

        return new LeagueParser(con2.getInputStream()).getEvents();
    }

    private static List<LeagueWithSportName> selectTopLeagues(List<Sport> sports) {
        return sports.stream()
                .flatMap(sport ->
                        sport.getRegions().stream().flatMap(region -> region.getLeagues().stream())
                                .filter(League::getTop)
                                .map(league -> new LeagueWithSportName(sport.getName(), league)))
                .collect(Collectors.toList());
    }

    private static List<Sport> loadSport() throws IOException {
        URL linkUrl = new URL("https://leonbets.com/api-2/betline/sports?ctag=en-US&flags=urlv2");
        HttpURLConnection con = (HttpURLConnection) linkUrl.openConnection();
        return new SportParser(con.getInputStream()).getSportList();
    }


    private static class LeagueWithSportName {
        String sportName;
        League league;

        public LeagueWithSportName(String sportName, League league) {
            this.sportName = sportName;
            this.league = league;
        }
    }

    private static class Match {
        String sportName;
        League league;
        Event event;

        public Match(String sportName, League league, Event event) {
            this.sportName = sportName;
            this.league = league;
            this.event = event;
        }
    }
}
