package br.com.gabriel.auction.test;

import br.com.gabriel.auction.builder.AuctionTestDataBuilder;
import br.com.gabriel.auction.domain.Auction;
import br.com.gabriel.auction.domain.Bid;
import br.com.gabriel.auction.domain.User;
import br.com.gabriel.auction.service.Evaluator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class EvaluatorTest {

    private Evaluator evaluator;
    private User gabriel;
    private User guina;
    private User ines;
    private Auction auction;

    @Before
    public void before() {
        this.evaluator = new Evaluator();

        this.gabriel = new User("Gabriel");
        this.guina = new User("Agnaldo");
        this.ines = new User("Ines");
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotEvaluateAuctionsWithoutBids() {

        Auction auction = new AuctionTestDataBuilder().to("iPhone 12").build();

       evaluator.evaluate(auction);

       Assert.fail();
    }

    @Test
    public void mustUnderstandBidsInAscendingOrder() {

        Auction auction = new AuctionTestDataBuilder().to("iPhone 12")
                .bid(gabriel, 150.0)
                .bid(guina, 200.0)
                .bid(ines, 250.0)
                .build();

        Evaluator evaluator = new Evaluator();
        evaluator.evaluate(auction);

        double expectedHighestBid = 250.0;
        double expectedLowestBid = 150.0;

        assertEquals(expectedHighestBid, evaluator.getHighestBid(), 0.00001);
        assertEquals(expectedLowestBid, evaluator.getLowestBid(), 0.00001);
    }

    @Test
    public void mustUnderstandBidsInDescendingOrder() {

        Auction auction = new AuctionTestDataBuilder().to("iPhone 12")
                .bid(ines, 250.0)
                .bid(guina, 200.0)
                .bid(gabriel, 150.0)
                .build();

        evaluator.evaluate(auction);

        double expectedHighestBid = 250.0;
        double expectedLowestBid = 150.0;

        assertEquals(expectedHighestBid, evaluator.getHighestBid(), 0.00001);
        assertEquals(expectedLowestBid, evaluator.getLowestBid(), 0.00001);
    }

    @Test
    public void mustUnderstandBidsInRandomOrder() {
        Auction auction = new AuctionTestDataBuilder().to("iPhone 12")
                .bid(gabriel, 200.0)
                .bid(guina, 450.0)
                .bid(ines, 120.0)
                .bid(guina, 700.0)
                .bid(ines, 630.0)
                .bid(gabriel, 230.0)
                .build();

        evaluator.evaluate(auction);

        double expectedHighestBid = 700.0;
        double expectedLowestBid = 120.0;

        assertEquals(expectedHighestBid, evaluator.getHighestBid(), 0.00001);
        assertEquals(expectedLowestBid, evaluator.getLowestBid(), 0.00001);
    }

    @Test
    public void mustUnderstandAuctionWithOnlyOneBid() {
        Auction auction = new AuctionTestDataBuilder().to("iPhone 12")
                .bid(gabriel, 150.0)
                .build();

        evaluator.evaluate(auction);

        assertEquals(150.0, evaluator.getHighestBid(), 0.00001);
        assertEquals(150.0, evaluator.getLowestBid(), 0.00001);
    }

    @Test
    public void mustAssertAmountAverageOfBids() {
        Auction auction = new AuctionTestDataBuilder().to("iPhone 12")
                .bid(ines, 100.0)
                .bid(guina, 200.0)
                .bid(gabriel, 300.0)
                .build();

        evaluator.getAverageBid(auction);

        double expectedAverageAmount = 200.0;

        assertEquals(expectedAverageAmount, evaluator.getAmountAverage(), 0.00001);
    }

    @Test
    public void mustFoundThreeHighestsBids() {
        Auction auction = new AuctionTestDataBuilder().to("iPhone 12")
                .bid(ines, 100.0)
                .bid(guina, 200.0)
                .bid(gabriel, 300.0)
                .bid(guina, 400.0)
                .build();

        evaluator.evaluate(auction);

        List<Bid> highestsThree = evaluator.getHighestsThree();

        assertEquals(3, highestsThree.size(), 0);
        assertEquals(400.0, highestsThree.get(0).getAmount(), 0.00001);
        assertEquals(300.0, highestsThree.get(1).getAmount(), 0.00001);
        assertEquals(200.0, highestsThree.get(2).getAmount(), 0.00001);
    }

}
