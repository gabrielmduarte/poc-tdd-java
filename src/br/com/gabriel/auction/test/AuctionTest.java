package br.com.gabriel.auction.test;

import br.com.gabriel.auction.builder.AuctionTestDataBuilder;
import br.com.gabriel.auction.domain.Auction;
import br.com.gabriel.auction.domain.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AuctionTest {

    private User eminem;
    private User whiz;
    private User noel;

    @Before
    public void before() {
        this.eminem = new User("Eminem");
        this.whiz = new User("Whiz Khalifa");
        this.noel = new User("Noel Gallagher");
    }

    @Test
    public void mustReceiveOneBid() {
        Auction auction = new AuctionTestDataBuilder().to("Macbook Pro")
                                                        .bid(noel, 2000.0)
                                                        .build();

        assertEquals(1, auction.getBids().size());
        assertEquals(2000.0, auction.getBids().get(0).getAmount(), 0.00001);
    }

    @Test
    public void mustReceiveSeveralBids() {
        Auction auction = new AuctionTestDataBuilder().to("Macbook Pro")
                                                        .bid(noel, 2000)
                                                        .bid(eminem, 2500)
                                                        .build();

        assertEquals(2, auction.getBids().size());
        assertEquals(2000.0, auction.getBids().get(0).getAmount(), 0.00001);
        assertEquals(2500.0, auction.getBids().get(1).getAmount(), 0.00001);
    }

    @Test
    public void mustNotAcceptTwoBidsInARowFromTheSameUser() {
        Auction auction = new AuctionTestDataBuilder().to("Macbook Pro")
                .bid(noel, 2000)
                .bid(noel, 2500)
                .build();

        assertEquals(1, auction.getBids().size());
        assertEquals(2000.0, auction.getBids().get(0).getAmount(), 0.00001);
    }

    @Test
    public void mustNotAcceptMoreThanFiveBidsFromTheSameUser() {
        Auction auction = new AuctionTestDataBuilder().to("Macbook Pro")
                .bid(noel, 1000)
                .bid(eminem, 2000)
                .bid(noel, 3000)
                .bid(eminem, 400)
                .bid(noel, 5000)
                .bid(eminem, 600)
                .bid(noel, 7000)
                .bid(eminem, 800)
                .bid(noel, 9000)
                .bid(eminem, 10000)
                .bid(noel, 11000)
                .build();

        assertEquals(10, auction.getBids().size());
        assertEquals(10000.0, auction.getBids().get(auction.getBids().size()-1).getAmount(), 0.00001);
    }

}
