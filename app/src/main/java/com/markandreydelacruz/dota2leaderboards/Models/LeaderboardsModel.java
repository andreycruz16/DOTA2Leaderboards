package com.markandreydelacruz.dota2leaderboards.Models;

/**
 * Created by mark on 6/30/2017.
 */

public class LeaderboardsModel {

    private int rank;
    private String name;
    private int team_id;
    private String team_tag;
    private String country;
    private String sponsor;
    private int solo_mmr;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getTeam_tag() {
        return team_tag;
    }

    public void setTeam_tag(String team_tag) {
        this.team_tag = team_tag;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public int getSolo_mmr() {
        return solo_mmr;
    }

    public void setSolo_mmr(int solo_mmr) {
        this.solo_mmr = solo_mmr;
    }

}
