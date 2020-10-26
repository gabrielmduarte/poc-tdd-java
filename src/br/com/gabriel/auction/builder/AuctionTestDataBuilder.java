package br.com.gabriel.auction.builder;

import br.com.gabriel.auction.domain.Auction;
import br.com.gabriel.auction.domain.Bid;
import br.com.gabriel.auction.domain.User;

public class AuctionTestDataBuilder {
    private Auction auction;

    public AuctionTestDataBuilder to(String description) {
        this.auction =  new Auction(description);
        return this;
    }


    public AuctionTestDataBuilder bid(User user, double amount) {
        auction.propose(new Bid(user, amount));
        return this;
    }

    public Auction build() {
        return auction;
    }
}
