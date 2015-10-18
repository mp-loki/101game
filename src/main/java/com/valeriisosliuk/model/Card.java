package com.valeriisosliuk.model;

public enum Card {

	ACE_OF_CLUBS(Rank.ACE, Suit.CLUBS),
	_6_OF_CLUBS(Rank._6, Suit.CLUBS),
	_7_OF_CLUBS(Rank._7, Suit.CLUBS),
	_8_OF_CLUBS(Rank._8, Suit.CLUBS),
	_9_OF_CLUBS(Rank._9, Suit.CLUBS),
	_10_OF_CLUBS(Rank._10, Suit.CLUBS),
	JACK_OF_CLUBS(Rank.JACK, Suit.CLUBS),
	QUEEN_OF_CLUBS(Rank.QUEEN, Suit.CLUBS),
	KING_OF_CLUBS(Rank.KING, Suit.CLUBS),
	
	ACE_OF_DIAMONDS(Rank.ACE, Suit.DIAMONDS),
	_6_OF_DIAMONDS(Rank._6, Suit.DIAMONDS),
	_7_OF_DIAMONDS(Rank._7, Suit.DIAMONDS),
	_8_OF_DIAMONDS(Rank._8, Suit.DIAMONDS),
	_9_OF_DIAMONDS(Rank._9, Suit.DIAMONDS),
	_10_OF_DIAMONDS(Rank._10, Suit.DIAMONDS),
	JACK_OF_DIAMONDS(Rank.JACK, Suit.DIAMONDS),
	QUEEN_OF_DIAMONDS(Rank.QUEEN, Suit.DIAMONDS),
	KING_OF_DIAMONDS(Rank.KING, Suit.DIAMONDS),
	
	ACE_OF_HEARTS(Rank.ACE, Suit.HEARTS),
	_6_OF_HEARTS(Rank._6, Suit.HEARTS),
	_7_OF_HEARTS(Rank._7, Suit.HEARTS),
	_8_OF_HEARTS(Rank._8, Suit.HEARTS),
	_9_OF_HEARTS(Rank._9, Suit.HEARTS),
	_10_OF_HEARTS(Rank._10, Suit.HEARTS),
	JACK_OF_HEARTS(Rank.JACK, Suit.HEARTS),
	QUEEN_OF_HEARTS(Rank.QUEEN, Suit.HEARTS),
	KING_OF_HEARTS(Rank.KING, Suit.HEARTS),
	
	ACE_OF_SPADES(Rank.ACE, Suit.SPADES),
	_6_OF_SPADES(Rank._6, Suit.SPADES),
	_7_OF_SPADES(Rank._7, Suit.SPADES),
	_8_OF_SPADES(Rank._8, Suit.SPADES),
	_9_OF_SPADES(Rank._9, Suit.SPADES),
	_10_OF_SPADES(Rank._10, Suit.SPADES),
	JACK_OF_SPADES(Rank.JACK, Suit.SPADES),
	QUEEN_OF_SPADES(Rank.QUEEN, Suit.SPADES),
	KING_OF_SPADES(Rank.KING, Suit.SPADES);
	
	private Rank rank;
	private Suit suit;

    Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	@Override
	public String toString() {
		return rank + " of " + suit;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public Suit getSuit() {
		return suit;
	}

}
