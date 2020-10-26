package br.com.gabriel.auction.service;

import br.com.gabriel.auction.domain.Auction;
import br.com.gabriel.auction.domain.Bid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Evaluator {

    private double highestBid = Double.NEGATIVE_INFINITY;

    private double lowestBid = Double.POSITIVE_INFINITY;

    private double amountAverage;

    private List<Bid> highests;

    public void evaluate(Auction auction) {

        if(auction.getBids().size() == 0) {
            throw new RuntimeException("Isn't possible to evaluete an auction without bids.");
        }

        for(Bid bid : auction.getBids()) {
            if(bid.getAmount() > highestBid) highestBid = bid.getAmount();
            if (bid.getAmount() < lowestBid) lowestBid = bid.getAmount();
        }

        highests = new ArrayList<Bid>(auction.getBids());

        Collections.sort(highests, new Comparator<Bid>() {
            @Override
            public int compare(Bid o1, Bid o2) {
                if(o1.getAmount() < o2.getAmount()) return 1;
                if(o1.getAmount() > o2.getAmount()) return -1;
                return 0;
            }
        });
        highests = highests.subList(0, Math.min(highests.size(), 3));
    }

    public void getAverageBid(Auction auction) {

        double total = 0;

        for(Bid bid : auction.getBids()) {
            total = total + bid.getAmount();
        }

        amountAverage = total / auction.getBids().size();
    }

    public List<Bid> getHighestsThree() {
        return highests;
    }

    public double getHighestBid() {
        return highestBid;
    }

    public double getLowestBid() {
        return lowestBid;
    }

    public double getAmountAverage() {
        return amountAverage;
    }
}
